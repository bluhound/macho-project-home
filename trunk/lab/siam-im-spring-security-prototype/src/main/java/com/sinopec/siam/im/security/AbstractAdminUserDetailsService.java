/**
 * 
 */
package com.sinopec.siam.im.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author zhaodonglu
 *
 */
public abstract class AbstractAdminUserDetailsService implements UserDetailsService {

  /**
   * 
   */
  public AbstractAdminUserDetailsService() {
    super();
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
   */
  public SIAMAdminUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SIAMAdminUserDetails userDetails = new SIAMAdminUserDetails();
    userDetails.setUsername(username);
    // 1. Load basic information & PersonDN from TIM LDAP, fill into a SIAMAdminUserDetails
    loadBasicInfoIntoUserDetails(username, userDetails);
    
    // 3. Load GrantedAuthorities (Я���������ĸ�������Ա��ɫ, �Լ�����Ͻ��Ŀ�����(����: Ӧ�ú���֯������))
    loadAuthoritiesIntoUserDetails(username, userDetails);
    
    return userDetails;
  }

  /**
   * ���ع����û���Ȩ�ޣ���䵽Ŀ�����UserDetails��.
   * 
   * @param username  ��ѯ���û���ʶ
   * @param userDetails  ���Ȩ����Ϣ��Ŀ�����.
   * @throws UsernameNotFoundException
   */
  protected abstract void loadAuthoritiesIntoUserDetails(String username, SIAMAdminUserDetails userDetails) throws UsernameNotFoundException;

  /**
   * �����û��������û�������Ϣ��UserDN,��䵽Ŀ�����UserDetails��.
   * @param username  ��ѯ���û���ʶ
   * @param userDetails  ���Ȩ����Ϣ��Ŀ�����.
   * @throws UsernameNotFoundException  δ�ҵ���Ӧ���û�
   */
  protected abstract void loadBasicInfoIntoUserDetails(String username, SIAMAdminUserDetails userDetails) throws UsernameNotFoundException;

}
