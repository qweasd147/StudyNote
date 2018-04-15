function closure(){
    var innerVal = 10;

    return {
        getInnerVal : function(){
            return innerVal;
        }
        , setInnerVal : function(_val){
            innerVal = _val;
        }
    }
}

var closureFunc = closure();

closureFunc.getInnerVal();  //10

closureFunc.setInnerVal(50);

closureFunc.getInnerVal();  //50