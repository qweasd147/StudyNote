# Generator.

# 1. 정의
javascript에서 기본적인 함수의 완전 실행이 아닌 중간중간 `generator` 함수 호출부와 메세징(통신)이 가능한 함수

# 2. 주요 내용

## 2.1 실행 범위
`generator`를 실행 후, `next()` 함수를 실행 시 `yield`를 만나기 직전 or `return` 까지 실행. 

```javascript
function *foo(){
    console.log('point 1');
    yield;
    console.log('point 2');
}

var _it = foo();

_it.next(); //point 1
_it.next(); //point 2
```

## 2.2 값을 주고 받을 수 있음

```javascript
function *foo(){
    var _foo = 10;
    yield _foo;
    console.log('end');
}

var _fooIt = foo();

var firstNext = _fooIt.next();

console.log(firstNext.value); // 10
_fooIt.next();  //end
```