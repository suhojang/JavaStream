package com.jsh.lambda.tests;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class CollectorGroupTests {
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

        System.out.println("================== 1. 단순그룹화(반 별로 그룹화) ==================");
        Map<Integer, List<Student>> stuByBan    = Stream.of(stuArr)
                .collect(groupingBy(Student::getBan));

        stuByBan.values().stream().forEach(System.out::println);

        System.out.println("================== 2. 단순그룹화(성적 별로 그룹화) ==================");
        Map<Student.Level, List<Student>> stuByLevel    = Stream.of(stuArr)
                .collect(groupingBy(s -> {
                    if (s.getScore() >= 200)
                        return Student.Level.HIGH;
                    else if (s.getScore() >= 100)
                        return Student.Level.MID;
                    else
                        return Student.Level.LOW;
                }))
                ;
        TreeSet<Student.Level> keySet   = new TreeSet<>(stuByLevel.keySet());

        keySet.stream().forEach(key -> {
            System.out.println("[" + key + "]");
            System.out.println(stuByLevel.get(key));
        });


        System.out.println("================== 3. 단순그룹화 + 통계(성적 별 학생수) ==================");
        Map<Student.Level, Long> stuCntByLevel  = Stream.of(stuArr)
                .collect(groupingBy(s -> {
                    if (s.getScore() >= 200)
                        return Student.Level.HIGH;
                    else if (s.getScore() >= 100)
                        return Student.Level.MID;
                    else
                        return Student.Level.LOW;
                }, counting()));

        stuCntByLevel.keySet().stream().forEach(key -> {
            System.out.println("["+key+"]");
            System.out.println("학생 수 :: "+stuCntByLevel.get(key));
        });

        System.out.println("================== 4. 다중 그룹화(학년별, 반별) ==================");
        Map<Integer, Map<Integer, List<Student>>> stuByHakAndBan    = Stream.of(stuArr)
                .collect(groupingBy(Student::getHak, groupingBy(Student::getBan)));


        stuByHakAndBan.values().stream().forEach(hak -> {
            hak.values().stream().forEach(ban -> {
                ban.stream().forEach(System.out::println);
                System.out.println();
            });
        });

        System.out.println("================== 5. 다중 그룹화 + 통계(학년별, 반별 1등) ==================");
        Map<Integer, Map<Integer, Student>> topStuByHakAndBan   = Stream.of(stuArr)
                .collect(groupingBy(Student::getHak,
                        groupingBy(Student::getBan,
                                collectingAndThen(
                                        maxBy(Comparator.comparingInt(Student::getScore))
                                        , Optional::get
                                ))))
                ;

        topStuByHakAndBan.values().stream().forEach(ban -> {
            ban.values().stream().forEach(System.out::println);
        });

        System.out.println("================== 6. 다중 그룹화 + 통계(학년별, 반별 성적그룹) ==================");
        Map<String, Set<Student.Level>> stuByScoreGroup    = Stream.of(stuArr)
                .collect(groupingBy(s -> s.getHak() + "-" + s.getBan(),
                        mapping(s -> {
                            if (s.getScore() >= 200)
                                return Student.Level.HIGH;
                            else if (s.getScore() >= 100)
                                return Student.Level.MID;
                            else
                                return Student.Level.LOW;
                        }, toSet())
                ));

        stuByScoreGroup.keySet().stream().forEach(key -> {
            System.out.println("["+key+"] :: "+stuByScoreGroup.get(key));
        });
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
