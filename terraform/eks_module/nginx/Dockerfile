FROM nginx:1.23.3

RUN rm -rf /usr/share/nginx/html/*
RUN rm -rf /etc/nginx/conf.d

COPY config/nginx /etc/nginx
COPY index.html /usr/share/nginx/html/index.html
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 80

ENTRYPOINT ["/entrypoint.sh"]

CMD ["nginx", "-g", "daemon off;"]