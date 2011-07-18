package com.ibm.tivoli.ldap.entity;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

/**
 * Resource对象对应的LDAP Objectclass为cu_resource
 * @author ZhaoDongLu
 *
 */
@Entry(objectClasses = { "top", "inetOrgPerson" })
public class InetOrgPerson {
  @Id
  private Name dn;
  
  @Attribute
  private String uid;
  
  @Attribute 
  private String cn;
  
  @Attribute 
  private String email;
  
  public InetOrgPerson() {
    super();
  }

  /**
   * @return the dn
   */
  public Name getDn() {
    return dn;
  }

  /**
   * @param dn the dn to set
   */
  public void setDn(Name dn) {
    this.dn = dn;
  }

  /**
   * @return the uid
   */
  public String getUid() {
    return uid;
  }

  /**
   * @param uid the uid to set
   */
  public void setUid(String uid) {
    this.uid = uid;
  }

  /**
   * @return the cn
   */
  public String getCn() {
    return cn;
  }

  /**
   * @param cn the cn to set
   */
  public void setCn(String cn) {
    this.cn = cn;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }
  
}
