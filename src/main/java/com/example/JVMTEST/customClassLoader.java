package com.example.JVMTEST;

import java.io.FileNotFoundException;

/**
 * @author banana
 * @create 2024-06-06 0:28
 */
public class customClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try{
            byte[] result = getClassFromCustomPath(name);

            if(result == null) {
                throw new FileNotFoundException();
            } else {
                return defineClass(name, result, 0, result.length);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private byte[] getClassFromCustomPath(String name) {
        // 从自定义路径中加载指定类：细节略
        // 如果字节码文件加密，这里需要有解密逻辑
        return null;
    }

}
