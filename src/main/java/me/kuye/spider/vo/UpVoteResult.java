package me.kuye.spider.vo;

public class UpVoteResult {
	private Paging paging;
	private int code;
	private boolean success;
	private String[] payload;

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String[] getPayload() {
		return payload;
	}

	public void setPayload(String[] payload) {
		this.payload = payload;
	}

}
