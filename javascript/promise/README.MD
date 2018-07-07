# Promise.

# 1. 정의

비동기 작업일지라도 현재 처리할 작업과 나중에 처리할 작업을 보장해주고 완벽하진 않더라도 

callback hell을 벗어게 해주는데 많은 도움을 준다.

# 2. 주요 내용
### 2.1 Promise 기본 형태 with resolve

```javascript
function after2sec(){
    return new Promise(function(resolve){
       //비동기 작업
       setTimeout(function(){
          console.log("첫번째 비동기 작업 완료");
          resolve("첫번째 결과물");
       },2000);
    })
 }

after2sec()
.then(function(data){
    console.log(data);

    //첫번째 비동기 작업 완료
    //첫번째 결과물
});
```

가장 기본적인 형태로 setTimeout을 사용하여 비동기 작업을 할지라도 그 이후에 결과값을 받아 처리가 가능하다.

정상적인 처리는 `resolve`를 실행 하여 인자로 값을 넘기면 `then`의 첫번째 함수에서 callback을 구현하여 데이터를 받을 수 있다.

### 2.2 Promise 기본 형태 with reject

```javascript
function after2sec(){
    return new Promise(function(resolve, reject){
       //비동기 작업
       setTimeout(function(){
          console.log("첫번째 비동기 작업 완료");

          reject("첫번째 결과물 에러");
       },2000);
    })
 }

after2sec()
.then(function(data){
    console.log('then');    //실행x
    console.log(data);      //실행x
})
.catch(function(err){
    console.log('catch');   //catch
    console.log(err);       //첫번째 결과물 에러
});
```

`resolve`는 정상적인 처리라면 `reject`는 명시적으로 에러를 발생시킨다고 생각하면 된다. `resolve`가 아닌 `reject`를 호출 시

`then`의 callbaclk이 아닌 catch의 callback이 실행된다.

```javascript
function after2sec(){
    return new Promise(function(resolve, reject){
       //비동기 작업
       setTimeout(function(){
          console.log("첫번째 비동기 작업 완료");

          reject("첫번째 결과물 에러");
       },2000);
    })
 }

after2sec()
.then(function(data){
    console.log('then');    //실행x
    console.log(data);      //실행x
},function(err){
    console.log('catch');   //catch
    console.log(err);       //첫번째 결과물 에러
});
```
위 소스처럼 `catch`를 사용하지 않고 then 함수의 두번째 인자값으로 callback함수를 넘겨주고, `reject`를 실행시키면

동일한 결과를 얻을 수 있지만, 직관적으로 알 수도 있고 잡히지 않는 에러도 처리할 수 있어서 대부분의 개발자들은

`catch`를 사용하는것을 추천하고 있다. 에러 관련해는 주의사항 참고!

### 2.3 Promise.all

`Promise.all` static method는 인자값으로 promise 배열(또는 `thenable` 한 객체)를 받아 모든 `promise`가 `resolve` 또는

`reject` 상태, 즉 처리가 완료되어 pending 상태가 아닐때까지 기다린다.

```javascript
function after2sec(){
    return new Promise(function(resolve, reject){
       //비동기 작업
       setTimeout(function(){
            resolve("after2sec 결과물");
       },2000);
    });
}

function after4sec(){
    return new Promise(function(resolve, reject){
       //비동기 작업
       setTimeout(function(){
           resolve("after4sec 결과물");
       },4000);
    });
}

Promise.all([after2sec(), after4sec()])
.then(function(arrResult){
    //약 4초후 실행

    Array.isArray(arrResult) // ==> true

    arrResult.forEach(function(result){
        console.log(result);
    });
});
```

`promise.all`을 사용 시, 비동기 작업 순서를 정하지 않고 동시에 처리되며, 단지 모든 promise가 끝날때까지 기다린다.

위 소스를 보면 2초, 4초의 작업이 걸리는 비동기 작업 시, 두개의 비동기 작업을 같이 처리하여 두 작업이 끝나면 `then`으로

넘어가게 된다. `then`에서 `resolve`를 처리하는 첫번째 callback함수의 매개변수는 항상 배열 형태로 받게된다.

```javascript
after2sec()
.then(after4sec)
.then(function(result){
    //약 6초 후 실행
    console.log(result);
});
```
당연히 위 소스는 `after2sec` 작업이 끝나고 `after4sec`을 실행하므로 약 6초가 걸리게 된다.

에러 날 때를 대비하여, 똑같이 `catch`를 체이닝 하여 사용 할 수 있다.

### 2.4 Promise.race

`Promise.race` static method는 all과 반대로 모든 promise가 처리 완료가 아닌 가장 첫번째로 처리 완료가 된

promise를 처리하게 된다.

```javascript
function after2sec(){
    return new Promise(function(resolve, reject){
       //비동기 작업
       setTimeout(function(){
           resolve("after2sec 결과물");
       },2000);
    });
}

function after4sec(){
    return new Promise(function(resolve, reject){
       //비동기 작업
       setTimeout(function(){
           console.log('실행은 하지만 결과물은 무시된다.');
           resolve("after4sec 결과물");
       }, 8000);
    });
}

Promise.race([after2sec(), after4sec()])
.then(function(result){
    console.log(result);
});

//after2sec 결과물
//실행은 하지만 결과물은 무시된다.
```

위 소스는 항상 `after2sec`의 `resolve`만 실행된다. 주의할 점은 race에 밀린 `promise`의 `resolve`는

조용히 무시된다. 위 소스에서는 `after4sec`의 `setTimeout`은 실행 되도, `resolve`는 실행되지 않는다.

### 2.5 Promise.resolve, Promise.reject

두 static method는 무조건 `resolved` 또는 `rejected` 상태인 `promise` 생성을 간단하게 만들 수가 있다.

```javascript
Promise.resolve("무조건 resolve")
.then(function(data){
    console.log(data);
});

Promise.reject("무조건 reject")
.catch(function(data){
    console.log(data);
});
```

primitive한 값들을 간단하게 `promise` 객체로 만들어서 처리가 가능하지만 유용할꺼 같으면서도 어디에 써야할지

잘 감은 안잡힌다....

# 3. 체이닝하여 사용

### 3.1 체이닝 시 실행 순서

### 3.2 체이닝 하여 사용시 항상 새로운 promise를 생성

# 4. es8 async await

# 5. 주의사항

### 5.1 pending 상태 이후,  resolved 또는 rejected 상태로 변경 시 다른 상태로 변경되지 않는다.

### 5.2 파라미터는 단일값

### 5.3 잡히지 않는 에러

### 5.4 thenable
