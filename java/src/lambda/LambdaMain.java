package lambda;

import java.util.function.Function;

public class LambdaMain {

    public static void main(String[] args) {
        LambdaMain mainInstance = new LambdaMain();

        mainInstance.lambdaBase();
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
        cb.doSomeThing();
    }

    public void closureByJava(){
        Function<Integer, Integer> func = getFunction();

        Integer result = func.apply(10);

    }

    public static Function getFunction(){
        int num = 10;

        return (Function<Integer, Integer>) n ->  n*num;
    }

    public static Function getFunction2(){
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
}
