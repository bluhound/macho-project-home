/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/digester/ProfileNodeMappingEntityFactory.java,v 1.1 2006/11/08 02:16:53 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2006/11/08 02:16:53 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.hibernate.management.digester;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;
import org.xml.sax.Attributes;

import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.hibernate.entity.ProfileNodeMappingEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileMappingBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2006/11/08 02:16:53 $
 */
public class ProfileNodeMappingEntityFactory implements ObjectCreationFactory {

  private Digester digester = null;
  /**
   * 
   */
  public ProfileNodeMappingEntityFactory() {
    super();
  }

  /* (non-Javadoc)
   * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
   */
  public Object createObject(Attributes attrs) throws Exception {
    ManagementBeanFactory factory = this.getFactory();
    ProfileMappingBean bean = factory.createProfileMappingBean();
    ProfileNodeMapping result = bean.newProfileNodeMappingInstance();
    if (result instanceof ProfileNodeMappingEntity) {
       ((ProfileNodeMappingEntity)result).setManagementBeanFactory(this.getFactory());
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.apache.commons.digester.ObjectCreationFactory#getDigester()
   */
  public Digester getDigester() {
    return this.digester;
  }

  /* (non-Javadoc)
   * @see org.apache.commons.digester.ObjectCreationFactory#setDigester(org.apache.commons.digester.Digester)
   */
  public void setDigester(Digester digester) {
    this.digester = digester;
  }
  
  // private methods -------------------------------------------------------------
  private ManagementBeanFactory getFactory() {
    Object root = this.digester.peek(this.digester.getCount() - 1);
    return (ManagementBeanFactory)root;
  }

}
