package com.jsh.lambda.tests;

import java.io.File;
import java.util.stream.Stream;

public class StreamMapTests {
    public static void main(String[] args){
        System.out.println("======== Stream map(중간 연산) 활용 ===========");
        Stream<File> fileStream = Stream.of(new File("D:\\Documents\\GitHub\\lamda\\build.gradle"));
        fileStream.map(File::getName)               // Stream<File> -> Stream<String>
                .filter(s -> s.indexOf(".") != -1)  //확장자가 없는거 제외
                .map(s -> s.substring(s.indexOf(".") + 1))  //Stream<String> -> Stream<String>
                .map(String::toUpperCase)       //Stream<String> -> Stream<String>
                .distinct()                     //중복제거
                .forEach(System.out::println);  //GRADLE

        System.out.println("======== Stream peek(중간 연산) 활용 ===========");
        fileStream = Stream.of(new File("D:\\Documents\\GitHub\\lamda\\build.gradle"));
        fileStream.map(File::getName)               // Stream<File> -> Stream<String>
                .filter(s -> s.indexOf(".") != -1)  //확장자가 없는거 제외
                .peek(s -> System.out.printf("filename=%s%n", s))   //파일명 출력
                .map(s -> s.substring(s.indexOf(".") + 1))  //확장자만 추출
                .peek(s -> System.out.printf("extension=%s%n", s))   //확장자명 출력
                .forEach(System.out::println);  //최종연산 스트림을 소비

    }
}
