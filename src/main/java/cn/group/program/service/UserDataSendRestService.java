package cn.group.program.service;

import cn.group.program.model.Describe;
import cn.group.program.model.Question;
import com.sun.xml.internal.fastinfoset.util.StringArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/member")
public class UserDataSendRestService {

    @Autowired
    QuestionService questionService;

    @Autowired
    DescribeService describeService;

    @RequestMapping("/saveUserData")
    public String saveConfigMachineBilling(HttpServletRequest request){
        try {
            //获取所有变量
            List<String> list = Arrays.asList(request.getParameterValues("describeArray"));
            String result = request.getParameter("questionResult");
            Describe describes;
            Question question = new Question();
            System.out.println(result);
            question.setAnswer(result);
            questionService.save(question);
            for(String describe : list) {
                System.out.println(describe);
                describes = new Describe();
                describes.setOwn(question.getId());
                describes.setToken(describe);
                describeService.save(describes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "success";
    }
}