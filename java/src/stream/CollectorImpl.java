package stream;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * collector<T, A, R>
 * T -> 입력 될 타입(stream 의 제너릭 타입이 됨)
 * A -> reduce에 활용될 타입
 * R -> 반환 될 결과
 */
class CollectorImpl implements Collector<String, StringBuilder, String> {

    /**
     * 작업 결과를 저장할 공간을 제공
     * @return
     */
    @Override
    public Supplier<StringBuilder> supplier() {
        return ()->new StringBuilder();
    }

    /**
     * 스트림의 요소를 수집(collect)할 방법을 제공
     * reduce 작업
     * @return
     */
    @Override
    public BiConsumer<StringBuilder, String> accumulator() {
        return new BiConsumer<StringBuilder, String>() {
            @Override
            public void accept(StringBuilder sb, String s) {
                sb.append(s);
            }
        };
    }

    /**
     * 두 저장공간을 병합할 방법을 제공.
     * 병렬스트림인 경우 여러 쓰레드에 의해 처리된 결과를
     * 어떻게 합칠 것인가를 정의한다.
     * reduce 작업
     * @return
     */
    @Override
    public BinaryOperator<StringBuilder> combiner() {
        return StringBuilder::append;
    }

    /**
     * 결과를 최종적으로 변환할 방법을 제공.
     * 작업결과를 변환하는일이 없다면 항등 함수( (x)->x )인
     * Function.identity()를 반환
     * @return
     */
    @Override
    public Function<StringBuilder, String> finisher() {
        return new Function<StringBuilder, String>() {
            @Override
            public String apply(StringBuilder sb) {
                return sb.toString();
            }
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        // CONCURRENT -> 병렬로 처리할 수 있는 작업 일 시
        // UNORDERED -> 스트림의 요소의 순서가 유지될 필요가 없는 작업일 시
        // IDENTITY_FINISH -> finisher()가 항등 함수인 작업일 시

        //아무것도 해당 사항이 없으면
        // //Collections.emptySet();를 반환

        /*
        return Collections.unmodifiableSet(EnumSet.of(
                Characteristics.CONCURRENT
                , Characteristics.UNORDERED
        ));
        */

        return Collections.emptySet();
    }
}

