package cn.itcast.lucene.search.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.itcast.lucene.search.dao.ArticleIndexDao;
import cn.itcast.lucene.search.domian.Article;

@Service
public class ArtilceIndexDaoImpl implements ArticleIndexDao {

	// 需要一个索引库写入的类
	private IndexWriter indexWriter;
	// 需要一个索引库的实例，用来保存索引
	private Directory directory;
	// 需要一个读取索引的实例
	private IndexSearcher indexSearcher;

	public ArtilceIndexDaoImpl() {

	}

	public void createIndex(Article article) throws Exception {
		Document doc = new Document();
		System.out.println(article);
		doc.add(new IntField("id", article.getId(), Store.YES));
		// title需要 即存储又索引
		doc.add(new TextField("title", article.getTitle(), Store.YES));
		doc.add(new StringField("author", article.getAuthor(), Store.YES));
		doc.add(new StringField("createTime", article.getCreateTime(), Store.YES));
		doc.add(new StringField("sc", article.getSc(), Store.YES));
		doc.add(new StringField("pl", article.getPl(), Store.YES));
		doc.add(new StringField("zan", article.getZan(), Store.YES));
		// 文章可以不用保存，我们一般通过文章的id去数据库查询文章的信息。
		doc.add(new TextField("content", article.getContent(), Store.YES));
		getIndexWriter().addDocument(doc);
		getIndexWriter().commit();
	}

	public List<Article> queryIndexByQuery(String field, String keyword, int resNum) throws Exception {
		List<Article> result = new ArrayList<Article>();
		QueryParser queryParser = new QueryParser(field, new IKAnalyzer());
		Query query = queryParser.parse(keyword);
		TopDocs response = getIndexSearcher().search(query, resNum);
		// 迭代文档
		for (ScoreDoc scoreDoc : response.scoreDocs) {
			Article article = new Article();
			Document doc = getIndexSearcher().doc(scoreDoc.doc);
			article.setId(Integer.parseInt(doc.get("id")));
			article.setTitle(doc.get("title"));
			article.setAuthor(doc.get("author"));
			result.add(article);
		}
		return result;
	}

	/**
	 * 创建IndexWriter
	 * 
	 * @return IndexWriter
	 * @throws Exception
	 */
	private synchronized IndexWriter getIndexWriter() throws Exception {
		if (indexWriter == null) {
			indexWriter = new IndexWriter(getDriectory(), new IndexWriterConfig(Version.LATEST, new IKAnalyzer()));
		}
		return indexWriter;
	}

	/**
	 * 创建一个IndexSeracher
	 * 
	 * @return IndexSearcher
	 * @throws Exception
	 */
	private IndexSearcher getIndexSearcher() throws Exception {
		if (indexSearcher == null) {
			// 创建索引 需要一个directory
			Directory directory = getDriectory();
			// 使用DirectoryReader 打开
			DirectoryReader reader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(reader);
		}
		return indexSearcher;
	}

	/**
	 * 创建一个lucene索引库
	 * 
	 * @return FSDirectory
	 * @throws Exception
	 */
	private synchronized Directory getDriectory() throws Exception {
		if (directory == null) {
			directory = FSDirectory.open(new File("index"));
		}
		return directory;
	}

}
