package cn.itcast.solr.cloud;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by maoxiangyi on 2017/9/15.
 */
public class CloudMain {
    private CloudSolrServer solrServer;

    @Before
    public void createSolrServer() {
        solrServer = new CloudSolrServer("zk01:2181,zk02:2181,zk03:2181");
        solrServer.setDefaultCollection("mycore2");
        solrServer.setZkClientTimeout(3000);
        solrServer.setZkConnectTimeout(3000);
        solrServer.connect();
    }

    // 向solrCloud上创建索引
    @Test
    public void testCreateIndexToSolrCloud() throws SolrServerException, IOException {

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "100001");
        document.addField("title", "李四");
        solrServer.add(document);
        solrServer.commit();

    }

    // 搜索索引
    @Test
    public void testSearchIndexFromSolrCloud() throws Exception {

        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        try {
            QueryResponse response = solrServer.query(query);
            SolrDocumentList docs = response.getResults();

            System.out.println("文档个数：" + docs.getNumFound());
            System.out.println("查询时间：" + response.getQTime());

            for (SolrDocument doc : docs) {
                ArrayList title = (ArrayList) doc.getFieldValue("title");
                String id = (String) doc.getFieldValue("id");
                System.out.println("id: " + id);
                System.out.println("title: " + title);
                System.out.println();
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unknowned Exception!!!!");
            e.printStackTrace();
        }
    }

    // 删除索引
    @Test
    public void testDeleteIndexFromSolrCloud() throws SolrServerException, IOException {

        // 根据id删除
        UpdateResponse response = solrServer.deleteById("100001");
        // 根据多个id删除
        // cloudSolrServer.deleteById(ids);
        // 自动查询条件删除
        // cloudSolrServer.deleteByQuery("product_keywords:教程");
        // 提交
        solrServer.commit();
    }
}
