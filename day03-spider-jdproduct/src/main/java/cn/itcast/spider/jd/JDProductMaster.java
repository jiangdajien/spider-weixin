package cn.itcast.spider.jd;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;

/**
 * 请求首页，将首页解析出来的product组成url，存放到redis的list数据结构中。 优化下：存放到redis中，只存放pid，节约内存资源。
 * 
 * @author maoxiangyi
 *
 */
public class JDProductMaster {
	private static final Jedis jedis = new Jedis("127.0.0.1", 6379);

	public static void main(String[] args) {
		// 1.准备url
		String indexUrl = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&wq=%E6%89%8B%E6%9C%BA&pvid=aa9a99bb0895488197c2a663d777a51b";
		try {
			HttpGet httpGet = new HttpGet(indexUrl);
			// 2.获取首页的信息
			String html = getHtml(httpGet);
			// 3.解析首页 此处不需要返回值，直接在方法中调用redis的jedis的客户端
			parseHtml(html);
		} catch (Exception e) {
			System.out.println("首页访问失败！" + indexUrl);
		}
		// 4、做分页请求
		int page = 1;
		for (int num = 2; num <= 100; num++) {
			page = (2 * num) - 1;
			String pagingUrl="https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&cid2=653&cid3=655&click=0&page="+page;
			HttpGet httpGet = new HttpGet(pagingUrl);
			try {
				String pagingHtml = getHtml(httpGet);
				parseHtml(pagingHtml);
			} catch (Exception e) {
				System.out.println("请求分页失败，分页编号是:"+page);
			}
			try {
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 解析搜索页的首页时，需要获取到pid
	 * 
	 * @param html
	 */
	public static void parseHtml(String html) {
		if (html != null) {
			Document doc = Jsoup.parse(html);
			Elements eles = doc.select("[data-pid]");
			for (Element element : eles) {
				jedis.lpush("itcast:spider:jd:pids", element.attr("data-pid"));
			}
		}

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
			html = EntityUtils.toString(entity, Charset.forName("utf-8"));
		}
		return html;
	}
}
