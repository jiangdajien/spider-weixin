package cn.itcast.spider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 使用JDK的api进行get请求
 * 
 * 1.在使用httpurlconnection时，默认就是get请求。如何改成post请求？
 * 2.http协议中，可以指定header，想添加user-agent
 * 
 * @author maoxiangyi
 *
 */
public class BasicHttpPost {

	public static void main(String[] args) throws Exception {
		//1.指定一个url
		String domain = "http://www.itcast.cn";
		//2.发起一个请求
		URL url = new URL(domain);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		//添加请求方式
		conn.setRequestMethod("GET");
		
		//添加请求头------如果编写爬虫，真实浏览器发送的header都拷贝
		conn.setRequestProperty("Accept", "text/html");
		/**
		 * Accept:text/html
		 **/
		
		
		//3.获取返回值
		InputStream inputStream = conn.getInputStream();
		//3.1 将输入流转换字符串
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		//3.2 一次读取bufferReader的数据
		String line =null;
		while((line=bufferedReader.readLine())!=null){
			System.out.println(line);
		}
		//4.关闭流
		inputStream.close();
		
	}
	
}
