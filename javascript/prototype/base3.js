function Foo(){}

Foo.prototype.method1 = function(){
    console.log("Foo method1");
}

var foo1 = new Foo();

Foo.prototype = {
    method : function(){
        console.log('new method');
    }
}

var foo2 = new Foo();
var foo3 = new Foo();

foo1.method1();     //Foo method1
foo2.method1;       //undefined

foo2.method();      //new method
foo1.method         //undefined

foo1.__proto__ === foo2.__proto__;  //false
foo2.__proto__ === foo3.__proto__;  //true