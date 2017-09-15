package cn.itcast.lucene.search.domian;

public class Article {
	private int id; //编号
	private String title; //标题
	private String author; //作者
	private String createTime; //创建时间
	private String sc; //收藏数
	private String pl;//评论数
	private String zan; //点赞数
	private String content; //内容
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSc() {
		return sc;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public String getZan() {
		return zan;
	}
	public void setZan(String zan) {
		this.zan = zan;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", author=" + author + ", createTime=" + createTime + ", sc="
				+ sc + ", pl=" + pl + ", zan=" + zan + ", content=" + content + "]";
	}
}
