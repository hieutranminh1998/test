package com.example.health.ulti;

public class AppConstant {

    public static final String SUCCESS = "success";

    public static final class ERROR {
        public static final String CUSTOMER_NOT_FOUND = "Customer not found";
        public static final String INPUT_INVALID = "Input invalid";
    }

    public static final class HTTP_STATUS {
        public static final int SUCCESS = 200;
        public static final int BAD_REQUEST = 400;
    }

    public static final class STEP_STATUS {
        public static final String ACTIVATE = "ACTIVATE";
        public static final String INACTIVATE = "INACTIVATE";
    }
}
