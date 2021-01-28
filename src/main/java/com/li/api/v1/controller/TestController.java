package com.li.api.v1.controller;

import com.li.api.manager.recketmq.ProducerSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黎源
 * @date 2021/1/27 20:45
 */
@RestController
public class TestController {

    @Autowired
    private ProducerSchedule producerSchedule;

    @GetMapping("/text")
    public void test1() {
        try {
            producerSchedule.send("mytopic", "", "nihao");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
