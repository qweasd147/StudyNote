function sleep(delay){
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}

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