/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/util/BeanHelper.java,v 1.2 2007/08/29 06:21:00 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2007/08/29 06:21:00 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

/**
 * �����ڵ�ǰ��������private/protected��Ա������private/protected������BeanUtils.
 * ע��,��Ϊ����Ϊ��ǰ�������ı���,ͨ���̳л�õ�protected���������ܷ���, ��Ҫת�͵������ñ���������ܷ���. ���������������ʹ��Apache
 * Jarkarta Commons BeanUtils
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/08/29 06:21:00 $
 */
public class BeanHelper {
  /**
   * ������ȡ��ǰ��������private/protected����
   */
  static public Object getDeclaredProperty(Object object, String propertyName) throws IllegalAccessException,
      NoSuchFieldException {
    assert object != null;
    assert StringUtils.isNotEmpty(propertyName);
    Field field = object.getClass().getDeclaredField(propertyName);
    return getDeclaredProperty(object, field);
  }

  /**
   * ������ȡ��ǰ��������private/protected����
   */
  static public Object getDeclaredProperty(Object object, Field field) throws IllegalAccessException {
    assert object != null;
    assert field != null;
    boolean accessible = field.isAccessible();
    field.setAccessible(true);
    Object result = field.get(object);
    field.setAccessible(accessible);
    return result;
  }

  /**
   * �������õ�ǰ��������private/protected����
   */
  static public void setDeclaredProperty(Object object, String propertyName, Object newValue)
      throws IllegalAccessException, NoSuchFieldException {
    assert object != null;
    assert StringUtils.isNotEmpty(propertyName);

    Field field = object.getClass().getDeclaredField(propertyName);
    setDeclaredProperty(object, field, newValue);
  }

  /**
   * �������õ�ǰ��������private/protected����
   */
  static public void setDeclaredProperty(Object object, Field field, Object newValue) throws IllegalAccessException {
    boolean accessible = field.isAccessible();
    field.setAccessible(true);
    field.set(object, newValue);
    field.setAccessible(accessible);
  }

  /**
   * ��ȡ��ǰ��������private/protected����
   */
  static public Object getPrivateProperty(Object object, String propertyName) throws IllegalAccessException,
      NoSuchFieldException {
    assert object != null;
    assert StringUtils.isNotEmpty(propertyName);
    Field field = object.getClass().getDeclaredField(propertyName);
    field.setAccessible(true);
    return field.get(object);
  }

  /**
   * ���õ�ǰ��������private/protected����
   */
  static public void setPrivateProperty(Object object, String propertyName, Object newValue)
      throws IllegalAccessException, NoSuchFieldException {
    assert object != null;
    assert StringUtils.isNotEmpty(propertyName);

    Field field = object.getClass().getDeclaredField(propertyName);
    field.setAccessible(true);
    field.set(object, newValue);
  }

  /**
   * ���õ�ǰ��������private/protected����
   */
  static public Object invokePrivateMethod(Object object, String methodName, Object[] params)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    assert object != null;
    assert StringUtils.isNotEmpty(methodName);
    Class<?>[] types = new Class[params.length];
    for (int i = 0; i < params.length; i++) {
      types[i] = params[i].getClass();
    }
    Method method = object.getClass().getDeclaredMethod(methodName, types);
    method.setAccessible(true);
    return method.invoke(object, params);
  }

  /**
   * ���õ�ǰ��������private/protected����
   */
  static public Object invokePrivateMethod(Object object, String methodName, Object param)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    return invokePrivateMethod(object, methodName, new Object[] { param });
  }

}
