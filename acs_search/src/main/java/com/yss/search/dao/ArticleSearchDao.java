package com.yss.search.dao;

import com.yss.search.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 文章数据访问层接口
 */
public interface ArticleSearchDao extends ElasticsearchRepository<Article, String> {

    /**
     * 检索
     *
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
