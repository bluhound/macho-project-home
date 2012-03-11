/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ModelClientProvMapEntity.java,v 1.2 2007/05/24 06:01:02 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/05/24 06:01:02 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.hibernate.entity;

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.Model;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/05/24 06:01:02 $
 */
public class ModelClientProvMapEntity extends AbstractModelClientProvMap implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public ModelClientProvMapEntity() {
  }

  /** minimal constructor */
  public ModelClientProvMapEntity(ModelClientProvMapId id) {
    super(id);
  }

  /** full constructor */
  public ModelClientProvMapEntity(ModelClientProvMapId id, ClientProvTemplate clientProvTemplateEntity, Model model) {
    super(id, clientProvTemplateEntity, model);
  }

}
