var obj = {
    a : 10
    , callA : function(){
        console.log(this.a);
    }
    , getA : function(){
        return this.a
    }
    , innerObj : {
        a : 20
        , getA : function(){
            return this.a;
        }
        , callA : function(){
            console.log(this.a);
        }
    }
}

obj.callA();            //10
obj.innerObj.callA()    //20

//callback

var obj2 = {
    a:20
}

function cbTest(cb){
    var a = 30;

    cb();
}

cbTest(obj.callA);
cbTest(obj.callA.bind(obj2))    //20