package com.example.api.base;

import org.springframework.http.ResponseEntity;

public interface ServiceInterface<I, O> {

	abstract ResponseEntity<O> execute(I requestBody);

}
