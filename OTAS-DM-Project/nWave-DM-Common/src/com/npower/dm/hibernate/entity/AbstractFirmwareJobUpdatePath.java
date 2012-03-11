/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractFirmwareJobUpdatePath.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2006/04/25 16:31:09 $
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

import com.npower.dm.core.Update;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractFirmwareJobUpdatePath implements java.io.Serializable {

  // Fields

  private FirmwareJobUpdatePathID id;

  private Update nwDmUpdate;

  private JobState nwDmJobState;

  // Constructors

  /** default constructor */
  public AbstractFirmwareJobUpdatePath() {
  }

  /** minimal constructor */
  public AbstractFirmwareJobUpdatePath(FirmwareJobUpdatePathID id) {
    this.id = id;
  }

  /** full constructor */
  public AbstractFirmwareJobUpdatePath(FirmwareJobUpdatePathID id, Update nwDmUpdate, JobState nwDmJobState) {
    this.id = id;
    this.nwDmUpdate = nwDmUpdate;
    this.nwDmJobState = nwDmJobState;
  }

  // Property accessors

  public FirmwareJobUpdatePathID getId() {
    return this.id;
  }

  public void setId(FirmwareJobUpdatePathID id) {
    this.id = id;
  }

  public Update getNwDmUpdate() {
    return this.nwDmUpdate;
  }

  public void setNwDmUpdate(Update nwDmUpdate) {
    this.nwDmUpdate = nwDmUpdate;
  }

  public JobState getNwDmJobState() {
    return this.nwDmJobState;
  }

  public void setNwDmJobState(JobState nwDmJobState) {
    this.nwDmJobState = nwDmJobState;
  }

}