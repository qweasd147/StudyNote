var setter;
var getter;

function closure(){
    var innerVal = 10;

    getter = function(){
        return innerVal;
    }
    setter = function(_val){
        innerVal = _val;
    }
}

closure();

getter();   //10
setter(50); //50
getter();   //50