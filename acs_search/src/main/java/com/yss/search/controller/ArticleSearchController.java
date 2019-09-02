package com.yss.search.controller;

import com.yss.common.entity.PageResult;
import com.yss.common.entity.Result;
import com.yss.common.entity.StatusCode;
import com.yss.search.entity.Article;
import com.yss.search.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService articleSearchService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article) {
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK, "操作成功");
    }

    @RequestMapping(value = "/search/{keywords}/{page}/{size}", method = RequestMethod.GET)
    public Result findByTitleLike(@PathVariable String keywords, @PathVariable int page, @PathVariable int size) {
        Page<Article> articlePage = articleSearchService.findByKeywords(keywords, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(articlePage.getTotalElements(), articlePage.getContent()));
    }

}