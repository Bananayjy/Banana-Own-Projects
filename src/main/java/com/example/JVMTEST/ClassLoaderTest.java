package com.example.JVMTEST;

/**
 * @author banana
 * @create 2024-06-05 21:34
 */
public class ClassLoaderTest {
    public static void main(String[] args) {

        // 他们的关系如图目录 /A/B/C.text, 是一种包含关系，只不过可以通过getPatent方法获取上层

        // 获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader); // sun.misc.Launcher$AppClassLoader@18b4aac2

        // 获取系统类加载器上层：扩展类加载器
        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader); // sun.misc.Launcher$ExtClassLoader@6842775d

        // 获取扩展类加载器的上层(引导类加载器): 获取不到引导类加载器
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(bootstrapClassLoader); // null

        // 对于用户自定义类来说：默认使用系统类加载器进行加载
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);    // sun.misc.Launcher$AppClassLoader@18b4aac2

        // String类(Java的核心类库)都是使用引导类加载器进行加载
        ClassLoader classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1);   // null
    }
}
