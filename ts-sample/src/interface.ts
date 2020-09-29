export {};

interface Person1 {
  name: string;
  age?: number; //선택 속성
  readonly lang: string; //수정 x
  //[key: string]: string; //인덱스 타입
}

const person1: Person1 = { name: "joo", age: 13, lang: "ko" };

interface Person2 {
  name: string;
  [key: string]: string; //인덱스 타입
}

interface WithFunc {
  testFunc(name: string): boolean; //함수 형태 필드
}

type PP = Person1 & Person2;
