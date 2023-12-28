package com.cern.runner;

import com.cern.domain.entity.Article;
import com.cern.service.ArticleService;
import com.cern.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AppStartCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    // 应用启动时执行,将数据库中的文章浏览次数存入redis中
    @Override
    public void run(String... args) throws Exception {
        List<Article> list = articleService.list();
        Map<String, Integer> collect = list.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));
        redisCache.setCacheMap("article:viewCount",collect);
    }
}
