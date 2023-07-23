package com.example.health.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StepRankDto {

    List<Rank> listRankDay;

    List<Rank> listRank;
}
