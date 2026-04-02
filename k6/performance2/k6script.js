import http from "k6/http";
import { check, group, sleep } from "k6";

const BASE_URL = "http://localhost:8081";

export const options = {
  // stages:
  // 가상 사용자(VU, Virtual Users) 수를 시간에 따라 어떻게 늘리고 줄일지 정의합니다.
  // 아래 설정은 5초 동안 5명까지 증가 -> 20초 동안 20명 유지 -> 5초 동안 0명까지 감소를 의미합니다.
  // 참고:
  // stages 방식은 "정확히 몇 번 요청할지"를 보장하지 않습니다.
  // 응답 속도, sleep 시간, 현재 VU 수에 따라 실제 요청 수는 달라집니다.
  stages: [
    { duration: "5s", target: 5 },
    { duration: "20s", target: 20 },
    { duration: "5s", target: 0 },
  ],
  // thresholds:
  // 테스트가 "성공"인지 판단하는 기준입니다.
  // 기준을 넘기면 k6 실행 결과가 실패로 표시되어 성능 저하를 빠르게 확인할 수 있습니다.
  thresholds: {
    // 전체 요청 중 실패 비율이 1% 미만이어야 합니다.
    http_req_failed: ["rate<0.01"],
    // 전체 요청의 95%가 1000ms(1초) 안에 끝나야 합니다.
    http_req_duration: ["p(95)<1000"],
  },

  // scenarios:
  // 각 API를 정확한 횟수로 따로 실행하고 싶을 때 사용하는 방식입니다.
  // 예를 들어 아래처럼 설정하면:
  // - optimistic-lock API는 정확히 100번
  // - shared-lock API는 정확히 50번
  // 호출되도록 분리할 수 있습니다.
  //
  // 주의:
  // scenarios를 실제로 활성화하면 stages와는 다른 실행 모델로 동작하므로,
  // 지금은 참고용으로만 주석 처리해둡니다.
  //
  // scenarios: {
  //   optimistic_lock_test: {
  //     executor: "shared-iterations", // per-vu-iterations -> 각각 iterations 만큼 실행
  //     vus: 5,
  //     iterations: 100,
  //     exec: "optimisticLock",
  //   },
  //   shared_lock_test: {
  //     executor: "shared-iterations",
  //     vus: 5,
  //     iterations: 50,
  //     exec: "sharedLock",
  //   },
  // },
};

export default function () {
  group("normal detail optimistic lock", () => {
    const res = http.get(`${BASE_URL}/normal/detail/1/optimistic-lock`, {
      tags: { api_group: "optimistic_lock" },
    });

    check(res, {
      "optimistic lock status is 200": (r) => r.status === 200,
    });

    sleep(0.5);
  });

  group("normal detail shared lock", () => {
    const res = http.get(`${BASE_URL}/normal/detail/2/shared-lock`, {
      tags: { api_group: "shared_lock" },
    });

    check(res, {
      "shared lock status is 200": (r) => r.status === 200,
    });

    sleep(0.5);
  });
}

// scenarios 예시를 실제로 사용할 경우 아래 함수를 함께 export 해서 연결할 수 있습니다.
// export function optimisticLock() {
//   const res = http.get(`${BASE_URL}/normal/detail/1/optimistic-lock`, {
//     tags: { api_group: "optimistic_lock" },
//   });
//
//   check(res, {
//     "optimistic lock status is 200": (r) => r.status === 200,
//   });
// }
//
// export function sharedLock() {
//   const res = http.get(`${BASE_URL}/normal/detail/2/shared-lock`, {
//     tags: { api_group: "shared_lock" },
//   });
//
//   check(res, {
//     "shared lock status is 200": (r) => r.status === 200,
//   });
// }

// run: k6 run k6script.js
// run: k6 run --summary-mode=full k6script.js
// K6_WEB_DASHBOARD=true K6_WEB_DASHBOARD_EXPORT=html-report.html k6 run k6script.js
