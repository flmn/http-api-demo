package tech.jitao.httpapidemo.util;

import java.util.UUID;

public final class UuidHelper {
    private UuidHelper() {
    }

    public static String gen() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
