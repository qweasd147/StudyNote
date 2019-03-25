var multiple = function(x, y){
    return x * y;
}

var multipleFi = multiple.bind(null, 3.14);

/**
 * 원 넓이
 * @param {} r 반지름
 */
var circleArea = function(r){
    return multipleFi(r * r);
}

/**
 * 원 둘레
 * @param {} r 반지름
 */
var circumference = function(r){
    return multipleFi(2 * r);
}
