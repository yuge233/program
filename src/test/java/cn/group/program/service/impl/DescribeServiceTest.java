package cn.group.program.service.impl;

import cn.group.program.model.Describe;
import cn.group.program.service.DescribeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DescribeServiceTest {
    @Autowired
    private DescribeService service;

    @Test
    public void findByOrder() {
    }

    @Test
    public void save() {
        Describe describe=new Describe();
        describe.setToken("苹果");
        describe.setOwn(1L);
        service.save(describe);
    }
}