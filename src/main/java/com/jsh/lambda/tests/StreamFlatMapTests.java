package com.jsh.lambda.tests;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamFlatMapTests {
    public static void main(String[] args){
        Stream<String[]> strArrStream   = Stream.of(
                new String[]{"abc", "def", "ghi"},
                new String[]{"ABC", "GHI", "JKLMN"}
        );

        //Stream<Stream<String>> strStrStrm   = strArrStream.map(Arrays::stream);
        System.out.println("======== Stream flatMap(중간 연산) 활용 ===========");
        Stream<String> strStrm  = strArrStream.flatMap(Arrays::stream);

        strStrm.map(String::toUpperCase)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        System.out.println();

        String[] lineArr    = {
          "Believe or not It is true",
          "Do or do not There is no try"
        };

        Stream<String> lineStream   = Arrays.stream(lineArr);

        lineStream.map(line -> Stream.of(line.split(" +"))) // 하나 이상의 공백
            //    .map(String::toUpperCase)
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }
}
