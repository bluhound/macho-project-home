package com.ibm.tivoli.tuna.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;

import com.ibm.tivoli.tuna.service.AttributeStatement;
import com.ibm.tivoli.tuna.util.StringUtil;

public class SearchContextMapper implements ContextMapper {
  private static Log log = LogFactory.getLog(SearchContextMapper.class);
  
  //��װbean�����Ա����
  private String[] property = new String[0];
  //����Ҫ���ҵ����ԣ���Ϊ�������ȫ��
  private String[] arrShowField = new String[0];
  //��Ҫ��װ��bean
  private Class appClass;
  
  /**
   * 
   * @param multivalued
   * @param appClass
   */
  public SearchContextMapper(String[] arrShowField, Class appClass) {
    super();
    this.appClass = appClass;
    this.arrShowField = arrShowField;
    Field[] fields = appClass.getDeclaredFields();
    
    property = new String[fields.length];
    for (int i = 0; i < fields.length; i++) {
      property[i] = fields[i].getName();
    }
  }
  
  public Object mapFromContext(Object ctx) {
    try{
      AttributeStatement obj = (AttributeStatement) appClass.newInstance();
      if(ctx == null) {
        return obj;
      } 
      
      DirContextAdapter context = (DirContextAdapter) ctx;
      Attributes attrs = context.getAttributes();
      NamingEnumeration results = attrs.getAll();
      
      while(results.hasMoreElements()) {
        try {
          Attribute attr = (Attribute) results.nextElement();
          String name = attr.getID().toLowerCase();
          
          if(!StringUtil.isNullArray(property) && StringUtil.isExistElement(name, property)) {
            //��ֵΪ�����Ա����
            String methodName = "set" + name.substring(0, 1).toUpperCase()
              + name.substring(1);
            Method method = appClass.getDeclaredMethod(methodName, 
                new Class[]{String.class});
            method.invoke(obj, new Object[]{attr.get(0).toString()});
          } else if(StringUtil.isNullArray(arrShowField) || StringUtil.isExistElement(name, arrShowField)) {
            //�����ֵ�ֵ�Ͷ�ֵ��ͳһ���ն�ֵȥȡ
            List<String> buteValues=new ArrayList<String>();
            NamingEnumeration repeatEnumer = attr.getAll();
            while (repeatEnumer.hasMoreElements()) {
              String value = (String) repeatEnumer.nextElement();
              buteValues.add(value);
            }
            obj.getAttributes().add(
                new com.ibm.tivoli.tuna.service.Attribute(name, "string", buteValues));
          } 
        } catch(Exception ex) {
          log.error("Ldap����ת�������쳣��", ex);
        }
      }
      com.ibm.tivoli.tuna.service.Attribute userDN = 
        new com.ibm.tivoli.tuna.service.Attribute("userdn", "String", context.getNameInNamespace());
      obj.getAttributes().add(userDN);
      return obj;
    }catch(Exception e){
      log.error("Ldap����ת�������쳣��", e);
    }
    return null;
  }
  
}
