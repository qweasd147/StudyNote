var inherit = (function(parent, child){
    var F = function(){};

    return function(parent, child){
        F.prototype = parent.prototype;
        child.prototype = new F();
        child.prototype.constructor = child;
        child.super = parent.prototype;
    }
})();

function Foo(name){
    this.name = name;
}

Foo.prototype.myName = function(){
    return this.name;
}

function Bar(name, label){
    Foo.call(this, name);
    
    this.label = label;
}

inherit(Foo, Bar);

/*
inherit 함수로 대체
Bar.prototype = Object.create(Foo.prototype);
Bar.prototype = new Foo();
Bar.prototype.constructor = Bar;
*/

Bar.prototype.myLabel = function(){
    return this.label;
}

var a = new Bar("a","obj a");

console.log(a.myName());     //"a"
console.log(a.myLabel());    //"obj a"