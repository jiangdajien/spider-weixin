package cn.itcast.spider.httpClient;

import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 使用 httpclient 来进行get请求
 * 
 * @author maoxiangyi
 *
 */
public class HCPost {
	public static void main(String[] args) throws Exception {
		// 1.拿到一个httpclient的对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 2.设置请求方式和请求信息
//		HttpGet httpGet = new HttpGet("http://www.itcast.cn");
		HttpPost httpPost = new HttpPost("http://www.itcast.cn");
		
		//2.1 提交header头信息
		httpPost.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		//2.1 提交请求体
		//提交方式1:一般用在原生ajax请求
//		httpPost.setEntity(new StringEntity("username=zhangsan&passwd=123"));
		//提交方式2：大多数的情况下用这种方式
		ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("username", "zhangsan"));
		parameters.add(new BasicNameValuePair("passwd", "123"));
		httpPost.setEntity(new UrlEncodedFormEntity(parameters));
		
		// 3.执行请求
		CloseableHttpResponse response = httpClient.execute(httpPost);
		// 4.获取返回值
		String html = EntityUtils.toString(response.getEntity(),Charset.forName("utf-8"));
		// 5.打印
		System.out.println(html);

	}
}
