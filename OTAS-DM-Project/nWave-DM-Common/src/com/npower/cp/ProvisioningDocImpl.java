/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/ProvisioningDocImpl.java,v 1.1 2007/05/24 08:48:48 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/05/24 08:48:48 $
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
package com.npower.cp;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/05/24 08:48:48 $
 */
class ProvisioningDocImpl implements ProvisioningDoc {
  
  private String buffer = null;

  /**
   * Default constructor
   */
  private ProvisioningDocImpl() {
    super();
  }

  /**
   * Constructor
   */
  public ProvisioningDocImpl(StringWriter writer) {
    this();
    this.buffer = writer.toString();
  }

  /* (non-Javadoc)
   * @see com.npower.cp.ProvisioningDoc#getReader()
   */
  public Reader getReader() throws OTAException {
    return new StringReader(buffer);
  }
  
  /* (non-Javadoc)
   * @see com.npower.cp.ProvisioningDoc#getContent()
   */
  public String getContent() throws OTAException {
    return this.buffer;
  }

}
