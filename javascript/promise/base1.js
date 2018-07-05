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