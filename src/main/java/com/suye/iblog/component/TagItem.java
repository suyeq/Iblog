package com.suye.iblog.component;


import java.io.Serializable;

/**
 * 标签 值对象.
 */
public class TagItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Long count;

	public TagItem(String name, Long count) {
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
 
}
