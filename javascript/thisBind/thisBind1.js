function ThisBind(){

    console.log(this === window);
    console.log(this instanceof ThisBind)
}

var newInstance1 = new ThisBind();      //false, true

var newInstance2 = new ThisBind();      //false, true

console.log(newInstance1 === newInstance2);     //false

ThisBind(); //true, false