# Event Loop.

# 1. 목적

javascript 싱글 스레드 환경에서 어떻게 이벤트 처리가 이루어지는지 확인하는 목적으로, 

여러 처리가 들어올 시, 어떻게 처리되는지 흐름을 확인한다.


# 2. 주요 용어
### 2.1 Web API

브라우저에서 제공되는 API로 Ajax나 setTimeout 등의 비동기 작업을 처리한다. 

### 2.2 Task Queue, Event Loop

Task Queue(또는 callback queue) 큐 형태의 공간에 Web API로부터 비동기 작업이 완료 시 callback 함수를 넘겨받아

저장한다. 저장된 callback 함수들은 javascript engine에 존재하는 call stack에 현재 실행중인 작업이 존재하는지 확인하고,

없을 시, call stack으로 넘겨줘 처리를 요청한다. Task Queue에 존재하는 callback 함수들을 관리하고 call stack에 옮기는

작업은 Event Loop가 담당한다. Event Loop는 데몬 형태로, 계속해서 반복해서 작업을 실행한다(Loop).

```javascript
    //가상으로 만든 Event Loop의 처리 로직
    while(true){
        if(hasCBFromTastQueue()){
            //task queue에 callback이 존재 시 처리
        }
    }
```

# 3. 처리과정 정리

### 3.1 Callback 함수가 포함된 비동기 함수 실행 (Call Stack에 저장)
### 3.2 Call Stack은 비동기작업 처리를 위해 Web API로 넘김
### 3.3 Web API에서 비동기 작업 완료시 callback 함수를 Task Queue로 넘긴다.
### 3.4 Event Loop는 Task Queue로 저장된 함수(callback)를 현재 Call Stack이 비어있는지 확인한다.
### 3.5 Call Stack이 비어있으면 Call Stack으로 넘겨 처리를 요청한다(만약 비어있지 않으면 대기).

# 4. 분할처리

`async function`을 제외하고 자바스크립트에는 하나의 함수를 실행하면 그 함수가 끝날때까지 다른 작업을 할 수가 없다.

하지만 event loop 특성을 이용하면 작업을 나누어 처리가 가능하다. 마치 round robin과 비슷한 효과를 볼 수가 있다.

    round robin은 시간을 단위로 나누어 처리하지만 설명할 방법은 작업(function)을 단위로 나누어 처리하기 때문에 개념 자체가
    다르지만 말하고 싶은건 일단 하나의 프로세서로 여러 작업 동시에 하는 것처럼 보이는 효과를 말한것이다.

```javascript
function nonSplitProc(procArr){
    var arrSize = procArr.length;
    
    for(let i=0;i<arrSize;i++){
        var procTarget = procArr.shift();       //처리할 배열 중 첫번째 작업을 꺼낸다.

        sleep(1000);    //처리중

        console.log("처리완료 : " + procTarget);
    }
}
var procQueue = [1,2,3,4,5];
nonSplitProc(procQueue);

console.log("TODO : 다른 작업 진행");
/*
처리완료 : 1
처리완료 : 2
처리완료 : 3
처리완료 : 4
처리완료 : 5
TODO : 다른 작업 진행
*/
```

`nonSplitProc`함수가 종료 될때까지 다른작업은 처리되지 않는다. 만약 해당 함수에 많은양의 배열을 입력받고, 처리

해야할 시간이 길면(소스에서 `sleep`은 처리 시간을 말한것) 전부다 처리될때까지 다른작업은 진행 할 수가 없게된다.

```javascript
function splitProc(procArr){
    if(procArr.length>0){
        var procTarget = procArr.shift();       //처리할 배열 중 첫번째 작업을 꺼낸다.

        sleep(1000);        //처리중

        console.log("처리완료 : " + procTarget);
        
        setTimeout(splitProc.bind(this, procArr), 0);
    }
}

var procQueue = [1,2,3,4,5];
splitProc(procQueue);

console.log("다른 작업 진행");
/*
처리완료 : 1
다른 작업 진행
처리완료 : 2
처리완료 : 3
처리완료 : 4
처리완료 : 5
*/
```

비동기작업은 Web API를 통해서 Task Queue로 옮기고, event loop가 call stack이 비어있는지 확인하고 비어있으면

call stack으로 callback 함수를 옮겨 처리를 한다고 했다. 이 과정을 이용해서 일정량의 작업만 처리하고 setTimeoue을

사용해서 일단 Web API로 보내 처리를 미루고, 먼저 다른 작업이 있다면 처리를 하고 다시 일정량의 작업을 마저 처리를 하게된다.

따라서 위 소스에서 모든 처리를 완료하지 않았어도 다른 작업 진행이 가능한 이유다.

참고로 일반 `function`은 위 소스같은 작업을 안하면 처리될때까지 다른작업을 안하지만 `async function`은 다른 작업도 진행 할 수 있다.

```javascript

async function asyncFunc(){
    const _promise = new Promise((resolve, reject)=>{
        setTimeout(()=>{
            return resolve("END");
        },2000);
    })
    
    await _promise;

    console.log("end asyncFunc");
}

function syncFunc(){
    sleep(5000);
    console.log("end syncFunc");
}

syncFunc();
console.log("syncFunc 끝");

asyncFunc();
console.log("asyncFunc 끝???");
/*
end syncFunc
syncFunc 끝
asyncFunc 끝???
end asyncFunc
*/
```

자세한건 promise 참고!

# 5. 주의사항

### 5.1 setTimeout의 interval은 정확한 시간에 처리되지 않는다.

```javascript
    //정확히 2초 후 실행되지 않을 수 있다!
    setTimeout(func, 2000);
```

경우에 따라 정확히 2초 후 실행되지 않을 수 있다. 이유는 위에서 설명한 바와같이 2초 후 `func`은 일단

Task Queue로 넘어간다. 그 이후 Event Loop가 Call stack이 비어있는지 지속적으로 검사하는데 만약 비어있지

않다면 대기를 하게 된다. 만약 1초 후 call stack이 비어진다면 (2+1)초 후 `func`이 실행된다.

