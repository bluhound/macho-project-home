/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/SimpleProvisionJobDispatcherImpl.java,v 1.3 2008/07/29 08:37:37 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/07/29 08:37:37 $
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
package com.npower.dm.dispatch;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/07/29 08:37:37 $
 */
public class SimpleProvisionJobDispatcherImpl extends BaseProvisionJobDispatcher implements ProvisionJobDispatcher {
  private static Log log = LogFactory.getLog(SimpleProvisionJobDispatcherImpl.class);
  
  private static Object globalLock = new Object();
  
  /**
   * Default Constructor
   */
  public SimpleProvisionJobDispatcherImpl() {
    super();
  }
  
  /**
   * @param jobNotificationSender
   */
  public SimpleProvisionJobDispatcherImpl(JobNotificationSender jobNotificationSender) {
    super();
    this.jobNotificationSender = jobNotificationSender;
  }

  // Private methods -----------------------------------------------------------
  /**
   * �豸�Ѿ�Bootstrap�ɹ�, ����Ƿ���Ҫ����Notify
   * @param job
   * @param deviceStatus
   * @return
   */
  private boolean readyToNotify(ProvisionJob job, ProvisionJobStatus deviceStatus) {
    if (deviceStatus.isFinished()) {
       // ���������, ����Notification
       return false;
    }
    if (deviceStatus.isDoing()) {
       // �������ڽ�����, ����Notification
       return false;
    }

    Device device = deviceStatus.getDevice();
    if (!device.isBooted()) {
       // �豸û��boot, ����Notification
       return false;
    }
    // �豸�Ѿ�Boot, �����������
    Carrier carrier = deviceStatus.getDevice().getSubscriber().getCarrier();
      
    long maxRetries = job.getMaxRetries();
    if (maxRetries <= 0) {
       maxRetries = carrier.getNotificationMaxNumRetries();
    }
    // In mile seconds
    long maxDuration = job.getMaxDuration();
    if (maxDuration <= 0) {
      maxDuration = carrier.getNotificationStateTimeout();
    }
    if (deviceStatus.getAskCount() >= maxRetries) {
       // ������������������Notification
       return false;
    }
    // ����Ƿ񵽴���ʱ��
    Date lastNotificationTime = device.getSubscriber().getNotificationTime();
    //lastNotificationTime = deviceStatus.getLastNotificationTime();
    if (lastNotificationTime != null &&
        lastNotificationTime.getTime() + maxDuration * 1000 > System.currentTimeMillis()) {
       // δ������ʱ��, ������
       return false; 
    }
    
    // 
    return true;
  }

  /**
   * ����Notification��, �޸�ProvisionJobStatus��״̬: ��������1, ���������һ�η���ʱ��
   * @param factory
   * @param jobBean
   * @param deviceStatus
   * @throws DMException
   */
  private void changeDeviceJobStatus4Notification(ManagementBeanFactory factory, ProvisionJobBean jobBean,
      ProvisionJobStatus deviceStatus) throws DMException {
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    synchronized (globalLock) {
      try {
          factory.beginTransaction();
          Date now = new Date();
          deviceStatus.setLastNotificationTime(now);
          deviceStatus.setAskCount(deviceStatus.getAskCount() + 1);
          jobBean.update(deviceStatus);
          // Ϊ�˷�ֹdispatchAll()��һ���绰�����ж����������, �ڲ��ܼ��ʱ��Ŀ������ظ���η���Notification, ����subscriber�м�¼���һ�η���Notification��ʱ�� 
          Subscriber subscriber = deviceStatus.getDevice().getSubscriber();
          subscriber.setNotificationTime(now);
          subscriberBean.update(subscriber);
          factory.commit();
      } catch (Exception ex) {
        factory.rollback();
        //throw new DMException(ex.getMessage(), ex);
        log.error(ex.getMessage(), ex);
      } finally {
      }
    }
  }

  /**
   * @param factory
   * @param jobBean
   * @param job
   * @param deviceStatus
   */
  private void notify(ManagementBeanFactory factory, ProvisionJobBean jobBean, ProvisionJob job,
      ProvisionJobStatus deviceStatus) {
    try {
        log.info("Dispatch notification (retry#" + deviceStatus.getAskCount() + 
                 ") for device ID: " + deviceStatus.getDevice().getID() + " JobID: " + job.getID());
        // Immediately Update device status
        changeDeviceJobStatus4Notification(factory, jobBean, deviceStatus);
        // Dispatch Notification
        JobNotificationSender jobNotificationDispacher = this.getJobNotificationSender();
        jobNotificationDispacher.notify(job.getID(), deviceStatus.getID(), deviceStatus.getDevice().getID(), System.currentTimeMillis());
    } catch (Exception ex) {
      log.error("Failure to dispatch notification (retry#" + deviceStatus.getAskCount() + 
                ") for device ID: " + deviceStatus.getDevice().getID() + " JobID: " + job.getID(), ex);
    } finally {
    }
  }

  /**
   * ����豸�Ƿ���Ҫ��Bootstrap
   * @param factory
   * @param job
   * @param deviceStatus
   * @return
   */
  private boolean needToBootstrap(ManagementBeanFactory factory, ProvisionJob job, ProvisionJobStatus deviceStatus) {
    Device device = deviceStatus.getDevice();
    if (device.isBooted()) {
       // Boostrap finished!
       return false;
    }
    
    Carrier carrier = device.getSubscriber().getCarrier();
    long maxDuration = carrier.getBootstrapTimeout();
    int maxRetries = carrier.getBootstrapMaxRetries();
    if (device.getBootstrapAskCounter() >= 2 * maxRetries) {
       return false;
    }
    
    if (device.getLastBootstrapTime() != null &&
        device.getLastBootstrapTime().getTime() + maxDuration * 1000 > System.currentTimeMillis()) {
       return false; 
    }
    return true;
  }

  /**
   * ����Bootstrap��, �޸�Boostrap��״̬: ��������1, ���������һ�η���ʱ��
   * @param factory
   * @param jobBean
   * @param deviceStatus
   * @throws DMException
   */
  private void changeDeviceBootstrapStatus(ManagementBeanFactory factory, Device device) throws DMException {
    synchronized (globalLock) {
      try {
          DeviceBean deviceBean = factory.createDeviceBean();
          factory.beginTransaction();
          device.setLastBootstrapTime(new Date());
          device.setBootstrapAskCounter(device.getBootstrapAskCounter() + 1);
          deviceBean.update(device);
          factory.commit();
      } catch (Exception ex) {
        factory.rollback();
        //throw new DMException(ex.getMessage(), ex);
        log.error(ex.getMessage(), ex);
      } finally {
      }
    }
  }

  /**
   * @param factory
   * @param jobBean
   * @param job
   * @param deviceStatus
   */
  private void bootstrap(ManagementBeanFactory factory, ProvisionJobBean jobBean, ProvisionJob job,
      ProvisionJobStatus deviceStatus) {
    try {
        Device device = deviceStatus.getDevice();
        // Immediately Update device status
        changeDeviceBootstrapStatus(factory, device);

        if (device.getBootstrapAskCounter() % 2 == 1) {
           log.info("Dispatch bootstrap (retry#" + deviceStatus.getDevice().getBootstrapAskCounter() + 
                   ") for deviceID: " + deviceStatus.getDevice().getID()  + " JobID: " + job.getID());
           // Dispatch Notification
           JobNotificationSender jobSender = this.getJobNotificationSender();
           jobSender.bootstrap(job.getID(), deviceStatus.getID(), deviceStatus.getDevice().getID(), System.currentTimeMillis(), job.getMaxRetries(), job.getMaxDuration());
        } else {
          log.info("Dispatch notification to fire bootstrap (retry#" + deviceStatus.getDevice().getBootstrapAskCounter() + 
              ") for deviceID: " + deviceStatus.getDevice().getID()  + " JobID: " + job.getID());
          // Dispatch Notification
          JobNotificationSender jobSender = this.getJobNotificationSender();
          jobSender.notify(job.getID(), deviceStatus.getID(), deviceStatus.getDevice().getID(), System.currentTimeMillis());
        }
    } catch (Exception ex) {
      log.error("Failure to dispatch boostrap (retry#" + deviceStatus.getDevice().getBootstrapAskCounter() + 
                ") for deviceID: " + deviceStatus.getDevice().getID()  + " JobID: " + job.getID(), ex);
    } finally {
    }
  }

  /**
   * ����Ƿ���Ҫ����Device Job
   * @param job
   * @param deviceStatus
   * @return
   */
  private boolean needToCancel(ProvisionJob job, ProvisionJobStatus deviceStatus) {
    Device device = deviceStatus.getDevice();
    Carrier carrier = device.getSubscriber().getCarrier();
    int maxRetries = carrier.getBootstrapMaxRetries();
    if (device.getBootstrapAskCounter() >= 2 * maxRetries) {
       // �������Bootstrap����
       return true;
    }
    
    maxRetries = (int)job.getMaxRetries();
    if (maxRetries <= 0) {
       maxRetries = (int)carrier.getNotificationMaxNumRetries();
    }
    if (deviceStatus.getAskCount() >= maxRetries) {
       // �������Notification����
       
       long maxDuration = job.getMaxDuration(); // In mile seconds
       if (maxDuration <= 0) {
         maxDuration = carrier.getNotificationStateTimeout();
       }
       // ����Ƿ񵽴���ʱ��
       Date lastNotificationTime = device.getSubscriber().getNotificationTime();
       //lastNotificationTime = deviceStatus.getLastNotificationTime();
       if (lastNotificationTime != null &&
           lastNotificationTime.getTime() + maxDuration * 1000 <= System.currentTimeMillis()) {
          // δ������ʱ��, ������
          return true; 
       }
    }
    return false;
  }

  private void cancelJob(ManagementBeanFactory factory, ProvisionJobBean jobBean, ProvisionJob job,
      ProvisionJobStatus deviceStatus) {
    log.info("The JobID: " + job.getID() + " for device: " + + deviceStatus.getDevice().getID() + " has been cancelled");
    //synchronized (globalLock) {
      try {
          factory.beginTransaction();
          deviceStatus.setState(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED);
          deviceStatus.setCause("Cancelled by job dispatcher, reach maxium retries!");
          jobBean.update(deviceStatus);
          factory.commit();
      } catch (Exception ex) {
        factory.rollback();
        //throw new DMException(ex.getMessage(), ex);
        log.error(ex.getMessage(), ex);
      } finally {
      }
    //}
  }

  /**
   * <pre>
   * �����������£�
   * 1������Ƿ���ҪBootstrap
   * 2�������ҪBootstrap, ����Bootstrap����
   *    a�����Bootstrap�Ƿ��Ѿ���������, ���ﵽ�ͺ���Notification��ʱ��
   *    b�����������Bootstrap֮���ͺ���Notification��ʱ������, ����Notification
   * 5��������跢��Bootstrap, ����Ƿ���Ҫ����Notification
   *    a����������, ����Notification
   * </pre>
   * Dispatch a provision job
   * @param factory
   * @param jobBean
   * @param job
   * @throws DMException
   */
  private void dispatch(ManagementBeanFactory factory, ProvisionJobBean jobBean, ProvisionJob job) throws DMException {
    // ��������DMģʽ������
    if (ProvisionJob.JOB_MODE_DM.equalsIgnoreCase(job.getJobMode())
        && job.isRequiredNotification()) {
      if (job.getScheduledTime().getTime() > System.currentTimeMillis()) {
         // δ�������ʱ��
         return;
      }
      for (ProvisionJobStatus deviceStatus: job.getAllOfProvisionJobStatus()) {
          if (needToBootstrap(factory, job, deviceStatus )) {
             // Dispatch Bootstrap message
             this.bootstrap(factory, jobBean, job, deviceStatus);
          } else if (readyToNotify(job, deviceStatus)) {
            // Dispatch Notification
            this.notify(factory, jobBean, job, deviceStatus);
          } else if (needToCancel(job, deviceStatus)) {
            // Reach max retries, cancel it
            this.cancelJob(factory, jobBean, job, deviceStatus);
          }
      }
    }
  }

  // Implements ProvisionJobDispatcher interface -------------------------------

  /* (non-Javadoc)
   * @see com.npower.dm.service.ProvisionJobDispatcher#dispatch()
   */
  public void dispatchAll() throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        List<ProvisionJob> jobs = jobBean.findJobsQueued(true);
        for (ProvisionJob job: jobs) {
            dispatch(factory, jobBean, job);
        }
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new DMException(ex.getMessage(), ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.ProvisionJobDispatcher#dispatch(long)
   */
  public void dispatch(long jobID) throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        ProvisionJob job = jobBean.loadJobByID(jobID);
        dispatch(factory, jobBean, job);
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new DMException(ex.getMessage(), ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
