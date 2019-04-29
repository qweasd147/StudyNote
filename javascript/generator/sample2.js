function *genWithTryCath(){
    var cnt = 1;
    while(true){
        console.log('break ', cnt);
        cnt++;
        try{
            yield;
        }catch(e){
            console.log('error 감지. ', e);
        }
        
    }
}
/*
var it1 = genWithTryCath();

it1.next();                     //break 1
it1.next();                     //break 2
it1.throw("외부에서 에러 발생!");   //error 감지.  외부에서 에러 발생!
//break 3
console.log(it1.return().done);     //true
*/

var it2 = genWithTryCath();
it2.next(); //break 1
it2.next(); //break 2
it2.next(); //break 3

it2.return();

console.log(it2.next().done);      //true