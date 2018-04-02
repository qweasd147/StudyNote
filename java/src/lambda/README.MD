# Lambda.

# 1. Lambda 기본 사용법
### 1.1 메소드 표현식을 ()=>{} 형태로 사용함
    
### 1.2 FunctionalInterface
java에선 기본적으로 함수 개념이 없다. 하지만 함수를 주고 받아야 하기 때문에 조금 편법을 사용한다.

interface에 하나의 메소드만 선언 해 놓고, 이 메소드의 구현하여 interface를 주고 받는 것이다. 
    
     
```java
public interface LambdaInterface {
    public void doSomeThing();
}
```

```java
public void likeCallback(LambdaInterface cb){
    // 함수를 넘겨 받아 원하는 시점에 호출할수 있다
    cb.doSomeThing();
}
```

```java
likeCallback(() -> {
    //TODO : .....

});
```

위와 같이 interface에 단 하나의 메소드만 선언하고(LambdaInterface의 doSomeThing) 사용 시,

그 메소드 부분만 정의 하고 사용 하는 쪽에서는 해당 인터페이스로 받아 그냥 사용하는 방식이다. 다른 부분은

그렇다 치더라도, 위의 'LambdaInterface'는 진짜 별 의미 없는 인터페이스가 된다.

무조건 하나의 메소드만 강제해야 되고, 여러 소스에 걸쳐 사용 할 수도 있고, 가장 큰 문제점은 확장성이다.

위의 doSomeThing은 반환값이 없고, 매개변수가 없다. 하지만 매개변수가 있고 반환값이 있다면? 또 새로운 의미없는

인터페이스를 작성하는 방식이다.  java 1.8이상부터 이렇게 별 의미없고(어디까지나 모든 소스에서 쓸수도 있는 인터페이스라는 

점에서 의미가 없다는 것이다.) 경우에 따라 많이 선언해 놓아야 하는 이런 인터페이스를 선수 쳐서 미리 선언만 해놓았다.

```java
@FunctionalInterface
public interface Function<T, R> {
    
    ....
    
    R apply(T t);
    
    ....
}
```

위의 interface는 이러한 조건을 만족하려고 1.8버전 이상부터 미리 만들어 놓은 인터페이스 이다.

제너릭을 이용하여 타입을 선언 해놓았고(T -> argument type, R -> result type) 단 하나의 메소드(apply)만 존재한다.

이렇게 단 하나의 메소드만 선언 해 놓고 위와 같이 사용 목적인 인터페이스를 컴파일에서 강제하기 위하여,

@FuntionalInterface를 선언해 놓는다.

# 2. 장점
    간단하다!(조금 더 공부해 보겠습니다.)

# 3 주의할점
### 3.1 외부 변수는 별다른 선언이 없어도 final하다. 아래 소스는 1.8 버전 기준
    
```java
public void lambdaBase(){
    int num = 5;

    callMethod(() -> {
        //num = 10;  ERROR!
    });

    callMethod(new LambdaInterface() {
        @Override
        public void doSomeThing() {
            //num = 10;  ERROR!
        }
    });
}
```

다음과 같은 소스가 있다고 했을떄, 내부에서 num을 바꾸는 행위(num=10)는 할수 없다. 이는 내부적으로

변수가 final로 선언 되어서 그러는데, 이유는 thread safe 문제 때문이라고 한다.

해당 메소드의 실행 시점을 알 수가 없기에, 아예 내부적으로도 final로 입력 받는다고 한다.

따라서 공통된 resource(위 소스에서 num)를 어디서 요청 되든 공통된 값으로 사용 가능하다.

추가적으로 자세히 알고 싶을 경우, side effect, effectively final, thread safe 등의 키워드로 검색!

참고 자료 : http://wonwoo.ml/index.php/post/1125

### 3.2 closure
java에서 closure를 사용할 수 있는데, 다른 쪽에서 설명하기도 뭐해서 람다와 같이 설명함.
혹시나 개념을 모른다면 javascript를 참고하자.
```java
public static Function getFunction(){
    int num = 10;

    return (Function<Integer, Integer>) n ->  n*num;
}
```
위 static method는 Function(Interface이다)의 구현체를 반환하는 메소드가 되겠다.

반환 된 메소드를 사용시, 내부의 num값 범위(lexical scope)를 기억하고 있다는 점에서 closure와 비슷하다고 볼수 있다.

문제점은 역시 반환하는 메소드에서 num값을 바꿀수 없다는 제한 사항이 존재한다.

혹시나 소스가 잘 이해가 안간다면 'Function'은 interface라는 점을 잘 기억하고, 추상팩토리 패턴(design pattern)을 공부하자.

### 3.3 Method Reference
준비중....