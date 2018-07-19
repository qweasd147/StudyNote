var scope = "global";

function fnForHoisting(){
    console.log(scope); //undefined;

    var scope = "inner";
}