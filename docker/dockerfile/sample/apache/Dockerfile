FROM httpd:2.4

#set apache root dir
WORKDIR /app/web/apache24

#copy config file
COPY ./.htaccess ./htdocs/.htaccess
COPY ./httpd.conf ./conf/httpd.conf

EXPOSE 80
