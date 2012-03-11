/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/SyncResultWriter.java,v 1.3 2008/11/18 08:16:47 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/18 08:16:47 $
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
package com.npower.unicom.sync;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/11/18 08:16:47 $
 */
public class SyncResultWriter {
  private static Log log = LogFactory.getLog(SyncResultWriter.class);
  
  private static final String SUCCESS_FLAG = "0";
  private static final String SUB_DIR_FOR_RIGHT = "right";
  private static final String SUB_DIR_FOR_BAD = "bad";
  private static final String SUB_DIR_FOR_SEMI_WRONG = "semiwrong";
  
  private int totalRecords = 0;
  private int errorRecords = 0;
  private int successRecords = 0;
  
  /**
   * Request File Object.
   */
  private File requestFile;

  private File bodyFile;

  private Writer bodyWriter;
  private File responseDir;

  /**
   * �����ִ�ļ�������
   * @param requestFile
   *        �����ļ� 
   * @param responseDir
   *        ��ִ�ļ���ŵĻ���Ŀ¼, �ڴ�Ŀ¼�������right, bad��semiwrong����Ŀ¼
   */
  public SyncResultWriter(File requestFile, File responseDir) {
    super();
    this.requestFile = requestFile;
    this.responseDir = responseDir;
  }
  
  /**
   * ��Writer, ׼��д������
   * @throws IOException
   */
  public void open() throws IOException {
    bodyFile = File.createTempFile("otas_sync_error", "body");
    bodyWriter = new FileWriter(bodyFile);
    if (log.isDebugEnabled()) {
       log.debug("open reponse body file: " + this.bodyFile.getAbsolutePath());
    }
  }
  
  /**
   * �ر�Writer
   * @throws IOException
   */
  public void close() throws IOException {
    this.bodyWriter.close();

    File outputFile = null;

    // ��������ļ�·�����ļ���
    String requestFilename = this.requestFile.getName();
    String reponseFilename = requestFilename.substring(0, requestFilename.length() - ".req".length()) + ".res";
    if (this.totalRecords == this.successRecords) {
       // ��ȫ��ȷ
       File outputDir = new File(this.responseDir, SUB_DIR_FOR_RIGHT);
       if (!outputDir.exists()) {
          log.info("create directory: " + outputDir.getAbsolutePath());
          outputDir.mkdirs();
       }
       outputFile = new File(outputDir, reponseFilename);
    } else if (this.totalRecords == this.errorRecords) {
      // ��ȫ����
      File outputDir = new File(this.responseDir, SUB_DIR_FOR_BAD);
      if (!outputDir.exists()) {
         log.info("create directory: " + outputDir.getAbsolutePath());
         outputDir.mkdirs();
      }
      outputFile = new File(outputDir, reponseFilename);
    } else {
      // ���ִ���
      File outputDir = new File(this.responseDir, SUB_DIR_FOR_SEMI_WRONG);
      outputFile = new File(outputDir, reponseFilename);
      if (!outputDir.exists()) {
         log.info("create directory: " + outputDir.getAbsolutePath());
         outputDir.mkdirs();
      }
    }
    
    log.info("output reponse file: " + outputFile.getAbsolutePath());
    
    // ����ļ�ͷ��Ϣ
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    RequestHeader header = RequestHeader.parseHeaderInfo(this.requestFile);
    Writer writer = new FileWriter(outputFile);
    writer.write(header.getSerialNumber());
    writer.write('\t');
    writer.write(header.getVersion());
    writer.write('\t');
    writer.write(format.format(new Date()));
    writer.write('\t');
    writer.write("����");
    writer.write('\t');
    writer.write(this.totalRecords);
    writer.write('\t');
    writer.write(this.successRecords);    
    writer.write('\t');
    writer.write('\n');
    
    if (this.totalRecords != this.successRecords && this.totalRecords != this.errorRecords) {
       // ���ִ���
       FileReader in = new FileReader(this.bodyFile);
       char[] buf = new char[512];
       int len = in.read(buf);
       while (len > 0) {
             writer.write(buf, 0, len);
             len = in.read(buf);
       }
       in.close();
    }
    
    writer.close();
  }
  
  /**
   * ���һ��Item
   * @param item
   * @throws IOException
   */
  public void write(SyncItem item, String errorCode) throws IOException {
    if (item == null) {
       return;
    }
    this.totalRecords++;
    if (SUCCESS_FLAG.equals(errorCode)) {
       this.successRecords++;
    } else {
      this.errorRecords++;
      // ���������Ϣ
      this.bodyWriter.write(item.getId());
      this.bodyWriter.write('\t');
      this.bodyWriter.write(errorCode);
      this.bodyWriter.write('\n');
    }
  }

}
