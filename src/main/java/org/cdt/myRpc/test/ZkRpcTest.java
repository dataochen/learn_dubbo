package org.cdt.myRpc.test;

import org.cdt.myRpc.TestServerOneImpl;
import org.cdt.myRpc.TestServiceOne;
import org.cdt.myRpc.TestServiceTwo;
import org.cdt.myRpc.TestServiceTwoImpl;
import org.cdt.myRpc.zk.DirectoryZkAdapter;

import java.io.IOException;
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
            DirectoryZkAdapter directoryZkAdapter = new DirectoryZkAdapter("127.0.0.1");
            product(directoryZkAdapter);
            Thread.sleep(Long.parseLong("2000"));
            System.out.println("========开始消费");
            consume(directoryZkAdapter);
            System.out.println("========开始摧毁");
            destroy(directoryZkAdapter);
            System.out.println("===end");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void product(DirectoryZkAdapter directoryZkAdapter) throws UnknownHostException {
        TestServerOneImpl testServerOne = new TestServerOneImpl();
        TestServiceTwoImpl testServiceTwo = new TestServiceTwoImpl();
        directoryZkAdapter.export(testServerOne, TestServiceOne.class);
        directoryZkAdapter.export(testServiceTwo, TestServiceTwo.class);
    }

    private static void consume(DirectoryZkAdapter directoryZkAdapter) throws InterruptedException, ExecutionException, IllegalAccessException, IOException {

        TestServiceOne testServiceOne = directoryZkAdapter.reference(TestServiceOne.class);
        testServiceOne.testOne("ZkRpcTest");
        TestServiceTwo reference = directoryZkAdapter.reference(TestServiceTwo.class);
        reference.testTwo("ZkRpcTest");
    }

    private static void destroy(DirectoryZkAdapter directoryZkAdapter) {
        directoryZkAdapter.destroy();
    }
}
