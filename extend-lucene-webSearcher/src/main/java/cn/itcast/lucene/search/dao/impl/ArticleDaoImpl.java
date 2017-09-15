package cn.itcast.lucene.search.dao.impl;

import java.sql.Driver;
import java.util.List;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;


import cn.itcast.lucene.search.dao.ArticleDao;
import cn.itcast.lucene.search.domian.Article;

@Service
public class ArticleDaoImpl extends JdbcTemplate implements ArticleDao {
    public ArticleDaoImpl() {
        // 1、由于需要连接出库，创建按一个数据库连接池
        try {
            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setPassword("root");
            dataSource.setUser("root");
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/spider?characterEncoding=utf-8&useSSL=false");
            setDataSource(dataSource);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 读取数据
     *
     * @param start 起始值
     * @param end   结束值
     * @return
     */
    public List<Article> queryByPaging(int start, int end) {
        String sql = "select `id`, `title`, `author`, `createTime`, `sc`, `pl`, `zan`, `content` from `spider`.`huxiu_article` limit "
                + start + "," + 2542;
        List<Article> resultList = null;
        try {
            resultList = query(sql, new BeanPropertyRowMapper<Article>(Article.class));
        } catch (Exception e) {
            System.out.println("--------------" + e);
        }
        return resultList;
    }

    /**
     * 获取总的新闻条数
     *
     * @return
     */
    public int totalNum() {
        String sql = "select count(*) from `spider`.`huxiu_article`";
        return queryForObject(sql, Integer.class);

    }

}
