import http from "k6/http";
import { sleep, check, group } from "k6";

export const options = {
  stages: [
    { duration: "3s", target: 3 },
    { duration: "10s", target: 10 },
    { duration: "3s", target: 3 },
  ],
};

export default function () {
  group("query sleep", () => {
    const res = http.get("http://localhost:8081/sleep/query");

    check(res, {
      "is status 200": (r) => {
        return r.status === 200;
      },
    });

    sleep(0.5);
  });

  group("thread sleep", () => {
    const res = http.get("http://localhost:8081/sleep/thread");

    check(res, {
      "is status 200": (r) => {
        return r.status === 200;
      },
    });

    sleep(0.5);
  });
}

// k6 run k6script.js
