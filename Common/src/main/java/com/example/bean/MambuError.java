package com.example.bean;

public enum MambuError {
    INVALID_FIELD_LENGTH(-56, "INVALID_FIELD_LENGTH"), TIMEOUT_CONNECT_CORE_SVL(-54, "TIMEOUT_CONNECT_CORE_SVL")
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