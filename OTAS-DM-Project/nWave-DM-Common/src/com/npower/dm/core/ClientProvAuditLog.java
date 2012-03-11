package com.npower.dm.core;

import java.util.Date;

/**
* @author Liu AiHui
* @version $Revision: 1.5 $ $Date: 2008/08/01 06:45:39 $
*/
public interface ClientProvAuditLog {

  /**
   * SMS STATUS: queued
   *     ��ʾ����Ϣû�гɹ��ύ��SMS Gateway�Ķ���, �ȴ��ٴ��ύ��SMS Gateway����
   */
  public final static String STATUS_WAIT = "wait_to_enqueue";
  
  /**
   * SMS STATUS: queued
   *     ��ʾ����Ϣ�Ѿ��ɹ��ύ��SMS Gateway�Ķ���
   */
  public final static String STATUS_QUEUED = "queued";
  
  /**
   * SMS STATUS: sent
   *     ��ʾ����Ϣ�Ѿ���SMS Gateway�ɹ��ͳ�
   */
  public final static String STATUS_SENT = "sent";

  /**
   * SMS STATUS: failure
   *     ��ʾ����Ϣû�б�SMS Gateway�ɹ��ͳ�, ��SMS Gateway����ʧ��
   */
  public final static String STATUS_FAILURE = "failure";

  /**
   * SMS STATUS: received
   *     ��ʾ����Ϣ�Ѿ��ɹ����ն��豸����
   */
  public final static String STATUS_RECEIVED = "received";

  /**
   * SMS STATUS: success
   *     Ԥ��, ��ʾ�����Ѿ��ɹ����, ���������Ѿ����ɹ���װ
   */
  public final static String STATUS_SUCCESS = "success";

  /**
   * SMS STATUS: cancelled
   *     ��ȡ��, ��ʾ��������е���������
   */
  public final static String STATUS_CANCELLED = "cancelled";

  /**
   * SMS STATUS: unkown
   *     ״̬δ֪
   */
  public final static String STATUS_UNKNOW = "unkown";

  /** 
   * @return Client Provider Audit Log of ID 
   */
  public abstract Long getCpAuditLogId() ;
  
  /**
   * @param Client Provider Audit Log of ID 
   */
  public abstract void setCpAuditLogId(Long cpAuditLogId) ;

  /** 
   * @return Client Provider Job ID 
   */
  public abstract long getJobId() ;
  
  /**
   * @param Client Provider Job ID 
   */
  public abstract void setJobId(long cpAuditLogId) ;

  /**
   * @return Send SMS Time
   */
  public abstract Date getTimeStamp() ;
  
  public abstract void setTimeStamp(Date timeStamp) ;

  /**
   * @return PhoneNumber
   */
  public abstract String getDevicePhoneNumber() ;
  
  public abstract void setDevicePhoneNumber(String devicePhoneNumber) ;

  /**
   * @return Manufacturer
   */
  public abstract String getManufacturerExtID() ;
  
  public abstract void setManufacturerExtID(String manufacturerExtID) ;

  /**
   * @return Model
   */
  public abstract String getModelExtID() ;
  
  public abstract void setModelExtID(String modelExtID) ;

  /**
   * @return Carrier
   */
  public abstract String getCarrierExtID() ;
  
  public abstract void setCarrierExtID(String carrierExtID) ;

  /**
   * Status:Send SMS Status
   *        1.Success
   *        2.Failure
   * @return Status
   */
  public abstract String getStatus() ;
  
  public abstract void setStatus(String status) ;

  /**
   * @return Profile Category
   */
  public abstract String getProfileCategoryName() ;
  
  public abstract void setProfileCategoryName(String profileCategoryName) ;

  /**
   * @return Profile Name
   */
  public abstract String getProfileName();
  
  public abstract void setProfileName(String profileName) ;

  /**
   * @return Profile Content (��������)
   */
  public abstract String getProfileContent() ;
  
  public abstract void setProfileContent(String profileContent) ;

  /**
   * ���Ͷ���Ϣ��SMS Gateway�������ID
   * @return Returns the messageID.
   */
  public abstract String getMessageID();

  /**
   * @param messageID The messageID to set.
   */
  public abstract void setMessageID(String messageID);

  /**
   * ���Ͷ���Ϣ��SMS Encoder
   * @return Returns the messageID.
   */
  public abstract String getMessageEncoder();

  /**
   * @param Encoder The Encoder to set.
   */
  public abstract void setMessageEncoder(String encoder);

  /**
   * @return Memo
   */
  public abstract String getMemo() ;
  
  public abstract void setMemo(String memo) ;

}
