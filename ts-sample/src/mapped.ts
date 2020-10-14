import { partial } from "lodash";

export {};

//mapped type(일종의 유틸리티 타입)
type T1 = { [K in "prop1" | "prop2"]: boolean };

interface Person {
  name: string;
  age: number;
}

type T2 = Person["name"];

//ready only로 전환
type Readonly<T> = { readonly [P in keyof T]: T[P] };
type T3 = Readonly<Person>;

const person3: T3 = { name: "", age: 15 };
//person3.age = 5; ERROR!

//옵셔널 적용
type Partial<T> = { [P in keyof T]?: T[P] };
type T4 = Partial<Person>;

const person4: T4 = {};
person4.age;

//pick
type Pick<T, K extends keyof T> = { [P in K]: T[P] };

interface Person2 {
  name: string;
  age: number;
  language: string;
}

//age가 제외된 type
type T5 = Pick<Person2, "name" | "language">;

//record
type Record<K extends string, T> = { [P in K]: T };
type T6 = Record<"p1" | "p2", Person2>;

const person6: T6 = {
  p1: { name: "", age: 15, language: "ko" },
  p2: { name: "", age: 15, language: "ko" },
};

const enum Fruit {
  Apple,
  Banana,
  Orange,
}

//enum에 존재하지만 price 정보가 빠질 수가 있다.
/*const FRUIT_PRICE = {
  [Fruit.Apple]: 1000,
};
*/

const FRUIT_PRICE: { [K in Fruit]: number } = {
  [Fruit.Apple]: 1000,
  [Fruit.Banana]: 2000,
  [Fruit.Orange]: 3000,
};

const price: number = FRUIT_PRICE[Fruit.Apple];
