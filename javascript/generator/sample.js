function *foo(){
    console.log('point 1');
    yield;
    console.log('point 2');
}

var _fooIt = foo();

_fooIt.next(); //point 1
_fooIt.next(); //point 2

function *bar(){
    var _bar = 10;
    yield _bar;
    console.log('end');
}

var _barIt = bar();

var firstNext = _barIt.next();

console.log(firstNext.value); // 10
_barIt.next();  //end