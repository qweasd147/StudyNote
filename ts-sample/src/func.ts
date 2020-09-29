const getText1 = (name: string, age: number): string => {
  console.log(`name ${name}, age : ${age}`);

  return "";
};

const getText2: (name: string, age: number) => string = (name, age) => {
  console.log(`name ${name}, age : ${age}`);

  return "test";
};

const optionalFunc = (
  maybeStr: string | undefined,
  maybeNum?: string
): void => {
  console.log(`is null? ${maybeStr}`);
};

const defaultValFunc = (name: string = "joo"): void => {};

const restParameterFunc: (name: String, ...rest: number[]) => void = (
  name,
  rest
) => {
  console.log(`name ${name}`);
  console.log(rest);
};
