/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/S60V3InstallProcessor4DL.java,v 1.13 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.13 $
  * $Date: 2008/06/16 10:11:15 $
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

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.config.ConfigurationConstants;
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.AddManagementOperation;
import sync4j.framework.engine.dm.DeleteManagementOperation;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ExecManagementOperation;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.ReplaceManagementOperation;
import sync4j.framework.engine.dm.SequenceManagementOperation;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.engine.dm.TreeManagementOperation;
import sync4j.framework.engine.dm.TreeNode;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.processor.GenericTreeDiscoveryProcessor;
import com.npower.dm.processor.JobQueueProcessor;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.util.MessageResources;
import com.npower.sms.SmsException;

/**
 * DM Processor for Software Management
 * Adapted for: Nokia S60 3rd platform.
 * <pre>
 * The following command script tested with Nokia 6120:
 * 
<?xml version="1.0" encoding="UTF-8"?>
<Script>
  <Sequence>
    <Add>
      <InteriorNode>
        <Target>./SCM/Download/MSNApp</Target>
      </InteriorNode>
    </Add>
    <Add>
      <LeafNode>
        <Target>./SCM/Download/MSNApp/Name</Target>
        <Data format="chr">MSN</Data>
      </LeafNode>
    </Add>
    <Add>
      <LeafNode>
        <Target>./SCM/Download/MSNApp/Version</Target>
        <Data format="chr">1.0</Data>
      </LeafNode>
    </Add>
  </Sequence>
  
  <Replace>
    <LeafNode>
      <Target>./SCM/Download/MSNApp/URI</Target>
      <Data format="chr">http://www.otas.mobi:8080/download/msn_mobile_3.5_for_nokia_6120_544.sisx</Data>
    </LeafNode>
  </Replace>
  
  <Replace>
    <LeafNode>
      <Target>./SCM/Download/MSNApp/InstallOpts</Target>
      <Data format="chr" type="text/xml"><![CDATA[ <InstOpts>
<StdOpt name="drive" value="c"/>
<StdOpt name="lang" value="*" />
<StdOpt name="upgrade" value="yes"/>
<StdOpt name="kill" value="yes"/>
<StdSymOpt name="pkginfo" value="yes"/>
<StdSymOpt name="optionals" value="yes"/>
<StdSymOpt name="ocsp" value="no"/>
<StdSymOpt name="capabilities" value="yes"/>
<StdSymOpt name="untrusted" value="yes"/>
<StdSymOpt name="ignoreocspwarn" value="yes"/>
<StdSymOpt name="ignorewarn" value="yes"/>
<StdSymOpt name="fileoverwrite" value="yes"/>
</InstOpts> ]]></Data>
    </LeafNode>
  </Replace>
  
  <Exec>
    <Item>
      <Target>./SCM/Download/MSNApp/Operations/DownloadAndInstallAndActivate</Target>
    </Item>
  </Exec>
  
</Script>
 * </pre>
 * @author Zhao DongLu
 * @version $Revision: 1.13 $ $Date: 2008/06/16 10:11:15 $
 */
public class S60V3InstallProcessor4DL extends BaseS60V3Processor implements ManagementProcessor, Serializable {

  private static transient Log log = LogFactory.getLog(S60V3InstallProcessor4DL.class);
  
  /**
   * 检查Software Download节点是否存在.
   * 例如: 检查./SCM/Download/MSNApp节点是否存在
   * 
   */
  private static final int STEP_Get_Software_Nodes_Information = 100;
  
  /**
   * 创建或更新Software Download节点. 
   * 例如:
   * 创建或更新./SCM/Download/MSNApp节点
   */
  private static final int STEP_Create_Or_Update_Software_Node = 200;
  
  /**
   * 设置软件安装节点的各个参数.
   * 例如:
   *   设置download URL和installation options
   */
  private static final int STEP_Setup_Software_Node_Attributes = 300;
  
  /**
   * 执行软件安装任务
   * 
   */
  private static final int STEP_Execute_Installation = 400;
  
  /**
   * 表示第一阶段安装已经完成, 等待终端安装结束.
   */
  private static final int STEP_Stage_1_Finished = 600;

  /**
   * 表示软件已经安装
   */
  private static final int STEP_Deployed = 800;
  
  private ManagementProcessor processor4Discovery = null;

  /**
   * Flag
   * 表示软件是否已经被安装, 无需继续安装
   */
  private boolean deployed  = false;
  
  /**
   * 
   */
  public S60V3InstallProcessor4DL() {
    super();
  }
  
  // Private methods ------------------------------------------------------------------------

  /**
   * 计算软件下载的URL
   * @param software
   * @return
   */
  private String getSoftwareDownloadURI(ManagementBeanFactory factory, Software software) throws DMException, ManagementException {
    //String downloadURI = "http://www.otas.mobi:8080/download/msn_mobile_3.5_for_nokia_6120_544.sisx";
    SoftwareBean bean = factory.createSoftwareBean();
    Device device = this.getDevice(factory);
    Model model = device.getModel();
    String serverDownlaodURIPattern = getDownloadServerProperties().getProperty(ConfigurationConstants.CFG_SERVER_SOFTWARE_DOWNLOAD_URI);
    String url = bean.getSoftwareDownloadURL(software, model, serverDownlaodURIPattern);
    log.info("Software Download URL: " + url);
    return url;
  }

  /**
   * 获取软件安装的的参数
   * @param software
   * @return
   */
  private String getSoftwareInstallationOpts(ManagementBeanFactory factory, Software software) throws DMException, ManagementException {
    String defaultInstallationOpts = "<InstOpts>" + 
                              "<StdOpt name=\"drive\" value=\"!\"/>" + 
                              "<StdOpt name=\"lang\" value=\"*\" />" + 
                              "<StdOpt name=\"upgrade\" value=\"yes\"/>" + 
                              "<StdOpt name=\"kill\" value=\"yes\"/>" + 
                              "<StdSymOpt name=\"pkginfo\" value=\"yes\"/>" + 
                              "<StdSymOpt name=\"optionals\" value=\"yes\"/>" + 
                              "<StdSymOpt name=\"ocsp\" value=\"no\"/>" + 
                              "<StdSymOpt name=\"capabilities\" value=\"yes\"/>" + 
                              "<StdSymOpt name=\"untrusted\" value=\"yes\"/>" + 
                              "<StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>" + 
                              "<StdSymOpt name=\"ignorewarn\" value=\"yes\"/>" + 
                              "<StdSymOpt name=\"fileoverwrite\" value=\"yes\"/>" + 
                              "</InstOpts>";
    
    SoftwareBean bean = factory.createSoftwareBean();
    Device device = this.getDevice(factory);
    Model model = device.getModel();
    SoftwarePackage pkg = bean.getPackage(software, model);
    if (StringUtils.isEmpty(pkg.getInstallationOptions())) {
       return defaultInstallationOpts;
    }
    return pkg.getInstallationOptions();
  }


  /**
   * 生成创建Software安装节点的DM命令.
   * 创建或更新./SCM/Download/MSNApp节点
   * @param softwareExternalID
   * @return
   */
  private ManagementOperation[] getNextOperations4CreateDownloadNode(ManagementBeanFactory factory, Device device, Software software) throws DMException, ManagementException {
    String baseNodePath = getSoftwareDownloadBaseNodePath(software);

    List<ManagementOperation> operations = new ArrayList<ManagementOperation>();
    SequenceManagementOperation seqOper = new SequenceManagementOperation();
    operations.add(seqOper);
    {
      TreeManagementOperation oper1 = getTreeManagementOperation(factory, device);
      TreeNode treeNode = new TreeNode(baseNodePath);
      treeNode.setFormat(DDFNode.DDF_FORMAT_NODE);
      oper1.addTreeNode(treeNode);
      seqOper.add(oper1);
    }
    
    {
      TreeManagementOperation oper2 = getTreeManagementOperation(factory, device);
      TreeNode treeNode = new TreeNode(baseNodePath + "/ID", software.getExternalId());
      treeNode.setFormat(DDFNode.DDF_FORMAT_CHR);
      oper2.addTreeNode(treeNode);
      seqOper.add(oper2);
    }
    
    {
      TreeManagementOperation oper2 = getTreeManagementOperation(factory, device);
      TreeNode treeNode = new TreeNode(baseNodePath + "/Name", software.getName());
      treeNode.setFormat(DDFNode.DDF_FORMAT_CHR);
      oper2.addTreeNode(treeNode);
      seqOper.add(oper2);
    }
    
    {
      TreeManagementOperation oper3 = getTreeManagementOperation(factory, device);
      TreeNode treeNode = new TreeNode(baseNodePath + "/Version", software.getVersion());
      treeNode.setFormat(DDFNode.DDF_FORMAT_CHR);
      oper3.addTreeNode(treeNode);
      seqOper.add(oper3);
    }
    
    {
      TreeManagementOperation oper4 = getTreeManagementOperation(factory, device);
      String uri = this.getSoftwareDownloadURI(factory, software);
      TreeNode treeNode = new TreeNode(baseNodePath + "/URI", uri);
      treeNode.setFormat(DDFNode.DDF_FORMAT_CHR);
      oper4.addTreeNode(treeNode);
      seqOper.add(oper4);
    }
    
    {
      TreeManagementOperation oper4 = getTreeManagementOperation(factory, device);
      String installationOpts = this.getSoftwareInstallationOpts(factory, software);
      TreeNode treeNode = new TreeNode(baseNodePath + "/InstallOpts", installationOpts);
      treeNode.setFormat(DDFNode.DDF_FORMAT_CHR);
      treeNode.setType("text/xml");
      oper4.addTreeNode(treeNode);
      seqOper.add(oper4);
    }
    
    // 检查是否在Delivered下存在软件节点, 如果存在则删除.
    DeviceBean deviceBean = factory.createDeviceBean();
    DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), this.getSoftwareDeliveredBaseNodePath(software));
    if (node != null) {
      TreeManagementOperation oper = new DeleteManagementOperation();
      TreeNode treeNode = new TreeNode(this.getSoftwareDeliveredBaseNodePath(software));
      treeNode.setFormat(DDFNode.DDF_FORMAT_NODE);
      oper.addTreeNode(treeNode);
      seqOper.add(oper);
    }
    
    if (!operations.isEmpty()) {
       // Name detection operations 
       this.appendDummyOperation(operations);
       return (ManagementOperation[]) operations.toArray(new ManagementOperation[0]);
    } else {
      return new ManagementOperation[0];
    }
  }

  /**
   * 返回创建或更新节点的DM Command.
   * @return
   */
  private TreeManagementOperation getTreeManagementOperation(ManagementBeanFactory factory, Device device) throws DMException {
    DeviceBean deviceBean = factory.createDeviceBean();
    DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), 
                                                        this.getSoftwareDeployedBaseNodePath(this.getTargetSoftware(factory)));
    if (node == null) {
       return new AddManagementOperation();
    } else {
      return new ReplaceManagementOperation();
    }
  }

  /**
   * 生成创建Software安装节点的DM命令
   * @param softwareExternalID
   * @return
   */
  private ManagementOperation[] getNextOperations4SetupDownloadNode(ManagementBeanFactory factory, Software software) throws DMException, ManagementException {
    String baseNodePath = getSoftwareDownloadBaseNodePath(software);

    List<ManagementOperation> operations = new ArrayList<ManagementOperation>();
    {
      TreeManagementOperation oper4 = new ReplaceManagementOperation();
      String uri = this.getSoftwareDownloadURI(factory, software);
      TreeNode treeNode = new TreeNode(baseNodePath + "/URI", uri);
      treeNode.setFormat(DDFNode.DDF_FORMAT_CHR);
      oper4.addTreeNode(treeNode);
      operations.add(oper4);
    }
    
    {
      TreeManagementOperation oper4 = new ReplaceManagementOperation();
      String installationOpts = this.getSoftwareInstallationOpts(factory, software);
      TreeNode treeNode = new TreeNode(baseNodePath + "/InstallOpts", installationOpts);
      treeNode.setFormat(DDFNode.DDF_FORMAT_CHR);
      treeNode.setType("text/xml");
      oper4.addTreeNode(treeNode);
      operations.add(oper4);
    }
    
    if (!operations.isEmpty()) {
       // Name detection operations 
       this.appendDummyOperation(operations);
       return (ManagementOperation[]) operations.toArray(new ManagementOperation[0]);
    } else {
      return new ManagementOperation[0];
    }
  }

  /**
   * 生成执行Download并安装的命令
   * @param software
   * @return
   */
  private ManagementOperation[] getNextOperations4ExecuteDownloadNode(ManagementBeanFactory factory, Software software) {
    String baseNodePath = getSoftwareDownloadBaseNodePath(software);
    
    List<ManagementOperation> operations = new ArrayList<ManagementOperation>();

    {
      ExecManagementOperation oper1 = new ExecManagementOperation();
      TreeNode treeNode = new TreeNode(baseNodePath + "/Operations/DownloadAndInstallAndActivate");
      treeNode.setFormat(DDFNode.DDF_FORMAT_NODE);
      oper1.addTreeNode(treeNode);
      operations.add(oper1);
    }

    if (!operations.isEmpty()) {
       // Name detection operations 
       this.appendDummyOperation(operations);
       return (ManagementOperation[]) operations.toArray(new ManagementOperation[0]);
    } else {
      return new ManagementOperation[0];
    }
  }

  /**
   * 创建Stage 2 Job, 采集软件安装的状态
   * @param factory
   * @throws DMException
   * @throws ManagementException
   */
  private void createStage2Job(ManagementBeanFactory factory) throws DMException, ManagementException {
    // Create a stage2 job
    ProvisionJobBean jobBean = factory.createProvisionJobBean();
    Software software = getTargetSoftware(factory);
    Device device = this.getDevice(factory);
    ProvisionJob job4Stage2 = jobBean.newJob4SoftwareInstall4Stage2(device, software);
    job4Stage2.setRequiredNotification(false);
    factory.beginTransaction();
    jobBean.update(job4Stage2);
    factory.commit();
    
    // 登记"检查软件安装状况"的任务到不在本次会话中执行. 确保此任务能够在软件安装完成后, 手机端自动发起DM Session时才执行.
    ManagementProcessor parentProcessor = this.getParentProcessor();
    if (parentProcessor != null && parentProcessor instanceof JobQueueProcessor) {
       ((JobQueueProcessor)parentProcessor).getExcludedJobIDs().add(new Long(job4Stage2.getID()));
    }
  }

  /**
   * 软件已经存在, 发送短信息.
   * @param device
   * @param software
   * @throws Exception
   * @throws SmsException
   * @throws IOException
   */
  private void sendExistsMessage(Device device, Software software) throws Exception, SmsException, IOException {
    MessageResources resource = getResources();
    String text = resource.getMessage(getLocale(device), 
                                      "dm.msm.software.installed.exists.nokia.s60.3rd.message", 
                                      software.getName());
    // Success!
    sendTextMessage(device, text);
  }

  // Public methods ------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#beginSession(sync4j.framework.engine.dm.SessionContext)
   */
  @Override
  public void beginSession(SessionContext session) throws ManagementException {
    this.sessionContext = session;

    String        sessionId = session.getSessionId();
    Principal     principal = session.getPrincipal();
    int           type      = session.getType();
    DevInfo       devInfo   = session.getDevInfo();

    if (log.isDebugEnabled()) {
      log.debug("Starting a new DM management session");
      log.debug("sessionId: " + sessionId);
      log.debug("principal: " + principal);
      log.debug("type: " + type);
      log.debug("deviceId: " + devInfo);
    }

    ManagementBeanFactory factory = null;
    try {
        // Load Device bundled with this Session
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        String deviceExternalID = this.sessionContext.getDmstate().deviceId;
        Device device = loadDeviceByExternalID(factory, deviceExternalID);
        if (device == null) {
          throw new ManagementException("Could not load device: from DM inventory.");
        }
        this.setDeviceID(device.getID());
  
        // Set the job status
        super.setJobStatus4BeginSession();
  
        // Update DevInfo
        super.updateDevInfo(devInfo);
        
        // Set next step
        step = STEP_Get_Software_Nodes_Information;
        
        // Get root nodes which will discovery.
        List<String> queuedNodePathsToDiscovery = new ArrayList<String>();
        // Download node path
        queuedNodePathsToDiscovery.add(this.getSoftwareDownloadBaseNodePath(this.getTargetSoftware(factory)));
        // Delivery node path
        String softwareDeliveredBaseNodePath = this.getSoftwareDeliveredBaseNodePath(this.getTargetSoftware(factory));
        // 获取Delivered 节点下是否存在软件, 为了确保性能, 不提取Data的内容, 只提取Name节点
        queuedNodePathsToDiscovery.add(softwareDeliveredBaseNodePath + "/Name");
        // Deployed node path
        queuedNodePathsToDiscovery.add(this.getBaseScomoDeployedNode());
        this.processor4Discovery = new GenericTreeDiscoveryProcessor(queuedNodePathsToDiscovery);
        
        this.processor4Discovery.beginSession(session);
        
    } catch (Exception ex) {
      throw new ManagementException("Error in beginSession: ", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#endSession(int)
   */
  @Override
  public void endSession(int completionCode) throws ManagementException {
    if (log.isDebugEnabled()) {
      log.debug("End a DM management session with sessionId: " + sessionContext.getSessionId());
    }

    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        // update the jobStatus for end session
        if (completionCode == DeviceDMState.STATE_COMPLETED) {
           if (!this.deployed) {
              // Waiting Client Initialized Session, client report the state of firmware update
              completionCode = DeviceDMState.WAITING_CLIENT_INITIALIZED_SESSION;
           } else {
             // 软件已经存在, 发送短信息提示
             Device device = this.getDevice(factory);
             Software software = this.getTargetSoftware(factory);
             this.sendExistsMessage(device, software);
           }
        }
        setJobStatus4EndSession(completionCode);
    } catch (Exception ex) {
      throw new ManagementException("End of DM S60V3InstallProcessor4DL error", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#getNextOperations()
   */
  @Override
  public ManagementOperation[] getNextOperations() throws ManagementException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        Software software = getTargetSoftware(factory);
        Device device = this.getDevice(factory);
        switch (step) {
               case STEP_Get_Software_Nodes_Information: {
                 ManagementOperation[] operations = this.processor4Discovery.getNextOperations();
                 if (operations != null && operations.length > 0) {
                    return operations;
                 } else {
                   this.processor4Discovery.endSession(DeviceDMState.STATE_COMPLETED);
                   if (this.isInstalledAndActived(factory, device, software)) {
                      // 检测到软件已经安装, 无需运行后续步骤
                      this.deployed = true;
                      this.step = STEP_Deployed;
                      log.info(software.getExternalId() + " had been deployed, give up installation process for device: ." + device.getExternalId());
                      return new ManagementOperation[0];
                   }
                 }
               }
               case STEP_Create_Or_Update_Software_Node: {
                 this.step = STEP_Create_Or_Update_Software_Node;
                 ManagementOperation[] operations = this.getNextOperations4CreateDownloadNode(factory, device, software);
                 if (operations != null && operations.length > 0) {
                    return operations;
                 }
               }
               case STEP_Setup_Software_Node_Attributes: {
                 this.step = STEP_Setup_Software_Node_Attributes;
                 ManagementOperation[] operations = this.getNextOperations4SetupDownloadNode(factory, software);
                 if (operations != null && operations.length > 0) {
                    return operations;
                 }
               }
               case STEP_Execute_Installation: {
                 this.step = STEP_Execute_Installation;
                 ManagementOperation[] operations = this.getNextOperations4ExecuteDownloadNode(factory, software);
                 if (operations != null && operations.length > 0) {
                    return operations;
                 }
               }
               default:
                 // End
                 return new ManagementOperation[0];
        }
    } catch (Exception e) {
      throw new ManagementException("Error in getNextOperation()", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.BaseProcessor#setOperationResults(sync4j.framework.engine.dm.ManagementOperationResult[])
   */
  @Override
  public void setOperationResults(ManagementOperationResult[] results) throws ManagementException {
    @SuppressWarnings("unused")
    int statusCode = 0; 
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        switch (this.step) {
            case STEP_Get_Software_Nodes_Information: {
                 this.processor4Discovery.setOperationResults(results);
                 break;
            }
            case STEP_Create_Or_Update_Software_Node: {
                 this.step = STEP_Setup_Software_Node_Attributes;
                 for (ManagementOperationResult result: results) {
                     String command = result.getCommand();
                     if (command.equalsIgnoreCase("Add")) {
                        statusCode = result.getStatusCode();
                     }
                 }
                 break;
            }
            case STEP_Setup_Software_Node_Attributes: {
                 this.step = STEP_Execute_Installation;
                 for (ManagementOperationResult result: results) {
                     String command = result.getCommand();
                     if (command.equalsIgnoreCase("Replace")) {
                        statusCode = result.getStatusCode();
                     }
                 }
                 break;
            }
            case STEP_Execute_Installation: {
                 this.step = STEP_Stage_1_Finished;
                 for (ManagementOperationResult result: results) {
                     String command = result.getCommand();
                     if (command.equalsIgnoreCase("Exec")) {
                        statusCode = result.getStatusCode();
                     }
                 }
                 // Create stage 2 job to response for CLIENT_INITIALIZED_SESSION
                 createStage2Job(factory);
                 break;
            }
            case STEP_Deployed:
            default :
                 break;
        } // End of switch
    
    } catch (Exception e) {
      throw new ManagementException("Error in setOperationResults()", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
