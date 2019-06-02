function valueCopy(value){

    value = 10;
}

function referenceCopy(ref){
    ref.a = 10;
}

var value = 1;
var ref = { a: 1 }

valueCopy(value);
console.log(value); //1

referenceCopy(ref);
console.log(ref.a); //10

function replaceRef(ref){
    ref = {a : 1000};
}

replaceRef(ref);
console.log(ref.a); //10