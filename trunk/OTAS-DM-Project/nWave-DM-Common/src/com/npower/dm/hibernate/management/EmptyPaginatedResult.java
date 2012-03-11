/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/EmptyPaginatedResult.java,v 1.2 2008/08/26 07:59:38 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/26 07:59:38 $
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
package com.npower.dm.hibernate.management;

import java.util.ArrayList;

import com.npower.dm.management.PaginatedResult;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/08/26 07:59:38 $
 */
public class EmptyPaginatedResult extends AbstractPaginatedResult implements PaginatedResult {

  /**
   * 
   */
  protected EmptyPaginatedResult() {
    super();
  }

  /**
   * 
   */
  public EmptyPaginatedResult(int pageNumber, int objectsPerPage) {
    super(pageNumber, objectsPerPage);
  }

  // protected methods
  // ------------------------------------------------------------------------------------
  protected void load() {
    this.fullListSize = 0;
    this.results = new ArrayList();
  }

}
