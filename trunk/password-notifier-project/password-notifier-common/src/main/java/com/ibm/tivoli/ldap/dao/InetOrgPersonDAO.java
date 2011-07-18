/**
 * 
 */
package com.ibm.tivoli.ldap.dao;

import java.util.List;

import com.ibm.tivoli.ldap.entity.InetOrgPerson;


/**
 * @author ZhaoDongLu
 *
 */
public interface InetOrgPersonDAO {
  
  /**
   * ����globalID��ȡ��Դ����. 
   * @param resourceGlobalID ��Դ��erGlobalId
   * @return ���δ�ҵ�����null
   * @throws DAOException
   */
  public abstract InetOrgPerson getByUserID(String uid) throws DAOException;

  /**
   * ����LDAP Filter��ѯ��Դ
   * @param baseResourceGlobalID
   * @param filter
   * @param level ��ʾ��ѯ�Ĳ㼶
   * @return
   * @throws DAOException
   */
  public abstract List<InetOrgPerson> findByFilter(String baseDN, String filter) throws DAOException;
  
}
