package org.cdt.myRpc.test;

import org.cdt.myRpc.zk.ZkUniqNoUtil;

/**
 * @author dataochen
 * @Description
 * @date: 2020/6/3 11:26
 */
public class ZkUniqUtilTest {
    public static void main(String[] args) {
        ZkUniqNoUtil instance = ZkUniqNoUtil.getInstance("127.0.0.1");
        Thread thread = new Thread(() -> {

        for (int i = 0; i < 100; i++) {

            String cdt_ = instance.getUuid("cdt_");
            System.out.println(cdt_);
        }
        });
        thread.start();
        thread.start();

    }
}
