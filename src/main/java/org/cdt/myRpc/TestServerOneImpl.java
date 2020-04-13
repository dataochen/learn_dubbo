package org.cdt.myRpc;

/**
 * @author dataochen
 * @Description
 * @date: 2020/4/13 17:55
 */
public class TestServerOneImpl implements TestServiceOne {
    @Override
    public String testOne(String content) {
        return "cdtTestOne+"+content;
    }

    @Override
    public String testOneMore(String content) {
        return "cdtTestOneMore+"+content;

    }
}
