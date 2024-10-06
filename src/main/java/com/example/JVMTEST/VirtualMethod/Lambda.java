package com.example.JVMTEST.VirtualMethod;



/**
 * @author banana
 * @create 2024-10-05 17:27
 */

@FunctionalInterface
interface Func {
    public boolean func(String str);
}

public class Lambda {

    public void lambda(Func func) {
        return;
    }

    public static void main(String[] args) {
        // invokespecial
        Lambda lambda = new Lambda();

        // invokedynamic
        Func func = s -> {
            return true;
        };

        //  invokedynamic
        lambda.lambda(func);

        //  invokedynamic
        lambda.lambda(s -> {
            return true;
        });
    }

}
