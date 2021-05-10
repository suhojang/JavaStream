package com.jsh.lambda.tests;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class CollectorTests {
    public static void main(String[] args){
        Student[] stuArr    = {
          new Student("나자바", true, 1, 1, 300),
          new Student("김지미", false, 1, 1, 250),
          new Student("김자바", true, 1, 2, 100),
          new Student("이지미", false, 1, 2, 150),
          new Student("남자바", true, 2, 1, 300),
          new Student("안지미", false, 2, 1, 150),
          new Student("황지미", true, 2, 2, 200),
          new Student("이자바", false, 2, 2, 90)
        };

        System.out.println("================== 1. 단순분할(성별로 분할) ==================");
        Map<Boolean, List<Student>> stuBySex    = Stream.of(stuArr)
                .collect(partitioningBy(Student::isMale));

        List<Student> maleStudent   = stuBySex.get(true);
        List<Student> femaleStudent = stuBySex.get(false);

        System.out.println("================== maleStudent ==================");
        maleStudent.stream().forEach(System.out::println);
        System.out.println("================== femaleStudent ==================");
        femaleStudent.stream().forEach(System.out::println);

        System.out.println("================== 2. 단순분할 + 통계(성별 학생수) ==================");
        Map<Boolean, Long> stuNumBySex    = Stream.of(stuArr)
                .collect(partitioningBy(Student::isMale, counting()));

        System.out.println("남학생 수 :: "+stuNumBySex.get(true));
        System.out.println("여학생 수 :: "+stuNumBySex.get(false));

        System.out.println("================== 3. 단순분할 + 통계(성별 1등) ==================");
        Map<Boolean, Optional<Student>> topScoreBySex   = Stream.of(stuArr)
                .collect(partitioningBy(Student::isMale, maxBy(comparingInt(Student::getScore))))
                ;

        /*topScoreBySex.get(true).ifPresent(student -> {
            System.out.println("남학생 1등 :: "+student.toString());
        });*/
        //topScoreBySex.values().stream().filter(s -> s.get().isMale()).forEach(System.out::println);

        System.out.println("남학생 1등 :: "+topScoreBySex.get(true));
        System.out.println("여학생 1등 :: "+topScoreBySex.get(false));

        System.out.println("================== 4. 다중분할(성별 불합격자, 100점 이하) ==================");

        Map<Boolean, Map<Boolean, List<Student>>> failedStuBySex    = Stream.of(stuArr)
                .collect(partitioningBy(Student::isMale, partitioningBy(s -> s.getScore() <= 100)))
                ;

        List<Student> failedMaleStu = failedStuBySex.get(true).get(true);
        failedMaleStu.stream().forEach(s -> {
            System.out.println("남학생 100점 이하 :: " + s);
        });

        //failedMaleStu.stream().forEach(System.out::println);
        
        List<Student> failedFemaleStu  = failedStuBySex.get(false).get(true);
        failedFemaleStu.stream().forEach(s -> {
            System.out.println("여학생 100점 이하 :: " + s);
        });

        //failedFemaleStu.stream().forEach(System.out::println);
    }

    public static class Student{
        private String name;
        private boolean isMale;
        private int hak;    //학년
        private int ban;    //반
        private int score;

        enum Level { HIGH, MID, LOW }

        public Student(String name, boolean isMale, int hak, int ban, int score) {
            this.name = name;
            this.isMale = isMale;
            this.hak = hak;
            this.ban = ban;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public boolean isMale() {
            return isMale;
        }

        public int getHak() {
            return hak;
        }

        public int getBan() {
            return ban;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", isMale=" + isMale +
                    ", hak=" + hak +
                    ", ban=" + ban +
                    ", score=" + score +
                    '}';
        }
    }
}
