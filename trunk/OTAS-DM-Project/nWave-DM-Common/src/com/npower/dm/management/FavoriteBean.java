package com.npower.dm.management;

import java.util.List;

import org.hibernate.Criteria;

import com.npower.dm.core.Favorite;
import com.npower.dm.hibernate.entity.FavoriteEntity;

public interface FavoriteBean {
  
  public Favorite newInstance();

  public abstract void save(Favorite transientInstance);

  public abstract void delete(Favorite persistentInstance);

  public abstract Favorite findById(java.lang.Long id);

  public abstract List<Favorite> findByExample(Favorite instance);

  public abstract List<Favorite> findByProperty(String propertyName, Object value);

  public abstract List<Favorite> findByName(Object name);

  public abstract List<Favorite> findByDescription(Object description);

  public abstract List<Favorite> findByIsshare(Object isshare);

  public abstract List<Favorite> findByOwner(Object owner);

  public abstract List<Favorite> findAll();

  public abstract Favorite merge(FavoriteEntity detachedInstance);

  public abstract void attachDirty(FavoriteEntity instance);

  public abstract void attachClean(FavoriteEntity instance);
  
  /**
   * <pre>
   *  �÷���Ϊ��ѯ��������������ݰ�owner�������С�
   *  �������£�
   *  
   * ---------------------------------------------
   *          Name   Share    Owner
   * ---------------------------------------------
   *           A1      T       ZDL
   *           A2      F       ZDL
   *           B1      T       HCP
   *           B2      F       HCP
   * ---------------------------------------------
   *      ����1��OwnerΪZDL����ShareΪtrueʱ���г�A1,A2,B1.
   *      ����2��OwnerΪZDL����ShareΪfalseʱ���г�A1,A2.
   * </pre>
   * @param owner
   *        ������Ϊnull
   * @param shared
   * @param searchText
   *        ����Ϊnull
   * @return
   */
  public abstract List<Favorite> findBySimpleSearch(String owner, boolean shared, String searchText);
  
  public abstract Criteria getCriteria();

}