package org.cdt.mySpi.support;

import org.cdt.mySpi.MySpiLoad;
import org.cdt.mySpi.TestInterface;

import java.io.IOException;

/**
 * @author dataochen
 * @Description
 * @date: 2020/4/15 14:16
 */
public class TestService {
    private TestInterface getBeanFactory() {
        TestInterface good = null;
        try {
            good = MySpiLoad.loadFirst(TestInterface.class);
            return good;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void test() {
        TestInterface beanFactory = getBeanFactory();
        beanFactory.say("test");
    }

    public static void main(String[] args) {
        TestService testService = new TestService();
        testService.test();
    }
}
