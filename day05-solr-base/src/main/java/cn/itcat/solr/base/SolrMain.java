package cn.itcat.solr.base;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 操作solr，增删改查
 */
public class SolrMain {

    public static void main(String[] args) throws Exception {
//        //1.solrserver  httpsolrserver
//        HttpSolrServer httpSolrServer = new HttpSolrServer("http://127.0.0.1:8080/solr/collection1");
//        httpSolrServer.deleteById("3333");
//        httpSolrServer.commit();
        createIndexByBean();
        queryAndHighLight();



    }

    private static void createIndexByBean() throws IOException, SolrServerException {
        //1.solrserver  httpsolrserver
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://127.0.0.1:8080/solr/collection1");
        //2.插入一个javaBean
        httpSolrServer.addBean(new Article("3333","祖国 我爱idea编辑器","我的祖国很强大，很强大，祖国啊"));
        // 3.commit
        httpSolrServer.commit();
    }

    private static void getBeans() throws SolrServerException {
        //查询索引
        //1.solrserver  httpsolrserver
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://127.0.0.1:8080/solr/collection1");
        //2.查询
        String query = "title:祖国";
        SolrQuery solrQuery = new SolrQuery(query);
        QueryResponse res = httpSolrServer.query(solrQuery);
        List<Article> articleList = res.getBeans(Article.class);
//        3. 打印结果集
        for (Article article : articleList) {
            System.out.println(article);
        }
    }
    private static void queryAndHighLight() throws SolrServerException {
        //查询索引
        //1.solrserver  httpsolrserver
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://127.0.0.1:8080/solr/collection1");
        //2.查询
        String query = "祖国";

        SolrQuery solrQuery = new SolrQuery(query);
        // 设置高亮的步骤
        // 1.开启
        solrQuery.setHighlight(true);
        // 2.设置摘要的长度
        solrQuery.setHighlightFragsize(100);
        // 3.添加html标签
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        // 4. 指定哪些字段要添加高亮
        solrQuery.addHighlightField("title,content");
        QueryResponse res = httpSolrServer.query(solrQuery);
        //5、由于高亮的原理是基于已经查询出来的结果集，进行二次分词并添加html。
        // 所以在获取高亮的结果是，需要单独调用一个方法。
        Map<String, Map<String, List<String>>> highlighting = res.getHighlighting();
        for (Map.Entry<String, Map<String, List<String>>> map1 : highlighting.entrySet()) {
            System.out.println("map1-------------key:"+map1.getKey());
            Map<String, List<String>> map2 = map1.getValue();
            for (Map.Entry<String, List<String>> map2EntrySet : map2.entrySet()) {
                System.out.println(map2EntrySet.getKey());
                List<String> list = map2EntrySet.getValue();
                System.out.println(list);
            }
            System.out.println("-----------------------------------------------------------------------");

        }
    }



    private static void querySolrDocumentList() throws SolrServerException {
        //查询索引
        //1.solrserver  httpsolrserver
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://127.0.0.1:8080/solr/collection1");
        //2.查询
        String query = "title:祖国";
        SolrQuery solrQuery = new SolrQuery(query);
        QueryResponse res = httpSolrServer.query(solrQuery);
//        3. 打印结果集
        SolrDocumentList results = res.getResults();
        for(SolrDocument doc:results){
            System.out.println(doc);
        }
    }


    /**
     * 创建索引
     *
     * @throws SolrServerException
     * @throws IOException
     */
    private static void createIndex() throws SolrServerException, IOException {
        //1、连接solr，由于solr是一个web服务。
        //  http://127.0.0.1:8080/solr/ 使用默认collection1
        //  http://127.0.0.1:8080/solr/#/collection1 这种方式不行，去掉#就可以了
        String url = "http://127.0.0.1:8080/solr/collection1";
        SolrServer solrServer = new HttpSolrServer(url);
        //2、创建索引
        // 2.1、准备一个document对象
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "22222");
        doc.addField("title", "我爱我的祖国222222222222222");
        doc.addField("author", "传智播客222222222222222");
        doc.addField("content", "222222222222222这里是地下城与勇士专区 您的问题可能不属于这个分类下的..可能无法及时得到满意回答 建议您从新手动分类下. 这样才能快点得到精确的答案哦");
        doc.addField("url", "https://www.itcast.cn/");

        // 2.2、调用方法添加索引
        solrServer.add(doc);

        //3、commit提交索引
        solrServer.commit();
    }


}
