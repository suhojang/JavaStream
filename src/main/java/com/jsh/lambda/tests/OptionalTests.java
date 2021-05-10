package com.jsh.lambda.tests;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.OptionalInt;

public class OptionalTests {
    /**
     * Optional<T>
     *     1. null을 직접 다루는 것은 위험 NullPointerException
     *     2. null 체크 if문 필수. 코드가 지저분 해짐.
     */

    public static void main(String[] args){
        String str  = "abc";

        Optional<String> optVal = Optional.of(str);
        optVal = Optional.of("abc");
        //optVal = Optional.of(null);     //NullPointerException 발생
        optVal = Optional.ofNullable(null); //OK

        //null 대신 빈 Optional<T> 객체를 사용하자
        //Optional<String> optVal = null; //바람직하지 않음
        optVal  = Optional.<String>empty(); //빈객체로 초기화

        //Option<T> 객체의 값 가져오기
        optVal  = Optional.of("abc");
        String str1 = optVal.get();               //null이면 예외발생
        String str2 = optVal.orElse("Empty");    //null일때는 "Empty"으로 반환
        String str3 = optVal.orElseGet(() -> new String());    //람다식 사용가능 () -> new String()
        String str4 = optVal.orElseThrow(NullPointerException::new);    //null이면 예외발생

        if (Optional.ofNullable(str1).isPresent())      //if(str!=null)
            System.out.println("str :: "+str);

        //null이 아닐때만 작업수행
        Optional.ofNullable(str1).ifPresent((value) -> System.out.println(value));

        //활용 예제
        Optional<String> optStr = Optional.of("abcde");
        Optional<Integer> optint = optStr.map(s -> s.length());
        System.out.println("optStr :: " + optStr.get());
        System.out.println("optint :: " + optint.get());

        int result1 = Optional.of("123")
                .filter(x -> x.length() > 0)
                .map((s) -> Integer.parseInt(s))
                .get();

        int result2 = Optional.of("")
                .filter(x -> x.length() > 0)
                .map((s) -> Integer.parseInt(s))
                .orElse(-1);

        System.out.println("result1 :: " + result1);
        System.out.println("result2 :: " + result2);

        Optional.of("456").map((s) -> Integer.parseInt(s))
                .ifPresent(x -> System.out.printf("result :: %d%n", x));

        //OptionalInt 예제
        OptionalInt opt = OptionalInt.of(0);
        OptionalInt opt2 = OptionalInt.empty();

        //isPresent -> 값이 있는지 체크
        //equals -> 값 비교
        System.out.println("opt.isPresent() :: "+opt.isPresent());      //true
        System.out.println("opt2.isPresent() :: "+opt2.isPresent());    //false

        System.out.println(opt.getAsInt()); //0
        //System.out.println(opt2.getAsInt());    //NoSuchElementException
        System.out.println("opt :: " + opt);
        System.out.println("opt2 :: " + opt2);
        System.out.println("opt.equals(opt2) :: "+opt.equals(opt2));
    }
}
