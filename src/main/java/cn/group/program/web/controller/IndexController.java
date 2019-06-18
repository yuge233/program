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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private DescribeService describeService;

    Random random=new Random();

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/single")
    public String single(){
        long count=questionService.count();
        long id=random.nextLong()%count+1;
        return "redirect:/single/"+id;
    }

    @GetMapping("/single/{id}")
    public String single_id(@PathVariable long id,Model model){
        Question question=questionService.findById(id);
        List<Describe> describes=describeService.findByOwn(id);
        model.addAttribute("question",question);
        model.addAttribute("describes",describes);
        return "single";
    }
}
