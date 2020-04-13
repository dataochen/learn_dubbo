package org.cdt.myRpc;

/**
 * @author dataochen
 * @Description
 * @date: 2020/4/13 17:56
 */
public class TestServiceTwoImpl implements TestServiceTwo {
    @Override
    public String testTwo(String content) {
        return "cdtTestTwo+"+content;

    }
}
