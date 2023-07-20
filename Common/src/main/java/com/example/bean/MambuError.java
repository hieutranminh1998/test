package com.example.bean;

import com.example.api.controller.ApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MambuError {
    INTERNAL_ERROR_CONSUMER(-2, "INTERNAL_ERROR_CONSUMER"),
    SERVICE_NOT_FOUND(-3, "SERVICE_NOT_FOUND"),
    INTERNAL_ERROR_KAFKA_EXCEPTION_NULL_VALUE(-14, "INTERNAL_ERROR_KAFKA_EXCEPTION_NULL_VALUE"),
    INTERNAL_ERROR_KAFKA_EXCEPTION_EXECUTION(-13, "INTERNAL_ERROR_KAFKA_EXCEPTION_EXECUTION"),
    INTERNAL_ERROR_KAFKA_EXCEPTION(-12, "INTERNAL_ERROR_KAFKA_EXCEPTION"),
    INTERNAL_ERROR_KAFKA_EXCEPTION_INTERRUP(-11, "INTERNAL_ERROR_KAFKA_InterruptedException"),
    INVALID_PARAMETERS(4, "INVALID_PARAMETERS"),
    MISSING_ENTITY_JSON(30, "MISSING_ENTITY_JSON")
    ;



    private final int code;
    private final String message;

    MambuError(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static MambuError fromValue(String text) {
        for (MambuError b : MambuError.values()) {
            if (String.valueOf(b.message).equals(text)) {
                return b;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return getCode() + " - " + getMessage();
    }
}