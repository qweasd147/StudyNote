function Foo(){}

Foo.prototype.method1 = function(){
    console.log("Foo method1");
}

Foo.prototype.method2 = function(){
    console.log("Foo method2");
}

function Bar(){}

Bar.prototype = Object.create(Foo.prototype);

Bar.prototype.method2 = function(){
    console.log("Bar method2");
}

var bar = new Bar();

bar.method1();      //Foo method1
bar.method2();      //Bar method2


bar.__proto__.method1 === Foo.prototype.method1;    //true
bar.__proto__.method2 === Foo.prototype.method2;    //false