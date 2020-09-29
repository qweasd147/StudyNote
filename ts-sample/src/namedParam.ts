export {};

function getParam({
  name,
  age,
  language,
}: {
  name: string;
  age: number;
  language: string;
}): string {
  console.log("named parameter");

  console.log(`${name} - ${age} - ${language}`);

  return "";
}

getParam({ name: "name", age: 13, language: "ko" });
