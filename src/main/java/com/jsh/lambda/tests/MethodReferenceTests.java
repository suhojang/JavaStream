package com.jsh.lambda.tests;

import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferenceTests {
    /**
     * 메소드 참조 형식
     *      종류                          람다                       메소드 참조
     * static 메소드 참조      (x) -> ClassName.method(x)        ClassName::method
     * 인스턴스 메소드 참조      (obj, x) -> obj.method(x)        ClassName::method
     * 특정 객체 인스턴스 메소드 참조    (x) -> obj.method(x)       obj::method
     */

    public static void main(String[] args){
        //간단한 문자를 숫자로 변경하는 람다식
        Function<String, Integer> function  = (s) -> Integer.parseInt(s);
        System.out.println(function.apply("100") + 200);

        //메소드 참조로 변경
        Function<String, Integer> function1 = Integer::parseInt;
        System.out.println(function.apply("100") + 200);

        //Supplier 를 이용한 람다식
        Supplier<MyClass> s     = () -> new MyClass();
        MyClass myClass = s.get();
        System.out.println(myClass);

        //Supplier 를 메소드 참조로 변경
        Supplier<MyClass> supplier  = MyClass::new;
        myClass = s.get();
        System.out.println(myClass);

        //Function 을 이용한 람다식
        Function<Integer, MyClass2>  f1 = (i) -> new MyClass2(i);
        MyClass2 myClass2 = f1.apply(100);
        System.out.println(myClass2.iv);
        System.out.println(f1.apply(200).iv);

        //Function 을 메소드 참조로 변경
        Function<Integer, MyClass2> f2  = MyClass2::new;
        MyClass2 m2 = f2.apply(100);
        System.out.println(m2.iv);
        System.out.println(f2.apply(200).iv);

        //배열을 람다식으로 선언
        Function<Integer, int[]> f3 = (i) -> new int[i];
        int[] arr   = f3.apply(100);
        System.out.println(arr.length);
        System.out.println(f3.apply(100).length);

        //배열을 메소드 참조로 변경
        Function<Integer, int[]> f4 = int[]::new;
        int[] arr2  = f4.apply(100);
        System.out.println(arr2.length);
        System.out.println(f4.apply(100).length);
    }
    private static class MyClass {
    }

    private static class MyClass2 {
        int iv;

        MyClass2(int iv){
            this.iv = iv;
        }
    }
}
