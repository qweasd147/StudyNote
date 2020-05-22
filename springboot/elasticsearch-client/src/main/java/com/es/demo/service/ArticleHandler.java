package com.es.demo.service;

import com.es.demo.docs.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.List;

@Service
@Slf4j
public class ArticleHandler {


    public List<Article> searchAll(ServerRequest serverRequest){

        MultiValueMap<String, String> params = serverRequest.queryParams();



        return null;
    }
}
