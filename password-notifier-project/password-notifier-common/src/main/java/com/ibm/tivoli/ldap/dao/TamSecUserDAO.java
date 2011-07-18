/**
 * 
 */
package com.ibm.tivoli.ldap.dao;

import java.util.List;

import com.ibm.tivoli.ldap.entity.TamSecUser;


/**
 * @author ZhaoDongLu
 *
 */
public interface TamSecUserDAO {
  
  /**
   * ����globalID��ȡ��Դ����. 
   * @param resourceGlobalID ��Դ��erGlobalId
   * @return ���δ�ҵ�����null
   * @throws DAOException
   */
  public abstract TamSecUser getByUserID(String uid) throws DAOException;

  /**
   * ����LDAP Filter��ѯ��Դ
   * @param baseResourceGlobalID
   * @param filter
   * @param level ��ʾ��ѯ�Ĳ㼶
   * @return
   * @throws DAOException
   */
  public abstract List<TamSecUser> findByFilter(String baseDN, String filter) throws DAOException;
  
}
