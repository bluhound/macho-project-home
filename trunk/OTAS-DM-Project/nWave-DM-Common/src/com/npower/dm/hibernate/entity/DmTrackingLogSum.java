/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DmTrackingLogSum.java,v 1.4 2009/02/12 06:49:19 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2009/02/12 06:49:19 $
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


/**
 * DmTrackingLogSum generated by MyEclipse Persistence Tools
 */

public class DmTrackingLogSum extends AbstractDmTrackingLogSum  implements java.io.Serializable {


    // Fields

     /** default constructor */
    public DmTrackingLogSum() {
      super();
    }
    
    /** full constructor */
    public DmTrackingLogSum(long jobId, String deviceId, Date beginTimeStamp, Date endTimeStamp, long requestSum, long responseSum) {
      super(jobId, deviceId, beginTimeStamp, endTimeStamp, requestSum, responseSum); 
    }
       
    /**
     * Return duration time in milliseconds.
     * @return
     */
    public long getDuration(){
      if (this.getBeginTimeStamp() == null || this.getEndTimeStamp() == null) {
        return 0;
      } else {
        long result = this.getEndTimeStamp().getTime() - this.getBeginTimeStamp().getTime();
        return result;
      }
    }
}    
   







