/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ClientProvJobEntity.java,v 1.3 2008/06/05 10:34:41 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/06/05 10:34:41 $
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
package com.npower.dm.hibernate.entity;

import java.util.Date;
import java.util.Set;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/06/05 10:34:41 $
 */
public class ClientProvJobEntity extends AbstractClientProvJob implements java.io.Serializable {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = -6436274242519784744L;

  /** default constructor */
  public ClientProvJobEntity() {
    super();
  }

  /** minimal constructor */
  public ClientProvJobEntity(String state, Date createdTime) {
    super(state, createdTime);
  }

  /** full constructor */
  public ClientProvJobEntity(String name, String type, String description, String state, Date scheduleTime, long maxRetries,
      long maxDuration, Date createdTime, String createdBy, Date lastUpdatedTime, String lastUpdatedBy,
      Set targetDevices) {
    super(name, type, description, state, scheduleTime, maxRetries, maxDuration, createdTime, createdBy,
        lastUpdatedTime, lastUpdatedBy, targetDevices);
  }

}
