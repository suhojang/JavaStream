package com.jsh.lambda.tests;

import com.jsh.lambda.function.Myfunction;
import com.jsh.lambda.function.ThreadFunction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class FunctionalTest {
    public static void execute(ThreadFunction function){
        function.run();
    }

    public static ThreadFunction getThreadFunction(){
        return () -> System.out.println("f3.run()");
    }

    public static void main(String[] args){
        /******************* 함수형 인터페이스를 이용한 Lambda식  ***********************/
        Myfunction f    = (a, b) -> a > b ? a : b;
        int value       = f.max(3,5);

        System.out.println("value :: " + value);

        /********************** Collections 를 이용한 Lambda식 **********************/
        List<String> list   = Arrays.asList("aaa", "bbb", "ccc");
        //Comparator는 @FunctionalInterface로 선언되어 함수형 인터페이스
        Collections.sort(list, (s1, s2) -> s2.compareTo(s1));

        System.out.println("list :: " + list);

        /********************** 함수형 인터페이스 활용 **********************/
        //Lambda식으로 ThreadFunction run() 구현
        ThreadFunction function1    = () -> System.out.println("f1.run()");

        //익명 클래스를 활용하여 run() 구현
        ThreadFunction function2    = new ThreadFunction() {
            @Override
            public void run() {
                System.out.println("f2.run()");
            }
        };

        ThreadFunction function3    = () -> System.out.println("f3.run()");

        execute(function1);
        execute(function2);
        execute(function3);


    }
}
