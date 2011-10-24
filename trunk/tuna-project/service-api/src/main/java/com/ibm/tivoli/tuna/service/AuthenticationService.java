/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author zhaodonglu
 *
 */
@WebService(targetNamespace="http://service.tuna.tivoli.ibm.com/")
public interface AuthenticationService {
  
  @WebMethod(operationName = "authenticate")
  @WebResult(name="AuthnResult")
  public abstract AuthenticationResult authenticate(@WebParam(name = "requester")Requester requester, @WebParam(name = "context")Context context, @WebParam(name = "credentials")Credentials credentials);

}
