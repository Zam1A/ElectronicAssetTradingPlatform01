package net.wms.bean;

public class Storage {
	private int id;
	private String storagename;
	private String storagestyle;
	private String storageID;
	private String seller;
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStoragename() {
		return storagename;
	}
	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}
	public String getStoragestyle() {
		return storagestyle;
	}
	public void setStoragestyle(String storagestyle) {
		this.storagestyle = storagestyle;
	}
	public String getStorageID() {
		return storageID;
	}
	public void setStorageID(String storageID) {
		this.storageID = storageID;
	}
	
}
