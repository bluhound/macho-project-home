/**
 * 
 */
package com.ibm.tivoli.ldap.dao;

/**
 * @author ZhaoDongLu
 *
 */
public interface DAOListener<T> {
  
  /**
   * ����һ��DAO�¼� 
   * @param event
   * @throws DAOException
   */
  public void fireEvent(T event) throws DAOException;

}
