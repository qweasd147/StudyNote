# Thunk(썽크).

# 1. 정의
특정 함수를 또 다른 함수 호출부로 감싼 형태의 함수

ex)
```javascript
function foo(x, y){
    return x + y;
}

function fooThunk(){
    return foo(3, 4);
}
```

# 2. 사용하는곳
### 2.1 기본적인 지연실행

    가장 많이 활용하는곳으로 함수의 매겨변수를 채워놓고 원하는 시점이 사용하여 프로그램이 바쁠 시간대
    실행을 피하고, 사용자가 원하는 시점에 실행 시켜 결과를 보내줄수 있음


### 2.2 redux-thunk

react를 사용하다보면 기본적으로 상태관리가 필요한데 요즘 Mobx를 쓸 수가 있지만 native에 없었을 때 대표적으로

사용하는 라이브러리가 redux가 있다(요즘도 많이 쓰는걸로알고 있음). 또 이런 redux를 통해 비동기 통신을 사용해도

원하는 시점에 상태관리를 해주는 라이브러리가 대표적으로 redux-thunk와 redux-saga가 있다.

(사족으로 react의 러닝커브가 괜히 높은게 아니다... 알아야 하는게 너무 많다)

당연히 이 중 react-thunk를 알아보면 일단 여기서 `react`&`redux`를 상세히 설명할 수는 없으니까 앞뒤 짤라서

필요한 부분만 설명하면 redux에서 받아서 처리해야 할 형태는 기본적으로 `action`객체 형태로만 제한되어 있다.

ex) redux에서 처리 할 수 있는 기본적인 `action` 객체 구조
```javascript
{
    type: 'State1'
}
```

하지만 비동기 통신 시, 통신이 언제 완료 될 지도 모르니까 어느시점에 이 객체를 반환 시켜야 할줄 모르니까 이걸 한번

함수로 감싸서, 비동기 통신이 완료 된 시점에 함수를 실행(결과적으로 지연실행)하여 redux의 상태를 변화시키는 라이브러리이다.

ex) thunk를 써서 비동기 통신이 완료된 시점에 `action`객체를 반환하게 설계
```javascript
function afterAjax(){
    return {
        type : 'State1';
    }
}

//내부적으로 비동기 통신이 완료 될 시 thunk형태의 함수를 실행하여 action객체를 넘겨준다.
return afterAjax();
```