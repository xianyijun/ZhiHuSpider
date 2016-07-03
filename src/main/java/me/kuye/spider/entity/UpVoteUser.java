package me.kuye.spider.entity;

/**
 * @author xianyijun
 *
 */
public class UpVoteUser implements Entity {
	
	private static final long serialVersionUID = -5279850430034997640L;
	private String avatar;
	private String name;
	private String bio;
	private String agree;
	private String thanks;
	private String asks;
	private String answers;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getThanks() {
		return thanks;
	}

	public void setThanks(String thanks) {
		this.thanks = thanks;
	}

	public String getAsks() {
		return asks;
	}

	public void setAsks(String asks) {
		this.asks = asks;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agree == null) ? 0 : agree.hashCode());
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((asks == null) ? 0 : asks.hashCode());
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + ((bio == null) ? 0 : bio.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((thanks == null) ? 0 : thanks.hashCode());
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
		UpVoteUser other = (UpVoteUser) obj;
		if (agree == null) {
			if (other.agree != null)
				return false;
		} else if (!agree.equals(other.agree))
			return false;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (asks == null) {
			if (other.asks != null)
				return false;
		} else if (!asks.equals(other.asks))
			return false;
		if (avatar == null) {
			if (other.avatar != null)
				return false;
		} else if (!avatar.equals(other.avatar))
			return false;
		if (bio == null) {
			if (other.bio != null)
				return false;
		} else if (!bio.equals(other.bio))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (thanks == null) {
			if (other.thanks != null)
				return false;
		} else if (!thanks.equals(other.thanks))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UpVoteUser [avatar=" + avatar + ", name=" + name + ", bio=" + bio + ", agree=" + agree + ", thanks="
				+ thanks + ", asks=" + asks + ", answers=" + answers + "]";
	}

	@Override
	public String getKey() {
		return "upVoteUser";
	}

}
