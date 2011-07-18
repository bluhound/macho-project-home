package com.ibm.tivoli.ldap;

import java.util.Locale;

public class AdminContext {
  
  private String username;
  private char[] password;

  /**
   * ��¼�û���ʹ�õ�����
   */
  private Locale locale = Locale.getDefault();
  /**
   * Ԥ��: δ������֧��TAM Admin API
   */
  private String domain;
  /**
   * Ԥ����δ������֧��TAM Admin API
   */
  private String configFileUrl;

  public AdminContext(String adminUsername, char[] adminPassword) {
    super();
    this.username = adminUsername;
    this.password = adminPassword;
  }

  public AdminContext(String domain, String adminUsername, char[] adminPassword) {
    super();
    this.domain = domain;
    this.username = adminUsername;
    this.password = adminPassword;
  }

  public AdminContext(String domain, String adminUsername, char[] adminPassword, Locale locale) {
    super();
    this.domain = domain;
    this.username = adminUsername;
    this.password = adminPassword;
    this.locale = locale;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String adminUsername) {
    this.username = adminUsername;
  }

  public char[] getPassword() {
    return password;
  }

  public void setPassword(char[] adminPassword) {
    this.password = adminPassword;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getConfigFileUrl() {
    return configFileUrl;
  }

  public void setConfigFileUrl(String configFileUrl) {
    this.configFileUrl = configFileUrl;
  }

}
