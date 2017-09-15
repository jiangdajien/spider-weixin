package cn.itcast.lucene.search.service;

import java.util.List;

import cn.itcast.lucene.search.domian.Article;

public interface ArticleIndexService {
	/**
	 * 创建索引
	 * 从数据库中读取数据之后，一次性创建所有的内容
	 */
	public void createIndexFromDB();

	/**
	 * 为单个文章创建索引
	 * @param article
	 */
	public void createIndex(Article article);

	/**
	 * 用户查询
	 * 
	 * @param field
	 * @param keyword
	 * @param resNum
	 * @return 
	 */
	public List<Article> query(String field, String keyword, int resNum);

}
