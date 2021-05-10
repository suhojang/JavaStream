package com.jsh.lambda.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamFinalTests {
    /**
     * 스트림의 모든 요소에 지정 된 작업을 수행 - forEach(), forEachOrdered()
     *
     * void forEach(Consumer<? super T> action);            //병렬스트림인 경우 순서가 보장되지 않음
     * void forEachOrdered(Consumer<? super T> action);     //병렬스트림인 경우 순서가 보장됨
     *
     * 스트림의 최종연산자 - 조건검사
     *
     * > 조건검사 - allMatch(), anyMatch(), noneMatch()
     * boolean allMatch(Predicate<? super T> predicate);    //모든 요소가 조건을 만족시키면 true
     * boolean anyMatch(Predicate<? super T> predicate);    //한 요소라도 조건을 만족시키면 true
     * boolean noneMatch(Predicate<? super T> predicate);   //모든 요소가 조건을 만족시키지 않으면 true
     *
     * 
     * > 조건에 일치하는 요소찾기 - findFirst(), findAny()
     * 
     * Optional<T> findFirst();     //첫번째 요소를 반환. 순차 스트림에 사용
     * Optional<T> findAny();       //아무거나 하나를 반환. 병렬 스트림에 사용
     * 
     */

    public static void main(String[] args){
        /**
         * 직렬 스트림
         */
        System.out.println("=================== 직렬 스트림 ===================");
        IntStream.range(1, 10).sequential().forEach(System.out::print);
        System.out.println();
        IntStream.range(1, 10).sequential().forEachOrdered(System.out::print);

        /**
         * 병렬 스트림
         */
        System.out.println();
        System.out.println("=================== 병렬 스트림 ===================");
        IntStream.range(1, 10).parallel().forEach(System.out::print);
        System.out.println();
        IntStream.range(1, 10).parallel().forEachOrdered(System.out::print);

        System.out.println();
        System.out.println("=================== 조건검사 ===================");

        String[] strArr = new String[]{
          "Inheritance", "Java", "Lambda", "Stream", "OptionalDouble", "IntStream", "count", "sum"
        };

        boolean noEmptyStr  = Stream.of(strArr).noneMatch(s->s.length()==0);
        System.out.println("noEmptyStr :: "+noEmptyStr);

        Optional<String> sWord  = Stream.of(strArr)
                .filter(s->s.charAt(0)=='s').findFirst();

        System.out.println("sWord :: "+sWord.get());

        System.out.println();
        System.out.println("=================== Stream<String[]>을 IntStream 으로 변환하여 reduce 사용 ===================");

        //Stream<Integer> stream  = Stream.of(strArr).map(value -> value.length());

        IntStream intStream1    = Stream.of(strArr).mapToInt(value -> value.length());
        IntStream intStream2    = Stream.of(strArr).mapToInt(value -> value.length());
        IntStream intStream3    = Stream.of(strArr).mapToInt(value -> value.length());
        IntStream intStream4    = Stream.of(strArr).mapToInt(value -> value.length());

        int count   = intStream1.reduce(0, (a, b) -> a + 1);
        int sum     = intStream2.reduce(0, (a, b) -> a + b);

        OptionalInt max = intStream3.reduce((a, b) -> Integer.max(a, b));
        OptionalInt min = intStream4.reduce((a, b)-> Integer.min(a, b));

        System.out.println("count :: "+count);
        System.out.println("sum :: "+sum);
        System.out.println("max :: "+max.orElse(0));
        System.out.println("max :: "+max.orElseGet(()->0));
        System.out.println("min :: "+min.getAsInt());
    }
}
