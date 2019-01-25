package com.supe.supertest.test.leakcanary;

/**
 * @Author yinzh
 * @Date 2019/1/13 10:06
 * @Description
 */
final class Preconditions {

    /**
     * Returns instance unless it's null.
     *
     * @throws NullPointerException if instance is null
     */
    static <T> T checkNotNull(T instance, String name) {
        if (instance == null) {
            throw new NullPointerException(name + " must not be null");
        }
        return instance;
    }

    private Preconditions() {
        throw new AssertionError();
    }
}
