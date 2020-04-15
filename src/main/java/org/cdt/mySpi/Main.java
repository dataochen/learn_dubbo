package org.cdt.mySpi;

import java.io.IOException;
import java.util.List;

/**
 * @author dataochen
 * @Description
 * @date: 2020/4/15 11:02
 */
public class Main {
    public static void main(String[] args) {
        try {
            loadAll();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void loadAll() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        List<TestInterface> testInterfaces = MySpiLoad.loadAll(TestInterface.class);
        for (TestInterface testInterface : testInterfaces) {
            testInterface.say("cdt");
        }
    }

    public static void load(String key) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        TestInterface good = MySpiLoad.load(TestInterface.class, key);
        good.say("cdt");
    }
    }
