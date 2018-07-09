var p1 = new Promise(function(resolve){
    console.log("first message");

    resolve("last message");
});

p1.then(function(data){
    console.log(data);
});

console.log("second message");