package com.jsh.lambda.tests;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTests {
    /***
     * Stream
     * 다양한 데이터 소르르 표준화 된 방법으로 다루기 위한 것
     * 
     * 기존 Collection의 List, Set, Map을 Stream으로 변환 후 처리과정을 동일하게 만듬
     *
     * 특징 : 스트림은 데이터 소스로부터 데이터를 읽기만할 뿐 변경하지 않는다(read only)
     *       스트림은 Iterator처럼 일회용이다.(필요하면 다시 스트림을 생성하여야 함)
     *       최종 연산 전까지 중간연산이 수행되지 않는다. - 지연된 연산
     *
     *       스트림은 작업을 내부 반복으로 처리한다.
     *       스트림의 작업을 병렬로 처리 - 병렬 스트림(Multi Thread)
     *       
     *       기본형 스트림 - IntStream, LongStream, DoubleStream
     *       - 오토박싱 & 언박싱의 비효율이 제거됨(Stream<Integer> 대신 IntStream 사용)
     *       - 숫자와 관련된 유용한 메소드를 Stream<T>보다 더 많이 제공
     *
     *  중간연산 Method
     *    distinct()                        중복제거
     *    filter(Predicate<T> predicate)    조건에 안 맞는 요소 제외
     *    limit(long maxSize)               스트림의 일부를 잘라낸다.
     *    skip(long n)                      스트림의 일부를 건너뛴다.
     *    peek(Consumer<T> action)          스트림의 요소에 작업 수행
     *    sorted()                          스트림의 요소를 정렬한다.
     *    sorted(Comparator<T> comparator)  스트림의 요소를 정렬한다.
     *
     *    - 스트림의 요소를 변화하는 중간연산 method
     *    map(Function<T,R> mapper)                 Stream<R>
     *    mapToDouble(ToDoubleFunction<T> mapper)   DoubleStream
     *    mapToInt(ToIntFunction<T> mapper)         IntStream
     *    mapToLong(ToLongFunction<T> mapper)       LongStream
     *
     *    flatMap(Function<T,Stream<R>> mapper)     Stream<R>
     *    flatMapToDouble(Function<T, DoubleStream> m)  DoubleStream
     *    flatMapToInt(Function<T, IntStream> m)    IntStream
     *    flatMapToLong(Function<T, LongStream> m)  LongStream
     *
     *  최종연산 method
     *    void forEach(Consumer<? super T> action);         각 요소에 지정 된 작업 수행
     *    void forEachOrdered(Consumer<? super T> action);  각 요소에 지정 된 작업 수행(병렬스트림으로 처리 할 시 순서유지)
     *
     *    long count();                                     스트림의 요소의 개수 반환
     *
     *    Optional<T> max(Comparator<? super T> comparator); 스트림의 최대값 반환
     *    Optional<T> min(Comparator<? super T> comparator); 스트림의 최소값 반환
     *
     *    Optional<T> findAny();                             스트림의 요소 하나를 반환(병렬)
     *    Optional<T> findFirst();                           스트림의 요소 하나를 반환(직렬)
     *
     *    boolean allMatch(Predicate<? super T> predicate);     주어진 조건이 모두 만족시키는 확인
     *    boolean anyMatch(Predicate<? super T> predicate);     주어진 조건이 하나라도 만족하는지 확인
     *    boolean noneMatch(Predicate<? super T> predicate);    주어진 조건이 모두 만족하지 않는지 확인
     *
     *    Object[] toArray();                               스트림의 모든 요소를 배열로 반환
     *    <A> A[] toArray(IntFunction<A[]> generator);      스트림의 모든 요소를 배열로 반환
     *
     *    Optional<T> reduce(BinaryOperator<T> accumulator);       스트림의 요소를 하나씩 줄여가면서(리듀싱) 계산한다. sum(),count() ...
     *    T reduce(T identity, BinaryOperator<T> accumulator);
     *    <U> U reduce(U identity,
     *                  BiFunction<U, ? super T, U> accumulator,
     *                  BinaryOperator<U> combiner);
     *
     *
     *    <R, A> R collect(Collector<? super T, A, R> collector);   스트림의 요소를 수집한다. 주로 요소를 그룹화 하거나 분할한 결과를 컬렉션에 담다 반환하는데 사용된다.
     *    <R> R collect(Supplier<R> supplier,
     *                   BiConsumer<R, ? super T> accumulator,
     *                   BiConsumer<R, R> combiner);
     *
     * */
    public static void main(String[] args){
        // first Example

        List<Integer> list  = Arrays.asList(1, 2, 3, 4, 5);

        Stream<Integer> intStream = list.stream();  //Collection

        Stream<String> strStream = Stream.of(new String[]{"a","b","c"});    //Array
        Stream<Integer> evenStream = Stream.iterate(0, n -> n+2);   //0,2,4,6...
        Stream<Double> randomStream = Stream.generate(Math::random);    //람다식
        IntStream intStream1 = new Random().ints(5);    //난수 스트림

        System.out.println("======= IntegerStream =======");
        intStream.distinct().limit(5).sorted().forEach(System.out::println);

        System.out.println("======= StringStream =======");
        long count  = strStream
                .filter((String s) -> !s.equals("a")) //걸러내기(중간 연산)
                .distinct() //중복제거(중간 연산)
                .sorted()   //정렬(중간 연산)
                .limit(2)   //스트림 자르기(중간 연산)
                .count();   //요소 개수(최종 연산)
                //.forEach(System.out::println);
        System.out.println("count :: "+count);

        //evenStream(무한스트림)
        System.out.println("======= evenStream(무한스트림) =======");
        evenStream.limit(10).forEach(System.out::println);

        //randomStream(무한스트림)
        System.out.println("======= randomStream(무한스트림) =======");
        randomStream.limit(10).forEach(System.out::println);


        System.out.println("======= list 정렬 =======");
        List<Integer> integerList   = Arrays.asList(3,1,5,4,2);
        List<Integer> sortedList    = integerList.stream()
                .sorted()   //리스트 정렬
                .collect(Collectors.toList());  //새로운 List에 저장

        System.out.println("list :: "+integerList);
        System.out.println("sortedList :: "+sortedList);

        // Stream은 최종연산을 하고 난 후(Stream Close)에는 새로 생성하여야 한다. 아래 로직은 오류를 반환함.
        //strStream.forEach(System.out::println);
        //long numOfStr    = strStream.count();

        //지연 연산 예제 - 무한스트림
        System.out.println("======= 지연 연산 예제 - 무한스트림 =======");
        IntStream intStream2    = new Random().ints(1,46);
        final int[] idx = {0};
        intStream2
                .distinct()
                .limit(6)
                .sorted()
                .forEach(i -> {
                    System.out.print((idx[0]==0?"":",") + i);
                    idx[0]++ ;
                });

        System.out.println();

        //병렬 처리 예제
        System.out.println("======= 병렬 처리 예제 =======");
        Stream<String> stringStream = Stream.of("dd", "aaa", "CC", "cc", "b");
        int sum = stringStream.parallel()   //병렬 스트림으로 전환(속성만 변경)
                .mapToInt(s -> s.length())  //문자열의 길이(IntStream 으로 변환)
                //.boxed()    //Stream<Integer>로 boxing
                //.min()  //최소값
                //.max()  //최대값
                //.average();   //평균
                .sum(); //모든 문자열의 길이의 합


        System.out.println("sum :: "+sum);

        //Map의 Stream처리
        System.out.println("======= Map의 Stream처리 =======");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key3","value3");
        params.put("key1","value1");
        params.put("key2","value2");

        Stream<Map<String,Object>> mapStream   = Stream.of(params);
        mapStream
                .distinct()
                .sorted()
                .forEachOrdered(
                        (i) -> {
                            i.forEach((key, value) -> System.out.println("key::" + key + ", vlaue::" + value));
                        }
                );

        intStream    = list.stream();
        intStream.forEach(System.out::println);
        //스트림은 1회용 이므로 오류 발생
        //intStream.forEach(System.out::println); //에러. 스트림이 이미 닫혔다.
    }
}
