function getParam(this: string, index: number): string {
  console.log(`this ${this}`);

  return "";
}

interface String {
  getParam(this: string, index: number): string;
}

String.prototype.getParam = getParam;
