package com.gmail.mironchik.kos.web;

import com.gmail.mironchik.kos.dto.Friend;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 17.08.13
 * Time: 15:53
 */
@Controller
public class SixHandsWayController {

    @RequestMapping(value = "/getuser/{id}", method = RequestMethod.GET)
    public @ResponseBody Map<String,String> getUser(@PathVariable String id){
        RestTemplate restTemplate = new RestTemplate();
        Map result = restTemplate.getForObject("https://api.vk.com/method/users.get?user_ids=" + id, Map.class);
        return result;
    }

    @RequestMapping(value = "/getfriends/{id}", method = RequestMethod.GET)
    public @ResponseBody Map<String,String> getFriends(@PathVariable Integer id){
        RestTemplate restTemplate = new RestTemplate();
        Map result = restTemplate.getForObject("https://api.vk.com/method/friends.get?user_id=" + id, Map.class);
        return result;
    }
}
