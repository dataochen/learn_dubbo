package org.cdt.myRpc.test;

import org.cdt.myRpc.TestServerOneImpl;
import org.cdt.myRpc.TestServiceOne;
import org.cdt.myRpc.TestServiceTwo;
import org.cdt.myRpc.TestServiceTwoImpl;
import org.cdt.myRpc.zk.DirectoryZkAdapter;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * @author dataochen
 * @Description 基础zk后 测试类
 * @date: 2020/5/29 18:31
 */
public class ZkRpcTest {
    public static void main(String[] args) {
        try {
            product();
            consume();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void product() throws UnknownHostException {
        TestServerOneImpl testServerOne = new TestServerOneImpl();
        TestServiceTwoImpl testServiceTwo = new TestServiceTwoImpl();
        DirectoryZkAdapter directoryZkAdapter = new DirectoryZkAdapter("127.0.0.1");
        directoryZkAdapter.export(testServerOne, TestServiceOne.class);
        directoryZkAdapter.export(testServiceTwo, TestServiceTwo.class);
    }

    private static void consume() throws InterruptedException, ExecutionException, IllegalAccessException, UnknownHostException {
        DirectoryZkAdapter directoryZkAdapter = new DirectoryZkAdapter("127.0.0.1");

        TestServiceOne testServiceOne = directoryZkAdapter.reference(TestServiceOne.class);
        testServiceOne.testOne("ZkRpcTest");
        TestServiceTwo reference = directoryZkAdapter.reference(TestServiceTwo.class);
        reference.testTwo("ZkRpcTest");
    }
}
