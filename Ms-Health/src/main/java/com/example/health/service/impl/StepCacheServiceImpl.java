package com.example.health.service.impl;

import com.example.health.dao.StepMonthRepository;
import com.example.health.dao.StepRepository;
import com.example.health.dto.StepRankDto;
import com.example.health.entity.Step;
import com.example.health.entity.StepMonth;
import com.example.health.entity.StepWeek;
import com.example.health.kafka.service.KafkaSendService;
import com.example.health.service.StepCacheService;
import com.example.health.ulti.AppConstant;
import com.example.health.ulti.CacheUtil;
import com.example.health.ulti.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StepCacheServiceImpl implements StepCacheService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StepRepository stepRepository;

    @Autowired
    StepMonthRepository stepMonthRepository;

    @Autowired
    KafkaSendService kafkaSendService;

    @Override
    @Cacheable(value = CacheUtil.CACHE_NAME.STEP, key = "{#customerId, #dateStr}")
    public Step getStep(String customerId, Date date, String dateStr) {
        Step step = stepRepository.getByCustomerIdAndDate(customerId, date);
        if (step == null) {
            step = new Step();
        }
        return step;
    }

    @Override
    @Cacheable(value = CacheUtil.CACHE_NAME.STEP, key = "{#step.customerId, #dateStr}")
    public Step updateStep(Step step, String dateStr) {
        return step;
    }

    @Override
    @CacheEvict(value = CacheUtil.CACHE_NAME.STEP, allEntries = true)
    @Scheduled(cron = "0 3 1 */1 * *")
    public void resetCacheStep() {
        logger.info("Empty step cache");
    }

    @Override
    @Cacheable(value = CacheUtil.CACHE_NAME.TOTAL_STEP_IN_WEEK, key = "#customerId")
    public Integer getStepInThisWeek(String customerId, Date startDate, Date endDate) {
        return stepRepository.sumStepInWeek(customerId, startDate, endDate, AppConstant.STEP_STATUS.ACTIVATE);
    }

    @Override
    @CachePut(value = CacheUtil.CACHE_NAME.TOTAL_STEP_IN_WEEK, key = "#step.customerId")
    public Integer updateStepInThisWeek(Step step, Step oldStep, String dateStr) {
        if (oldStep.getStep() == null) {
            oldStep.setStep(0);
        }
        Date startDate = DateUtil.getMondayThisWeek();
        Date endDate = DateUtil.getSundayThisWeek();
        int total = getStepInThisWeek(step.getCustomerId(), startDate, endDate) - oldStep.getStep() + step.getStep();
        if (total < 0) {
            total = 0;
        }
        StepWeek stepWeek = new StepWeek();
        stepWeek.setCustomerId(step.getCustomerId());
        stepWeek.setStep(total);
        stepWeek.setDate(dateStr);
        //save database
        kafkaSendService.send(stepWeek);
        return total;
    }

    @Override
    @CacheEvict(value = CacheUtil.CACHE_NAME.TOTAL_STEP_IN_WEEK, allEntries = true)
    @Scheduled(cron = "0 33 23 */1 * *")
    public void resetCacheStepInThisWeek() {
    }

    @Override
    @Cacheable(value = CacheUtil.CACHE_NAME.TOTAL_STEP_IN_MONTH, key = "#customerId")
    public Integer getStepInThisMonth(String customerId) {
        return stepRepository
                .sumStepInMonth(customerId, AppConstant.STEP_STATUS.ACTIVATE);
    }

    @Override
    public Integer updateStepInThisMonth(Step step, Step oldStep) {
        if (oldStep.getStep() == null) {
            oldStep.setStep(0);
        }
        String month = DateUtil.simpleDateFormatMMYY.format(new Date());
        int total = getStepInThisMonth(step.getCustomerId()) - oldStep.getStep() + step.getStep();
        if (total < 0) {
            total = 0;
        }
        StepMonth stepMonth = new StepMonth();
        stepMonth.setCustomerId(step.getCustomerId());
        stepMonth.setStep(total);
        stepMonth.setMonth(month);
        kafkaSendService.send(stepMonth);
        return total;
    }

    @Override
    @CacheEvict(value = CacheUtil.CACHE_NAME.TOTAL_STEP_IN_MONTH, allEntries = true)
    @Scheduled(cron = "0 37 23 */1 * *")
    public void resetCacheStepInThisMonth() {
    }

    @Override
    @Cacheable(value = CacheUtil.CACHE_NAME.RANK_MONTH, key = "#offset", condition = "#offset!=0")
    public List<StepRankDto> getListRank(Integer offset, Integer limit) {
        String month = DateUtil.simpleDateFormatMMYY.format(new Date());
        return stepMonthRepository.getListRank(month, offset, limit);
    }

    @CacheEvict(value = CacheUtil.CACHE_NAME.RANK_MONTH, allEntries = true)
    @Scheduled(cron = "*/20 * * * * *")
    public void resetCacheRank() {
    }

}
