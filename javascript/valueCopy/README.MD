# 매개변수 전달 방식

## 1 primitive

기본적으로 `primitive`값들은 값들이 복사되어 독립적인 값들로 사용됨

```javascript
function valueCopy(value){

    value = 10;
}

var value = 1;

valueCopy(value);
console.log(value); //1
```

값이 복사 되어 함수 내 변수와 `global scope`에 있는 변수는 각각 독립적인 값이다.

## 2 합성값

그 외 합성값(`Compound Values`)은 레퍼런스 사본을 생성하여 사용된다.

```javascript
function referenceCopy(ref){
    //ref의 레퍼런스 사본값으로 핸들링한다.
    ref.a = 10;
}
var ref = { a: 1 }

referenceCopy(ref);
console.log(ref.a); //10
```

레퍼런스의 사본이 넘어가지만 이 또한 같은 곳을 참조하므로 함수 내에서 객체의 상태값이 변경하면 다른곳에서도 영향을 미친다.

주의할 점은 값의 재할당은 되지 않는다.

```javascript
function replaceRef(ref){
    ref = {a : 1000};
}

replaceRef(ref);
console.log(ref.a); //10
```

## 3 call by...

인터넷에 찾아보면 javascript의 매개변수 전달은 `call by value`, `call by reference` 어떠한 것을 택하는지에 대해 많이 나온다.
의견이 두가지로 나누는데 `primitive`는 `call by value` 그 외에는 `call by reference`라는 의견과 위의 **합성값**에서 재할당이 불가능하다는 점을 어필하면서 `call by value`만 존재한다 점이 나오게 된다. 내가 개인적으로 여러 책을 보면서 자바 스크립트 책 중에서도 정말 유명한 `인사이드 자바스크립트`에서는 js에서 매개변수 전달 방식으로 `call by value`와 `call by reference` 두가지 모두를 설명하고 있었고, 또 다른 책 `you don't know js`에서는 직접적인 선택은 안하고 그저 `value-copy`, `reference-copy`라는 용어만으로 설명을 하고 있다. 뭐 아무래도 C언어에서 포인터 개념이 완벽한 `call by reference`로 본다면 이는 js에서 설명이 불가능하니까 `call by value`만 존재하는게 맞지 않을까 가 개인적인 생각이다.