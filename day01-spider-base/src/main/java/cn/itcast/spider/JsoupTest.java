package cn.itcast.spider;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 使用Jsoup解析文档
 * 
 * @author maoxiangyi
 *
 */
public class JsoupTest {
	public static void main(String[] args) throws Exception {
		//1.输入方式 字符串、文件、html片段、url
		Document doc = Jsoup.connect("http://www.163.com/").get();
		//2、通过标签名称访问元素
//		Elements elements = document.select("a[href=http://www.52ij.com/]");
		
//		通过id访问元素
		
//		Elements els = doc.select("#js_N_navHighlight");
		
//		Elements els = doc.select(".JS_NTES_LOG_FE");
		
//		Elements els = doc.select("[^ne-c]");
		
		//<ul class="ntes-quicknav-column ntes-quicknav-column-11">
		
		Elements els = doc.select("ul[class=ntes-quicknav-column ntes-quicknav-column-11] > h3");
		
		
		//3、打印
		for (Element element : els) {
			System.out.println(element);
		}
	}

}
