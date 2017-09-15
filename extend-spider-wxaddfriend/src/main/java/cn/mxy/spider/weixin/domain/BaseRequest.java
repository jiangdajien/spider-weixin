package cn.mxy.spider.weixin.domain;

public class BaseRequest {
	private Long Uin;
	private String Sid;
	private String Skey;
	private String DeviceID;
	
	public String getDeviceID() {
		return DeviceID;
	}
	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

	
	
	
	public Long getUin() {
		return Uin;
	}
	public void setUin(Long uin) {
		Uin = uin;
	}
	public String getSid() {
		return Sid;
	}
	public void setSid(String sid) {
		Sid = sid;
	}
	public String getSkey() {
		return Skey;
	}
	public void setSkey(String skey) {
		Skey = skey;
	}
	@Override
	public String toString() {
		return "BaseRequest [Uin=" + Uin + ", Sid=" + Sid + ", Skey=" + Skey + ", DeviceID=" + DeviceID + "]";
	}
	
}
