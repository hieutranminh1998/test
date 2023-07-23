package com.example.health.service.impl;

import com.example.health.dao.StepMonthRepository;
import com.example.health.dao.StepRepository;
import com.example.health.dao.StepWeekRepository;
import com.example.health.dto.*;
import com.example.health.entity.Step;
import com.example.health.entity.StepMonth;
import com.example.health.entity.StepWeek;
import com.example.health.form.StepForm;
import com.example.health.service.CustomerService;
import com.example.health.service.StepCacheService;
import com.example.health.service.StepService;
import com.example.health.ulti.AppConstant;
import com.example.health.ulti.DateUtil;
import com.example.health.ulti.StringUtil;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    CustomerService customerService;

    @Autowired
    StepRepository stepRepository;

    @Autowired
    StepCacheService stepCacheService;

    @Autowired
    StepWeekRepository stepWeekRepository;

    @Autowired
    StepMonthRepository stepMonthRepository;

    @Override
    public StepDto addStep(StepForm stepForm) {
        CustomerDto customerDto = customerService.getCustomer(stepForm.getCustomerId());
        if (customerDto == null) {
            throw new ApiException(AppConstant.ERROR.CUSTOMER_NOT_FOUND);
        }
        String dateStr = DateUtil.simpleDateFormatDDMMYY.format(stepForm.getDate());
        //check exits null ? new : step
        Step oldStep = stepCacheService.getStep(stepForm.getCustomerId(), stepForm.getDate(), dateStr);
        Step step = setParam(stepForm, oldStep);
        //update cache
        stepCacheService.updateStep(step, dateStr);
        stepCacheService.updateStepInThisWeek(step, oldStep, dateStr);
        stepCacheService.updateStepInThisMonth(step, oldStep);

        stepRepository.save(step);
        StepDto output = new StepDto();
        BeanUtils.copyProperties(step, output);
        return output;
    }

    @Override
    public TotalStepWeekDto getStepInThisWeek(String customerId) {
        if (StringUtil.isEmpty(customerId)) {
            throw new ApiException(AppConstant.ERROR.INPUT_INVALID);
        }
        CustomerDto customerDto = customerService.getCustomer(customerId);
        if (customerDto == null) {
            throw new ApiException(AppConstant.ERROR.CUSTOMER_NOT_FOUND);
        }
        Date startDate = DateUtil.getMondayThisWeek();
        Date endDate = DateUtil.getSundayThisWeek();
        Integer step = stepCacheService.getStepInThisWeek(customerId, startDate, endDate);


        return setParam(customerId, step, startDate);
    }

    @Override
    public TotalStepMonthDto getStepInThisMonth(String customerId) {
        if (StringUtil.isEmpty(customerId)) {
            throw new ApiException(AppConstant.ERROR.INPUT_INVALID);
        }
        CustomerDto customerDto = customerService.getCustomer(customerId);
        if (customerDto == null) {
            throw new ApiException(AppConstant.ERROR.CUSTOMER_NOT_FOUND);
        }
        Integer step = stepCacheService.getStepInThisMonth(customerId);
        return setParam(customerId, step);
    }

    @Override
    public void saveStepWeek(StepWeek stepWeek) {
        Date startDate = DateUtil.getMondayThisWeek();
        Date endDate = DateUtil.getSundayThisWeek();
        Integer total = stepRepository.sumStepInWeek(stepWeek.getCustomerId(), startDate, endDate, AppConstant.STEP_STATUS.ACTIVATE);

        StepWeek data = stepWeekRepository.findByCustomerIdAndDate(stepWeek.getCustomerId(), stepWeek.getDate());
        if(data == null){
            data = new StepWeek();
        }
        data.setDate(stepWeek.getDate());
        data.setStep(total);
        data.setCustomerId(stepWeek.getCustomerId());
        data.setStatus(AppConstant.STEP_STATUS.ACTIVATE);
        data.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        stepWeekRepository.save(data);
    }

    @Override
    public void saveStepMonth(StepMonth stepMonth) {
        Integer total = stepRepository.sumStepInMonth(stepMonth.getCustomerId(), AppConstant.STEP_STATUS.ACTIVATE);
        StepMonth data = stepMonthRepository.findByCustomerIdAndMonth(stepMonth.getCustomerId(), stepMonth.getMonth());
        if(data == null){
            data = new StepMonth();
        }
        data.setMonth(stepMonth.getMonth());
        data.setStep(total);
        data.setCustomerId(stepMonth.getCustomerId());
        data.setStatus(AppConstant.STEP_STATUS.ACTIVATE);
        data.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        stepMonthRepository.save(data);
    }

    @Override
    public List<StepRankDto> getListRank(Integer offset, Integer limit) {
        String month = DateUtil.simpleDateFormatDDMM.format(new Date());
        return stepMonthRepository.getListRank(month, offset, limit);
    }

    private Step setParam(StepForm stepForm, Step oldStep) {
        Step step = new Step();
        BeanUtils.copyProperties(stepForm, step);
        step.setId(oldStep.getId());
        step.setStatus(AppConstant.STEP_STATUS.ACTIVATE);
        step.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        step.setCreatedAt(oldStep.getCreatedAt());
        return step;
    }

    private TotalStepWeekDto setParam(String customerId, Integer step, Date date) {
        TotalStepWeekDto dto = new TotalStepWeekDto();
        dto.setCustomerId(customerId);
        dto.setStep(step);
        dto.setWeek(DateUtil.simpleDateFormatDDMM.format(date));
        return dto;
    }

    private TotalStepMonthDto setParam(String customerId, Integer step) {
        TotalStepMonthDto dto = new TotalStepMonthDto();
        dto.setCustomerId(customerId);
        dto.setStep(step);
        dto.setMonth(DateUtil.simpleDateFormatMMYY.format(new Date()));
        return dto;
    }


}
