package com.cern.utils;

import com.cern.domain.entity.Article;
import com.cern.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

// Bean拷贝工具类
public class BeanCopyUtils {

    // 表明该类不可new
    private BeanCopyUtils(){}

    // TODO 理解
    // 使用泛型V确定new Instance的返回类型
    public static <V> V copyBean(Object source,Class<V> clazz){
        V result = null;
        try {
            /*
              此处需要在工具类方法中自动帮我们new出待拷贝目标类的实例，为此传入的参数应该为class类型
              为了确定new 出来的对象具体类型我们使用泛型来确定实际类型
              不加泛型默认new 出来的为object,Object o = clazz.newInstance();
             */
            result = clazz.newInstance();
            BeanUtils.copyProperties(source,result);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 使用流的形式拷贝
    public static <K,V> List<V> copyBeanList(List<K> source,Class<V> clazz){
        return source.stream()
                .map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }

    // 测试单个对象拷贝
    public static void main(String[] args) {
        Article article = new Article();
        article.setId(20L);
        article.setTitle("hello world!");
        HotArticleVo hotArticleVo = copyBean(article,HotArticleVo.class);
        System.out.println(hotArticleVo);
    }
}
