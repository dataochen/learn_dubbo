package org.cdt.mySpi;

/**
 * @author dataochen
 * @Description
 * @date: 2020/4/15 10:40
 */
public class SayGood implements TestInterface {
    @Override
    public void say(String word) {
        System.out.println("good "+word);
    }
}
