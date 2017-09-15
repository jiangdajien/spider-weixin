package cn.itcast.lucene.search.dao;

import java.util.List;

import cn.itcast.lucene.search.domian.Article;

public interface ArticleDao {

	/**
	 * 读取数据
	 * 
	 * @param start
	 *            起始值
	 * @param end
	 *            结束值
	 * @return
	 */
	public List<Article> queryByPaging(int start, int end);
}
