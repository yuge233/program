package cn.group.program;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(ClassUtils.getDefaultClassLoader().getResource("").getPath());
    }

}
