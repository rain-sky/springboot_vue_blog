package com.cern.corn;


import com.cern.service.ArticleService;
import com.cern.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// redis中的文章浏览数据定时同步到数据库中
@Component
public class RedisJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    //每隔3分钟，把redis的浏览量数据更新到mysql数据库
//    @Scheduled(cron = "0/10 * * * * ?")
//    public void updateViewCount(){
//        //获取redis中的浏览量，注意得到的viewCountMap是HashMap双列集合
//        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
//        //让双列集合调用entrySet方法即可转为单列集合，然后才能调用stream方法
//        List<Article> articles = viewCountMap.entrySet()
//                .stream()
//                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
//                //把最终数据转为List集合
//                .collect(Collectors.toList());
//        //把获取到的浏览量更新到mysql数据库中。updateBatchById是mybatisplus提供的批量操作数据的接口
//        articleService.updateBatchById(articles);
//        //方便在控制台看打印信息
//        System.out.println("redis的文章浏览量数据已更新到数据库，现在的时间是: "+ LocalTime.now());
//    }
}