/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/DeviceBean.java,v 1.25 2009/02/23 07:26:11 zhao Exp $
 * $Revision: 1.25 $
 * $Date: 2009/02/23 07:26:11 $
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

import java.util.List;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.DeviceTree;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.core.Model;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.core.Subscriber;

/**
 * All operations against Device, DeviceGroup and DeviceTree will be conducted througn this DeviceBean.
 * 
 * @author Zhao DongLu
 * 
 */
public interface DeviceBean extends BaseBean {
  
  // Methods related with Device *******************************************************
  
  /**
   * Create a instance of Device.
   * @return
   * @throws DMException
   */
  //public abstract Device newDeviceInstance() throws DMException;
  
  /**
   * Create a instance of Device.
   * @param subscriber
   * @param model
   * @param externalID
   * @return
   * @throws DMException
   */
  public abstract Device newDeviceInstance(Subscriber subscriber, Model model, String externalID) throws DMException;

  /**
   * Add or update a DeviceEntity into DM inventory. If the DeviceEntity exists,
   * will update into DM inventory.
   * 
   * @param device
   *          DeviceEntity
   * @throws DMException
   */
  public abstract void update(Device device) throws DMException;
  
  /**
   * Load a DeviceEntity by id from DM inventory. If no device found, will
   * return a null.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public Device getDeviceByID(long id) throws DMException;

  /**
   * Load a DeviceEntity by id from DM inventory. If no device found, will
   * return a null.
   * 
   * @param id
   * @return
   * @throws DMException
   */
  public abstract Device getDeviceByID(String id) throws DMException;

  /**
   * Find a DeviceEntity by it's ExternalID
   * 
   * @param id
   *          String externalID
   * 
   */
  public Device getDeviceByExternalID(String id) throws DMException;

  /**
   * Delete a device from DM inventory. All entity related with the device will
   * be deleted!!!
   * 
   * @param device
   * @throws DMException
   */
  public abstract void delete(Device device) throws DMException;
  

  /**
   * Find devices from DM inventory by the clause. For exmaple: " from
   * DeviceEntity where ....";
   * 
   * 
   * @param whereClause
   * @return Return array of (@link com.npower.dm.core.Deivce) objects.
   * @throws DMException
   */
  public abstract List<Device> findDevice(String whereClause) throws DMException;
  
  /**
   * <pre>
   * Reboundle the phoneNumber with deviceExternalID.
   * The reboulded device will be returned.
   * If phoneNumber and deviceExternalID not found in device database, this method will return null.
   * 
   * ���������ڴ����豸ID��PhoneNumber�İ󶨹�ϵ�����ñ��������Զ��豸�������°󶨣��󶨴���������¼��������
   * Case#1: �豸����,�ޱ仯,�����κβ���, �����豸����
   * Case#2: msisdn��ExternalID��Ӧ�벻ͬ���豸, ������. �������豸�ϲ�Ϊһ���豸, �����豸����
   * Case#3: ExternalID ��Ӧ���豸����,��msisdn�����仯,�޸Ĵ����豸��msisdn, �����豸����
   * Case#4: msisdn ��Ӧ���豸����, �����豸��externalID�����仯, �޸Ĵ����豸��externalID, ���޸�model, �����豸����
   * Case#5: ��������, ����null
   * 
   * ��Case#4ʱ�������豸��IMEI�������, ���Զ������豸����IMEI�����ͺţ������豸���ͺ�����.
   * </pre>
   * @param deviceExternalID
   *        DevInfo.DevID
   * @param msisdn
   *        PhoneNumber
   * @return 
   *        The rebounded device
   * @throws DMException
   */
  public abstract Device bind(String deviceExternalID, String msisdn) throws DMException;
  
  /**
   * <pre>
   * Reboundle the phoneNumber with deviceExternalID.
   * The reboulded device will be returned.
   * If phoneNumber and deviceExternalID not found in device database, this method will return null.
   * 
   * ���������ڴ����豸ID��PhoneNumber�İ󶨹�ϵ�����ñ��������Զ��豸�������°󶨣��󶨴���������¼��������
   * Case#1: �豸����,�ޱ仯,�����κβ���, �����豸����
   * Case#2: msisdn��ExternalID��Ӧ�벻ͬ���豸, ������. �������豸�ϲ�Ϊһ���豸, �����豸����
   * Case#3: ExternalID ��Ӧ���豸����,��msisdn�����仯,�޸Ĵ����豸��msisdn, �����豸����
   * Case#4: msisdn ��Ӧ���豸����, �����豸��externalID�����仯, �޸Ĵ����豸��externalID, ���޸�model, �����豸����
   * Case#5: ��������, ����������
   * 
   * ��Case#4ʱ�������豸��IMEI�������, ���Զ������豸����IMEI�����ͺţ������豸���ͺ�����.
   * </pre>
   * @param deviceExternalID
   *        DevInfo.DevID
   * @param msisdn
   *        PhoneNumber
   * @return 
   *        The rebounded device
   * @throws DMException
   */
  public abstract Device bind(String deviceExternalID, String msisdn, boolean creation) throws DMException;
  
  // Methods related with DeviceGroup *******************************************************  
  /**
   * Create a instance of DeviceGroup.
   * 
   * @return
   * @throws DMException
   */
  public abstract DeviceGroup newDeviceGroup() throws DMException;
  
  /**
   * Load a DeviceGroup by it's ID from DM inventory.
   * @param  id   ID of DeviceGroup.
   * @return
   * @throws DMException
   */
  public abstract DeviceGroup getDeviceGroupByID(String id) throws DMException;
  
  /**
   * Delete the DeviceGroup.
   * 
   * @param group
   * @throws DMException
   */
  public abstract void delete(DeviceGroup group) throws DMException;

  /**
   * Add or update a DeviceGroup into DM inventory. If the DeviceGroup exists,
   * will update into DM inventory.
   * 
   * @param device
   *          DeviceEntity
   * @throws DMException
   */
  public abstract void update(DeviceGroup group) throws DMException;

  /**
   * Add a device into this DeviceGroup.
   * 
   * @param group
   * @param device
   * @throws DMException
   */
  public abstract void add(DeviceGroup group, Device device) throws DMException;
  
  /**
   * Remove a device from this DeviceGroup.
   * 
   * @param group
   * @param device
   * @throws DMException
   */
  public abstract void remove(DeviceGroup group, Device device) throws DMException;
  
  // Methods related with DeviceTree *******************************************************  
  /**
   * Delete a DeviceTree.
   * 
   * All deviceTreeID of Device reference this DeviceTree will be set a NULL.
   * 
   * @param tree
   */
  public void delete(DeviceTree tree) throws DMException;
  
  /**
   * Delete a DeviceTreeNode.
   * 
   * All children of DeviceTreeNode will be deleted.
   * 
   * @param node
   */
  public void delete(DeviceTreeNode node) throws DMException;

  /**
   * Find a DeviceTreeNode.
   * The node path like as:
   * 
   *  ./DevDetail/Man
   *  /DevDetail/Man
   *  
   * @param nodePath
   * @return
   * @throws DMException
   */
  public abstract DeviceTreeNode findDeviceTreeNode(String deviceID, String nodePath) throws DMException;
  
  /**
   * Find a DeviceTreeNode.
   * The node path like as:
   * 
   *  ./DevDetail/Man
   *  /DevDetail/Man
   *  
   * @param nodePath
   * @return
   * @throws DMException
   */
  public abstract DeviceTreeNode findDeviceTreeNode(long deviceID, String nodePath) throws DMException;
  
  /**
   * ����DDF�Ŀ�����豸���в��Ҷ�Ӧ��ʵ��ڵ�.
   * 
   * @param deviceID
   * @param ddfNodeID
   * @return
   * @throws DMException
   */
  public abstract List<DeviceTreeNode> findConcreteTreeNodes(long deviceID, long ddfNodeID) throws DMException;

  /**
   * ����DDF�Ŀ�����豸���в��Ҷ�Ӧ��ʵ��ڵ�, ����Щʵ��ڵ��λ��baseNode��.
   * @param deviceID
   * @param baseNode
   * @param ddfNodeID
   * @return
   * @throws DMException 
   */
  public abstract List<DeviceTreeNode> findConcreteTreeNodes(Device device, DeviceTreeNode baseNode, DDFNode ddfNode) throws DMException;

  /**
   * Store the node into DeviceTree related with the device.
   * For exmaple: 
   * The nodePath is "./DevInfo/Mod", nodeValue is "W32 dm client"
   * 
   * @param nodePath
   * @param nodeValue
   * @return DeviceTreeNode
   * @throws DMException
   */
  public abstract DeviceTreeNode updateDeviceTreeNode(String deviceID, String nodePath, Object nodeValue) throws DMException;
  
  /**
   * Load a Device Tree Node by his ID.
   * @param nodeID
   * @return
   * @throws DMException
   */
  public abstract DeviceTreeNode getDeviceTreeNodeByID(String nodeID) throws DMException;
  
  /**
   * Return current Firmware version id.
   * @param deviceID
   *        device 's ID
   * @return  If return null, the version id unknown.
   * @throws DMException
   */
  public abstract String getCurrentFirmwareVersionId(long deviceID) throws DMException;
  
  
  /**
   * <pre>
   * ע��һ���豸, ��������ĵ绰������ͺŴ������ѯ�豸.
   * �����������̣�
   * 1������PhoneNumber�����Ƿ����Subscriber
   * 2�����Subscriber������, �򴴽�, ������ʹ�ô��ڵ�Subscriber
   * 3����Subscriber��Ѱ���Ƿ���ͬһ�ͺŵ�Device
   * 4������ͬһ�ͺŵ�Device, ��ʹ�ô�Device, ������
   * 5��������, ���Զ�����һ����ʱ��IMEI, ���ݴ˴���Device, ����
   * </pre>
   * @param phoneNumber
   *        Not null
   * @param model
   *        Not null
   * @param carrier
   *        Not null
   * @param serviceProvider
   *        Who provide this subscriber information, could be null
   * @return
   * @throws DMException
   */
  public abstract Device enroll(String phoneNumber, 
      Model model, 
      Carrier carrier, ServiceProvider serviceProvider) throws DMException;
  
  /**
   * <pre>
   * ע��һ��Device��һ��Subscriber
   * �����������̣�
   * 1������msisdn�����Ƿ����Subscriber
   * 2�����Subscriber������, �򴴽�, ������ʹ�ô��ڵ�Subscriber
   * 3������imei�����Ƿ����Device
   * 4������Device, ��ʹ�ô�Device, ������
   * 5��������, �򴴽�Device, ����
   * 
   * </pre>
   * @param imei
   * @param msisdn
   * @param imsi
   * @param model
   * @param carrier
   * @param serviceProvider
   * @return
   * @throws DMException
   */
  public abstract Device enroll(String imei, 
                                String msisdn, 
                                String imsi, 
                                Model model, 
                                Carrier carrier, 
                                ServiceProvider serviceProvider) throws DMException;
  
  /**
   * ��λһ���豸. ��λ�����������£�
   * 1. ����豸�Ѿ�Bootstrap�ɹ�, �����κβ���
   * 2. ����豸��δ�����κ�Bootstrap, �����κβ���
   * 3. ����豸��������Bootstrap����, �������ȥBootstrap��������ʷ״̬, ��������Ϊ0, ȷ��������ȳ�����һ���µ��豸������.
   * @param device
   */
  public abstract void reset(Device device);

}