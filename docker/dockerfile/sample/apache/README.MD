apache(httpd) 올리기 위한 docker file.

설정 파일도 같이 올리고, 설정파일의 변경점은 기본 설정에서 rewrite_module를 활성화 시키고

access, error로그 파일 경로를 ServerRoot/logs 하위로 변경시켰다.
(참고로 server root는 /usr/local/apache2)

.htaccess 파일에서는 경로를 찾을수 없을 시 index.html로 리다이렉트 시켜주었다.

이런식으로 redirect해주는 이유는 사용한 어플리케이션(react, react-router)에서 소스 레벨에서

알아서 컴포넌트 파일을 찾아서 해주면 그만인데 app까지 가지도 못하고 apache에서 물리적으로 파일을

찾으려고 하여 오류가 나서 바꿔줬다.

참고로 document directory로 그냥 있는거 썻다(htdocs).

    docker run -d -p 3000:80 --name="컨테이너 명" -v "$PWD":/usr/local/apache2/htdocs httpd