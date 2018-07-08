Promise.resolve("start")
.then(function first(data){

    //console.log(data);  //start

    throw new Error("first ERROR");  //jump catch

    return "end first";
})
.then(function second(data){
    //console.log(end first);  //second

    //throw new Error("second ERROR!");

    return "end second"
})
.catch(function(data){
    console.log(data.message);  //first ERROR
})
.then(function(){
    console.log('end');
});


var p1 = Promise.resolve("test");

var p2 = p1.then(function(){});
var p3 = p1.catch(function(){});

console.log(p1 === p2);     //false
console.log(p1 === p3);     //false