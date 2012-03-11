/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DDFNodeMIMETypeEntity.java,v 1.3 2006/06/03 16:11:44 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2006/06/03 16:11:44 $
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

import com.npower.dm.core.DDFNode;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2006/06/03 16:11:44 $
 */
public class DDFNodeMIMETypeEntity extends AbstractDDFNodeMIMEType implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public DDFNodeMIMETypeEntity() {
    super();
  }

  /** full constructor */
  public DDFNodeMIMETypeEntity(DDFNodeMIMETypeID id, DDFNode nwDmDdfNode) {
    super(id, nwDmDdfNode);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.DDFNodeMIMEType#getMimeType()
   */
  public String getMimeType() {
    return this.getID().getMimeType();
  }

}
