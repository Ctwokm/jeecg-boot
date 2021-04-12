package org.jeecg.modules.activiti.entity;

import java.io.Serializable;

/**
 * Created by xieyt on 2017-3-28.
 */
public class SampleInfoEntity implements Serializable {

    private static final long serialVersionUID = 2210021542439541182L;

    private String sampleId;//系统主键

    private String examOrderId;//检测业务单ID

    private String sampleName;//样品名称

    private String sampleCode;//对于电厂来说=采样编码

    private String samplingCode;//制样码

    private String laborCode;//化验码

    private String sampleType;//烟煤、炉渣。字典表：sample_type

    private String sampleGranular;//来样粒度是个连续值

    private String sampleWeight;//重量

    private String packCode;//采/来样封装码

    private String packCnt;//样品包数

    private String srcSampleCd;//对于抽检样，就是电厂编码

    private String createDt;//创建日期

    private String createMan;//创建人

    private String modifyMan;//修改人

    private String modifyDt;//修改日期

    private String remark;// 备注

    private String examItemCd;//检测项目编码

    private String examItemName; //检测项目名称

    private String examIndexName; //检测指标名称

    public String getExamIndexName() {
        return examIndexName;
    }

    public void setExamIndexName(String examIndexName) {
        this.examIndexName = examIndexName;
    }

    public String getExamItemCd() {
        return examItemCd;
    }

    public void setExamItemCd(String examItemCd) {
        this.examItemCd = examItemCd;
    }

    public String getExamItemName() {
        return examItemName;
    }

    public void setExamItemName(String examItemName) {
        this.examItemName = examItemName;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getExamOrderId() {
        return examOrderId;
    }

    public void setExamOrderId(String examOrderId) {
        this.examOrderId = examOrderId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getSamplingCode() {
        return samplingCode;
    }

    public void setSamplingCode(String samplingCode) {
        this.samplingCode = samplingCode;
    }

    public String getLaborCode() {
        return laborCode;
    }

    public void setLaborCode(String laborCode) {
        this.laborCode = laborCode;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleGranular() {
        return sampleGranular;
    }

    public void setSampleGranular(String sampleGranular) {
        this.sampleGranular = sampleGranular;
    }

    public String getSampleWeight() {
        return sampleWeight;
    }

    public void setSampleWeight(String sampleWeight) {
        this.sampleWeight = sampleWeight;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getPackCnt() {
        return packCnt;
    }

    public void setPackCnt(String packCnt) {
        this.packCnt = packCnt;
    }

    public String getSrcSampleCd() {
        return srcSampleCd;
    }

    public void setSrcSampleCd(String srcSampleCd) {
        this.srcSampleCd = srcSampleCd;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getModifyMan() {
        return modifyMan;
    }

    public void setModifyMan(String modifyMan) {
        this.modifyMan = modifyMan;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
