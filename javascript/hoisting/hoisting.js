hoisting1();

function hoisting1(){
    console.log('call hoisting1');
}

console.log(foo);


var foo = 5;

//bar(); ERROR! 함수 표현식은 호이스팅이 안됨

var bar = function(){
    console.log('call bar');
}