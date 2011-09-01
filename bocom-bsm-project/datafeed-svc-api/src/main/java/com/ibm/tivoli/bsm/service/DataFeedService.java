/**
 * 
 */
package com.ibm.tivoli.bsm.service;

import javax.jws.WebService;

/**
 * @author ZhaoDongLu
 *
 */
@WebService
public interface DataFeedService {
   
  /**
   * Receive and process data feed request
   * @param xml
   * @return
   */
  public abstract DataFeedResponse process(String xml);
  
  /**
   * ΪӦ��Ԥ���Ľӿڣ�ʵ�ַ�ʽ����
   * @param xml
   * @return
   */
  //public abstract DataFeedResponse submitXMLData(String xml);
}
