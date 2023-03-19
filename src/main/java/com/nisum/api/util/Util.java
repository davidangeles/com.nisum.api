package com.nisum.api.util;

import java.util.UUID;

public class Util {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[cl/CL]{2,})$";

    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d{2,})\\S{4,}$";

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

}
