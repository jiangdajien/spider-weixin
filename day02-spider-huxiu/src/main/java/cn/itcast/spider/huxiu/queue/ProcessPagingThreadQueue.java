package cn.itcast.spider.huxiu.queue;

import org.apache.http.client.methods.HttpGet;

import cn.itcast.spider.huxiu.Article;

public class ProcessPagingThreadQueue extends Thread {

	@Override
	public void run() {
		while (true) {
			try {
				String aid = HuxiuSpiderMainQueue.blockingQueue.take();
				// 爬取单个页面，并且保存的数据库
				// 2.1.1 获取每个新闻链接的内容
				HttpGet httpGet = new HttpGet(HuxiuSpiderMainQueue.prefix + aid + HuxiuSpiderMainQueue.end);
				HuxiuSpiderMainQueue.setHeader(httpGet);
				String html = HuxiuSpiderMainQueue.getHtml(httpGet);
				// 2.1.2 解析单个新闻详情页
				Article article = HuxiuSpiderMainQueue.parseSinglePage(html);
				if (article != null) {
					article.setId(aid);
					article.setUrl(HuxiuSpiderMainQueue.prefix + aid + HuxiuSpiderMainQueue.end);
				}
				// 2.1.3 保存单个新闻
				// 创建数据库 创建表
				// 准备sql
				// INSERT INTO `spider`.`huxiu_article` (`id`, `title`,
				// `author`,
				// `createTime`, `zan`, `pl`, `sc`, `content`, `url` ) VALUES(
				// ?,?,?,?,?,?,?,?,?);
				HuxiuSpiderMainQueue.articleDao.save(article);
			} catch (Exception e) {
			}
		}
	}
}
