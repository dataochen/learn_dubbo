package org.cdt.myRpc.cluster;

import org.cdt.myRpc.model.Invoker;

import java.util.List;

/**
 * @author dataochen
 * @Description
 * @date: 2020/5/29 15:30
 */
public interface Cluster {

    public Invoker select(List<Invoker> invokers);
}
