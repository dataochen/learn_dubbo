/* 
 * Copyright 2011 Alibaba.com All right reserved. This software is the 
 * confidential and proprietary information of Alibaba.com ("Confidential 
 * Information"). You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement you entered 
 * into with Alibaba.com. 
 */
package org.cdt.rpc;
  
/**
 * RpcProvider 
 *  
 * @author william.liangf 
 */  
public class RpcProvider {  
  
    public static void main(String[] args) throws Exception {  
        HelloService service = new HelloServiceImpl();
        TestServiceImp testServiceImp = new TestServiceImp();
        RpcFramework.export(service, 1234);  
        RpcFramework.export(testServiceImp, 1234);
    }
  
}  