package stream;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class StreamMain {
    public static void main(String[] args) {
        StreamMain mainInstance = new StreamMain();

        mainInstance.baseStream();        //스트림 기본

        //mainInstance.streamOperator();    //스트림 중간처리, 최종 처리

        //mainInstance.parallelStream();      //병렬 처리
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
        strToStream.forEach(System.out::println);
        arrToStream.forEach(System.out::println);
        colToStream.forEach(System.out::println);

        colToStream = list.stream();
        colToStream
                .filter(s->s.equals("one"))
                .forEach(System.out::println);

        list.forEach(System.out::println);

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

        userVoStream.peek(System.out::println); // 아무것도 출력되지 않음

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
}
