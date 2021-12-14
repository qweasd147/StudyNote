const btn = document.querySelector("#btn");
const rootDiv = document.querySelector("#root");

let id;
let changeCount = 1;

btn.addEventListener(
  "click",
  function (e) {
    e.preventDefault();
    changeCount = 1;
    id = requestAnimationFrame(limitedRepaint);

    setTimeout(function cutRepeat() {
      cancelAnimationFrame(id);
    }, 2000);
  },
  false
);

function limitedRepaint() {
  rootDiv.textContent = "change" + changeCount;
  changeCount++;
  id = requestAnimationFrame(limitedRepaint);
}
