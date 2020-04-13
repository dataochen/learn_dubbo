package org.cdt;

import java.lang.reflect.InvocationTargetException;

/**
 * @author dataochen
 * @Description
 * @date: 2019/10/24 20:37
 */
public class  Test {

    private String c;

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        while (true) {
            System.out.println("kais");
            for (int i = 0; i < 19; i++) {
                System.out.println(i);
                if (i == 1) {
                    break ;
                }
            }
        }
    }

    public <T>  T cc(T cc) {
        String[] strings = new String[3];
        return null;
    }
}
