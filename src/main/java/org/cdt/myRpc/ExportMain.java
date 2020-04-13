package org.cdt.myRpc;

/**
 * @author dataochen
 * @Description
 * @date: 2020/4/13 18:55
 */
public class ExportMain {
    public static void main(String[] args) {
        new Thread(()->{
            //        暴露服务
            TestServerOneImpl testServerOne = new TestServerOneImpl();
            TestServiceTwoImpl testServiceTwo = new TestServiceTwoImpl();
            RpcFrameWork.export(1234, testServerOne, testServiceTwo);
        }).start();
    }
}
