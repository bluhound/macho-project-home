package com.ibm.tivoli.ldap;

public abstract class GlobalIDGenerator {
  /**
   * ����һ��ErGlobalID
   * @return
   */
  public static String generate() {
    return System.currentTimeMillis() + "";
  }
}
