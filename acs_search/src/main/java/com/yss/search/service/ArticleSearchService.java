package com.yss.search.service;

import com.yss.search.dao.ArticleSearchDao;
import com.yss.search.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

    /**
     * 增加文章
     *
     * @param article
     */
    public void add(Article article) {
        articleSearchDao.save(article);
    }

    /**
     * 检索
     *
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByKeywords(String keywords, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return articleSearchDao.findByTitleOrContentLike(keywords, keywords, pageRequest);
    }
}
