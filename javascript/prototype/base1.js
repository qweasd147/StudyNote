function Foo(name){
    this.name = name;
}

Foo.prototype.getName = function(){
    return this.name;
}

Foo.prototype.setName = function(name){
    this.name = name;
}

var foo = new Foo();

foo.setName("name1");
console.log(foo.getName());

//chaining
function Bar(){}

Bar.apply;  // 존재함
Bar.bind;   // 존재함