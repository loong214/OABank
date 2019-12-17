package com.findingdata.oabank.entity;

/**
 * Created by Loong on 2019/12/17.
 * Version: 1.0
 * Describe: 项目的属性实体
 */
public class PropertyList {
    private static final long serialVersionUID = 1L;

    /**
     * PROPERTY_ID : 1761
     * PROJECT_ID : 1481
     * PROPERTY_NAME : 测试超时情况的项目
     * PROPERTY_TYPE : 40002001
     * PROPERTY_TYPE_CHS : 住宅类型
     * PCA_CODE : 430102
     * PCA_CODE_CHS : 芙蓉区
     * ADDRESS : 地址不迷裙楼改成
     * AREA : 125.55
     * INSPECTION_CONTACT :
     * INSPECTION_CONTACT_PHONE :
     * LATITUDE : 0
     * LONGITUDE : 0
     * CREATE_BY : 1165
     * CREATE_TIME : 2019-12-17T11:34:16
     * IS_DELETED : 0
     * IS_QUICK_MODE : 0
     * TOTAL_FLOOR : 0
     * LOCATED_FLOOR : 0
     * PROPERTY_FORM_ID : 0
     */

    private int PROPERTY_ID;
    private int PROJECT_ID;
    private String PROPERTY_NAME;
    private int PROPERTY_TYPE;
    private String PROPERTY_TYPE_CHS;
    private int PCA_CODE;
    private String PCA_CODE_CHS;
    private String ADDRESS;
    private double AREA;
    private String INSPECTION_CONTACT;
    private String INSPECTION_CONTACT_PHONE;
    private int LATITUDE;
    private int LONGITUDE;
    private int CREATE_BY;
    private String CREATE_TIME;
    private int IS_DELETED;
    private int IS_QUICK_MODE;
    private int TOTAL_FLOOR;
    private int LOCATED_FLOOR;
    private int PROPERTY_FORM_ID;

    public int getPROPERTY_ID() {
        return PROPERTY_ID;
    }

    public void setPROPERTY_ID(int PROPERTY_ID) {
        this.PROPERTY_ID = PROPERTY_ID;
    }

    public int getPROJECT_ID() {
        return PROJECT_ID;
    }

    public void setPROJECT_ID(int PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public String getPROPERTY_NAME() {
        return PROPERTY_NAME;
    }

    public void setPROPERTY_NAME(String PROPERTY_NAME) {
        this.PROPERTY_NAME = PROPERTY_NAME;
    }

    public int getPROPERTY_TYPE() {
        return PROPERTY_TYPE;
    }

    public void setPROPERTY_TYPE(int PROPERTY_TYPE) {
        this.PROPERTY_TYPE = PROPERTY_TYPE;
    }

    public String getPROPERTY_TYPE_CHS() {
        return PROPERTY_TYPE_CHS;
    }

    public void setPROPERTY_TYPE_CHS(String PROPERTY_TYPE_CHS) {
        this.PROPERTY_TYPE_CHS = PROPERTY_TYPE_CHS;
    }

    public int getPCA_CODE() {
        return PCA_CODE;
    }

    public void setPCA_CODE(int PCA_CODE) {
        this.PCA_CODE = PCA_CODE;
    }

    public String getPCA_CODE_CHS() {
        return PCA_CODE_CHS;
    }

    public void setPCA_CODE_CHS(String PCA_CODE_CHS) {
        this.PCA_CODE_CHS = PCA_CODE_CHS;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public double getAREA() {
        return AREA;
    }

    public void setAREA(double AREA) {
        this.AREA = AREA;
    }

    public String getINSPECTION_CONTACT() {
        return INSPECTION_CONTACT;
    }

    public void setINSPECTION_CONTACT(String INSPECTION_CONTACT) {
        this.INSPECTION_CONTACT = INSPECTION_CONTACT;
    }

    public String getINSPECTION_CONTACT_PHONE() {
        return INSPECTION_CONTACT_PHONE;
    }

    public void setINSPECTION_CONTACT_PHONE(String INSPECTION_CONTACT_PHONE) {
        this.INSPECTION_CONTACT_PHONE = INSPECTION_CONTACT_PHONE;
    }

    public int getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(int LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public int getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(int LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public int getCREATE_BY() {
        return CREATE_BY;
    }

    public void setCREATE_BY(int CREATE_BY) {
        this.CREATE_BY = CREATE_BY;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public int getIS_DELETED() {
        return IS_DELETED;
    }

    public void setIS_DELETED(int IS_DELETED) {
        this.IS_DELETED = IS_DELETED;
    }

    public int getIS_QUICK_MODE() {
        return IS_QUICK_MODE;
    }

    public void setIS_QUICK_MODE(int IS_QUICK_MODE) {
        this.IS_QUICK_MODE = IS_QUICK_MODE;
    }

    public int getTOTAL_FLOOR() {
        return TOTAL_FLOOR;
    }

    public void setTOTAL_FLOOR(int TOTAL_FLOOR) {
        this.TOTAL_FLOOR = TOTAL_FLOOR;
    }

    public int getLOCATED_FLOOR() {
        return LOCATED_FLOOR;
    }

    public void setLOCATED_FLOOR(int LOCATED_FLOOR) {
        this.LOCATED_FLOOR = LOCATED_FLOOR;
    }

    public int getPROPERTY_FORM_ID() {
        return PROPERTY_FORM_ID;
    }

    public void setPROPERTY_FORM_ID(int PROPERTY_FORM_ID) {
        this.PROPERTY_FORM_ID = PROPERTY_FORM_ID;
    }
}
