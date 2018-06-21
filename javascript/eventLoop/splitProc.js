//처리할 작업들을 모아놓은 큐
var procQueue = [1,2,3,4,5];

function splitProc(procArr){
    if(procArr.length>0){
        var procTarget = procArr.shift();       //처리할 배열 중 첫번째 작업을 꺼낸다.

        sleep(1000);        //처리중

        console.log("처리완료 : " + procTarget);
        
        setTimeout(splitProc.bind(this, procArr), 0);
    }
}

function nonSplitProc(procArr){
    var arrSize = procArr.length;
    
    for(let i=0;i<arrSize;i++){
        var procTarget = procArr.shift();       //처리할 배열 중 첫번째 작업을 꺼낸다.

        sleep(1000);    //처리중

        console.log("처리완료 : " + procTarget);
    }
}

function sleep(delay){
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}

splitProc(procQueue);
//nonSplitProc(procQueue);

console.log("다른 작업 진행");
