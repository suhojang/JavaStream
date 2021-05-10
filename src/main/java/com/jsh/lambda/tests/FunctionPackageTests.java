package com.jsh.lambda.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionPackageTests {
    /**
     * java.util.function package
     *
     * Runnable                 void run()            매개변수도 없고, 반환값도 없음
     * Supplier<T> 공급자        T get()               매개변수는 없고, 반환값만 있음
     * Consumer<T> 소비자자      void accept(T t)       Supplier와 반대로 매개변수만 있고, 반환값만 없음
     * Function<T,R> 함수       R apply(T t)           일반적인 함수. 하나의 매개변수를 받아서 결과를 반환
     * Predicate<T> 조건식      boolean test(T t)      조건식을 표현하는데 사용됨. 매개변수는 하나, 반환타입은 boolean
     *
     * */

    public static void main(String[] args){
        //조건식 예제
        Predicate<String> isEmptyStr    = s -> s.length() == 0;
        String s    = "";

        if (isEmptyStr.test(s))
            System.out.println("This is an empty String.");

        Supplier<Integer> supplier            = () -> (int)(Math.random() * 100) + 1;
        Consumer<Integer> consumer            = i -> System.out.print(i + ", ");
        Predicate<Integer> predicate          = i -> i % 2 == 0;
        Function<Integer, Integer> function2  = i -> i / 10 * 10;

        System.out.println(supplier.get());

        consumer.accept(0);

        System.out.println(predicate.test(21));

        System.out.println(function2.apply(10));

        List<Integer> list  = new ArrayList<>();
        makeRandomList(supplier, list); //list를 랜덤값을 채움
        System.out.println(list);

        printEventNum(predicate, consumer, list);   //짝수를 출력
        List<Integer> newList   = doSomething(function2, list);
        System.out.println(newList);

    }

    private static <T> List<T> doSomething(Function<T, T> function2, List<T> list) {
        List<T> newList = new ArrayList<T>(list.size());

        for (T i : list)
            newList.add(function2.apply(i));

        return newList;
    }

    private static <T> void printEventNum(Predicate<T> predicate, Consumer<T> consumer, List<T> list) {
        System.out.print("[");
        for (T i : list){
            if (predicate.test(i))
                consumer.accept(i);
        }
        System.out.println("]");
    }

    public static <T> void makeRandomList(Supplier<T> s, List<T> list){
        for (int i=0;i<10;i++)
            list.add(s.get());
    }
}
