# 搜索引擎底层核心技术Lucune

# 1、lucene 是什么
* Lucene是一套用来创建索引和查询索引的API，是构建搜索引擎的一部分。
* lucene 创建索引的核心类是 IndexWriter。
* lucene 查询索引的核心类是 IndexReader,有了IndexReader就可以构建IndexSearcher用来查询。 
* lucene 索引存储的地方可以叫做Directory,Directory 有两种类型一种是RAMDirectory，一种是FSDirectory。一般我们使用文件

# 2、创建基于Maven的lucene项目
```shell
	<dependency>
			<groupId>org.apache.lucene</groupId>
			<artifactId>lucene-core</artifactId>
			<version>4.10.2</version>
		</dependency>
		<dependency>
			<groupId>org.apache.lucene</groupId>
			<artifactId>lucene-queries</artifactId>
			<version>4.10.2</version>
		</dependency>
		<dependency>
			<groupId>org.apache.lucene</groupId>
			<artifactId>lucene-test-framework</artifactId>
			<version>4.10.2</version>
		</dependency>
		<dependency>
			<groupId>org.apache.lucene</groupId>
			<artifactId>lucene-analyzers-common</artifactId>
			<version>4.10.2</version>
		</dependency>
		<dependency>
			<groupId>org.apache.lucene</groupId>
			<artifactId>lucene-queryparser</artifactId>
			<version>4.10.2</version>
		</dependency>
```
注：当前最新版本的Lucene已经到了6.6版本，可以考虑使用最新版本。

# 3、Lucene IndexWriter
创建索引需要分词，分词可以使用默认的standardAnaylzer，也可以使用[外置的分词器](http://developer.51cto.com/art/201609/517563.htm)，就逼着了解目前市场上使用结巴分词，IK较多。
```java
	public void createIndex() throws Exception{
		
		//指定索引库的目录，在当前项目根目录下创建index。
		Directory directory  = FSDirectory.open(new File("index"));
		
		Analyzer analyzer = new StandardAnalyzer();
		//索引编辑的默认配置
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
		
		//创建索引编辑器，需要一个索引库的地址。需要IndexWriterConfig。
		IndexWriter indexWriter =new IndexWriter(directory, indexWriterConfig);
		
		//将外部数据转化成lucnne能够识别的文件格式，即Document
		Document doc = new Document();
		doc.add(new StringField("id", "1",Store.YES));
		doc.add(new TextField("title", "学习lucene",Store.YES));
		doc.add(new TextField("content", "学习lucene需要掌握搜索引擎的基本原理和lucene创建索引和查询索引",Store.YES));
		
		//将转化后的文档，提交给IndexWriter
		indexWriter.addDocument(doc);
		//提交
		indexWriter.commit();
		//关闭
		indexWriter.close();
	}
```

# 4、Lucene IndexReader
```java
	public void queryIndex() throws Exception {
		//3,指定索引库的地址
		Directory directory = FSDirectory.open(new File("index"));
		//2，创建一个IndexReader,指定读取的索引库的信息。
		IndexReader r = DirectoryReader.open(directory);
		//1，索引查询器，用来查询索引的API IndexSearcher。 指定索引库在哪里
		IndexSearcher indexSearcher = new IndexSearcher(r);
		
		//4，意图解析 
		Query query = new TermQuery(new Term("title","lucene2"));
		//5,查询，指定query和需要返回的条数
		TopDocs topDocs =indexSearcher.search(query, 10);
		
		//6，打印符合条件的结果数量
		System.out.println("符合条件的结果数量:"+topDocs.totalHits);
		for (ScoreDoc scoreDoc :topDocs.scoreDocs) {
			//6，打印符合条件的结果数量
			System.out.println("当前文章的得分："+scoreDoc.score);
			Document document = indexSearcher.doc(scoreDoc.doc);
			//6，打印符合条件的结果数量
			System.out.println(document.get("id"));
			System.out.println(document.get("title"));
			System.out.println(document.get("content"));
		}
		
	}
```