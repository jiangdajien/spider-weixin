package cn.itcast.spider.httpClient;

import java.nio.charset.Charset;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 使用 httpclient 来进行get请求
 * 
 * @author maoxiangyi
 *
 */
public class HCGet {
	public static void main(String[] args) throws Exception {
		// 1.拿到一个httpclient的对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 2.设置请求方式和请求信息
		HttpGet httpGet = new HttpGet("http://www.itcast.cn");
		// 3.执行请求
		CloseableHttpResponse response = httpClient.execute(httpGet);
		// 4.获取返回值
		String html = EntityUtils.toString(response.getEntity(),Charset.forName("utf-8"));
		// 5.打印
		System.out.println(html);

	}
}
