package com.suye.iblog.component;




import com.suye.iblog.moder.Catalog;

import java.io.Serializable;

public class CatalogItem implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private String username;
	private Catalog catalog;
	
	public CatalogItem() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

}
