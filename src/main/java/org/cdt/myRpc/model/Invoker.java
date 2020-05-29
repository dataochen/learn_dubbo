package org.cdt.myRpc.model;

/**
 * @author dataochen
 * @Description
 * @date: 2020/5/29 18:03
 */
public class Invoker {
    public Invoker(String providerIp) {
        this.providerIp = providerIp;
    }

    private String providerIp;



    public String getProviderIp() {
        return providerIp;
    }

    public void setProviderIp(String providerIp) {
        this.providerIp = providerIp;
    }
}
