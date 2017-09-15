package cn.itcast.spider.login;

import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 登录之后获取数据
 * 
 * @author maoxiangyi
 *
 */
public class Login {
	public static void main(String[] args) throws Exception {
		// 1.分析请求的url的信息
		// 找到POST请求的url:http://shop.itcast.cn/login/login.html
		// 分析出提交的三个参数:username,password,reURL
		String url = "http://shop.itcast.cn/login/login.html";
		HttpPost httpPost = new HttpPost(url);

		// 2.设置登录的参数
		// 传递参数的方式1.Stringentity
		// 传递参数的方式2.basicnameandvalupair
		ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("username", "itcast"));
		parameters.add(new BasicNameValuePair("password", "itcast"));
		parameters.add(new BasicNameValuePair("reURL", "http://shop.itcast.cn/item/itemList.html"));
		httpPost.setEntity(new UrlEncodedFormEntity(parameters));

		// 3.获取一个httpclient对象，用来执行http请求
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 4.执行请求，并得到返回对象
		CloseableHttpResponse response = httpclient.execute(httpPost);
		
		// 获取状态码 302 重定向
		// 5.获取重定向的跳转的location地址
		String location = null;
		if (response.getStatusLine().getStatusCode() == 302) {
			Header[] allHeaders = response.getAllHeaders();
			for (Header header : allHeaders) {
				if (header.getName().equals("Location")) {
					location = header.getValue();
				}
			}
		}
		/**
		 * 登录之后，要记录登录的状态，有几种方式。 session中 jsession
		 */
		// 6.根据登录之后返回的location地址，获取数据
		CloseableHttpClient hc1 = HttpClients.createDefault();
		CloseableHttpResponse res1 = hc1.execute(new HttpGet(location));
		if (res1.getStatusLine().getStatusCode() == 200) {
			String html = EntityUtils.toString(res1.getEntity());
			// Jsoup解析
			Document doc = Jsoup.parse(html);
			// 6.1 获取到商品列表所在的form表单
			Elements elements = doc.select("form[action=/item/batchUpdate.html]");
			// 6.2 获取到所有的tr标签，发现有个tr标签的表头
			elements = elements.select("tr");
			ArrayList<Product> arrayList = new ArrayList<Product>();
			// 6.3 迭代所有的的tr
			for (int i = 1; i < elements.size(); i++) {
				Product product = new Product();
				Element element = elements.get(i);
				// 6.3.1 获取id
				// <input type="hidden" name="itemList[0].id"
				Elements ids = element.select("input[type=hidden]");
				for (Element id : ids) {
					String value = id.attr("value");
					int idValue = Integer.parseInt(value) - 1;
					product.setId(idValue+"");
				}
				// 6.3.2 获取标题
				Elements names = element.select("input[name$=name]");
				product.setName(names.get(0).attr("value"));
				// 6.3.3获取价格
				Elements prices = element.select("input[name$=price]");
				product.setPrice(prices.get(0).attr("value"));
				// 6.3.4获取发布时间
				Elements createtimes = element.select("input[name$=createtime]");
				product.setCreateTime(createtimes.get(0).attr("value"));
				// 6.3.5 获取描述信息
				Elements details = element.select("input[name$=detail]");
				product.setDes(details.get(0).attr("value"));
				arrayList.add(product);
			}
			//7.保存数据
			save2db(arrayList);
		
		}

	}

	private static void save2db(ArrayList<Product> arrayList) {
		for (Product product : arrayList) {
			System.out.println(product);
		}
		
	}
}
