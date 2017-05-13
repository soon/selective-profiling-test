package org.jetbrains.test;

public final class Assert {
    private Assert() {
    }

    public static void notNull(Object o, String message) {
        if (o == null) {
            throw new NullPointerException(message);
        }
    }
}
