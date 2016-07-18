package me.kuye.spider.dto.answer;

import java.util.Arrays;

public class AnswerResult {
	private int r;
	private String[] msg;

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public String[] getMsg() {
		return msg;
	}

	public void setMsg(String[] msg) {
		this.msg = msg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(msg);
		result = prime * result + r;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnswerResult other = (AnswerResult) obj;
		if (!Arrays.equals(msg, other.msg))
			return false;
		if (r != other.r)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AnswerResult [r=" + r + ", msg=" + Arrays.toString(msg) + "]";
	}

}
