/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/SoftwareRatingEntity.java,v 1.2 2008/01/28 10:30:12 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/01/28 10:30:12 $
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

import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareRating;

/**
 * SoftwareRatingEntity generated by MyEclipse Persistence Tools
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/01/28 10:30:12 $
 */
public class SoftwareRatingEntity extends AbstractSoftwareRating implements java.io.Serializable, SoftwareRating {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = -823441986711199092L;

  /** default constructor */
  public SoftwareRatingEntity() {
  }

  /** minimal constructor */
  public SoftwareRatingEntity(long ratingId) {
    super(ratingId);
  }

  /** full constructor */
  public SoftwareRatingEntity(long ratingId, String name, SoftwarePackage softwarePackage, long rate) {
    super(ratingId, name, softwarePackage, rate);
  }

}
