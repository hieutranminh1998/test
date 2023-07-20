package com.example.bean.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class UserInfo {

    private String customerId;
}
