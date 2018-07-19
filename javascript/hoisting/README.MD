# hoisting.

# 1. 정의
변수 끌어올림.

# 2. 주요 내용
자바 스크립트 컴파일 시, 각 변수의 선언과 함수들(표현식으로 작성된 것은 제외)을 최상단에 위치 시키는 것과

동일한 작업을 수행한다.

```javascript
hoisting1();

function hoisting1(){
    console.log('call hoisting1');
}
```

순서상 hoisting1를 호출하고 hoisting1를 정의 했지만, 에러가 발생하지 않는다.


# 3. 주의점

### 3.1 변수는 선언만 하고 초기화는 수행하지 않는다.
```javascript
console.log(value);     //undefined

var value = 10;
```

위 소스에서 변수 `value`는 호이스팅이 이루어져 최상단에 선언한 것과 같은 효과를 갖는다. 하지만 값은 초기화가

이루어 지지 않으며, 결과적으로 아래와 같은 소스로 실행된다.

```javascript
var value;

console.log(value);     //undefined

value = 10;
```

### 3.2 함수 표현식은 제외
함수 표현식은 3.1에서 말한것과 같이 변수는 선언만 되고 안에 함수는 주입되지 않는다. 따라서 아래 소스는 함수형태가

아니라고(`undefined`니까) 에러를 발생시킨다.

```javascript

console.log(lateFn);     //undefined

lateFn();       //error!

var lateFn = function(){
    //...
}
```

### 3.3 각 context 마다 호이스팅이 이루어 진다.

```javascript
var scope = "global";

function fnForHoisting(){
    console.log(scope); //undefined;

    var scope = "inner";
}
```

`fnForHoisting`함수 안에 있는 `scope`변수는 `fnForHoisting` 내 최상단에 선언한 것과 같은 효과를 갖는다.

따라서 아래와 같은 소스로 해석된다.

```javascript
var scope = "global";

function fnForHoisting(){
    var scope;

    console.log(scope); //undefined;

    scope = "inner";
}
```

물론 함수가 많거나 함수 안에 함수라도 각 컨텍스트(실행환경. 함수)마다 호이스팅이 이루어 진다.