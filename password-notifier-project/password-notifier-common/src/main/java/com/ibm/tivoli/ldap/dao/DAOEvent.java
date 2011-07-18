/**
 * 
 */
package com.ibm.tivoli.ldap.dao;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ibm.tivoli.ldap.AdminContext;

/**
 * @author ZhaoDongLu
 * 
 */
public class DAOEvent<T> {
  // TODO �����㹻���¼�������Ϣ

  /**
   * DAO���¼�����
   */
  private String action;

  /**
   * DAO�¼��Ĳ�����
   */
  private AdminContext actor;

  /**
   * DAO�¼�������Ŀ�����
   */
  private T target;

  /**
   * ���������������磺ʧ�ܡ��ɹ��ȣ�
   */
  private String status;

  /**
   * �¼��Ľ�һ��������Ϣ
   */
  private String description;

  /**
   * 
   */
  public DAOEvent() {
    super();
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public AdminContext getActor() {
    return actor;
  }

  public void setActor(AdminContext actor) {
    this.actor = actor;
  }

  public T getTarget() {
    return target;
  }

  public void setTarget(T target) {
    this.target = target;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
