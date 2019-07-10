package cn.group.program.web.controller;

import cn.group.program.model.Describe;
import cn.group.program.model.Question;
import cn.group.program.service.DescribeService;
import cn.group.program.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class UserDataSendController {

    private String photosPath = "test";

    @Autowired
    QuestionService questionService;

    @Autowired
    DescribeService describeService;

    @GetMapping("/userAddGameContent")
    public String addGameContent(Model model) {
        model.addAttribute("photosPath", photosPath);
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
                if(describe.contains("images/")) {
                    describes.setPhotoPath(describe);
                    describes.setToken(null);
                }else {
                    describes.setToken(describe);
                    describes.setPhotoPath(null);
                }
                describeService.save(describes);
            }
            return ""+id;
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    @ResponseBody
    @RequestMapping("/insertPhoto.ajax")
    public String uploadPhoto(@RequestParam("photo") MultipartFile[] file, HttpServletRequest request) {
        try {
            String path = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images";
            for(MultipartFile thisFile : file) {
                String fileName = thisFile.getOriginalFilename();// 得到文件名称
                File tempFile = new File(path, fileName);
                if (!fileName.isEmpty() && !thisFile.isEmpty()) {
                    if (!tempFile.getParentFile().exists()) {// 检测是否存在目录
                        tempFile.getParentFile().mkdirs();
                    }
                    thisFile.transferTo(tempFile);// 写入文件
                }
            }
            return "true";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "false";//上传成功返回上传图片页面。
    }

}