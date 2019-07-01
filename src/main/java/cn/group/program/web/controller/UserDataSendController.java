package cn.group.program.web.controller;

import cn.group.program.model.Describe;
import cn.group.program.model.Question;
import cn.group.program.service.DescribeService;
import cn.group.program.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/member")
public class UserDataSendController {

    @Autowired
    QuestionService questionService;

    @Autowired
    DescribeService describeService;

    @GetMapping("/userAddGameContent")
    public String addGameContent() {
        return "userAddGameContent";
    }

    @ResponseBody
    @RequestMapping("/saveUserData.ajax")
    public String saveConfigMachineBilling(HttpServletRequest request){
        try {
            //获取所有变量
            List<String> list = Arrays.asList(request.getParameterValues("describeArray"));
            String result = request.getParameter("questionResult");
            Describe describes;
            Question question = new Question();
            System.out.println(result);
            question.setAnswer(result);
            Long id=questionService.save(question);
            for(String describe : list) {
                System.out.println(describe);
                describes = new Describe();
                describes.setOwn(question.getId());
                describes.setToken(describe);
                describeService.save(describes);
            }
            return ""+id;
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}