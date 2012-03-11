package com.npower.dm.management;

import java.util.List;

import org.hibernate.Criteria;

import com.npower.dm.core.FavoriteDevice;
import com.npower.dm.hibernate.entity.FavoriteDeviceEntity;

public interface FavoriteDeviceBean {
  
  public FavoriteDevice newInstance();

  public abstract void save(FavoriteDevice transientInstance);

  public abstract void delete(FavoriteDevice persistentInstance);

  public abstract FavoriteDevice findById(java.lang.Long id);

  public abstract List<FavoriteDevice> findByExample(FavoriteDevice instance);

  public abstract List<FavoriteDevice> findByProperty(String propertyName, Object value);

  public abstract List<FavoriteDevice> findByDeviceId(Object deviceId);

  public abstract List<FavoriteDevice> findAll();

  public abstract FavoriteDevice merge(FavoriteDeviceEntity detachedInstance);

  public abstract void attachDirty(FavoriteDeviceEntity instance);

  public abstract void attachClean(FavoriteDeviceEntity instance);
  
  /**
   * ����豸���ղؼ���
   * 
   * @param deviceId
   *          �豸ID
   * @param favoriteDeviceId
   *          �豸�ղ�ID
   * @param owner
   *          �ղظ��豸���û�
   */
  public abstract void addDeviceFavorite(String deviceId, String favoriteId, String owner);
  
  public abstract Criteria getCriteria();

}