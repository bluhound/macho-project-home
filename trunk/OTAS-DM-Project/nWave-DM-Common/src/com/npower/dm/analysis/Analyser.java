/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/analysis/Analyser.java,v 1.2 2009/01/21 07:53:10 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2009/01/21 07:53:10 $
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
package com.npower.dm.analysis;

import java.util.List;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAssignment;

/**
 * �豸������, Ϊ�豸�ṩ��������.
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2009/01/21 07:53:10 $
 */
public interface Analyser {
  
  /**
   * �����豸��Ϣ, �ṩ��������
   * @param report
   *        ����������, �����������ŵ��ñ�����.
   * @param deviceId
   *        ���������豸IMEI
   * @return
   *        ����������豸�˴��ڵ�������Ϣ
   * @throws DMException
   */
  public abstract List<ProfileAssignment> analyse(Report report, String deviceExternalId) throws DMException;

}
