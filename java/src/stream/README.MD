# Stream.

# 1. 장점
### 1.1 내부 반복처리를 진행하고, 직관적이라 이해하기 쉽다.

### 1.2 손쉬운 병렬처리

### 1.3 최종 연산이 실행될때 중간연산들을 실행한다.
  이 부분이 좋은 점이 불필요한 연산을 줄여준다는 점이다. 밑에 2.1에서 좀 더 자세히 설명
    
# 2. Stream 기본 사용법

### 2.1 중간연산, 최종연산
스트림 사용 시 중간연산, 최종 연산이 존재 하며, 최종 연산이 실행되면 해당 스트림은 더이상 사용 할 수가 없다.

일단 표부터 확인!

<table>
  <thead>
    <tr><th>중간연산</th><th>설명</th></tr>
  </thead>
  <tbody>
    <tr><td>Stream&lt;T&gt; distinct()</td><td>중복을 제거</td></tr>
    <tr><td>Stream&lt;T&gt; filter(Predicate&lt;T&gt; predicate)</td><td>조건에 안맞는 요소 제외</td></tr>
    <tr><td>Stream&lt;T&gt; limit(long maxSize)</td><td>스트림의 일부를 제한(개수 제한)한다.</td></tr>
    <tr><td>Stream&lt;T&gt; skip(long n)</td><td>스트림의 일부를 skip한다.</td></tr>
    <tr><td>Stream&lt;T&gt; peek(Consumer&lt;T&gt; action)</td><td>스트림의 요소에 작업을 수행한다.</td></tr>
    <tr><td>Stream&lt;T&gt; sorted()</td><td rowspan="2">스트림의 요소를 정렬한다.</td></tr>
    <tr><td>Stream&lt;T&gt; sorted(Conparator&lt;T&gt; comparator)</td></tr>
    <tr><td>Stream&lt;R&gt; map(Function&lt;T, R&gt; mapper)</td><td rowspan="8">스트림의 요소를 변환한다.</td></tr>
    <tr><td>DoubleStream mapToDouble(ToDoubleFunction&lt;T&gt; mapper</td></tr>
    <tr><td>IntStream mapToInt(ToIntFunction&lt;T&gt; mapper)</td></tr>
    <tr><td>LongStream mapToLong(ToLongFunction&lt;T&gt; mapper)</td></tr>
    <tr><td>Stream&lt;R&gt; flatMap(Function&lt;T, Stream&lt;R&gt;&gt; mapper)</td></tr>
    <tr><td>DoubleStream flatMapToDouble(Function&lt;T, DoubleStream&gt; m)</td></tr>
    <tr><td>IntStream flatMapToInt(Function&lt;T, IntStream&gt; m)</td></tr>
    <tr><td>LongStream flatMapToLong(Function&lt;T, LongStream&gt; m)</td></tr>
  </tbody>
</table>

<table>
  <thead>
    <tr><th>최종연산</th><th>설명</th></tr>
  </thead>
  <tbody>
    <tr><td>void forEach(Consumer&lt;? super T&gt; action)</td><td>각 요소에 지정된 작업 수행</td></tr>
    <tr><td>void forEachOrdered(Consumer&lt;? super T&gt; action)</td><td>각 요소에 지정된 작업 수행</td></tr>
    <tr><td>long count()</td><td>스트림의 요소의 개수 반환</td></tr>
    <tr><td>Optional&lt;T&gt; max(Comparator&lt;? super T&gt; comparator)</td><td>스트림의 최대값 반환</td></tr>
    <tr><td>Optional&lt;T&gt; min(Comparator&lt;? super T&gt; comparator)</td><td>스트림의 최소값 반환</td></tr>
    <tr><td>Optional&lt;T&gt; findAny()</td><td>스트림의 아무거나 하나 반환</td></tr>
    <tr><td>Optional&lt;T&gt; findFirst()</td><td>스트림의 첫번째 요소 반환</td></tr>
    <tr><td>boolean allMatch(Predicate&lt;T&gt; p)</td><td>조건에 모든 요소가 만족하는지</td></tr>
    <tr><td>boolean anyMatch(Predicate&lt;T&gt; p)</td><td>조건에 하나라도 만족하는지</td></tr>
    <tr><td>boolean noneMatch(Predicate&lt;T&gt; p)</td><td>조건에 모두 만족하지 않는지</td></tr>
    <tr><td>Object[] toArray()</td><td>스트림의 모든 요소를 배열로 반환</td></tr>
    <tr><td>A[] toArray(IntFunction&lt;A[]&gt; generator)</td><td>스트림의 모든 요소를 배열로 반환</td></tr>
    <tr><td>Optional&lt;T&gt; reduce(BinaryOperator&lt;T&gt; accumulator)</td><td rowspan="4">스트림의 요소를 하나씩 줄여 가면서 계산한다.</td></tr>
    <tr><td>T reduce(T identity, BinaryOperator&lt;T&gt; accumulator)</td></tr>
    <tr><td>U reduce(U identity, BinaryOperator&lt;U, T, U&gt; accumulator)</td></tr>
    <tr><td>BinaryOperator&lt;U&gt; combiner</td></tr>
    <tr><td>R collect(Collector&lt;T, A, R&gt; collector)</td><td rowspan="2">스트림의 요소를 수집한다.<br/>주로 요소를 그룹화 하거나 분할한<br/>결과를 컬렉션에 담아 반환하는데 사용한다.</td></tr>
    <tr><td>R collect(Supplier&lt;R&gt; supplier<br/>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ,BiConsumer&lt;T, R&gt; accumulator<br/>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;, BiConsumer&lt;R, T&gt; Combiner)</td></tr>
  </tbody>
</table>

미리 말하면 '자바의 정석' 보고 배꼇다... 그래도 markdown + table 조합때문에 작성이 힘들었다....

아무튼 중간연산은 항상 stream을 반환하는 것을 확인 할 수가 있다. 이러한 점을 활용하여 각 중간 연산을

chaining 하여 편하게 사용이 가능하다. 또한 중간연산은 최종 연산을 하여야 의미가 있으므로, 최종적으로 최종 연산

을 하지 않으면 실행되지 않는다(바꿔 말하면 최종 연산이 될때 중간연산을 실행함).
```java
public void streamOperator(){
  
  //중간연산만 단독으로 실행 시, 실행되지 않는다.
  Stream<UserVo> userVoStream = getMockUserList().stream();
  
  userVoStream.peek(System.out::println);  
}
```

위의 소스와 표를 보면 peek 메소드는 각 요소에 일정한 작업을 하기위한 '중간 연산'이다.

하지만 중간연산이라 화면에 표출되는 것은 아무것도 없다. 최종연산인 forEach로 바꾸던가 

아니면 peek 이후에 최종연산 메소드를 체이닝 하여야한다.

이러한 점이 좋은 점이유는 불필요한 연산을 줄여준다는 점이다. 예를들어 스트림 요소가 무한 스트림이고

그 중 중간연산에서 limit을 사용 후 중간연산 처리시 무한한 데이터를 전부 핸들링하는게 아니라 limit만큼

제한 후 핸들링 하는 점이라 효율적이라고 말할 수 있다.

### 2.2 병렬처리
병렬처리는 스레드를 사용 해야 할것이고 그럴때 생각 해야 할 것이 많을 것이다.

하지만 stream을 사용하여 병렬 처리 시 사용자(프로그래머)는 그냥 일반 stream 사용 시 사용하면 된다.
```java
public void parallelStream(){
    IntStream forParallelStream = IntStream.range(1, 10);

    forParallelStream
            .parallel()
            .forEach((n)->System.out.println("parallel numbering : "+n));

  }
```
위 소스와 같이 스트림을 병렬처리 스트림으로 변경(parallel) 후 일반 스트림 같이 사용하면 된다.

### 2.2 collect
가공 및 처리한 데이터를 수집. 간단히 stream을 array, collection framework 등으로 형변환 한다고 생각하면 된다.
```java
public void streamCollect(){
  
  List<UserVo> userList = getMockUserList();
  
  //stream -> array
  Stream<UserVo> toArrayStream = userList.stream();
  UserVo[] resultArray = toArrayStream.toArray(UserVo[]::new);
}
```

array로 변환 시, 그냥 toArray() 메소드를 호출하면된다. 하지만 여기서 Object 배열이 아닌 지정된 배열로 반환하기

위해서는 toArray() 메소드에 IntFunction 인터페이스를 구현해서 넘겨줘야한다.

번외로 메소드 참조에 관련해서 꽤나 부정적인 인식이 있었는데 이렇게 보니까 깔끔한것 같기는하다.

```java
public void streamCollect(){
  List<UserVo> userList = getMockUserList();
  
  Stream<UserVo> toMapStream = userList.stream();
  
  Map<String, UserVo> resultMap = toMapStream.collect(Collectors.toMap(userVo -> userVo.getName(), userVo -> userVo));
}
```
collection framework로 변환 시, Stream.collect를 사용하고, 인자값(함수 같은 메소드)으로 toMap을 넘겨주면 된다.

여기서 주의점으로 map은 (key, value)로 존재하므로 key값을 구하기 위한 Function interface,

value값을 구하기 위한 Function interface를 구현해서 넘겨줘야 한다. 소스를 보면 key 값으로 user name, 값으로 vo객체 그대로

넘겨주는 것을 확인 할 수 있다.

```java
public void streamCollect(){
  List<UserVo> userList = getMockUserList();
  
  Stream<UserVo> toListStream = userList.stream();
  
  List<UserVo> resultList = toListStream.collect(Collectors.toList());
}
```
list로 변환은 간단하다. 다른 쪽도 마찬가지지만 현재 스트림을 통해 타입 유추가 가능하므로, 그냥 리스트로 받겠다는 명령만 하면 된다.

### 2.3 map
각 스트림마다 동일한 작업을 수행한다.
```java
public void streamMap(){
  Stream<String> strArrStream = Stream.of(
    "1_1", "1_2", "1_3", "1_4", "1_5", "1_6"
    , "2_1", "2_2", "2_3", "2_4", "2_5", "2_6"
    , "3_1", "3_2", "3_3", "3_4", "3_5", "3_6"
  );

  strArrStream
    .map((s)->"prefix_"+s))
    .forEach(System.out::println);
}
```


### 2.4 flatMap
스트림의 타입이 배열 등인 경우, 핸들링 하기가 불편한 경우가 있을 수 있다. 예를 들어 Stream<String[]> 같이 배열로

스트림이 구성 된 경우 각 배열을 꺼내 모든 아이템을 문자열로 직렬화 하여 핸들링하는 경우가 더 편하게 느껴질 수도 있다.

(개인 차에 따라 무조건 편하지 않을 수도 있다.) 그런 경우 flatMap을 사용하여 말그대로 스트림의 배열을 평평하게? 만들어 줄 수도 있다.

```java
public void flatStream(){
  Stream<String[]> strArrStream = Stream.of(
    new String[]{"1_1", "1_2", "1_3", "1_4", "1_5", "1_6"}
      , new String[]{"2_1", "2_2", "2_3", "2_4", "2_5", "2_6"}
      , new String[]{"3_1", "3_2", "3_3", "3_4", "3_5", "3_6"}
  );

  strArrStream
    .flatMap(Arrays::stream)
    .forEach(System.out::println);
}
```
메소드 참조에 대해 부정적인 인식을 가지고 있지만, 일단 잘 써먹고 있다....

flatMap을 사용하여 스트림 내 배열(또는 객체)로 구성된 된 아이템을 단일 원소로 구성 할 수가 있다.

### 2.5 reduce
처음 부터 마지막 원소까지 하나씩 처리하면서 하나의 원소로 줄여가는 작업을 수행한다.

```java
public void streamReduce(){
  Integer[] numberArr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 10};

  Stream<Integer> numberStream1 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 10});

  Integer intReuslt = numberStream1.reduce(0, (integer1, integer2) -> integer1 + integer2);

  System.out.println("integer Sum : "+intReuslt);
}
```

위 소스에서 reduce 메소드에 넘겨주는 첫번째 인자값은  저장될 변수(Stream의 제너릭 타입을 따라간다)와

두번째 인자로 함수를 받아 어떻게 줄여갈지를 결정한다. 위 소스는 각 원소를 순회하면서 모든 값을 더해가는 형태이다.

참고로
첫번째 사이클 : 0+1
두번째 사이클 : 1+2
세번째 사이클 : 3+3
....

### 2.6 Collector 구현

스트림의 최종 연산 메소드 중 하나인 Stream.collect()를 사용하여 map, list, set 등의 형태로 수집하여

반환하고 싶을 경우도 있겠지만, 경우에 따라서 내가 원하는 형태의 결과로 수집되어 받고 싶은 경우도 있을 것이다.

이런 경우에 Stream.collect() 메소드의 인자값으로 Collector를 구현한 인스턴스를 넘겨주면 된다. 이것만 구현하면 

내부적으로 병렬 처리 시 생각해야 할 부분을 내부적으로 해결이 되므로 꽤나 편하다고 생각된다.

소스가 길어지므로 collectorImpl 메소드와 CollectorImpl 클래스를 참고!

참고로 CollectorImpl 클래스는 메소드(정확히 함수형 인터페이스) 표현 방법은 여러가지로 써놓았다. 모르거나

어디서 무조건 배껴온거 아니다.

위에서 말한 두 부분을 보기만하면 얼추 이해는 갈것이라고 생각되고, 추가로 내부적으로는 reduce를 사용한다는 점도

같이 기억하면 될것이다.


# 3. 주의점
### 3.1 데이터 원본을 변경하지 않음

```java
public void streamOperator(){

    List<UserVo> mockUserList = getMockUserList();

    Stream<UserVo> userListStream = mockUserList.stream();

    
    userListStream
      .filter(userVo -> userVo.getAge()>20)
      //.peek(System.out::println)
      .filter(userVo -> userVo.getAuth().contains("master"))
      .forEach(userVo -> System.out.println(userVo.getName()));

    //스트림 연산을 하여도 원본은 변경되지 않는다.
    mockUserList.forEach((userVo)->{System.out.println(userVo.getName());});
}
```

위 소스를 보면 stream으로 변경하여 User List를 필터링 작업을 수행했다. 하지만 mockUserList의 내용을 보면 

그대로 변함없이 출력 되는 것을 확인 할 수 있다.

### 3.2 일회용

```java
public void baseStream(){
    //create stream
    Stream<String> strToStream = Stream.of("one", "two", "three", "four");

    strToStream
      .filter(s->s.equals("one"))
      .forEach(System.out::println);


    //strToStream.forEach(System.out::println); ERROR!! 스트림은 소모성
}
```
스트림은 최종 연산을 수행하며 다시 재사용이 불가능하다.


### 3.3 내부 작업을 반복으로 처리

주의점이라기 보단 3.2에 있는 소스를 보면 forEach문을 사용하여 스트림에 반복된 작업 수행이 가능하다.

이는 for문을 사용하여 직접 데이터를 가져와서 작업하는 방법이 아니라, 더 간결하고 빠르게 작업이 가능하다.

### 3.4 병렬처리 thread safe
아무리 병렬 처리가 쉽다고 해도, 결국엔 병렬 처리이다. 순서가 보장 되지 않으므로 이에 따른 문제가 발생 할 수도 있다.

당장 2.2에 있는 소스만 봐도 순서가 보장 되지 않는 것을 확인 할 수가 있다.

병렬 처리 시 thread safe 관련 해서 항상 유의해야 한다(thread safe 관련 설명은 생략).

### 3.5 병렬처리 시 sort

```java
public void streamSort(){
    IntStream intsStream = new Random().ints(30, 0, 100);

    //intsStream.sorted().forEach(System.out::println);
    intsStream.parallel().filter(value -> value>30).sorted().forEach(System.out::println);
}
```

병렬처리의 문제점이다. 위 소스에서 filter 후 sort 시, sort는 모든 처리가 종료 된 후 마지막에 정렬이 되야 하지만

병렬처리라서 실행 순서를 보장 할 수가 없다. 따라서 위 소스는 filter는 정상적으로 작동 해도, sort는 정상 작동하지 않는다.

하지만 'forEach'메소드 대신 'forEachOrdered' 사용 시, 병렬 처리 여부에 상관없이 처리가 가능하지만

병렬처리로서의 이점(속도)은 줄어든다고 한다.