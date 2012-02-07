/**
 * 
 */
package com.ibm.siam.ds.metadata;

import org.opensaml.saml2.metadata.EntitiesDescriptor;

/**
 * Discovery�������һ��������IDP�嵥�����е���Щ IDP��ͬ����һ��IDP������. �˽ӿڶ������ⷢ��IDP�嵥�ķ���.
 * @author zhaodonglu
 *
 */
public interface FederatedIDPMetadataPublisher {
  
  /**
   * Return all IdP metadata which defined in configuration.
   * @return
   */
  public abstract EntitiesDescriptor getAllIdPMetadata();
}
