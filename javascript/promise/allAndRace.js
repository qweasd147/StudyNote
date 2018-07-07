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
       }, 8000);
    });
}

Promise.all([after2sec(), after4sec()])
.then(function(arrResult){
    arrResult.forEach(function(result){
        console.log(result);
    });
});


Promise.race([after2sec(), after4sec()])
.then(function(result){
    console.log(result);
});

