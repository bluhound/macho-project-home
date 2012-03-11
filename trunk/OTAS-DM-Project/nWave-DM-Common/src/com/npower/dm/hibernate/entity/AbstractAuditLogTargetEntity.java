package com.npower.dm.hibernate.entity;
// default package

/**
 * AbstractAuditLogTargetEntity generated by MyEclipse - Hibernate Tools
 */

public abstract class AbstractAuditLogTargetEntity implements java.io.Serializable {

  // Fields

  private long ID;
  
  private AuditLogEntity         actionLog;

  private String                 roleName;

  private String                 description;

  private String                 type;

  private String                 deviceId;

  private String                 deviceExternalId;

  private String                 imageId;

  private String                 imageExternalId;

  private String                 subscriberId;

  private String                 subscriberExternalId;

  private String                 subscriberPhoneNumber;

  private String                 carrierExternalId;

  private String                 carrierId;

  private String                 countryExternalId;

  private String                 countryId;

  private String                 modelId;

  private String                 modelName;

  private String                 updateId;

  private String                 workflowName;

  private String                 manufacturerId;

  private String                 manufacturerExternalId;

  private String                 provReqId;

  private String                 templateId;

  private String                 profileId;

  private String                 profileName;

  private String                 profileTemplateId;

  private String                 profileTemplateName;

  private String                 userId;

  private String                 userName;

  private String                 profileAssignmentId;

  private String                 attributeTypeName;

  private String                 softwareName;
  
  private String                 softwareVendor;
  
  private String                 softwareCategory;
  
  private String                 clientProvTemplateId;

  private String                 clientProvTemplateName;
  
  private String                 softwareCategoryId;

  private String                 softwareCategoryParent;

  private String                 vendorId;
  
  private String                 vendorExtId;

  // Constructors

  /** default constructor */
  public AbstractAuditLogTargetEntity() {
  }

  // Property accessors

  public long getID() {
    return this.ID;
  }

  public void setID(long id) {
    this.ID = id;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceExternalId() {
    return this.deviceExternalId;
  }

  public void setDeviceExternalId(String deviceExternalId) {
    this.deviceExternalId = deviceExternalId;
  }

  public String getImageId() {
    return this.imageId;
  }

  public void setImageId(String imageId) {
    this.imageId = imageId;
  }

  public String getImageExternalId() {
    return this.imageExternalId;
  }

  public void setImageExternalId(String imageExternalId) {
    this.imageExternalId = imageExternalId;
  }

  public String getSubscriberId() {
    return this.subscriberId;
  }

  public void setSubscriberId(String subscriberId) {
    this.subscriberId = subscriberId;
  }

  public String getSubscriberExternalId() {
    return this.subscriberExternalId;
  }

  public void setSubscriberExternalId(String subscriberExternalId) {
    this.subscriberExternalId = subscriberExternalId;
  }

  public String getSubscriberPhoneNumber() {
    return this.subscriberPhoneNumber;
  }

  public void setSubscriberPhoneNumber(String subscriberPhoneNumber) {
    this.subscriberPhoneNumber = subscriberPhoneNumber;
  }

  public String getCarrierExternalId() {
    return this.carrierExternalId;
  }

  public void setCarrierExternalId(String carrierExternalId) {
    this.carrierExternalId = carrierExternalId;
  }

  public String getCarrierId() {
    return this.carrierId;
  }

  public void setCarrierId(String carrierId) {
    this.carrierId = carrierId;
  }

  public String getModelId() {
    return this.modelId;
  }

  public void setModelId(String modelId) {
    this.modelId = modelId;
  }

  public String getModelName() {
    return this.modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public String getUpdateId() {
    return this.updateId;
  }

  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

  public String getWorkflowName() {
    return this.workflowName;
  }

  public void setWorkflowName(String workflowName) {
    this.workflowName = workflowName;
  }

  public String getManufacturerId() {
    return this.manufacturerId;
  }

  public void setManufacturerId(String manufacturerId) {
    this.manufacturerId = manufacturerId;
  }

  public String getManufacturerExternalId() {
    return this.manufacturerExternalId;
  }

  public void setManufacturerExternalId(String manufacturerExternalId) {
    this.manufacturerExternalId = manufacturerExternalId;
  }

  public String getProvReqId() {
    return this.provReqId;
  }

  public void setProvReqId(String provReqId) {
    this.provReqId = provReqId;
  }

  public String getTemplateId() {
    return this.templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public String getProfileId() {
    return this.profileId;
  }

  public void setProfileId(String profileId) {
    this.profileId = profileId;
  }

  public String getProfileName() {
    return this.profileName;
  }

  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  public String getProfileTemplateId() {
    return this.profileTemplateId;
  }

  public void setProfileTemplateId(String profileTemplateId) {
    this.profileTemplateId = profileTemplateId;
  }

  public String getProfileTemplateName() {
    return this.profileTemplateName;
  }

  public void setProfileTemplateName(String profileTemplateName) {
    this.profileTemplateName = profileTemplateName;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getProfileAssignmentId() {
    return this.profileAssignmentId;
  }

  public void setProfileAssignmentId(String profileAssignmentId) {
    this.profileAssignmentId = profileAssignmentId;
  }

  public String getAttributeTypeName() {
    return this.attributeTypeName;
  }

  public void setAttributeTypeName(String attributeTypeName) {
    this.attributeTypeName = attributeTypeName;
  }

  /**
   * @return the actionLog
   */
  public AuditLogEntity getActionLog() {
    return actionLog;
  }

  /**
   * @param actionLog the actionLog to set
   */
  public void setActionLog(AuditLogEntity actionLog) {
    this.actionLog = actionLog;
  }

  /**
   * @return the roleName
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   * @param roleName the roleName to set
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /**
   * @return the countryId
   */
  public String getCountryId() {
    return countryId;
  }

  /**
   * @param countryId the countryId to set
   */
  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  /**
   * @return the countryExternalId
   */
  public String getCountryExternalId() {
    return countryExternalId;
  }

  /**
   * @param countryExternalId the countryExternalId to set
   */
  public void setCountryExternalId(String countryExternalId) {
    this.countryExternalId = countryExternalId;
  }

  
  public String getSoftwareName() {
    return softwareName;
  }

  public void setSoftwareName(String softwareName) {
    this.softwareName = softwareName;
  }

  public String getSoftwareVendor() {
    return softwareVendor;
  }

  public void setSoftwareVendor(String softwareVendor) {
    this.softwareVendor = softwareVendor;
  }

  public String getSoftwareCategory() {
    return softwareCategory;
  }

  public void setSoftwareCategory(String softwareCategory) {
    this.softwareCategory = softwareCategory;
  } 

  public String getClientProvTemplateId() {
    return clientProvTemplateId;
  }

  public void setClientProvTemplateId(String clientProvTemplateId) {
    this.clientProvTemplateId = clientProvTemplateId;
  }

  public String getClientProvTemplateName() {
    return clientProvTemplateName;
  }

  public void setClientProvTemplateName(String clientProvTemplateName) {
    this.clientProvTemplateName = clientProvTemplateName;
  }

  public String getSoftwareCategoryId() {
    return softwareCategoryId;
  }

  public void setSoftwareCategoryId(String softwareCategoryId) {
    this.softwareCategoryId = softwareCategoryId;
  }

  public String getSoftwareCategoryParent() {
    return softwareCategoryParent;
  }

  public void setSoftwareCategoryParent(String softwareCategoryParent) {
    this.softwareCategoryParent = softwareCategoryParent;
  }

  /**
   * @return the vendorId
   */
  public String getVendorId() {
    return vendorId;
  }

  /**
   * @param vendorId the vendorId to set
   */
  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  /**
   * @return the vendorExtId
   */
  public String getVendorExtId() {
    return vendorExtId;
  }

  /**
   * @param vendorExtId the vendorExtId to set
   */
  public void setVendorExtId(String vendorExtId) {
    this.vendorExtId = vendorExtId;
  }

}