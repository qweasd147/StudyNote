FROM httpd:2.4

#set apache root dir
WORKDIR /app/web/apache24

#copy config file
COPY ./.htaccess ./htdocs
COPY ./http.conf ./conf

EXPOSE 80
