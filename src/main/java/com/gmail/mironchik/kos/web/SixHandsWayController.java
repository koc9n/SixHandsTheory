package com.gmail.mironchik.kos.web;

import com.gmail.mironchik.kos.dto.Friend;
import org.apache.commons.lang3.StringUtils;
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

    @RequestMapping(value = "/getfriends/{ids}", method = RequestMethod.GET)
    public @ResponseBody Map<String,String> getFriends(@PathVariable String ids){
        String[] strIds = StringUtils.split(",");
        RestTemplate restTemplate = new RestTemplate();
        Map result = restTemplate.getForObject("https://api.vk.com/method/friends.get?user_id=" + strIds[0], Map.class);
        return result;
    }
}
