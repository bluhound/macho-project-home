/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.siam.am.common.log;

import java.util.Timer;

import org.opensaml.util.resource.ClasspathResource;
import org.opensaml.util.resource.FilesystemResource;
import org.opensaml.util.resource.Resource;
import org.opensaml.util.resource.ResourceChangeWatcher;
import org.opensaml.util.resource.ResourceException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.ErrorStatus;
import ch.qos.logback.core.status.StatusManager;
import edu.internet2.middleware.shibboleth.common.log.LogbackConfigurationChangeListener;

/**
 * Simple logging service that watches for logback configuration file changes
 * and reloads the file when a change occurs.
 */
public class LogbackLoggingService {

  /**
   * Constructor.
   * 
   * @param taskTimer
   *          resource watchdog task timer
   * @param loggingConfigurationFile
   *          logback configuration file. If start with "classpath:", will load from classpath.
   * @param pollingFrequency
   *          frequency the configuration file should be checked for changes
   */
  public LogbackLoggingService(Timer taskTimer, String loggingConfigurationFile, long pollingFrequency) {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    StatusManager statusManager = loggerContext.getStatusManager();

    try {
      Resource configResource = null;
      if (loggingConfigurationFile != null && loggingConfigurationFile.startsWith("classpath:")) {
        configResource = new ClasspathResource(loggingConfigurationFile.substring("classpath:".length()));
      } else {
        configResource = new FilesystemResource(loggingConfigurationFile);
      }
      LogbackConfigurationChangeListener configChangeListener = new LogbackConfigurationChangeListener();
      configChangeListener.onResourceCreate(configResource);

      ResourceChangeWatcher resourceWatcher = new ResourceChangeWatcher(configResource, pollingFrequency, 5);
      resourceWatcher.getResourceListeners().add(configChangeListener);

      taskTimer.schedule(resourceWatcher, 0, pollingFrequency);
    } catch (ResourceException e) {
      statusManager.add(new ErrorStatus("Error loading logging configuration file: " + loggingConfigurationFile, this,
          e));
    }
  }
}
