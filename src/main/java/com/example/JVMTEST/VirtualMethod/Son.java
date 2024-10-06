package com.example.JVMTEST.VirtualMethod;

/**
 * 解析调用汇总废墟方法、虚方法测试
 * @author banana
 * @create 2024-10-05 16:20
 */
public class Son extends Father{

    public Son() {
        // invokespecial
        super();
    }

    public Son(int age) {
        // invokespecial
        this();
    }

    // 注：父类静态方法无法被重写
    public static  void showStatic(String str) {
        System.out.println("son " + str);
    }

    // 私有方法
    private void showPrivate(String str) {
        System.out.println("son private " + str);
    }

    public void show() {
        // invokestatic(非虚方法)
        showStatic("banana");
        // invokestatic(非虚方法)
        super.showStatic("banana");
        // invokespecial(非虚方法)
        showPrivate("banana");
        // invokespecial(非虚方法)
        super.showCommon();

        // invokevirtual（但仍然是非虚方法，声明final不能被子类重写）
        showFinal();
        // invokespecial
        super.showFinal();
        // invokevirtual(虚方法，没有加super或this，如果子类重写了，就会出现多态的特性，编译过程中确定不下来)
        showCommon();
        info();

        // invokeinterface（体现虚方法，使用实现类中的方法）
        MethodInterface in = null;
        in.methodA();
    }

    public void info() {}

    public void display(Father f) {
        f.showCommon();
    }

    public static void main(String[] args) {
        Son so = new Son();
        so.show();
    }

}

interface MethodInterface{
    void methodA();
}

class Father {
    public Father() {
        System.out.println("Fatherd的构造器");
    }

    // 静态方法
    public static void showStatic(String str) {
        System.out.println("father " + str);
    }

    // final方法
    public final void showFinal() {
        System.out.println("father show final");
    }

    // 普通方法
    public void showCommon() {
        System.out.println("father show common");
    }

}
