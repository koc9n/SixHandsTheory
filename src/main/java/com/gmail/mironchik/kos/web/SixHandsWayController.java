package com.gmail.mironchik.kos.web;

import com.gmail.mironchik.kos.dto.HandShake;
import com.gmail.mironchik.kos.dto.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Date: 17.08.13
 * Time: 15:53
 */
@Controller
public class SixHandsWayController {

    private Set<Integer> owners = new HashSet<Integer>();

    @RequestMapping(value = "/getuser/{id}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable String id){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, List<Map<String, Object>>> tmp = restTemplate.getForObject("https://api.vk.com/method/users.get?user_ids=" + id, Map.class);
        User user = new User();
        user.setId((Integer)tmp.get("response").get(0).get("uid"));
        return user;
    }

    @RequestMapping(value = "/getfriends/{step}/{ids}", method = RequestMethod.GET)
    public @ResponseBody Map getFriends(@PathVariable String ids, @PathVariable Integer step, HttpSession session){
        HandShake handShake = new HandShake();
        handShake.setStep(step);
        RestTemplate restTemplate = new RestTemplate();
        Set<String> friendsSet = new HashSet<String>();
        String[] strIds = StringUtils.split(ids, ",");
        Set<Integer> tmpOwners = new HashSet<Integer>();
        for (String strId : strIds){
            if (session.getAttribute("owners") != null) {
                owners = (Set<Integer>) session.getAttribute("owners");
            }
            tmpOwners.add(Integer.valueOf(strId));
            Map<String, List<String>> tmp = restTemplate.getForObject("https://api.vk.com/method/friends.get?user_id=" + Integer.valueOf(strId), Map.class);
            friendsSet.addAll((List<String>)tmp.get("response"));
        }

        friendsSet.removeAll(owners);


        handShake.setOwners(new ArrayList<Integer>(owners));

        saveToSessionHandShakes(session, handShake);

        owners.addAll(tmpOwners);

        session.setAttribute("owners", owners);
        Map<String, Set<String>> result = new HashMap<String, Set<String>>();
        result.put("friends", friendsSet) ;
        return result;
    }

    private void saveToSessionHandShakes(HttpSession session, HandShake handShake) {
        List<HandShake> handShakeList = (List<HandShake>) session.getAttribute("handShakeList");
        if (handShakeList == null) {
            handShakeList = new ArrayList<HandShake>();

        }
        handShakeList.add(handShake);
        session.setAttribute("handShakeList", handShakeList);
    }
}
