enum Fruit {
  Apple, //0
  Banana = 5,
  Orange, //6
}

//const 를 쓰면  컴파일 후에 enum 정보가 남지 않는다.
const enum Language {
  ko,
  ja,
  en,
}

const v1: Fruit = Fruit.Apple;
let v2: Fruit;

console.log(Fruit.Banana);
console.log(Fruit["Banana"]);
console.log(Fruit[5]);

console.log(Language.en);
