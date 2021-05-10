package com.jsh.lambda.function;

/**
 * 함수형 인터페이스는 단 하나의 추상메서드만 가져야 함.
 */
@FunctionalInterface
public interface Myfunction {
    public int max(int a, int b);
}
