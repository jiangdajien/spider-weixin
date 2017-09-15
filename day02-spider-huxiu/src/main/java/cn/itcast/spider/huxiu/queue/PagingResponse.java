package cn.itcast.spider.huxiu.queue;

public class PagingResponse {
	
	/**
	 * 
	 * 
	 * 
	 * result: 1, msg: "获取成功",…}
data:"<div>这里是数据</div>"
last_dateline:"1504321620"
msg:"获取成功"
result:1
total_page:1585
	 * 
	 * 
	 */
	
	private int result;
	private String msg;
	private String data;
	private String last_dateline;
	private int total_page;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getLast_dateline() {
		return last_dateline;
	}
	public void setLast_dateline(String last_dateline) {
		this.last_dateline = last_dateline;
	}
	public int getTotal_page() {
		return total_page;
	}
	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}
	@Override
	public String toString() {
		return "PagingResponse [result=" + result + ", msg=" + msg + ", data=" + data + ", last_dateline="
				+ last_dateline + ", total_page=" + total_page + "]";
	}
	
}
