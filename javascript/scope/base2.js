//scope
function innerScope1(){
    var foo = 'innerScope1';

    innerScope2();
}

function innerScope2(){
    //var foo = 'innerScope2';

    console.log(foo);       //globalScope!
}

var foo = 'globalScope!';

innerScope1();


//---------------------------------------------

function outerScope(){
    var foo = 'outerScope!';

    innerScope();

    function innerScope(){
        //var foo = 'innerScope';
    
        console.log(foo);       //outerScope!
    }
}



var foo = 'globalScope!';

outerScope();