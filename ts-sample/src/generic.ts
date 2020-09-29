export {};

const makeArray = <T>(defaultValue: T, size: number): T[] => {
  const arr: T[] = [];

  for (let i = 0; i < size; i++) {
    arr.push(defaultValue);
  }

  return arr;
};

function makeArray2<T>(defaultValue: T, size: number): T[] {
  const arr: T[] = [];

  for (let i = 0; i < size; i++) {
    arr.push(defaultValue);
  }
  return arr;
}

class Stack<T extends number | string> {
  private items: T[] = [];

  push(item: T) {
    this.items.push(item);
  }

  pop() {
    this.items.pop();
  }
}

//keyof -> 멤버 변수중 하나
