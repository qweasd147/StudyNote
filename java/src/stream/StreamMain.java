package stream;

import sun.font.StrikeMetrics;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.*;

@SuppressWarnings("unused")
public class StreamMain {

    public static void main(String[] args) {
        StreamMain mainInstance = new StreamMain();

        //mainInstance.baseStream();        //스트림 기본

        //mainInstance.streamOperator();    //스트림 중간처리, 최종 처리

        //mainInstance.parallelStream();    //병렬 처리

        //mainInstance.streamCollect();     //스트림 수집(collect)

        //mainInstance.flatStream();        //flatMap 사용

        mainInstance.streamSort();          //스트림 정렬

        //mainInstance.streamMap();          //스트림 map 사용

        //mainInstance.collectorImpl();          //collector 구현
    }

    public void baseStream(){
        ArrayList<String> list = new ArrayList();

        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");

        //create stream
        Stream<String> strToStream = Stream.of("one", "two", "three", "four");
        Stream<String> arrToStream = Arrays.stream(new String[]{"one", "two", "three", "four"});
        Stream<String> colToStream = list.stream();


        //use stream
        strToStream.forEach(x4 -> System.out.println(x4));
        arrToStream.forEach(x3 -> System.out.println(x3));
        colToStream.forEach(x2 -> System.out.println(x2));

        colToStream = list.stream();
        colToStream
                .filter(s->s.equals("one"))
                .forEach(x1 -> System.out.println(x1));

        list.forEach(x -> System.out.println(x));

        //colToStream.forEach(System.out::println); ERROR!! 스트림은 소모성
    }

    public void streamOperator(){

        List<UserVo> mockUserList = getMockUserList();

        Stream<UserVo> userListStream = mockUserList.stream();

        //중간 처리 연산자
        Stream<UserVo> filteredStream = userListStream
                .filter(userVo -> userVo.getAge()>20)
                //.peek(System.out::println)
                .filter(userVo -> userVo.getAuth().contains("master"));

        //마지막 처리 연산자
        filteredStream.forEach(userVo -> System.out.println(userVo.getName()));

        //중간연산만 단독으로 실행 시, 실행되지 않는다.
        Stream<UserVo> userVoStream = getMockUserList().stream();

        userVoStream.peek(x -> System.out.println(x)); // 아무것도 출력되지 않음

        //스트림 연산을 하여도 원본은 변경되지 않는다.
        mockUserList.forEach((userVo)->{System.out.println(userVo.getName());});
    }

    /**
     * 병렬처리
     */
    public void parallelStream(){
        IntStream forParallelStream = IntStream.range(1, 10);
        IntStream forNormalStream = IntStream.range(1, 10);

        forParallelStream
                .parallel()
                .forEach((n)->System.out.println("parallel numbering : "+n));

        forNormalStream
                .forEach((n)->System.out.println("normal numbering : "+n));
    }

    /**
     * get mock up data
     * @return
     */
    public List<UserVo> getMockUserList(){

        List<UserVo> userList = new ArrayList<>();

        List<String> auth1 = new ArrayList<>();

        auth1.add("user1");
        auth1.add("user2");

        List<String> auth2 = new ArrayList<>(auth1);

        auth2.add("user3");
        auth2.add("user4");

        List<String> auth3 = new ArrayList<>(auth2);

        auth3.add("master");

        userList.add(new UserVo("userName1",23,"email1@test.co.kr",auth1));
        userList.add(new UserVo("userName2",38,"email2@test.co.kr",auth1));
        userList.add(new UserVo("userName3",40,"email3@test.co.kr",auth2));
        userList.add(new UserVo("userName4",31,"email4@test.co.kr",auth2));
        userList.add(new UserVo("userName5",22,"email5@test.co.kr",auth2));
        userList.add(new UserVo("userName6",19,"email6@test.co.kr",auth3));
        userList.add(new UserVo("userName7",13,"email7@test.co.kr",auth3));
        userList.add(new UserVo("userName8",38,"email8@test.co.kr",auth3));

        return userList;
    }

    public void streamCollect(){
        List<UserVo> userList = getMockUserList();

        //stream -> array
        Stream<UserVo> toArrayStream = userList.stream();

        UserVo[] resultArray = toArrayStream.toArray(UserVo[]::new);

        /* 람다 & 메소드 참조 안쓴거
        UserVo[] resultArray = toArrayStream.toArray(new IntFunction<UserVo[]>() {
            @Override
            public UserVo[] apply(int value) {
                return new UserVo[value];
            }
        });
        */

        //stream -> map
        Stream<UserVo> toMapStream = userList.stream();

        Map<String, UserVo> resultMap = toMapStream.collect(Collectors.toMap(userVo -> userVo.getName(), userVo -> userVo));

        //stream -> list
        Stream<UserVo> toListStream = userList.stream();

        List<UserVo> resultList = toListStream.collect(Collectors.toList());

        //ArrayList<UserVo> resultList = toListStream.collect(Collectors.toCollection(() -> new ArrayList<>()));    //Array List로 list 생성
        //ArrayList<UserVo> resultList = toListStream.collect(Collectors.toCollection(ArrayList::new));             //메소드 참조로 생성

        System.out.println("array");
        Arrays.stream(resultArray).forEach(System.out::println);

        System.out.println("map");
        System.out.println(resultMap);

        System.out.println("list");
        System.out.println(resultList);
    }

    public void flatStream(){

        Stream<String[]> strArrStream = Stream.of(
            new String[]{"1_1", "1_2", "1_3", "1_4", "1_5", "1_6"}
            , new String[]{"2_1", "2_2", "2_3", "2_4", "2_5", "2_6"}
            , new String[]{"3_1", "3_2", "3_3", "3_4", "3_5", "3_6"}
        );

        /*
        메소드 참조 x
        strArrStream
            .flatMap((strArr)-> Arrays.stream(strArr))
            .forEach(s -> System.out.println(s));
        */

        /*
        strArrStream
            .flatMap(Arrays::stream)
            .forEach(System.out::println);
*/
        strArrStream
            .map(Arrays::stream)
            .forEach(System.out::println);
    }

    public void streamSort(){
        IntStream intsStream = new Random().ints(30, 0, 100);

        //intsStream.sorted().forEach(System.out::println);
        intsStream.parallel().filter(value -> value>30).sorted().forEach(System.out::println);
        //intsStream.parallel().filter(value -> value>30).sorted().forEachOrdered(System.out::println);
    }

    public void streamMap(){

        Stream<String> strArrStream = Stream.of(
                "1_1", "1_2", "1_3", "1_4", "1_5", "1_6"
                , "2_1", "2_2", "2_3", "2_4", "2_5", "2_6"
                , "3_1", "3_2", "3_3", "3_4", "3_5", "3_6"
        );

        strArrStream
            .parallel()
            .filter(str -> str.indexOf("1")==0)
            .sorted()
            .map(value -> {

                Map<String, String> item = new HashMap<>();

                item.put("isFirst","true");
                item.put("itemVal",value);

                return item;
            })
            .filter(strMap -> strMap.get("itemVal")=="1_1" || strMap.get("itemVal")=="1_2" || strMap.get("itemVal")=="1_3")
            .forEach(System.out::println);
    }

    public void streamReduce(){

        Integer[] numberArr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 10};

        Stream<Integer> numberStream1 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 10});

        Integer intReuslt = numberStream1.reduce(0, (integer1, integer2) -> integer1 + integer2);

        System.out.println("integer Sum : "+intReuslt);
    }

    public void collectorImpl(){
        String[] strArr = {"111","222","333","444","555","666","777"};

        Stream<String> strSteam = Stream.of(strArr);

        String result = strSteam.collect(new CollectorImpl());

        System.out.println(result);
    }
}