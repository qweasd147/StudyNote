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

정상적인 처리는 resolve를 실행 하여 인자로 값을 넘기면 then의 첫번째 함수에서 callback을 구현하여 데이터를 받을 수 있다.

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

resolve는 정상적인 처리라면 reject는 명시적으로 에러를 발생시킨다고 생각하면 된다. resolve가 아닌 reject를 호출 시

then의 callbaclk이 아닌 catch의 callback이 실행된다.

### 2.3 Promise.all

### 2.4 Promise.race

### 2.5 Promise.resolve()

### 2.6 Promise.reject()

# 3. 체이닝하여 사용

### 3.1 체이닝 시 실행 순서

# 4. es8 async await

# 5. 주의사항

### 5.1 pending 상태 이후,  resolved 또는 rejected 상태로 변경 시 다른 상태로 변경되지 않는다.

### 5.2 파라미터는 단일값

### 5.3 thenable
