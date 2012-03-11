/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/JobQueueProcessor.java,v 1.19 2008/08/04 04:25:28 zhao Exp $
  * $Revision: 1.19 $
  * $Date: 2008/08/04 04:25:28 $
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
package com.npower.dm.processor;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;

import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * Processor for JobQueue, this processor served for the device which has been 
 * provisioned more than one jobs.
 * <br>
 * <pre>
 * �δ��������Զ�һ�ε������������Ŷ���ҵ. 
 * ���û������ȴ�����, ��������������
 * 1. GenericProcessor.
 * 
 * 
 * </pre>
 * @author Zhao DongLu
 * @version $Revision: 1.19 $ $Date: 2008/08/04 04:25:28 $
 */
public class JobQueueProcessor extends BaseProcessor {

  private static transient Log log = LogFactory.getLog(JobQueueProcessor.class);

  
  private ManagementProcessor currentProcessor = null;

  /**
   * ���ڼ�¼��ǰQueueProcessor�ڴ˴�Session���������в����д����Job, ��ҪΪJobQueueProcessor���ȴ���ĸ�����Processor�ڶ�̬����Job��֪ͨ
   * QueueProcessor����Щ�����ݲ�������, �ȴ���һ��Session�����ٽ��д���.
   */
  private Set<Long> excludedJobIDs = new HashSet<Long>();

  /**
   * 
   */
  public JobQueueProcessor() {
    super();
  }

  /**
   * @return Returns the excludedJobIDs.
   */
  public Set<Long> getExcludedJobIDs() {
    return excludedJobIDs;
  }

  /**
   * @param excludedJobIDs The excludedJobIDs to set.
   */
  public void setExcludedJobIDs(Set<Long> excludedJobIDs) {
    this.excludedJobIDs = excludedJobIDs;
  }

  // Private methods ****************************************************************************************
  
  /**
   * ����Processor Name��ȡProcessor instance.
   * @param processorClassName
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
  private ManagementProcessor getProcessorByName(String processorClassName) throws InstantiationException,
      IllegalAccessException, ClassNotFoundException {
    ManagementProcessor processor = (ManagementProcessor)(Class.forName(processorClassName).newInstance());
    return processor;
  }
  
  /**
   * ����JobQueue��״��, ���ص�ǰ�����Ӧ��Processor.
   * @param factory
   * @return
   * @throws ManagementException
   */
  private ManagementProcessor getNextProcessor(ManagementBeanFactory factory) throws ManagementException {
    ProvisionJobBean bean = factory.createProvisionJobBean();
    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByID(Long.toString(this.getDeviceID()));
        List<ProvisionJob> jobs = bean.findJobsQueued(device);
        ManagementProcessor processor = null;
        if (jobs.size() > 0) {
           ProvisionJob job = jobs.get(0);
           if (!this.excludedJobIDs.contains(new Long(job.getID()))) {
              // �μ�excludedJobIDs��˵��, ���ر�������Job�ݲ�������
              String processorClassName = JobProcessSelector.getProcessorByJobType(job.getJobType());
              processor = getProcessorByName(processorClassName);
             
              // Set job ID to mssid
              this.sessionContext.getDmstate().mssid = "" + job.getID();
           }
        }
        if (processor != null && processor instanceof BaseProcessor) {
           ((BaseProcessor)processor).setParentProcessor(this);
        }
        return processor;
    } catch (Exception ex) {
      throw new ManagementException("Error in getNextProcessor()", ex);
    }
  }

  /**
   * ��ȡ��һ��Processor
   * @param factory
   * @return
   * @throws ManagementException
   */
  private ManagementProcessor getFirstProcessor(ManagementBeanFactory factory) throws ManagementException {
    try {
        ManagementProcessor processor = this.getNextProcessor(factory);
        if (processor == null) {
           processor = getDefaultProcessor();
        }
        if (processor != null && processor instanceof BaseProcessor) {
           ((BaseProcessor)processor).setParentProcessor(this);
        }
        return processor;
    } catch (Exception ex) {
      throw new ManagementException("Error in getNextProcessor()", ex);
    }
  }

  /**
   * ����Default Processor.
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
  private ManagementProcessor getDefaultProcessor() throws InstantiationException, IllegalAccessException,
      ClassNotFoundException {
    String processorClassName = GenericProcessor.class.getName();
    ManagementProcessor processor = getProcessorByName(processorClassName);
    return processor;
  }
  /**
   * The readObject method is responsible for reading from the stream and restoring the classes fields,
   * and restore the transient fields.
   */
  protected void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    super.readObject(in);
    log = LogFactory.getLog(JobQueueProcessor.class);
  }  
  
  // Public methods *****************************************************************************************
  
  // Implements ManagementProcessor interface ***************************************************************
  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#beginSession(sync4j.framework.engine.dm.SessionContext)
   */
  @Override
  public void beginSession(SessionContext context) throws ManagementException {
    this.sessionContext = context;

    String        sessionId = context.getSessionId();
    Principal     principal         = context.getPrincipal();
    int           type      = context.getType();
    DevInfo       devInfo   = context.getDevInfo();

    if (log.isDebugEnabled()) {
       log.debug("Starting a new DM management session");
       log.debug("sessionId: " + sessionId          );
       log.debug("principal: " + principal          );
       log.debug("type: "      + type               );
       log.debug("deviceId: "  + devInfo            );
    }
    
    ManagementBeanFactory factory = null;
    try {
        // Load Device bundled with this Session
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        String deviceExternalID = this.sessionContext.getDmstate().deviceId;
        Device device = loadDeviceByExternalID(factory, deviceExternalID);
        if (device == null) {
           throw new ManagementException("Could not load device: from DM inventory.");
        }
        this.setDeviceID(device.getID());
        
        this.currentProcessor = this.getFirstProcessor(factory);
        if (this.currentProcessor == null) {
           throw new ManagementException("no any jobs wait for processing for the device: " + this.getDeviceID());
        }
        this.currentProcessor.beginSession(context);
    } catch(Exception ex) {
      throw new ManagementException("Error in beginSession: ", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#endSession(int)
   */
  @Override
  public void endSession(int completionCode) throws ManagementException {
    if (log.isDebugEnabled()) {
       log.debug("End a DM management session with sessionId: " + sessionContext.getSessionId());
    }
   
    try {
        /**
         * ע�⣺
         * ���µ�ǰJob��״̬, �������е�Job״̬����ÿһ��Processor�Լ����, ֱ���Ͽ�����Ҫ��JobQueueProcessor��
         * ����״̬. ������SessionHandle����ĳһ��Jobʧ��ʱ, �����JobQueueProcessor��endSession����, ֪ͨ
         * processor����ʧ��. �����JobQueueProcessor�б����״̬֪ͨ����ǰ����doing״̬��Job, ���Ը�״̬����
         * �޸�, ����ǰJob����Զ����doing״̬.
         * 
         * ����QueueProcessor����ӦΨһ��һ��Job, ��this.dmState.mssid���Ǳ���ͼ�¼��ǰ���ڴ��������, ���, 
         * setJobStatus4EndSession()�ܹ���ȷ�ĶԵ�ǰ��Job״̬��������.
         * 
         * this.dmState.mssid��getNextProcessor()��������ά��, ÿ�ε��ø÷�����ȡ��һ��Processor, ���ὫjobID����
         * ��this.dmState.mssid��.
         * 
         */
        // update the jobStatus for end session
        super.setJobStatus4EndSession(completionCode);
    } catch(Exception ex) {
      throw new ManagementException("Could not load device: from DM inventory.", ex);
    } finally {
      // Tracking Job
      this.trackJobLog(this.sessionContext);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#getNextOperations()
   */
  @Override
  public ManagementOperation[] getNextOperations() throws ManagementException {
    ManagementBeanFactory factory = null;
    try {
        ManagementOperation[] operations = this.currentProcessor.getNextOperations();
        if (operations == null || operations.length == 0) {
           this.currentProcessor.endSession(DeviceDMState.STATE_COMPLETED);
           // Get next processor
           factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
           this.currentProcessor = this.getNextProcessor(factory);
           if (this.currentProcessor == null) {
              // nothing in job of queue.
              return new ManagementOperation[0];
           } else {
             // Init next processor.
             this.currentProcessor.beginSession(this.sessionContext);
             operations = this.currentProcessor.getNextOperations();
             return operations;
           }
        } else {
          return operations;
        }
    } catch (Exception ex) {
      throw new ManagementException("Failure in get next processor.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#setOperationResults(sync4j.framework.engine.dm.ManagementOperationResult[])
   */
  @Override
  public void setOperationResults(ManagementOperationResult[] results) throws ManagementException {
    if (this.currentProcessor != null) {
       this.currentProcessor.setOperationResults(results);
    }
  }

}
