# this.

# 1. 정의
this는 일반적으로 자기 자신을 나타낼때 사용된다.

javascript에서도 비슷하긴 하지만(function에선 해석하기 나름이긴 하다) this가 바인딩 되는 객체는

사용방법에 따라 차이가 있다.

일단 모든 내용을 간추린 표

<table>
  <thead>
    <tr><th>경우</th><th>this</th></tr>
  </thead>
  <tbody>
    <tr><td>new로 호출</td><td>새로 생성된 객체가 this</td></tr>
    <tr><td>call, apply, bind 로 명시적 bind</td><td>주어진 객체가 this. null일 시, 전역 객체</td></tr>
    <tr><td>메소드로 호출(a.b.c())</td><td>해당 컨텍스트(b)가 this</td></tr>
    <tr><td>함수로 호출</td><td>전역 객체가 this</td></tr>
  </tbody>
</table>

# 2. 주요 내용
this 바인딩은 scope와 다르게 선언시 결정 되는것이 아니라 사용 시 어떻게 어디서 사용하느냐에 따라 동적으로 결정된다.

역시 이 부분만 알고 있으면 this bind 반은 알고 들어간다. 이점을 항상 생각하고 다음 바인딩 규칙을 보면 편하다.

### 2.1 new 사용 시, 새로 생성된 객체가 this가 된다.
```javascript
function ThisBind(){

    console.log(this === window);               //false
    console.log(this instanceof ThisBind)       //true
}

var newInstance1 = new ThisBind();

var newInstance2 = new ThisBind();

console.log(newInstance1 === newInstance2);     //false
```

소스를 보면 new를 사용하여 함수를 호출 시, this는 새롭게 객체가 생성되고, 그 새롭게 생성된 객체로 this가 바인딩 되는것을

확인 할 수가 있다.

### 2.2 일반 함수 호출 시, this는 window로 바인딩 된다.
```javascript
function ThisBind(){

    console.log(this === window);               //true
    console.log(this instanceof ThisBind)       //false
}


ThisBind();
```

2.1 내용과 달라진 점은 단순 new가 있냐, 없느냐 차이밖에 없다. 하지만 내부 this는 전역 객체로 바인딩 되고,

당연히 ThisBind의 instance도 아니다.

### 2.3 call, apply, bind

나열한 메소드는 '명시적으로 this를 바인딩 하겠다.'라고 문법적으로 제공해준다.

```javascript
function ThisBind(){

    console.log(this);
}


var obj1 = {a:1}

ThisBind.call(obj1);        //{a: 1}
```

call과 apply의 차이점은 그 외 매개변수를 함수 또는 그냥 나열하느냐 차이점만 있고,

bind는 함수를 실행하는 것이 아니라 this, 매개변수를 바인딩한 새로운 함수를 반환하는 메소드이다.

### 2.4 메소드 호출

```javascript
var obj = {
    a : 10
    , callA : function(){
        console.log(this.a);
    }
    , getA : function(){
        return this.a
    }
    , innerObj : {
        a : 20
        , getA : function(){
            return this.a;
        }
        , callA : function(){
            console.log(this.a);
        }
    }
}

obj.callA();            //10
obj.innerObj.callA()    //20
```

객체에 종속된 메소드를 호출 시, this는 이전 컨텍스트에 종속된다.

obj.callA() 에서 callA 내부 this는 obj가 되고, obj.innerObj.callA() 에서는 obj.innerObj가 된다.

간단히 a.b.c() 형태로 호출 시, c의 내부 this는 b가 된다.


# 3. 새부 내용 및 주의점

### 3.1 암시적 소실

다시 한번 말하지만 this는 동적으로 결정이 된다.

위의 2.4 내용을 간추려서 다시 보면
```javascript
var obj = {
    a : 10
    , callA : function(){
        console.log(this.a);
    }
}

obj.callA();            //10

var globalCallA = obj.callA;

globalCallA();  //undefined

var a = 20;
globalCallA();  //20
```

메소드를 받아서 특정 변수에 저장 후, 호출 시 기존에 바인딩 된 this는 소실된다.

이는 this가 동적으로 바인딩 된다는 점을 다시 보여주고 있다.

### 3.2 new vs 명시적 바인딩
결론부터 말하면 new를 사용한 this bind가 더 우선순위가 높다.

```javascript
function foo(something){
    this.a = something;
}

var obj1 = {};

var bar = foo.bind(obj1);
bar(2);
console.log(obj1.a);    //2

var baz = new bar(3);
console.log(obj1.a);    //2
console.log(baz.a);     //3
```

소스를 보면 명시적으로 bind 후, 결과를 new로 호출 후, this값을 확인하였다.

bind 후, new를 사용하여 우선순위라기 보단 덮어 씌여진것이라고 생각할 수도 있겠지만,

new 결과 인스턴스를 bind할 방법이 없는것으로 알고 있다... 적어도 우선순위가 같을 수도 있을것이란 생각이 든다.

### 3.3 callback function은 함수

callback으로 넘겨준 함수는 함수로 인식하고, 이는 기본적인 함수 바인딩으로 처리되어 글로벌 객체로 바인딩 된다.

```javascript
var obj = {
    a : 10
    , callA : function(){
        console.log(this.a);
    }
}

var obj2 = {
    a:20
}

function cbTest(cb){
    var a = 30;

    cb();
}

var a = 50;

cbTest(obj.callA);      //50
```

10, 30이 아닌 50이 호출된다는 점을 확인 할 수 있다. 그래도 bind는 먹힌다.

```javascript
cbTest(obj.callA.bind(obj2))    //20
```

### 3.4 soft bind
this를 고정적으로 하드바인딩 대신, 일차적으로 바이딩 될 this가 유효한지, 글로벌 객체는 아닌지 체크 후

바인딩 할 수도 있다. 소스는 softBind.js 참고 하고 사용하는 법부터 확인하면

```javascript
var obj = {name:"obj"},
    obj2 = {name:"obj2"},
    obj3 = {name:"obj3"};

var fooOBJ = foo.softBind(obj);

fooOBJ();   //name : obj
obj2.foo = foo.softBind(obj);
obj2.foo(); //name : obj2   !!

fooOBJ.call(obj3);      //name : obj3
setTimeout(obj2.foo, 10);   //name : obj <-소프트 바인딩됨
```

setTimeOut함수에 인자값으로 메소드를 넘겨줬다. 보통때라면 내부적으로 함수로 처리하여 글로벌 객체가 this로 바인딩

되겠지만, 디폴트 객체인 obj로 바인딩 된 점을 확인 할 수 있다. (내부적으로 클로저가 사용된 것도 확인가능하다.)

### 3.5 arrow function
충격적이게도 기껏 위에서 다 설명한 것을 무시하고 this bind 하는것이 es6에서 나타났다.

화살표 함수(arrow function)는 위의 규칙을 무시하고 자기 자신을 this로 바인딩 한다.

```javascript
var testArrow = {
    a : 50
    , arrowFn : function(){
        
        setTimeout(()=>{
            console.log(this.a);       //50
        },10);
    }
    , notUseArrowFn : function(){
        var that = this;

        setTimeout(function(){
            console.log(this.a, that.a);//undefinded 50
        },10);
    }
}

testArrow.arrowFn();        //50
testArrow.notUseArrowFn();  //undefinded 50
```

콜백 함수를 사용 시, 별다른 이유가 없다면 this는 글로벌 객체로 바인딩 된다. 하지만 arrow function 사용 시,

this는 호출한 내부 객체로 바인딩 된다.

```javascript
function dummyClazz(){
    this.a = 50;

    var notUseArrowFn = function (){
        console.log(this.a);
    }

    var useArrowFn = ()=>{console.log(this.a)}

    notUseArrowFn();       //undefined
    useArrowFn();          //50
}

var _instance = new dummyClazz();
```

new를 사용하여 호출 시, 새로운 객체가 생성되어 그 객체에 this가 바인딩된다(여기 까진 똑같고 문제도 없다);

똑같이 내부 함수를 사용 시, this 바인딩은 자기 자신에 바인딩 되는것을 확인 할 수 있다.