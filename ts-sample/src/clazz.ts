class Person {
  private name: string;
  #age: number = 0;

  constructor(name: string, age: number) {
    this.name = name;
    this.#age = age;
  }

  sayHello() {
    console.log(`name : ${name} & age ${this.#age}`);
  }
}

//생성자에서만 접근 제어를 설정하면 알아서 멤버 변수로 등록됨
class Person2 {
  constructor(public name: string, private age: number) {}

  sayHello() {
    console.log(`name : ${this.name} & age ${this.age}`);
  }
}

class Person3 {
  private _name: string = "";
  public static PERSON_NAME = "";

  get name(): string {
    return this._name;
  }

  set name(newName: string) {
    this._name = newName;
  }

  static personName(): string {
    return this.PERSON_NAME;
  }
}

class Programmer extends Person {
  constructor(name: string) {
    super(name, 13);
  }
}

const person123 = new Programmer("개발자1");
person123.sayHello();
