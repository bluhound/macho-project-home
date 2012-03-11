/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/chain/cp/DiscardCpJobProcessor.java,v 1.1 2008/08/01 06:45:39 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/01 06:45:39 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.dispatch.chain.cp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.ClientProvAuditLog;
import com.npower.dm.core.ClientProvJobTargetDevice;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.dispatch.chain.BaseDispatcherProcessor;
import com.npower.dm.dispatch.chain.BreakProcessorChainException;
import com.npower.dm.dispatch.chain.DispatcherProcessor;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.OTAClientProvJobBean;

/**
 * ����豸��ҪԤ��Bootstrap, �����Ҫ�ύBootstrap����.
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/01 06:45:39 $
 */
public class DiscardCpJobProcessor extends BaseDispatcherProcessor implements DispatcherProcessor {

  private static Log log = LogFactory.getLog(DiscardCpJobProcessor.class);
  
  /**
   * 
   */
  public DiscardCpJobProcessor() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#isNeedToProcess(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  public boolean isNeedToProcess(ProvisionJob job, Device device) throws DMException {
    if (!ProvisionJob.JOB_MODE_CP.equalsIgnoreCase(job.getJobMode())) {
       // ����CP Job
       return false;
    }
    long maxRetries4Boot = (job.getMaxRetries() < 1)?device.getSubscriber().getCarrier().getBootstrapMaxRetries():job.getMaxRetries();
    OTAClientProvJobBean jobBean = this.getManagementBeanFactory().createOTAClientProvJobBean(null, null);
    ClientProvJobTargetDevice deviceStatus = jobBean.getProvisionJobStatus(job, device);
    if (deviceStatus.getNumberOfEnqueueRetries() >= maxRetries4Boot) {
       // ����������, ��������
       return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.DispatcherProcessor#process(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  @SuppressWarnings("finally")
  public void process(ProvisionJob job, Device device) throws DMException, BreakProcessorChainException {
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    try {
        OTAClientProvJobBean jobBean = this.getManagementBeanFactory().createOTAClientProvJobBean(null, null);
        ClientProvJobTargetDevice deviceStatus = jobBean.getProvisionJobStatus(job, device);
        
        log.info("The JobID: " + job.getID() + " for device: " + deviceStatus.getDeviceId() + " has been cancelled");
        factory.beginTransaction();
        deviceStatus.setStatus(ClientProvAuditLog.STATUS_CANCELLED);
        //deviceStatus.set("Cancelled by job dispatcher, reach maxium retries!");
        jobBean.update(deviceStatus);
        factory.commit();
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      
      log.error("Failure to discard job:" + job.getID() + " for deviceID: " + device.getID(), ex);
      throw new DMException("Failure to discard job:" + job.getID() + " for deviceID: " + device.getID(), ex);
    } finally {
      // Break Processor Chain
      // Job was canceled, break processor chain.
      throw new BreakProcessorChainException();
    }
  }

}
