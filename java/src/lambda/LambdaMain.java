package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class LambdaMain {

    public static void main(String[] args) {
        LambdaMain mainInstance = new LambdaMain();

        //mainInstance.lambdaBase();            //람다 기본 베이스

        //mainInstance.closureByJava();         //자바에서 클로저 비슷하게 사용

        //mainInstance.collectionDefaultMethod(); //콜렉션 프레임워크에서 람다 활용

        //mainInstance.methodReference();         //메소드 참조

        mainInstance.funcCombine();                 //함수 합성
    }

    public void lambdaBase(){
        int num = 5;

        likeCallback(() -> System.out.println("콜백 비슷하게 사용가능!"));
        likeCallback(() -> {

            System.out.println("num : "+num);
            System.out.println("javascript 보단 불편하네요");
            System.out.println("그러긴 하네요");

            System.out.println("심지어 외부 변수는 final 이예요!");
            System.out.println("jdk 1.7 이하는 final 선언 안하면 아예 접근도 못해요!");     // ex) final int num = 5;

            //num = 5;  ERROR!
        });
    }

    public void likeCallback(LambdaInterface cb){
        // 함수를 넘겨 받아 원하는 시점에 호출할수 있다

        //TODO : before process
        cb.doSomeThing();
        //TODO : after process
    }

    public void closureByJava(){
        Function<Integer, Integer> func = getFunction();

        Integer result = func.apply(20);

        System.out.println("function apply result : "+result);
    }

    public Function<Integer, Integer> getFunction(){
        int num = 10;

        return n ->  n*num;
    }

    public Function getFunction2(){
        int num = 100;

        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer o) {

                //물론 람다 든 아니든 똑같이 오류가 발생한다.
                //num = 10;     ERROR!

                return null;
            }
        };
    }

    public void collectionDefaultMethod(){
        List<String> strList = new ArrayList<>();

        strList.add("one");
        strList.add("two");
        strList.add("three");
        strList.add("four");

        strList.replaceAll((s)->"number : "+s);

        strList.forEach((s)-> System.out.println(s));
    }

    public void methodReference(){
        Consumer<String> notUseReference = (s)->{
            System.out.println(s);
        };

        Consumer<String> useReference = System.out::println;

        notUseReference.accept("only Lambda!");

        useReference.accept("use Method Reference!");
    }

    public void funcCombine(){
        Function<String, String> before = (str)-> {
            System.out.println("before");
            return "before"+str;
        };

        Function<String, String> after = (str)-> {
            System.out.println("after");
            return str+"after";
        };

        Function<String, String> combine = before.andThen(after);
        String resultStr = combine.apply("문자열");

        System.out.println(resultStr);


        Predicate<String> isTrue = (str)->str.equals(Boolean.toString(true));
        Predicate<String> isFalse = (str)->str.equals(Boolean.toString(false));

        Predicate<String> onlyFalse = isTrue.and(isFalse);
        Predicate<String> onlyTrue = onlyFalse.negate();
        Predicate<String> mayBeTrue = isTrue.or(isFalse);

        boolean onlyFalseResult = onlyFalse.test("true");
        boolean onlyTrueResult = onlyTrue.test("true");
        boolean byInputResult = mayBeTrue.test("false");

        System.out.println("only false : "+onlyFalseResult);
        System.out.println("only true : "+onlyTrueResult);
        System.out.println("may be true : "+byInputResult);
    }
}
