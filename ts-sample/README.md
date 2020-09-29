# TypeScript Sample

### 1. install & typescript 설정 파일 만들기(tsconfig.json)

npx -> node_modules/.bin 하위에 있는 파일을 실행

```sh
$ npm install typescript
$ npx tsc --init
```

### 2. 컴파일

ts->js

```
$ npx tsc
```

### 3. TypeScript 타입 정의 다운로드

`DefinitelyTyped`에서 타입 정의 파일 다운로드

**주의** 모듈 다운로드 받는것과는 별개

```
$ npm install @types/xxxx
```
