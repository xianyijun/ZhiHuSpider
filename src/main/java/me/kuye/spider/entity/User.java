package me.kuye.spider.entity;

public class User {

	private String location;
	private String business;
	private String gender;
	private String employment;
	private String position;
	private String education;
	private String educationExtra;
	private String userName;
	private String userUrl;
	private int agree;
	private int thanks;
	private int followees;
	private int followers;
	private String hashId;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmployment() {
		return employment;
	}

	public void setEmployment(String employment) {
		this.employment = employment;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEducationExtra() {
		return educationExtra;
	}

	public void setEducationExtra(String educationExtra) {
		this.educationExtra = educationExtra;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserUrl() {
		return userUrl;
	}

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}

	public int getAgree() {
		return agree;
	}

	public void setAgree(int agree) {
		this.agree = agree;
	}

	public int getThanks() {
		return thanks;
	}

	public void setThanks(int thanks) {
		this.thanks = thanks;
	}

	public int getFollowees() {
		return followees;
	}

	public void setFollowees(int followees) {
		this.followees = followees;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public String getHashId() {
		return hashId;
	}

	public void setHashId(String hashId) {
		this.hashId = hashId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + agree;
		result = prime * result + ((business == null) ? 0 : business.hashCode());
		result = prime * result + ((education == null) ? 0 : education.hashCode());
		result = prime * result + ((educationExtra == null) ? 0 : educationExtra.hashCode());
		result = prime * result + ((employment == null) ? 0 : employment.hashCode());
		result = prime * result + followees;
		result = prime * result + followers;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((hashId == null) ? 0 : hashId.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + thanks;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userUrl == null) ? 0 : userUrl.hashCode());
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
		User other = (User) obj;
		if (agree != other.agree)
			return false;
		if (business == null) {
			if (other.business != null)
				return false;
		} else if (!business.equals(other.business))
			return false;
		if (education == null) {
			if (other.education != null)
				return false;
		} else if (!education.equals(other.education))
			return false;
		if (educationExtra == null) {
			if (other.educationExtra != null)
				return false;
		} else if (!educationExtra.equals(other.educationExtra))
			return false;
		if (employment == null) {
			if (other.employment != null)
				return false;
		} else if (!employment.equals(other.employment))
			return false;
		if (followees != other.followees)
			return false;
		if (followers != other.followers)
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (hashId == null) {
			if (other.hashId != null)
				return false;
		} else if (!hashId.equals(other.hashId))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (thanks != other.thanks)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userUrl == null) {
			if (other.userUrl != null)
				return false;
		} else if (!userUrl.equals(other.userUrl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [location=" + location + ", business=" + business + ", gender=" + gender + ", employment="
				+ employment + ", position=" + position + ", education=" + education + ", educationExtra="
				+ educationExtra + ", userName=" + userName + ", userUrl=" + userUrl + ", agree=" + agree + ", thanks="
				+ thanks + ", followees=" + followees + ", followers=" + followers + ", hashId=" + hashId + "]";
	}
}
