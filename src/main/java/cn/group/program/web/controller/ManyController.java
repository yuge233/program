package cn.group.program.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ManyController {
    private Map<String, Vector<String>> rooms=new ConcurrentHashMap<>(20);

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
            }else {
                result="success";
            }
        }else {
            result="未创建的房间名";
        }
        map.put("result",result);
        return map;
    }

    @RequestMapping(value = "/exit.ajax",method = RequestMethod.POST)
    @ResponseBody
    public void exit(@RequestBody Map<String, String> params){
        String username=params.get("username");
        String room=params.get("room");
        Vector<String> users=rooms.get(room);
        if (username!=null)
            users.remove(username);
        if (users.isEmpty()){
            rooms.remove(room);
        }
    }
}
