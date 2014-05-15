package com.tutucha.model.entity;

public class Navigation {

  /**
   * ������Ŀ��ID
   */
  private String id = null;

  /**
   * ����
   */
  private String label = null;

  /**
   * Ŀ��URL
   */
  private String url = null;

  /**
   * ������ͼ�꣬���ܵ���ʽΪstyle, class, url. ��Ϊurl, ��ΪͼƬ��ַ������Ϊcss��ص����η�ʽ
   */
  private String icon = null;

  /**
   * ������URL
   */
  private String description = null;

  /**
   * �����ķ����ʶ
   */
  private String categoryId = null;

  public Navigation() {
    super();
  }

  /**
   * @param id
   * @param label
   * @param url
   * @param icon
   * @param categoryId
   */
  public Navigation(String id, String label, String url, String icon, String categoryId) {
    super();
    this.id = id;
    this.label = label;
    this.url = url;
    this.icon = icon;
    this.categoryId = categoryId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String iconUrl) {
    this.icon = iconUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

}
