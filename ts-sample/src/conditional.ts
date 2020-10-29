export {};
// 조건부 타입

//문자열 타입 정의
type IsStringType<T> = T extends string ? "yes" : "no";
type T1 = IsStringType<string>;
type T2 = IsStringType<number>;

const typ1: T1 = "yes";

//TypeScript 내장 타입(exclude)
type T3 = number | string | never;
type Exclude<T, U> = T extends U ? never : T;
type T4 = Exclude<1 | 3 | 5 | 7, 1 | 5 | 9>;

type Extract<T, U> = T extends U ? T : never;
type T5 = Extract<1 | 3 | 5 | 7, 1 | 5 | 9>;
