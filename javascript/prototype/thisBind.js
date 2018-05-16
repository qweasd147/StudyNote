function Foo(name){
    this.name = name;
}

Foo.prototype.getName = function(){
    return this.name;
}

Foo.prototype.setName = function(name){
    this.getName = name;
}

var foo = new Foo("name");

foo.getName();              //name;
foo.__proto__.getName();    //undefined