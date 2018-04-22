//if(!Function.prototype.bind){
    Function.prototype.bind = function(oThis){
        if(typeof this !== "function"){
            throw new TypeError("함수 아님");
        }

        var aArgs = Array.prototype.slice.call(arguments,1);    //첫번째 arguments(this 대상)만 제외한 배열
        var fToBind = this;
        var fNOP = function(){};
        var fBound = function(){

            var that  = (this instanceof fNOP && oThis) ? oThis : this;
            var bindArgs = aArgs.concat(Array.prototype.slice.call(arguments));

            return fToBind.apply(that, bindArgs);
        }

        fNOP.prototype = this.prototype;
        fBound.prototype = new fNOP();

        return fBound;
    }
//}

function foo(p1, p2){
    this.val = p1+p2;
}

var obj = {
    'obj' : 'obj1'
};

//var bar = foo.bind(obj,"p1");
var bar = foo.bind(null,"p1","p2","p3");
var baz = new bar("p2");

console.log(baz.val);    //p1p2