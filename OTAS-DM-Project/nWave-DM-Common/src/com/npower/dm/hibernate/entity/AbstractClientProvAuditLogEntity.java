package com.npower.dm.hibernate.entity;
// default package

import java.util.Date;

import com.npower.dm.core.ClientProvAuditLog;


/**
 * AbstractClientProvAuditLogEntity generated by MyEclipse Persistence Tools
 */

public abstract class AbstractClientProvAuditLogEntity  implements java.io.Serializable ,ClientProvAuditLog {


    // Fields    

     private Long cpAuditLogId;
     private long jobId = 0;
     private Date timeStamp;
     private String devicePhoneNumber;
     private String manufacturerExtID;
     private String modelExtID;
     private String carrierExtID;
     private String status;
     private String profileCategoryName;
     private String profileName;
     private String profileContent;
     private String messageID;
     private String messageEncoder;
     private String memo;


    // Constructors

    /** default constructor */
    public AbstractClientProvAuditLogEntity() {
    }

	/** minimal constructor */
    public AbstractClientProvAuditLogEntity(Date timeStamp, String devicePhoneNumber, String status) {
        this.timeStamp = timeStamp;
        this.devicePhoneNumber = devicePhoneNumber;
        this.status = status;
    }
    
    /** full constructor */
    public AbstractClientProvAuditLogEntity(Date timeStamp, String devicePhoneNumber, String manufacturerExtID, String modelExtID, String carrierExtID, String status, String profileCategoryName, String profileName, String profileContent, String memo) {
        this.timeStamp = timeStamp;
        this.devicePhoneNumber = devicePhoneNumber;
        this.manufacturerExtID = manufacturerExtID;
        this.modelExtID = modelExtID;
        this.carrierExtID = carrierExtID;
        this.status = status;
        this.profileCategoryName = profileCategoryName;
        this.profileName = profileName;
        this.profileContent = profileContent;
        this.memo = memo;
    }

   
    // Property accessors

    public Long getCpAuditLogId() {
        return this.cpAuditLogId;
    }
    
    public void setCpAuditLogId(Long cpAuditLogId) {
        this.cpAuditLogId = cpAuditLogId;
    }

    public Date getTimeStamp() {
        return this.timeStamp;
    }
    
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDevicePhoneNumber() {
        return this.devicePhoneNumber;
    }
    
    public void setDevicePhoneNumber(String devicePhoneNumber) {
        this.devicePhoneNumber = devicePhoneNumber;
    }

    public String getManufacturerExtID() {
        return this.manufacturerExtID;
    }
    
    public void setManufacturerExtID(String manufacturerExtID) {
        this.manufacturerExtID = manufacturerExtID;
    }

    public String getModelExtID() {
        return this.modelExtID;
    }
    
    public void setModelExtID(String modelExtID) {
        this.modelExtID = modelExtID;
    }

    public String getCarrierExtID() {
        return this.carrierExtID;
    }
    
    public void setCarrierExtID(String carrierExtID) {
        this.carrierExtID = carrierExtID;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfileCategoryName() {
        return this.profileCategoryName;
    }
    
    public void setProfileCategoryName(String profileCategoryName) {
        this.profileCategoryName = profileCategoryName;
    }

    public String getProfileName() {
        return this.profileName;
    }
    
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileContent() {
        return this.profileContent;
    }
    
    public void setProfileContent(String profileContent) {
        this.profileContent = profileContent;
    }

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * @return Returns the messageID.
     */
    public String getMessageID() {
      return messageID;
    }

    /**
     * @param messageID The messageID to set.
     */
    public void setMessageID(String messageID) {
      this.messageID = messageID;
    }

    /**
     * @return Returns the jobId.
     */
    public long getJobId() {
      return jobId;
    }

    /**
     * @param jobId The jobId to set.
     */
    public void setJobId(long jobId) {
      this.jobId = jobId;
    }

    /**
     * @return Returns the messageEncoder.
     */
    public String getMessageEncoder() {
      return messageEncoder;
    }

    /**
     * @param messageEncoder The messageEncoder to set.
     */
    public void setMessageEncoder(String messageEncoder) {
      this.messageEncoder = messageEncoder;
    }
 
}