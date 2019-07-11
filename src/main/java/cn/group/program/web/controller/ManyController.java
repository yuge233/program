package cn.group.program.web.controller;

import cn.group.program.model.Describe;
import cn.group.program.model.Question;
import cn.group.program.service.DescribeService;
import cn.group.program.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ManyController {
    //记录房间对应的用户
    private Map<String, Vector<String>> rooms=new ConcurrentHashMap<>(10);

    //记录房间对应的问题
    private Map<String, Question> room_question=new ConcurrentHashMap<>(10);

    //记录房间开始游戏时间
    private Map<String,Long> room_time=new ConcurrentHashMap<>(10);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private DescribeService describeService;

    Random random=new Random();

    @GetMapping("/many")
    public String many(){
        return "many";
    }

    @RequestMapping(value = "/create.ajax",method = RequestMethod.POST)
    @ResponseBody
    public Map create(@RequestBody Map<String, String> params){
        String username=params.get("username");
        String room=params.get("room");

        Map map=new HashMap();
        String result;

        if (rooms.containsKey(room)){
            result="房间名重复";
        }else {
            Vector<String> users=new Vector<>(10);
            users.add(username);
            rooms.put(room,users);
            result="success";
        }
        map.put("result",result);
        return map;
    }

    @RequestMapping(value = "/join.ajax",method = RequestMethod.POST)
    @ResponseBody
    public Map join(@RequestBody Map<String, String> params){
        String username=params.get("username");
        String room=params.get("room");

        Map map=new HashMap();
        String result;

        if (rooms.containsKey(room)){
            Vector<String> users=rooms.get(room);
            if (users.contains(username)){
                result="用户名重复";
            }else if (room_question.containsKey(room)||room_time.containsKey(room)){
                result="房间已经开始游戏,无法进入";
            }else {
                users.add(username);
                result="success";
            }
        }else {
            result="未创建的房间名";
        }
        map.put("result",result);
        return map;
    }


    @RequestMapping(value = "/fire.ajax",method = RequestMethod.POST)
    @ResponseBody
    public Map fire(@RequestBody Map<String, String> params){
        String username=params.get("username");
        String room=params.get("room");
        Vector<String> users=null;
        Map map=new HashMap();
        String result="success";
        if (room!=null&&rooms.containsKey(room)){
            users=rooms.get(room);
        }
        if (users==null){
            result="fail";
            map.put("result",result);
            map.put("message","房间已关闭");
            return map;
        }
        if (username!=null){
            if(users.indexOf(username)==0){
                result="fail";
                map.put("message","无法踢自己");
            }else {
                users.remove(username);
            }
        }
        map.put("result",result);
        return map;
    }


    @RequestMapping(value = "/exit.ajax",method = RequestMethod.POST)
    @ResponseBody
    public Map exit(@RequestBody Map<String, String> params){
        String username=params.get("username");
        String room=params.get("room");
        Vector<String> users=null;
        Map map=new HashMap();
        String result="success";
        if (room!=null&&rooms.containsKey(room)){
            users=rooms.get(room);
        }
        if (users==null){
            result="fail";
            map.put("result",result);
            return map;
        }
        if (username!=null){
            if(users.indexOf(username)==0){
                room_question.remove(room);
                rooms.remove(room);
                room_time.remove(room);
                map.put("message","房主离开了房间,房间关闭,请离开房间");
            }else {
                users.remove(username);
            }
        }
        if (users.isEmpty()){
            room_time.remove(room);
            rooms.remove(room);
            room_question.remove(room);
        }
        map.put("result",result);
        return map;
    }

    @RequestMapping(value = "/begin.ajax",method = RequestMethod.POST)
    @ResponseBody
    public Map begin(@RequestBody Map<String, String> params){
        String room=params.get("room");
        Vector<String> users=rooms.get(room);
        String result="success";
        Map map=new HashMap();
        if (users.size()<=1){
            result="人数不足";
            map.put("result",result);
            return map;
        }

        long count = questionService.count(); //获取题库总量
        long id = 1;  //随机开始一题
        if(count != 0){
            do {    //防止跟上一题重复
                id =  Math.abs(random.nextLong()) % count + 1;
            }while (room_question.containsKey(room)&&room_question.get(room).getId().equals(id));
        }

        Question question=questionService.findById(id); //记录问题
        if (!room_question.containsKey(room))
            room_question.put(room,question);
        else
            room_question.replace(room,question);
        Long start_time=System.currentTimeMillis(); //记录开始时间
        if (!room_time.containsKey(room))
            room_time.put(room,start_time);
        else
            room_time.replace(room,start_time);

        List<Describe> describes=describeService.findByOwn(id);
        map.put("result",result);
        map.put("describes",describes);
        return map;
    }

    @RequestMapping(value = "/send.ajax",method = RequestMethod.POST)
    @ResponseBody
    public Map send(@RequestBody Map<String, String> params){
        String username=params.get("username");
        String room=params.get("room");
        String message=params.get("message");
        Question question=room_question.get(room);
        String result="fail";
        Map map=new HashMap();
        if (question!=null&&message.indexOf(question.getAnswer())>=0){
            Long start_time=room_time.get(room);
            Long end_time=System.currentTimeMillis();
            map.put("useTime",(end_time-start_time)/1000);
            result="success";
        }
        map.put("result",result);
        return map;
    }
}

//class User {
//    String username;
//    boolean isRoomate;  //判断是否是房主
//
//    public User(String username, boolean isRoomate) {
//        this.username = username;
//        this.isRoomate = isRoomate;
//    }
//}
