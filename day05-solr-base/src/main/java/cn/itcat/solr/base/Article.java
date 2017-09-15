package cn.itcat.solr.base;

import org.apache.solr.client.solrj.beans.Field;

import java.util.ArrayList;

/**
 * Created by maoxiangyi on 2017/9/11.
 */
public class Article {

    @Field
    private String id;
    @Field
    private String title;
    @Field
    private String url;
    @Field
    private String author;
    @Field
    private String content;

    public Article() {
    }

    public Article(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    //alt+insert 快速导出 getset快捷生成按钮
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
