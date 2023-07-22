package com.example.health.ulti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReponseData<T> {

    int code;
    T data;
    String message;
}
