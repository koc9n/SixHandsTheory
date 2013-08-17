package com.gmail.mironchik.kos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Date: 17.08.13
 * Time: 15:53
 */
@Controller
public class SixHandsWayController {
    @RequestMapping(value = "/getfriends/{id}")
    public void getFriends(@PathVariable String friendId){

    }

}
