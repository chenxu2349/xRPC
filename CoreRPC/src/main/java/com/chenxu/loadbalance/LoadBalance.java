package com.chenxu.loadbalance;

import com.chenxu.common.URL;

import java.util.List;
import java.util.Random;

public class LoadBalance {

    // TODO 实现更多优秀的负载均衡策略

    public static URL random(List<URL> urls) {
        Random random = new Random();
        int i = random.nextInt(urls.size());
        return urls.get(i);
    }
}
