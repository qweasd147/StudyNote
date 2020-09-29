function add(x: number, y: number): number;
function add(x: string, y: string): string;

function add(x: number | string, y: number | string): number | string {
  if (typeof x === "number" && typeof y === "number") return x + y;

  return `${Number(x) + Number(y)}`;
}
