import { isEqual } from "lodash";

const v1 = 123;
const v2 = () => console.log("123");

isEqual(v1, v2);
