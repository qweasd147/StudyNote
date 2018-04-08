//scope
function innerScope1(){
    var foo = 10;

    console.log(foo);
}


var foo = 30;

console.log(foo);   //30

innerScope1();       //10

console.log(foo);   //다시 30

function innerScope2(){
    console.log(bar);
}

var bar = 50;
innerScope2();      //50