package com.jsh.lambda.tests;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class FunctionPackageTests2 {
    /**
     * BiConsumer<T,U>      void accept(T t, U u)       두 개의 매개변수만 있고, 반환값이 없음
     * BiPredicate<T,U>     boolean test(T t, U u)      조건식을 표현하는데 사용됨. 매개변수는 둘, 반환값은 boolean
     * BIFunction<T,U,R>    R apply(T t, U u)           두 개의 매개변수를 받아서 하나의 결과를 반환
     */

    public static void main(String[] args){
        BiConsumer<Integer, Integer> consumer   = (t, u) -> System.out.println("두 수의 합 : "+(t + u));
        BiPredicate<Integer, Integer> predicate = (t, u) -> t - u == 0;
        BiFunction<Integer, Integer, Integer> function  = (t, u) -> t * u;

        consumer.accept(1, 2);
        System.out.println(predicate.test(1, 1));
        System.out.println(function.apply(1, 2));

    }
}
