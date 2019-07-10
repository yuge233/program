package cn.group.program;

import cn.group.program.web.endpoint.ManyServerEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.FileNotFoundException;

@SpringBootApplication
public class ProgramApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(ProgramApplication.class, args);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){ //暴露端点配置
        return new ServerEndpointExporter();
    }

    @Bean
    public ManyServerEndpoint manyServerEndpoint(){ //创建端点
        return new ManyServerEndpoint();
    }

}
