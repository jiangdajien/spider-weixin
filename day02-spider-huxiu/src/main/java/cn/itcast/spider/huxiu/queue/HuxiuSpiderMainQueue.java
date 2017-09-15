package cn.itcast.spider.huxiu.queue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import cn.itcast.spider.huxiu.Article;
import cn.itcast.spider.huxiu.ArticleDao;
import cn.itcast.spider.huxiu.PagingResponse;

/**
 * 开发一个爬虫，用来爬取虎嗅的新闻信息，并保存到数据库。
 * 
 * @author maoxiangyi
 *
 */
public class HuxiuSpiderMainQueue {
	public static final String prefix = "https://www.huxiu.com/article/";
	public static final String end = ".html";
	public static final ArticleDao articleDao = new ArticleDao();
	public static final ExecutorService threadPool = Executors.newFixedThreadPool(10);
	public static final ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(1000);

	public static void main(String[] args) throws Exception {
		//1.初始化10个线程用来消费队列的数据
		for (int i = 0; i < 10; i++) {
			threadPool.execute(new ProcessPagingThreadQueue());
		}
		//2.解析首页，将url放到队列中
		String indexHtml = getIndex();
		parseIndexHtml(indexHtml);

		// 3.进行分页
		String last_dataline = getValueByIndexHtml(indexHtml);
		for (int page = 2; page <= 1585; page++) {
			last_dataline = doSinglePageing(last_dataline, page);
		}

	}

	private static String doSinglePageing(String last_dataline, int page)
			throws UnsupportedEncodingException, IOException, ClientProtocolException {
		String url = "https://www.huxiu.com/v2_action/article_list";
		// 3.1 分析到分页时个post请求
		HttpPost httpPost = new HttpPost(url);

		// 3.2 准备参数
		ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
		arrayList.add(new BasicNameValuePair("huxiu_hash_code", "353a9683918c807f5f783dc1df116fad"));
		arrayList.add(new BasicNameValuePair("page", page + ""));
		arrayList.add(new BasicNameValuePair("last_dateline", last_dataline));

		// 3.3 设置参数
		httpPost.setEntity(new UrlEncodedFormEntity(arrayList));

		// 3.4 伪造user-agent
		setHeader(httpPost);

		// 4.发起post请求
		CloseableHttpClient postHttpClient = HttpClients.createDefault();
		CloseableHttpResponse pagingRes = postHttpClient.execute(httpPost);
		// 5.得到返回值
		String jsonData = EntityUtils.toString(pagingRes.getEntity());

		// 6.解析json串 fastjSON Gson
		Gson gson = new Gson();
		PagingResponse pagingResponse = gson.fromJson(jsonData, PagingResponse.class);

		// data 用来解析分页数据
		String data = pagingResponse.getData();

		parsePagingHtml(data);
		// last_dataline 用来进行下一次请求的
		last_dataline = pagingResponse.getLast_dateline();
		return last_dataline;
	}

	private static void parsePagingHtml(String data) {
		if (data != null) {
			// 1.使用jsoup解析data url,file,string,segment
			Document doc = Jsoup.parse(data);
			Elements divs = doc.select("div[data-aid]");
			for (Element element : divs) {
				try {
					blockingQueue.put(element.attr("data-aid"));
					System.out.println(element.attr("data-aid"));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取首页的last_dataline
	 * 
	 * @param indexHtml
	 * @return
	 */
	private static String getValueByIndexHtml(String indexHtml) {
		if (indexHtml != null) {
			Document doc = Jsoup.parse(indexHtml);
			Elements dataLines = doc.select("div[data-last_dateline]");
			return dataLines.get(0).attr("data-last_dateline");
		}
		return null;
	}

	public static Article parseSinglePage(String html) {
		try {
			if (html != null) {
				Article article = new Article();
				Document doc = Jsoup.parse(html);
				// 获取标题
				// text() 获取这个元素下面的所有 所有 所有 的文字。
				// ownText() 只获取自己包含的文字
				String title = doc.select(".t-h1").get(0).ownText();
				article.setTitle(title);
				// 获取作者
				String author = doc.select(".author-name").get(0).text();
				article.setAuthor(author);
				// 获取发布时间
				// 获取时间失败，发现是新闻详情页有两个不同的版本
				Elements timeEles = doc.select("span[class=article-time pull-left]");
				if (timeEles.size() == 0) {
					article.setCreateTime(doc.select(".article-time").get(0).ownText());
				} else {
					String createTime = timeEles.get(0).ownText();
					article.setCreateTime(createTime);
				}

				// 获取点赞
				String zan = doc.select(".num").get(0).ownText();
				article.setZan(zan);

				// 获取收藏 article-share pull-left
				article.setSc(doc.select(".article-share").get(0).ownText());
				// 获取正文
				article.setContent(doc.select(".article-content-wrap").get(0).text());
				// 获取评论 article-pl pull-left
				article.setPl(doc.select(".article-pl").get(0).ownText());
				return article;
			}
		} catch (Exception e) {
			System.out.println("文章解析失败:" + html);
		}
		return null;
	}

	public static void setHeader(HttpRequestBase request) {
		request.addHeader("user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
	}

	private static void parseIndexHtml(String indexHtml) {
		if (indexHtml != null) {
			// 1.使用jsoup将html文档转换成docement对象
			Document doc = Jsoup.parse(indexHtml);
			// 2.获取新闻列表
			// 3.如何选定哪些是新闻列表
			Elements divs = doc.select(".mod-info-flow div[data-aid]");
			for (Element element : divs) {
				// 获取div的data-aid属性
				String aid = element.attr("data-aid");
				try {
					blockingQueue.put(aid);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static String getIndex() throws Exception {
		String url = "https://www.huxiu.com/";
		// 1.发起一个get请求
		HttpGet httpGet = new HttpGet(url);
		// 2.设置请求头
		// User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36
		// (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36
		setHeader(httpGet);
		// 3.执行请求
		return getHtml(httpGet);
	}

	/**
	 * 专门用来访问httpget请求的方法
	 * 
	 * @param httpGet
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String getHtml(HttpGet httpGet) throws IOException, ClientProtocolException {
		String html = null;
		CloseableHttpClient hc = HttpClients.createDefault();
		CloseableHttpResponse res = hc.execute(httpGet);
		if (res.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = res.getEntity();
			html = EntityUtils.toString(entity);
		}
		return html;
	}

}
