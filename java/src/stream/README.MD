# Stream.

# 1. 장점
### 1.1 각 함수형 인터페이스를 모두 다 알아야 할 필요가 없어진다.

### 1.2 손쉬운 병렬처리
    
# 2. Stream 기본 사용법

### 2.1 중간연산, 최종연산
스트림 사용 시 중간연산, 최종 연산이 존재 하며, 최종 연산이 실행되면 스트림은 더이상 사용 할 수가 없다.

| 중간연산  | 설명 |
| ------------- | ------------- |
| Stream<T> distinct()  | 중복을 제거  |
| Stream<T> filter(Predicate<T> predicate)  | 조건에 안맞는 요소 제외  |
| Stream<T> limit(long maxSize)  | 스트림의 일부를 제한(개수 제한)한다.  |
| Stream<T> skip(long n)  | 스트림의 일부를 skip한다.  |
| Stream<T> peek(Consumer<T> action)  | 스트림의 요소에 작업을 수행한다. |
| Stream<T> sorted()  | 스트림의 요소를 정렬한다. |
| Stream<T> sorted(Conparator<T> comparator)  | ^ |
| Stream<R> map(Function<T,R> mapper)  | ^ |
| DoubleStream mapToDouble(ToDoubleFunction<T> mapper)| ^ |
| IntStream mapToInt(ToIntFunction<T> mapper)  | ^ |
| LongStream mapToLong(ToLongFunction<T> mapper)  | ^ |
| Stream<R> flatMap(Function<T, Stream<R>> mapper) | ^ |
| DoubleStream flatMapToDouble(Function<T, DoubleStream> m)  | ^ |
| IntStream flatMapToInt(Function<T, IntStream> m)  | ^ |
| LongStream flatMapToLong(Function<T, LongStream> m)  | ^ |

| 최종연산  | 설명 |
| ------------- | ------------- |
| void forEach(Consumer<? super T> action)  | 각 요소에 지정된 작업 수행  |
| void forEachOrdered(Consumer<? super T> action)  | 각 요소에 지정된 작업 수행  |
| long count()  | 스트림의 요소의 개수 반환  |
| Optional<T> max(Comparator<? super T> comparator) | 스트림의 최대값 반환  |
| Optional<T> min(Comparator<? super T> comparator)  | 스트림의 최소값 반환 |
| Optional<T> findAny() | 스트림의 아무거나 하나 반환 |
| Optional<T> findFirst()| 스트림의 첫번째 요소 반환 |
| boolean allMatch(Predicate<T> p)  | 조건에 모든 요소가 만족하는지 |
| boolean anyMatch(Predicate<T> p)| 조건에 하나라도 만족하는지 |
| boolean noneMatch(Predicate<T> p) | 조건에 모두 만족하지 않는지 |
| Object[] toArray() | 스트림의 모든 요소를 배열로 반환 |
| A[] toArray(IntFunction<A[]> generator) | 스트림의 모든 요소를 배열로 반환 |
| Optional<T> reduce(BinaryOperator<T> accumulator) | 스트림의 요소를 하나씩 줄여 가면서 계산한다. |
| T reduce(T identity, BinaryOperator<T> accumulator)  | 스트림의 요소를 하나씩 줄여 가면서 계산한다. |
| U reduce(U identity, BinaryOperator<U, T, U> accumulator)  | 스트림의 요소를 하나씩 줄여 가면서 계산한다. |
| BinaryOperator<U> combiner | 스트림의 요소를 하나씩 줄여 가면서 계산한다. |
| R collect(Collector<T, A, R> collector) | 스트림의 요소를 수집한다. 주로 요소를 그룹화 하거나 분할한 결과를 컬렉션에 담아 반환하는데 사용한다. |
| R collect(Supplier<R> supplier, BiConsumer<T, R> accumulator, BiConsumer<R, R> Combiner)  | 스트림의 요소를 수집한다. 주로 요소를 그룹화 하거나 분할한 결과를 컬렉션에 담아 반환하는데 사용한다. |

<table>
  <thead>
  <tr>
  <th>fsa</th><th>fsa</th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <td>One</td>
    <td>Two</td>
  </tr>
  <tr>
    <td colspan="2">Three</td>
  </tr>
  </tbody>
</table>

# 3. 주의점

### 3.1 데이터 소스를 변경하지 않음

### 3.2 일회용

### 3.3 내부 작업을 반복으로 처리