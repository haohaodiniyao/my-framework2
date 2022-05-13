package com.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class RedissionTest {
    @Resource
    private RedissonClient redissonClient;

    @Test
    public void test(){
        String lockName = "hello_lock";
        RLock lock = redissonClient.getLock(lockName);
        try {
            boolean result = lock.tryLock(10, TimeUnit.SECONDS);
            log.info("获取锁{} = {}",lockName,result);

            Thread.sleep(60*1000);

        } catch (InterruptedException e) {
            log.error("获取锁失败",e);
        }finally {
            lock.unlock();
        }
    }
}
