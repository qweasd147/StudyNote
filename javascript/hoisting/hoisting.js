hoisting1();

function hoisting1(){
    console.log('call hoisting1');
}

console.log(foo);


var foo = 5;

var bar = function(){
    console.log('call bar');
}