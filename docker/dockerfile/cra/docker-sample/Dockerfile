FROM node:14.4.0-alpine3.10 as builder

RUN mkdir -p /app/react/app
WORKDIR /app/react/app

# install에 필요한 파일 복사
COPY package.json .
COPY yarn.lock .

#RUN npm install -g yarn
RUN yarn install --frozen-lockfile
COPY . .
RUN yarn build

FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*
RUN rm -rf /etc/nginx/conf.d

COPY config/nginx /etc/nginx

COPY --from=builder /app/react/app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]