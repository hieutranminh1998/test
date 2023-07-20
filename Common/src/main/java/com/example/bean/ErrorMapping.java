package com.example.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the error_mapping database table.
 * 
 */
@Component
@Getter
@Setter
public class ErrorMapping {

	private String errorCode;

	private String errorMessage;

	private boolean timeout;

	private String provider;

	private String service;

}