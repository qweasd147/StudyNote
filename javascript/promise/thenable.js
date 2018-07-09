function fnThenAble(data){
    return {
        then : function(resolve, reject){
            if(data){
                resolve(data);
            }else{
                reject("data is falsely");
            }
        }
    }
}

/*
Promise.resolve(fnThenAble())
.then(function(data){
    console.log(data);
})
.catch(function(data){
    console.log(data);      //data is falsely
});
*/
Promise.resolve(fnThenAble("data"))
.then(function(data){
    console.log(data);      //data
})
.catch(function(data){
    console.log(data);
});
