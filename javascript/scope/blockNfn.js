//블록 스코프, 함수 스코프
function fnScope1(){
  var foo = 'foo';

  {
    var bar = 'bar';
  }

  console.log(foo, bar); //foo bar
}

fnScope1();

function fnScope2(){
  
  //var i = 100;

  for(var i=0;i<5;i++){

  }

  console.log(i);   // 5
}

fnScope2();


function blScope(){
  
  let foo = 'foo';

  {
    let bar = 'bar';
  }

  //console.log(foo, bar); ERROR! bar is not defined
}

blScope();


//즉시 실행 함수를 활용한 별도의 스코프 생성
var scope = 'globla scope';

(function(){
  var scope = 'inner scope'
})();

console.log(scope);