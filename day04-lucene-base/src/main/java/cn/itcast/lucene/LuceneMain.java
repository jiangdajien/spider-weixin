package cn.itcast.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 使用lucene创建索引和查询索引
 * 
 * @author maoxiangyi
 *
 */
public class LuceneMain {

	@Test
	public  void searchIndex() throws IOException {
		//需求:使用lucene查询索引
		FSDirectory directory = FSDirectory.open(new File("index"));
		// 第一步:创建IndexSearcher，IndexSearcher 是基于索引库进行查询 ，需要先读取索引库。
		IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(directory));
		// 第二步：设置用户的关键词
		String keyword = "文娱商业观察";
		// 第三步：根据词条进行查询
		// 将关键词转换成一个term对象，需要指定关键词要查询字段 new Term("author", keyword)
		TopDocs res = indexSearcher.search(new TermQuery(new Term("content", "战")), Integer.MAX_VALUE);
		// 第四步：从topdocs中获取数据
		System.out.println("当前查询命中多少天数据:"+res.totalHits);
		ScoreDoc[] scoreDocs = res.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			System.out.println("维护在lucene内部的文档编号:"+scoreDoc.doc);
			System.out.println("当前文档的得分:"+scoreDoc.score);
			//第五步:获取单篇文章的信息
			Document doc = indexSearcher.doc(scoreDoc.doc);
			System.out.println("id:   "+doc.get("id"));
			System.out.println("title:   "+doc.get("title"));
			System.out.println("author:   "+doc.get("author"));
			System.out.println("createTime:   "+doc.get("createTime"));
			System.out.println("content:   "+doc.get("content"));
			System.out.println("url:   "+doc.get("url"));
		}
	}
	
	@Test
	public void createIndex() throws IOException {
		// 需求:使用lucene创建索引
		// 1.使用某种技术
		// document--->分词--->倒排--->写入到索引库----介质(硬盘或者内存)

		Directory d = FSDirectory.open(new File("index"));
		IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, new StandardAnalyzer());
		// 第一步:创建indexWriter，传入两个参数，一个directory(FSDriectory,RAMDriectory);一个是IndexWriterConfig
		// IndexWriterConfig,传入两个参数，一个是版本号，一个分词器(中文分词器在3.1时过期了，推荐使用标准StandardAnalyzer)
		IndexWriter indexWriter = new IndexWriter(d, conf);

		// 第二步:对文件创建索引----虎嗅网站：id,title,author,createTime,content,url
		// 一个文档中，有很多字段，想通过title进行查询，这个字段需要被indexed。
		// 如果一个想创建索引，有两种选择，一种不被分词就是完全匹配，一种是分词。
		Document document = new Document();
		// StringField 不分词
		document.add(new StringField("id", "100011", Store.YES));
		// TextField 分词
		document.add(new TextField("title", "《跑男》《极限挑战3》相继停播，政策已成为今年综艺的最大风险", Store.YES));
		// StringField 不分词
		StringField authorField = new StringField("author", "文娱商业观察", Store.YES);
		document.add(authorField);
		// StringField 不分词
		document.add(new StringField("createTime", "100010", Store.YES));
		// TextField 分词
		TextField contentField = new TextField("content",
				"虽然安全播了三季，但每年都要被停播个那么几周。果不其然，这周日《极限挑战》又要停播，联系前段时间东方卫视《金星秀》《今晚80后脱口秀》宣布停播，这次《极限挑战》的停播是在警示谁？",
				Store.YES);
		contentField.setBoost(10);
		document.add(contentField);
		// StringField 不分词
		document.add(new StringField("url", "https://www.huxiu.com/article/213893.html", Store.YES));

		// 第三步：使用indexWriter创建索引
		indexWriter.addDocument(document);
		// commit将内存中等待的数据提交到硬盘创建索引。
		indexWriter.commit();

		// 第四步：关闭indexwriter
		indexWriter.close();
	}
	
	
	@Test
	public void createIndexByIkAnalyzer() throws IOException {
		// 需求:使用lucene创建索引
		// 1.使用某种技术
		// document--->分词--->倒排--->写入到索引库----介质(硬盘或者内存)

		Directory d = FSDirectory.open(new File("index"));
		IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
		// 第一步:创建indexWriter，传入两个参数，一个directory(FSDriectory,RAMDriectory);一个是IndexWriterConfig
		// IndexWriterConfig,传入两个参数，一个是版本号，一个分词器(中文分词器在3.1时过期了，推荐使用标准StandardAnalyzer)
		IndexWriter indexWriter = new IndexWriter(d, conf);

		// 第二步:对文件创建索引----虎嗅网站：id,title,author,createTime,content,url
		// 一个文档中，有很多字段，想通过title进行查询，这个字段需要被indexed。
		// 如果一个想创建索引，有两种选择，一种不被分词就是完全匹配，一种是分词。
		Document document = new Document();
		// StringField 不分词
		document.add(new StringField("id", "100011", Store.YES));
		// TextField 分词
		document.add(new TextField("title", "播客 《跑男》《极限挑战3》相继停播，政策已成为今年综艺的最大风险", Store.YES));
		// StringField 不分词
		StringField authorField = new StringField("author", "文娱商业观察", Store.YES);
		document.add(authorField);
		// StringField 不分词
		document.add(new StringField("createTime", "100010", Store.YES));
		// TextField 分词
		TextField contentField = new TextField("content",
				"大数据虽然安全播了三季，但每年都要被停播个那么几周。果不其然，这周日《极限挑战》又要停播，联系前段时间东方卫视《金星秀》《今晚80后脱口秀》宣布停播，这次《极限挑战》的停播是在警示谁？",
				Store.YES);
		contentField.setBoost(10);
		document.add(contentField);
		// StringField 不分词
		document.add(new StringField("url", "https://www.huxiu.com/article/213893.html", Store.YES));

		// 第三步：使用indexWriter创建索引
		indexWriter.addDocument(document);
		// commit将内存中等待的数据提交到硬盘创建索引。
		indexWriter.commit();

		// 第四步：关闭indexwriter
		indexWriter.close();
	}
}
