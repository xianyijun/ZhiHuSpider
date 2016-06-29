package me.kuye.spider.entity;

import java.io.Serializable;

/**
 * @author xianyijun
 * 标志接口，需要持久化类都需要实现该接口
 */
public interface Entity extends Serializable{
	/**
	* @Title: getKey
	* @Description: 返回Entity key,mongodb对应的就是collection键
	* @param     参数
	* @return String    返回类型
	* @throws
	*/
	public String getKey();
}
