package com.example.bean;

import com.example.api.controller.ApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MambuError {
    INTERNAL_ERROR_CONSUMER(-2, "INTERNAL_ERROR_CONSUMER")
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