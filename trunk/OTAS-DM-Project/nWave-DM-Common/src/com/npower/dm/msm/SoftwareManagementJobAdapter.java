/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/SoftwareManagementJobAdapter.java,v 1.4 2008/11/10 14:30:38 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/11/10 14:30:38 $
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
package com.npower.dm.msm;

import java.util.List;

import sync4j.framework.engine.dm.ManagementProcessor;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;

/**
 * ����������������������, ���ڿ�����Բ�ͬ���͵�������ͺ�, ѡ��ͬ��DM Processor
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/11/10 14:30:38 $
 */
public interface SoftwareManagementJobAdapter {
  
  /**
   * ����һ��ManagementProcessor, �����ṩ�������
   * @param jobType
   * @param deviceExtID
   * @return
   * @throws DMException
   */
  public abstract ManagementProcessor getProcessor(String jobType, String deviceExtID) throws DMException;
  
  
  /**
   * ����һ��ManagementProcessor, �����ṩ�������. һ������ڼ���ͺ��Ƿ�֧����Ӧ�����������������.
   * @param model
   * @return
   */
  public abstract ManagementProcessor getProcessor(String jobType, Model model) throws DMException;
  
  /**
   * �����ն��豸���Ѳ���������Ϣ.
   * 
   * ����豸����ΪNokia S60 3rd, NodeInfo�н�SoftwareExternalID��Ч;
   * ����豸����ΪSonyEriccson DM Client V3.0, NodeInfo�н������϶����Ϣ
   * @param deviceExtID
   * @return
   * @throws DMException
   */
  public List<SoftwareNodeInfo> getDeployedSoftwares(String deviceExtID) throws DMException;
  
  /**
   * ��������İ�װ״̬
   * @param deviceExtID
   * @param softwareExtID
   * @return
   * @throws DMException
   */
  public String getDeployedSoftwareState(String deviceExtID, String softwareExtID) throws DMException;

  /**
   * �����ͺ��Ƿ�֧���������
   * @param model
   * @return
   */
  public boolean isSupported(Model model) throws DMException;
  
  /**
   * �����ֻ����������Ƿ���֧�����������ֻ��ͺ�
   * @param model
   * @return
   */
  public boolean isSupported(Manufacturer manufacturer) throws DMException;
  
}
