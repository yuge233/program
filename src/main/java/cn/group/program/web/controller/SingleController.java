package cn.group.program.web.controller;

import cn.group.program.model.Describe;
import cn.group.program.model.Question;
import cn.group.program.service.DescribeService;
import cn.group.program.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Random;

@Controller
public class SingleController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private DescribeService describeService;

    Random random=new Random();

    @GetMapping("/single_choice")
    public String single_choice(){
        return "single_choice";
    }

    @GetMapping("/single")
    public String single(Model model){
        long count = questionService.count(); //获取题库总量
        long id = 1;  //随机开始一题
        if(count != 0){
            id =  Math.abs(random.nextLong()) % count + 1;
        }else{
            return "redirect:/test";
        }
        return "redirect:/single/" + id;
    }

    @RequestMapping(value = "/single/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public String single_id(@PathVariable long id, Model model){
        try{
            Question question=questionService.findById(id);
            List<Describe> describes=describeService.findByOwn(id);
            model.addAttribute("count",questionService.count());
            model.addAttribute("question",question);
            model.addAttribute("describes",describes);
            return "single";
        }catch (Exception e){
            model.addAttribute("message","无此编号游戏");
            e.printStackTrace();
            return "error";
        }
    }
}
