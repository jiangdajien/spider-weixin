package cn.itcast.spider.huxiu;

import org.apache.http.client.methods.HttpGet;

public class ProcessPagingThread extends Thread {

	private String aid;

	public ProcessPagingThread(String aid) {
		this.aid = aid;
	}

	@Override
	public void run() {
		try {
			// 爬取单个页面，并且保存的数据库
			// 2.1.1 获取每个新闻链接的内容
			HttpGet httpGet = new HttpGet(HuxiuSpiderMain.prefix + aid + HuxiuSpiderMain.end);
			HuxiuSpiderMain.setHeader(httpGet);
			String html = HuxiuSpiderMain.getHtml(httpGet);
			// 2.1.2 解析单个新闻详情页
			Article article = HuxiuSpiderMain.parseSinglePage(html);
			if (article != null) {
				article.setId(aid);
				article.setUrl(HuxiuSpiderMain.prefix + aid + HuxiuSpiderMain.end);
			}
			// 2.1.3 保存单个新闻
			// 创建数据库 创建表
			// 准备sql
			// INSERT INTO `spider`.`huxiu_article` (`id`, `title`, `author`,
			// `createTime`, `zan`, `pl`, `sc`, `content`, `url` ) VALUES(
			// ?,?,?,?,?,?,?,?,?);
			HuxiuSpiderMain.articleDao.save(article);
		} catch (Exception e) {
			System.out.println("线程"+Thread.currentThread().getId()+" 访问新闻编号为  "+aid+"  失败！");
		}
	}

}
