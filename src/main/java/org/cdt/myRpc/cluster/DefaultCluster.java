package org.cdt.myRpc.cluster;

import org.cdt.myRpc.model.Invoker;

import java.util.List;

/**
 * @author dataochen
 * @Description
 * @date: 2020/5/29 18:04
 */
public class DefaultCluster implements Cluster {
    @Override
    public Invoker select(List<Invoker> invokers) {
        return invokers.get(0);
    }
}
