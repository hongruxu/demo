package com.hongruxu.demo.persist;

import java.util.HashMap;
import java.util.Map;


// Persist 一个简单的单例类，用来存储临时数据，用于展示接口读写操作
public class Persist {

    // 用内存Map模拟保存数据,此处简单处理，只为了展示用
    private static Map<String, String> mapStorage = new HashMap<>();

    // 使用 volatile 保证可见性和禁止指令重排序
    private static volatile Persist instance;
    // 构造函数 private 化，防止主动产生对像
    private Persist() {}
    

    public static Persist getInstance() {
        if (instance == null) { // 第一次检查
            synchronized (Persist.class) {
                if (instance == null) { // 第二次检查
                    instance = new Persist();
                }
            }
        }
        return instance;
    }
    
    // 把数据写入内存
    public String put(String key, String value) {
        return mapStorage.put(key, value);
    }

    // 读出数据
    public String get(String key){
        return mapStorage.get(key);
    }

    // 删除数据
    public String del(String key){
        return mapStorage.remove(key);
    }

}
