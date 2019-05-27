package com.example.demo.api;

import com.example.demo.model.Article;
import com.example.demo.model.ArticleDto;
import com.example.demo.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/article")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity searchAll(Pageable pageable){
        Page<Article> articlePages = articleService.searchAll(pageable);

        return ResponseEntity.ok(articlePages);
    }

    @GetMapping("/{articleIdx}")
    public ResponseEntity searchOne(@PathVariable Long articleIdx){

        Article article = articleService.searchOne(articleIdx);
        ArticleDto.Resp respArticle = ArticleDto.Resp.of(article);

        return ResponseEntity.ok(respArticle);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void register(ArticleDto.CreateReq createReq){

        articleService.register(createReq);
    }

    @PutMapping("/{articleIdx}")
    @ResponseStatus(value = HttpStatus.OK)
    public void modify(@PathVariable Long articleIdx, ArticleDto.ModifyReq modifyReq){

        articleService.modify(articleIdx, modifyReq);
    }

    @DeleteMapping("/{articleIdx}")
    @ResponseStatus(value = HttpStatus.OK)
    public void remove(@PathVariable Long articleIdx){

        articleService.remove(articleIdx);
    }
}
