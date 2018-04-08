//유사 배열객체(array-like Objects)
function likeArray(...args){
    var argsSize = arguments.length;

    arguments instanceof Array;     //false. 배열을 proto로 잡지 않음

    Array.prototype.indexOf.call(arguments, 3); //2
    //arguments.indexOf(3)      ERROR!!!
}

likeArray(1,2,3,4,5,6)