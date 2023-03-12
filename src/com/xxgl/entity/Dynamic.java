package com.xxgl.entity;

import java.util.List;

/** 
 * 说明：动态表管理 实体类
 * 创建人：huangjianling
 * 创建时间：2019-06-18
 */
public class Dynamic{ 
	
	private String ID;	//主键
	private String NAME;					//名称
	private String C_PHYSICSNAME;			//表名
	private String TYPE;	//资源库类型
	private String PARENT_ID;				//父类ID
	private String target;
	private Dynamic dynamic;
	private List<Dynamic> subDynamic;
	private boolean hasDynamic = false;
	private String treeurl;
	
	private String CREATEMAN;			//创建人
	public String getFCREATEMAN() {
		return CREATEMAN;
	}
	public void setFCREATEMAN(String CREATEMAN) {
		this.CREATEMAN = CREATEMAN;
	}
	private String CREATEDATE;			//创建时间
	public String getFCREATEDATE() {
		return CREATEDATE;
	}
	public void setFCREATEDATE(String CREATEDATE) {
		this.CREATEDATE = CREATEDATE;
	}

	public String getID() {
		return ID;
	}
	public void setID(String ID) {
		this.ID = ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String PARENT_ID) {
		this.PARENT_ID = PARENT_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Dynamic getDynamic() {
		return dynamic;
	}
	public void setDynamic(Dynamic dynamic) {
		this.dynamic = dynamic;
	}
	public List<Dynamic> getSubDynamic() {
		return subDynamic;
	}
	public void setSubDynamic(List<Dynamic> subDynamic) {
		this.subDynamic = subDynamic;
	}
	public boolean isHasDynamic() {
		return hasDynamic;
	}
	public void setHasDynamic(boolean hasDynamic) {
		this.hasDynamic = hasDynamic;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	public String getC_PHYSICSNAME() {
		return C_PHYSICSNAME;
	}
	public void setC_PHYSICSNAME(String c_PHYSICSNAME) {
		C_PHYSICSNAME = c_PHYSICSNAME;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	
}
