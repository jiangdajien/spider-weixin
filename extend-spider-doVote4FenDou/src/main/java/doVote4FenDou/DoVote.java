package doVote4FenDou;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;

public class DoVote {
	public static void main(String[] args) throws Exception {
		 multiThread();
	}
	
	/**
	 * 使用多线程进行爬取
	 **/
	private static void multiThread() throws FileNotFoundException, IOException, InterruptedException {
		//1.创建阻塞队列用来存放代理IP地址
		final ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(10000);
		//2.读取文件，该文件中保存着代理ip和端口号。如 103.29.186.189:3128
		String ips = FileUtil.readFileToString("C:/workspace/eclipse/mars/doVote4FenDou/src/main/resources/ips.txt");
		String[] ipkv = ips.split(" ");
		System.out.println("total ips:"+ipkv.length);
		for (String ip : ipkv) {
			System.out.println(ip);
			arrayBlockingQueue.put(ip);
		}
		int size = 10;
		//3.开启多线程进行投票
		ExecutorService pool = Executors.newFixedThreadPool(size);
		for (int i = 0; i < size; i++) {
			pool.submit(new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							String ipkv = arrayBlockingQueue.take();
							System.out.println(ipkv);
							String[] kv = ipkv.split(":");
							dovote(kv[0], Integer.parseInt(kv[1]));
						} catch (Exception e) {
							System.out.println("请求失败!" + e);
						}
					}
				}
			}));
		}
	}

	private static void doProxy() {
		List<String> ips = new ArrayList<String>();
		ips.add("103.29.186.189:3128");
		ips.add("139.220.195.60:80");
		ips.add("138.0.152.166:3128");
		ips.add("108.179.54.171:80");
		ips.add("138.219.176.64:8080");
		ips.add("139.195.170.200:808");
		ips.add("14.207.72.133:3128");
		ips.add("14.102.152.249:8080");
		ips.add("139.59.5.186:8080");
		ips.add("84.241.21.10:8080");
		ips.add("139.196.196.74:80");
		ips.add("186.91.160.120:8080");
		ips.add("36.74.165.99:8080");
		ips.add("185.129.202.2:53281");
		ips.add("37.238.61.177:8080");
		ips.add("180.246.56.226:8080");
		ips.add("128.199.192.252:80");
		ips.add("139.59.125.12:80");
		ips.add("139.59.243.186:8080");
		ips.add("139.59.125.53:8080");

		for (String context : ips) {
			String[] kv = context.split(":");
			try {
				dovote(kv[0], Integer.parseInt(kv[1]));
			} catch (Exception e) {

			}
		}
	}
	
	private static void dovote(String ip, int port) throws IOException, ClientProtocolException {
		//1.投票的请求接口
		String url = "http://fendou.itcast.cn/article/updatevote";
		//2.准备请求头的信息
		Map<String, String> headers = getHeader();
		//3.创建POST请求对象
		HttpPost httpPost = new HttpPost(url);
		//4.准备请求头的信息
		for (Map.Entry<String, String> header : headers.entrySet()) {
			httpPost.addHeader(header.getKey(), header.getValue());
		}
		//5.创建代理HTTP请求
		HttpHost proxy = new HttpHost(ip, port);
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setBufferSize(4128).build();
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		CloseableHttpClient hc = HttpClients.custom().setDefaultConnectionConfig(connectionConfig)
				.setRoutePlanner(routePlanner).build();
		//6.使用代理HttpClient发起投票请求
		CloseableHttpResponse res = hc.execute(httpPost);
		//7.打印http请求状态码
		System.out.println("statusCode:" + res.getStatusLine().getStatusCode());
		for (Header header : res.getAllHeaders()) {
			//8.打印所有的response header信息，发现有set-cookie的信息就成功了。
			System.out.println(header);
		}
		//9.打印html信息 如果返回为空字符串 就是投票成功。有返回值的基本就是失败了。
		String html = EntityUtils.toString(res.getEntity(), Charset.forName("utf-8"));
		System.out.println("返回值:" + html);
	}

	private static String getPS(CloseableHttpClient hc) throws IOException, ClientProtocolException {
		HttpGet httpGet = new
				 HttpGet("http://fendou.itcast.cn/article/look/aid/193?qq-pf-to=pcqq.c2c");
		 CloseableHttpResponse res1 = hc.execute(httpGet);
		 String ps = res1.getHeaders("set-cookie")[0].getValue();
		return ps;
	}

	private static Map<String, String> getHeader() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Referer", "http://fendou.itcast.cn/article/look/aid/193?qq-pf-to=pcqq.c2c");
		headers.put("host", "fendou.itcast.cn");
		headers.put("Origin", "http://fendou.itcast.cn");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		headers.put("X-Requested-With", "XMLHttpRequest");
		headers.put("cookie", "PHPSESSID=8bj2oolgfq23idj1n3qofmm893; UM_distinctid=15e0d154d8a617-095bb15b8b50fd-414a0229-100200-15e0d154d8b4b3");
		
		headers.put("Accept", "*/*");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("Connection", "keep-alive");
		
		return headers;
	}

}
