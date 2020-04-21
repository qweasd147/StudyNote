package com.es.demo.controller;


import com.es.demo.ArticleDto;
import com.es.demo.docs.Article;
import com.es.demo.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/article")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity searchAll(@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC ,size = 10)
                Pageable pageable, ArticleDto.ListReq searchRequestDto
    ){

        //Page<Article> articlePages = articleService.searchAll(searchRequestDto,  pageable);
        Page<Article> articlePages = articleService.searchAllLike(searchRequestDto,  pageable);

        Map<String, Object> result = new HashMap<>();

        result.put("contents", articlePages.getContent());
        result.put("count", articlePages.getTotalElements());
        result.put("page", articlePages.getNumber() + 1);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{articleIdx}")
    public ResponseEntity searchOne(@PathVariable String articleIdx){

        Article article = articleService.searchOne(articleIdx);
        ArticleDto.Resp respArticle = ArticleDto.Resp.of(article);

        return ResponseEntity.ok(respArticle);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ArticleDto.Resp register(@Valid @ModelAttribute ArticleDto.CreateReq createReq){

        Article article = articleService.register(createReq);

        //잘 등록 되었나 확인차 반환
        return ArticleDto.Resp.of(article);
    }

    @PutMapping("/{articleIdx}")
    @ResponseStatus(value = HttpStatus.OK)
    public ArticleDto.Resp modify(@PathVariable String articleIdx, @Valid ArticleDto.ModifyReq modifyReq){

        Article article = articleService.modify(articleIdx, modifyReq);

        //잘 수정 되었나 확인차 반환
        return ArticleDto.Resp.of(article);
    }

    @DeleteMapping("/{articleIdx}")
    @ResponseStatus(value = HttpStatus.OK)
    public void remove(@PathVariable String articleIdx){

        articleService.remove(articleIdx);
    }
}
