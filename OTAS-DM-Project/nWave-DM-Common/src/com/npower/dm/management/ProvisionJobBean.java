/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/ProvisionJobBean.java,v 1.34 2008/07/24 15:05:14 zhao Exp $
  * $Revision: 1.34 $
  * $Date: 2008/07/24 15:05:14 $
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
package com.npower.dm.management;

import java.util.Date;
import java.util.List;

import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.server.store.NotFoundException;

import com.npower.dm.core.APLinkValueNotFoundException;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Software;
import com.npower.dm.core.Update;

/**
 * All operations against Job will be conducted through this Bean.
 * @author Zhao DongLu
 * @version $Revision: 1.34 $ $Date: 2008/07/24 15:05:14 $
 */
public interface ProvisionJobBean extends BaseBean {
  /**
   * Create a job for discovery.<br>
   * For example:<br>
   *   Root Node will be "."<br>
   *   DevDetail will be "/DevDetail" or "DevDetail"<br>
   * 
   * @param group Group of devices.
   * @param nodePath <code>String[]</code> Array of Node Path for discovery.
   * @return
   * @throws DMException
   */
  public abstract ProvisionJob newJob4Discovery(DeviceGroup group, String[] nodePath) throws DMException;
  
  /**
   * Create a job for discovery.<br>
   * For example:<br>
   *   Root Node will be "."<br>
   *   DevDetail will be "/DevDetail" or "DevDetail"<br>
   * 
   * @param device
   * @param nodePath  <code>String[]</code>  Array of Node Path for discovery.
   * @return
   * @throws DMException
   */
  public abstract ProvisionJob newJob4Discovery(Device device, String[] nodePath) throws DMException;
  
  /**
   * Create a job for command script.
   * @param device device.
   * @param scripts InputStream for comamnd script.
   * @return
   * @throws DMException
   */
  public abstract ProvisionJob newJob4Command(Device device, String scripts) throws DMException;
  
  /**
   * Create a job for command scripts
   * @param group  group of devices.
   * @param scripts   InputStream for comamnd script.
   * @return
   * @throws DMException
   */
  public abstract ProvisionJob newJob4Command(DeviceGroup group, String scripts) throws DMException;
  
  /**
   * Create or submit a job for profile assignement.<br>
   * �������һ��Jobʹ�ø�ProfileAssignment, ���Ҹ�Job��״̬ΪReady, ���������µ�Job, ֱ�ӷ��ظ�Job.
   * �������Jobʹ�ø�ProfileAssignment, ���Ҹ�Job��״̬ΪDone��Error, ����һ���µ�Job, ��Job��JobTypeΪRe-Assign.
   * @param assignment
   * @return
   * @throws DMException
   */
  public abstract ProvisionJob newJob4Assignment(ProfileAssignment assignment) throws DMException;
  
  /**
   * <pre>
   * ����һ��ProfileAssgingment Job.
   * ���������Զ�����һ������Job, ���ն��豸ʵ�������·�������:
   * 1. �����Profile����һ��Proxy Profile, �������ڶ����д���һ��Proxy Profile��Job;
   * 2. �����Profile����һ��NAP Profile, �������ڶ����д���һ��NAP Profile��Job;
   * 3. ��������õ�Proxy Profile����������һ��NAP Profile, �������ڶ����д�����������õ�NAP Profile, Ȼ���ٴ���Proxy Profile
   * 4. �������õ�Profile������, ��󴴽�һ��Job��ɵ�ǰProfile���·�����.
   * 5. ��������õ�Proxy Profile��Nap Profile�Ѿ���Ŀ���豸���͹�, ����ЩProxy Profile��NAP Profile����Re-Assignmentģʽ����Job.
   * 
   * ���磺
   * �������һ��SyncDS Profile, ��SyncDS Profile����һ��Proxy Profile, �����Proxy Profile����һ��NAP Profile.
   * ��ΪSyncDS Profile����Jobʱ, �����ý���������Provision Job, ������:
   * 1. NAP Profile Job
   * 2. Proxy Profile Job
   * 3. SyncDS Profile Job
   * 
   * ע��:
   * �������ܹ��Զ�ʶ���Ƿ���Profilre Re-Assignment״̬, ������漰��Profile(����: �����õ�Proxy, NAP��)�д����Ѿ����ͳɹ������,
   * ������Re-Assignment��ģʽ�Ը�Profile�����·�.
   * 
   * ���÷���:
   * 
   * // Create or Get Assignment
   * ProfileAssignment assignment = null;
   * if (StringUtils.isNotEmpty(assignmentID)) {
   *    assignment = assignmentBean.getProfileAssignmentByID(assignmentID);
   * } else {
   *   // Create a assignment
   *   assignment = assignmentBean.newProfileAssignmentInstance(profile, device);
   * }
   * 
   * // ��assignment���õ�ÿһ��Profile Attribute ��ֵ
   * for (...) {
   *     value = ....;
   *     assignmentBean.setAttributeValue(assignment, attrName, value);
   * }
   *  
   * factory.beginTransaction();
   * List<ProvisionJob> jobs = jobBean.newJobs4Assignment(assignment, jobName, jobDescription, scheduledTime);
   * factory.commit();
   *  
   * </pre>
   * @param assignment
   *        ProfileAssignment 
   * @param jobName
   *        Name of Job
   * @param jobDescription
   *        Description of job
   * @param scheduledTime
   *        Time of schedule for this job. Job��ִ��ʱ��.
   * @return
   * @throws DMException
   */
  public abstract List<ProvisionJob> newJobs4Assignment(ProfileAssignment assignment, String jobName, String jobDescription, Date scheduledTime) throws DMException;
  
  /**
   * Create and submit a job for firmware update.
   * @param device
   *        The device which firmware will update.
   * @param update
   *        The firmware.
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4FirmwareUpdate(Device device, Update update) throws DMException;
  
  /**
   * Create and submit a job for firmware update.
   * @param group
   *        The group which firmware will update.
   * @param update
   *        The firmware.
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4FirmwareUpdate(DeviceGroup group, Update update) throws DMException;
  
  /**
   * Create and submit a job for software discovery.
   * 
   * @param device
   *        The device which software will be discovered.
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4SoftwareDiscovery(Device device) throws DMException;
  
  /**
   * Create and submit a job for software discovery.
   * 
   * @param group
   *        The device group which software will be discovered.
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4SoftwareDiscovery(DeviceGroup group) throws DMException;
  
  /**
   * Create and submit a job for software installation.
   * 
   * @param device
   *        The device which software will be installed.
   * @param software
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4SoftwareInstall(Device device, Software software) throws DMException;
  
  /**
   * Create and submit a job for software installation.
   * 
   * @param group
   *        The device group which software will be installed.
   * @param software
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4SoftwareInstall(DeviceGroup group, Software software) throws DMException;
  
  /**
   * Create and submit a job for software un-installation.
   * 
   * @param device
   *        The device which software will be un-installed.
   * @param software
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4SoftwareUnInstall(Device device, Software software) throws DMException;
  
  /**
   * Create and submit a job for software activation.
   * 
   * @param device
   *        The device which software will be activated.
   * @param software
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4SoftwareActivation(Device device, Software software) throws DMException;
  
  /**
   * Create and submit a job for software de-activation.
   * 
   * @param device
   *        The device which software will be de-activated.
   * @param software
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4SoftwareDeactivation(Device device, Software software) throws DMException;
  
  /**
   * Create and submit a job for software un-installation.
   * 
   * @param group
   *        The device group which software will be un-installed.
   * @param software
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4SoftwareUnInstall(DeviceGroup group, Software software) throws DMException;
  
  /**
   * Create and submit a job for software activation.
   * 
   * @param group
   *        The device group which software will be activated.
   * @param software
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4SoftwareActivation(DeviceGroup group, Software software) throws DMException;
  
  /**
   * Create and submit a job for software de-activation.
   * 
   * @param group
   *        The device group which software will be de-activated.
   * @param software
   * @return
   *        The job.
   * @throws DMException
   */
  public abstract ProvisionJob newJob4SoftwareDeactivation(DeviceGroup group, Software software) throws DMException;
  
  /**
   * Create a job for software install stage2
   * @param group
   * @param software
   * @return
   * @throws DMException
   */
  public ProvisionJob newJob4SoftwareInstall4Stage2(DeviceGroup group, Software software) throws DMException;
  
  /**
   * Create a job for software install stage2
   * @param device
   * @param software
   * @return
   * @throws DMException
   */
  public ProvisionJob newJob4SoftwareInstall4Stage2(Device device, Software software) throws DMException;
  
  /**
   * Update the job into DM inventory.
   * 
   * @param job
   * @throws DMException
   */
  public abstract void update(ProvisionJob job) throws DMException;
  
  /**
   * Update the jobStatus into DM inventory.
   * 
   * @param job
   * @throws DMException
   */
  public abstract void update(ProvisionJobStatus jobStatus) throws DMException;
  
  /**
   * Load a job by it's jobID from DM inventory.
   * @param jobID   String job's ID
   * @return
   * @throws DMException
   */
  public abstract ProvisionJob loadJobByID(String jobID) throws DMException;
  
  /**
   * Load a job by it's jobID from DM inventory.
   * @param id   long job's ID
   * @return
   * @throws DMException
   */
  public abstract ProvisionJob loadJobByID(long id) throws DMException;

  /**
   * Cancel a job.
   * @param jobID
   * @throws DMException
   */
  public abstract void cancel(String jobID) throws DMException;
  
  /**
   * Cancel a job.
   * @param jobID
   * @throws DMException
   */
  public abstract void cancel(long jobID) throws DMException;
  
  /**
   * Disable a job.
   * @param jobID
   * @throws DMException
   */
  public abstract void disable(String jobID) throws DMException;

  /**
   * Disable a job.
   * @param jobID
   * @throws DMException
   */
  public abstract void disable(long jobID) throws DMException;
  
  /**
   * Enable a job
   * @param jobID
   * @throws DMException
   */
  public void enable(String jobID) throws DMException;

  /**
   * Enable a job
   * @param jobID
   * @throws DMException
   */
  public void enable(long jobID) throws DMException;
  /**
   * Delete the job from DM inventory.
   * 
   * @param job
   * @throws DMException
   */
  public abstract void delete(String jobID) throws DMException;
  
  /**
   * Delete the job from DM inventory.
   * 
   * @param job
   * @throws DMException
   */
  public abstract void delete(long jobID) throws DMException;
  
  /**
   * Find jobs by their job's type and job' s state. <br>
   * List sorted by scheduleTime ASC
   * <pre>
   * The JobType is one of:
   * ProvisionJob.JOB_TYPE_DISCOVERY
   * ProvisionJob.JOB_TYPE_ASSIGN_PROFILE
   * ProvisionJob.JOB_TYPE_RE_ASSIGN_PROFILE
   * ProvisionJob.JOB_TYPE_FIRMWARE
   * ProvisionJob.JOB_TYPE_SCRIPT
   * 
   * The jobState is one of:
   * ProvisionJobStatus.DEVICE_JOB_STATE_READY
   * ProvisionJobStatus.DEVICE_JOB_STATE_DOING
   * ProvisionJobStatus.DEVICE_JOB_STATE_DONE
   * ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED
   * ProvisionJobStatus.DEVICE_JOB_STATE_UNKNOWN
   * ProvisionJobStatus.DEVICE_JOB_STATE_ERROR
   * ProvisionJobStatus.DEVICE_JOB_STATE_NOTIFIED
   * 
   * 
   * If jobState is NULL, will return all of state.
   * 
   * If jobType is NULL, will return all of types.
   * 
   * 
   * For Example:
   * List finished job 
   * List list = bean.findJobs(ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJobStatus.DEVICE_JOB_STATE_DONE);
   * 
   * List pending job
   * List list = bean.findJobs(ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJobStatus.DEVICE_JOB_STATE_READY);
   * 
   * List failure job
   * List list = bean.findJobs(ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJobStatus.DEVICE_JOB_STATE_ERROR);
   * 
   * </pre>
   * 
   * @param jobType
   * @param jobState
   * @return
   * @throws DMException
   */
  public List<ProvisionJob> findJobs(String jobType, String jobState) throws DMException;
  
  /**
   * Find jobs by their job's type and job' s state. All of jobs belongs to the device.<br>
   * List sorted by scheduleTime ASC
   * <pre>
   * The JobType is one of:
   * ProvisionJob.JOB_TYPE_DISCOVERY
   * ProvisionJob.JOB_TYPE_ASSIGN_PROFILE
   * ProvisionJob.JOB_TYPE_RE_ASSIGN_PROFILE
   * ProvisionJob.JOB_TYPE_FIRMWARE
   * ProvisionJob.JOB_TYPE_SCRIPT
   * 
   * The jobState is one of:
   * ProvisionJobStatus.DEVICE_JOB_STATE_READY
   * ProvisionJobStatus.DEVICE_JOB_STATE_DOING
   * ProvisionJobStatus.DEVICE_JOB_STATE_DONE
   * ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED
   * ProvisionJobStatus.DEVICE_JOB_STATE_UNKNOWN
   * ProvisionJobStatus.DEVICE_JOB_STATE_ERROR
   * ProvisionJobStatus.DEVICE_JOB_STATE_NOTIFIED
   * 
   * For Example:
   * List finished job 
   * List list = bean.findJobs(ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJobStatus.DEVICE_JOB_STATE_DONE, device);
   * 
   * List pending job
   * List list = bean.findJobs(ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJobStatus.DEVICE_JOB_STATE_READY, device);
   * 
   * List failure job
   * List list = bean.findJobs(ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJobStatus.DEVICE_JOB_STATE_ERROR, device);
   * 
   * </pre>
   * 
   * @param jobType
   * @param jobState
   * @param device
   * @return  Return a array of {@link com.npower.dm.core.ProvisionJob} objects.
   * @throws DMException
   */
  public abstract List<ProvisionJob> findJobs(String jobType, String jobState, Device device) throws DMException;
  
  /**
   * Find queued jobs by their job's type . All of jobs belongs to the device.
   * All of job's state are applied, and Ready, Notified or Doing to scheduled!
   * <br>
   * List sorted by scheduleTime ASC<br>
   * 
   * @param jobType
   * @param device
   * @return  Return a array of {@link com.npower.dm.core.ProvisionJob} objects.
   * @throws DMException
   */
  public abstract List<ProvisionJob> findJobsQueued(String jobType, Device device) throws DMException;
  
  /**
   * Find queued jobs for the device, Return all of jobs belongs to the device.
   * All of job's state are applied, and Ready, Notified or Doing to scheduled!.<br>
   * 
   * List sorted by scheduleTime ASC<br>
   * 
   * @param device
   * @return Return a array of {@link com.npower.dm.core.ProvisionJob} objects.
   * @throws DMException
   */
  public abstract List<ProvisionJob> findJobsQueued(Device device) throws DMException;

  /**
   * Find queued jobs by their job's type . All of jobs belongs to the device.
   * All of job's state are applied, and Ready, Notified or Doing!
   * <br>
   * List sorted by scheduleTime ASC<br>
   * 
   * @param jobType
   * @param device
   * @param scheduled  
   *        If true, will only return schedules jobs
   * @return  Return a array of {@link com.npower.dm.core.ProvisionJob} objects.
   * @throws DMException
   */
  public abstract List<ProvisionJob> findJobsQueued(String jobType, Device device, boolean scheduled) throws DMException;
  
  /**
   * Find queued jobs for the device, Return all of jobs belongs to the device.
   * All of job's state are applied, and Ready, Notified or Doing!.<br>
   * 
   * List sorted by scheduleTime ASC<br>
   * 
   * @param device
   * @param scheduled  
   *        If true, will only return jobs which reached to schedule
   * @return Return a array of {@link com.npower.dm.core.ProvisionJob} objects.
   * @throws DMException
   */
  public abstract List<ProvisionJob> findJobsQueued(Device device, boolean scheduled) throws DMException;

  /**
   * Find queued jobs.
   * All of job's state are applied, and Ready, Notified or Doing!.<br>
   * 
   * List sorted by scheduleTime ASC<br>
   * 
   * @param scheduled  
   *        If true, will only return jobs which reached to schedule
   * @return Return a array of {@link com.npower.dm.core.ProvisionJob} objects.
   * @throws DMException
   */
  public abstract List<ProvisionJob> findJobsQueued(boolean scheduled) throws DMException;


  /**
   * Get the DeviceStatus for this ProvisionJob.
   * If the device is not target of the ProvisionJob, will return null.
   * @param device
   * @return
   * @throws DMException
   */
  public abstract ProvisionJobStatus getProvisionJobStatus(ProvisionJob job, Device device) throws DMException;
  
  /**
   * Get the ProfileAssignment for this ProvisionJob and Device.
   * @param device
   * @return
   * @throws DMException
   */
  public abstract ProfileAssignment getProfileAssignment(ProvisionJob job, Device device) throws DMException;

  /**
   * Return the linked profile assignment.
   * If the assignment link a NAP Profile or Proxy Profile, this method will return the NAP Profile or Proxy profile
   * which be reference by the assignment.
   * 
   * @param assignment
   *        Profile Assignemnt
   * @return
   *        NAP ProfileAssignment or Proxy Assginemnt which be referenced by assignment
   * @throws DMException
   */
  public abstract ProfileAssignment getLinkedProfileAssignment(ProfileAssignment assignment) throws DMException;
  
  /**
   * Caculate value for a nodeMapping.
   * @param assignment
   *        ProfileAssignment which contains the nodeMapping
   * @param nodeMapping
   *        Profile Node Mapping
   * @return
   *        Value of ProfileNodeMapping
   * @throws APLinkValueNotFoundException
   *         If the ProfileNodeMapping is type of APLink, and could not found boundled value.
   * @throws DMException
   *         Other error.
   */
  public abstract Object caculateProfileAssignmentValue(ProfileAssignment assignment, ProfileNodeMapping nodeMapping) throws APLinkValueNotFoundException, DMException;

  /**
   * Copy ProvisionJob into DeviceDMState.
   * @param dms
   */
  public void copy(ProvisionJob source, DeviceDMState target) throws NotFoundException;

  /**
   * Copy DeviceDMState into ProvisionJob and DeviceProvisionJobStatus.
   * @param dms
   */
  public void copy(DeviceDMState source, ProvisionJob target) throws DMException;
}
