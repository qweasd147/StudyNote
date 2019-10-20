package com.example.demo.api;

import com.example.demo.model.Article;
import com.example.demo.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    @PutMapping("/article")
    @ResponseStatus(value = HttpStatus.OK)
    public void articleModifyWithBulk(){

        Article article = articleService.articleModifyWithBulk();

        log.info("end transaction");
        log.info(article.toString());
    }

    @PutMapping("/tag")
    @ResponseStatus(value = HttpStatus.OK)
    public void tagModifyWithBulk(){

        Article article = articleService.tagModifyWithBulk();

        log.info("end transaction");
        log.info(article.toString());
    }
}
