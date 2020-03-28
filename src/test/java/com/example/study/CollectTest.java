package com.example.study;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class CollectTest {

    public static void main(String[] args) {
        Set set = new HashSet<>();
        Set set1 = new LinkedHashSet();
        Set set2 = new TreeSet();
        Set set3 = new ConcurrentSkipListSet();
        Set set4 = new CopyOnWriteArraySet();

        Hashtable hashtable = new Hashtable();
        Map map = new ConcurrentHashMap();
        Map c = new LinkedHashMap();
        Map hashMap = new HashMap();

        List list = new ArrayList();

        Stack<Character> stack = new Stack<>();
    }
}
