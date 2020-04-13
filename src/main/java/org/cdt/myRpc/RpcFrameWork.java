package org.cdt.myRpc;

import org.springframework.util.CollectionUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author dataochen
 * @Description 支持多接口 暴露和引用
 * @date: 2020/4/13 11:38
 */
public class RpcFrameWork {

    public static void export(int port, Object... servers) {

        System.out.println("提供者准备暴露服务");

        if (Objects.isNull(servers)) {
            throw new IllegalArgumentException("server is null");
        }
        if (Objects.isNull(port)) {
            throw new IllegalArgumentException("port is null");
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        一直监听端口
        while (true) {
            try {
                System.out.println("开始接受请求");
                Socket accept = serverSocket.accept();
                System.out.println("提供者收到请求");
                InputStream inputStream = accept.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                String methodName = (String) objectInputStream.readObject();
                Object[] arg = (Object[]) objectInputStream.readObject();
                Class[] paramType = (Class[]) objectInputStream.readObject();
                Class interfaceClass = (Class) objectInputStream.readObject();
//                路由 根据接口interfaceClass 来定位哪一个server
                for (Object server : servers) {
                    boolean contains = CollectionUtils.contains(Arrays.asList(server.getClass().getInterfaces()).iterator(), interfaceClass);
                    if (contains) {
//                        反射
                        Class<?> aClass = server.getClass();
                        Method method = aClass.getMethod(methodName, paramType);
                        Object res = method.invoke(server, arg);
                        System.out.println("提供者执行完成 result=" + res);
//            网络io 返回结果
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
                        objectOutputStream.writeObject(res);
                        System.out.println("提供者完成提供");
                        break;
                    }
                }
//
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 引用
     *
     * @param <T>
     * @return
     */
    public static <T> T refer(Class<T> interfaces, String address, int port) throws IOException {
        if (Objects.isNull(interfaces)) {
            throw new IllegalArgumentException("interfaces is null");
        }
        if (Objects.isNull(address)) {
            throw new IllegalArgumentException("address is null");
        }
        if (Objects.isNull(port)) {
            throw new IllegalArgumentException("port is null");
        }

        return (T) Proxy.newProxyInstance(interfaces.getClassLoader(), new Class[]{interfaces}
                , (p, m, a) -> {
//                    Object invoke = m.invoke(p, a);
//            网络请求
        Socket socket = new Socket(address, port);
                    OutputStream outputStream = socket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//                    方法名 参数
                    objectOutputStream.writeObject(m.getName());
                    objectOutputStream.writeObject(a);
                    objectOutputStream.writeObject(m.getParameterTypes());
                    objectOutputStream.writeObject(interfaces);
                    InputStream inputStream = socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    Object object = objectInputStream.readObject();
                    return object;
                });
    }

}
