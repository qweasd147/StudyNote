function throwErr(){
    throw new Error("ERROR!");
}

function handleError(err){
    console.log(err.message);
}

/*
//에러 처리를 handleError에서 잡지 못한다.
Promise.resolve("start")
.then(throwErr, handleError);
*/

/*
Promise.resolve("start")
.then(throwErr)
.then(null, handleError);
*/

Promise.resolve("start")
.then(throwErr)
.catch(handleError);