package cn.itcast.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 使用lucene创建索引和查询索引
 * 
 * @author maoxiangyi
 *
 */
public class LuceneQuery {
	private IndexSearcher indexSearcher;
	private TopDocs res;

	@Before
	public void initIndexSearcher() throws Exception {
		// 需求:使用lucene查询索引
		FSDirectory directory = FSDirectory.open(new File("index"));
		// 第一步:创建IndexSearcher，IndexSearcher 是基于索引库进行查询 ，需要先读取索引库。
		indexSearcher = new IndexSearcher(DirectoryReader.open(directory));
	}

	@Test
	public void searchIndex() throws IOException, Exception {

		// TermQuery query = new TermQuery(new Term("content", "战"));

//		//// 通过两次置换，能够得到一个词条
//		// Term term = new Term("content","大据数");
//		// FuzzyQuery query = new FuzzyQuery(term);
//
//		//通过前缀查询
////		PrefixQuery query = new PrefixQuery(new Term("content", "大"));
//		
////		WildcardQuery query = new WildcardQuery(new Term("content","大*"));
//		
//		
////		BooleanQuery query = new BooleanQuery();
////
////		WildcardQuery wildcardQuery = new WildcardQuery(new Term("content", "大*"));
////		query.add(wildcardQuery, Occur.MUST);
////
////		PrefixQuery prefixQuery = new PrefixQuery(new Term("content", "金"));
////		query.add(prefixQuery, Occur.MUST);
//		
//		//重要：在大多数情况下，用户的输入不一定是一个词条，
//		//所以我们需要对用户的输入进行分词，将输入编程多个词条之后进行查询。
////		Analyzer analyzer = new IKAnalyzer();
////		QueryParser queryParser = new QueryParser("content", analyzer);
////		Query query =queryParser.parse("学习大数据");
		
		//重要：有时候业务会提供多个字段供用户选择，店铺，商家，旺旺。
		MultiFieldQueryParser multiFieldQueryParser 
		= new MultiFieldQueryParser(new String[]{"title","content"},new IKAnalyzer());
		Query query =multiFieldQueryParser.parse("学习大数据");
		
		res = indexSearcher.search(query, Integer.MAX_VALUE);

	}

	@After
	public void printRes() throws Exception {
		// 第四步：从topdocs中获取数据
		System.out.println("当前查询命中多少天数据:" + res.totalHits);
		ScoreDoc[] scoreDocs = res.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			System.out.println("维护在lucene内部的文档编号:" + scoreDoc.doc);
			System.out.println("当前文档的得分:" + scoreDoc.score);
			// 第五步:获取单篇文章的信息
			Document doc = indexSearcher.doc(scoreDoc.doc);
			System.out.println("id:   " + doc.get("id"));
			System.out.println("title:   " + doc.get("title"));
			System.out.println("author:   " + doc.get("author"));
			System.out.println("createTime:   " + doc.get("createTime"));
			System.out.println("content:   " + doc.get("content"));
			System.out.println("url:   " + doc.get("url"));
		}
	}

}
