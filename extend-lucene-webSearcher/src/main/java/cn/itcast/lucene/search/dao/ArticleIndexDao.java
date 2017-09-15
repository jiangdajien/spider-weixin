package cn.itcast.lucene.search.dao;

import java.util.List;

import cn.itcast.lucene.search.domian.Article;

public interface ArticleIndexDao {

	/**
	 * 创建索引的功能
	 * 
	 * @param article
	 * @throws Exception
	 */
	public void createIndex(Article article) throws Exception;

	/**
	 * 
	 * @param field
	 *            用户查询是指定要查询的字段名称
	 * @param keyword
	 *            用户输入的关键词
	 * @param resNum
	 *            返回的新闻条数
	 * @return
	 * @throws Exception
	 */
	public List<Article> queryIndexByQuery(String field, String keyword, int resNum) throws Exception;

}
