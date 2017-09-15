package cn.itcast.spider.huxiu;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ArticleDao extends JdbcTemplate {

	public ArticleDao() {
		// 创建C3P0的datasource 1.配置 2.代码
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		// 1.url
		dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/spider?characterEncoding=utf-8");
		// 2.driver 不需要
		// 3.username&password
		dataSource.setUser("root");
		dataSource.setPassword("root");
		setDataSource(dataSource);
	}

	public void save(Article article) {
		String sql = "INSERT INTO `spider`.`huxiu_article` (`id`, `title`, `author`, `createTime`, `zan`, `pl`, `sc`, `content`, `url` ) VALUES( ?,?,?,?,?,?,?,?,?)";
		update(sql, article.getId(),article.getTitle(),article.getAuthor(),article.getCreateTime(),article.getZan(),article.getPl(),article.getSc(),article.getContent(),article.getUrl());
	}
}
