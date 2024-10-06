package com.example.JVMTEST;

/**
 * @author banana
 * @create 2024-06-05 22:50
 */
public class ClassLoaderTest1 {
    public static void main(String[] args) {

        // 获取BootstrapClassLoader能够加载的api路径
        /*URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL.toExternalForm());
        }*/

        // 获取扩展类加载器能够加载的api路径代码示例
        String extDirs = System.getProperty("java.ext.dirs");
        for(String path : extDirs.split(";")) {
            System.out.println(path);
        }
    }
}
