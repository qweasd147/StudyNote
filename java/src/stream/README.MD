# Stream.

# 1. 장점
### 1.1 내부 반복처리를 진행하고, 직관적이라 이해하기 쉽다.

### 1.2 손쉬운 병렬처리
    
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