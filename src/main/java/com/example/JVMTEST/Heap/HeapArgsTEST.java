package com.example.JVMTEST.Heap;

/**
 *
 * - -XX:+PrintrlagsInitial:查看所有的参数的默认初始值
 * - -XX:+Printrlagsrinal :查看所有的参数的最终值(可能会存在修改不再是初始值)
 * - -Xms:初始堆空间内存(默认为物理内存的1/64)
 * - -Xmx:最大堆空间内存(默认为物理内存的1/4)
 * - -Xmn:设置新生代的大小。(初始值及最大值)
 * - -XX:NewRatio:配置新生代与老年代在堆结构的占比
 * - -XX:SurvivorRatio:设置新生代中Eden和S0/S1空间的比例
 * - -XX:MaxTenuringThreshold:设置新生代垃圾的最大年龄（默认即最大，15）
 * - XX:+PrintGCDetails:输出详细的GC处理日志
 *   - 打印qc简要信息:①-XX:+PrintGC  ②-verbose:gC
 * - -XX:HandlePromotionFailure:是否设置空间分配担保
 * 
 * @author banana
 * @create 2024-10-08 23:04
 */
public class HeapArgsTEST {
}
