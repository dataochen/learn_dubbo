package org.cdt;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;

/**
 * @author dataochen
 * @Description
 * @date: 2019/10/24 20:37
 */
public class  Test {

    private String c;

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long maxMemory = runtime.maxMemory();
        boolean ok = (maxMemory - (totalMemory - freeMemory) > 2048); // Alarm when spare memory < 2M
        String msg = "max:" + (maxMemory / 1024 / 1024) + "M,total:"
                + (totalMemory / 1024 / 1024) + "M,used:" + ((totalMemory / 1024 / 1024) - (freeMemory / 1024 / 1024)) + "M,free:" + (freeMemory / 1024 / 1024) + "M";
        System.out.println(msg);

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

        double systemLoadAverage = operatingSystemMXBean.getSystemLoadAverage();
        int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
        System.out.println(systemLoadAverage+"==="+availableProcessors);
    }

    public <T>  T cc(T cc) {
        String[] strings = new String[3];
        return null;
    }
}
