package org.cdt.myRpc;

import java.io.IOException;

/**
 * @author dataochen
 * @Description
 * @date: 2020/4/13 17:57
 */
public class ReferMain {
    public static void main(String[] args) throws InterruptedException, IOException {
//        引用服务
        Class<TestServiceOne> testServiceOneClass = TestServiceOne.class;
        TestServiceOne refer = RpcFrameWork.refer(TestServiceOne.class, "127.0.0.1", 1234);
        TestServiceTwo refer2 = RpcFrameWork.refer(TestServiceTwo.class, "127.0.0.1", 1234);
        for (int i = 0; i < 10; i++) {

            System.out.println(refer.testOne(i + ""));
            System.out.println(refer.testOneMore(i + ""));
            System.out.println(refer2.testTwo(i + ""));
        }
    }

    public static void export() {

    }
}
