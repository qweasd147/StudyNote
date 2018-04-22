var testArrow = {
    a : 50
    , arrowFn : function(){
        
        setTimeout(()=>{
            console.log(this.a);       //50
        },10);
    }
    , notUseArrowFn : function(){
        var that = this;

        setTimeout(function(){
            console.log(this.a, that.a);//undefinded 50
        },10);
    }
}

testArrow.arrowFn();        //50
testArrow.notUseArrowFn();  //undefinded 50

function dummyClazz(){
    this.a = 50;

    var notUseArrowFn = function (){
        console.log(this.a);
    }

    var useArrowFn = ()=>{console.log(this.a)}

    notUseArrowFn();       //undefined
    useArrowFn();          //50
}

var _instance = new dummyClazz();