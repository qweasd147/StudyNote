if (!Function.prototype.softBind){
    Function.prototype.softBind = function(obj){
        var fn = this;

        var curried = [].slice.call(arguments, 1);
        var bound = function(){

            var that = (!this || this === (window || global))? obj : this;
            var args = curried.concat.apply(curried, arguments);

            return fn.apply(that, args);
        }

        bound.prototype = Object.create(fn.prototype);
        return bound;
    }
}

function foo(){
    console.log("name : "+this.name);
}

var obj = {name:"obj"},
    obj2 = {name:"obj2"},
    obj3 = {name:"obj3"};

var fooOBJ = foo.softBind(obj);

fooOBJ();   //name : obj
obj2.foo = foo.softBind(obj);
obj2.foo(); //name : obj2   !!

fooOBJ.call(obj3);      //name : obj3
setTimeout(obj2.foo, 10);   //name : obj <-소프트 바인딩됨