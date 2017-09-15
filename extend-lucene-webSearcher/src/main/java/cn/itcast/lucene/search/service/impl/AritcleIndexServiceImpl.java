package cn.itcast.lucene.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.lucene.search.dao.ArticleIndexDao;
import cn.itcast.lucene.search.dao.impl.ArticleDaoImpl;
import cn.itcast.lucene.search.domian.Article;
import cn.itcast.lucene.search.service.ArticleIndexService;

@Service
public class AritcleIndexServiceImpl implements ArticleIndexService {
	@Autowired
	private ArticleDaoImpl articleDao;

	@Autowired
	private ArticleIndexDao articleIndexDao;

	/**
	 * 创建索引 是查询数据库之后 进行的
	 * 
	 */
	public void createIndexFromDB() {
		// 1.查询数据库
		List<Article> resList = articleDao.queryByPaging(0, 10);
		System.out.println(resList);
		// 2.为数据创建索引
		for (Article article : resList) {
			try {
				articleIndexDao.createIndex(article);
			} catch (Exception e) {
				System.out.println("创建索引失败,文章编号：" + article.getTitle());
			}
		}
	}

	public void createIndex(Article article) {
		try {
			articleIndexDao.createIndex(article);
		} catch (Exception e) {
			System.out.println("创建索引失败,文章编号：" + article.getTitle());
		}
	}

	public List<Article> query(String field, String keyword, int resNum) {
		try {
			return articleIndexDao.queryIndexByQuery(field, keyword, resNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

}
