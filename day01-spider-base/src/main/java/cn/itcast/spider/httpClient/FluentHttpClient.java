package cn.itcast.spider.httpClient;

import java.nio.charset.Charset;

import org.apache.http.client.fluent.Request;

/**
 * 流畅的api
 * @author maoxiangyi
 *
 */
public class FluentHttpClient {
	public static void main(String[] args) throws Exception {
		String html = Request.Get("http://www.itcast.cn").execute().returnContent().asString(Charset.forName("utf-8"));
		System.out.println(html);
		
//		Request.Post("http://targethost/login")
//				.bodyForm(Form.form().add("username", "vip").add("password", "secret").build()).execute()
//				.returnContent();
	}
}
