package lambda;

public class LambdaMain {

    public static void main(String[] args) {
        LambdaMain mainInstance = new LambdaMain();

        mainInstance.lambdaBase();
    }

    public void lambdaBase(){
        int num = 5;

        likeCallback(() -> System.out.println("콜백 비슷하게 사용가능!"));
        likeCallback(() -> {

            int num2=5;


            System.out.println("num : "+num);
            System.out.println("javascript 보단 불편하네요");
            System.out.println("그러긴 하네요");

        });

        likeCallback(new LambdaInterface() {
            @Override
            public void doSomeThing() {
                System.out.println(num);
            }
        });
    }

    public void likeCallback(LambdaInterface cb){
        // 함수를 넘겨 받아 원하는 시점에 호출할수 있다
        cb.doSomeThing();
    }
}
