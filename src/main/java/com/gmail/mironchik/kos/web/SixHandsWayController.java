package com.gmail.mironchik.kos.web;

import com.gmail.mironchik.kos.dto.FriendsRequest;
import com.gmail.mironchik.kos.dto.HandShake;
import com.gmail.mironchik.kos.dto.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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

    @RequestMapping(value = "/getchain/{step}/{id}", method = RequestMethod.GET)
    public @ResponseBody Map getUser(@PathVariable Integer step, @PathVariable String id, HttpSession session){
        RestTemplate  restTemplate = new RestTemplate();
        List<HandShake> handShakes = (List<HandShake>) session.getAttribute("handShakeList");
        List<User> userList = new ArrayList<User>();
        for (int i = step - 1; i > 0; i--) {

            Map<String, List<String>> tmp = restTemplate.getForObject("https://api.vk.com/method/friends.get?user_id=" + Integer.valueOf(id), Map.class);
            userList.add((User) ((List) CollectionUtils.intersection(tmp.get("response"), handShakes.get(i).getOwners())).get(0));
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("chain", userList);
        return result;
    }

    @RequestMapping( value = "/getfriends", method = RequestMethod.POST)
    public @ResponseBody Map getFriends(@RequestBody FriendsRequest request, HttpSession session) throws InterruptedException {
        HandShake handShake = new HandShake();
        handShake.setStep(request.getStep());
        RestTemplate restTemplate = new RestTemplate();
        Set<String> friendsSet = new HashSet<String>();
        String[] strIds = StringUtils.split(request.getIds(), ",");
        Set<Integer> tmpOwners = new HashSet<Integer>();
        for (String strId : strIds){
            if (session.getAttribute("owners") != null) {
                owners = (Set<Integer>) session.getAttribute("owners");
            }
            tmpOwners.add(Integer.valueOf(strId));
            Map<String, List<String>> tmp = restTemplate.getForObject("https://api.vk.com/method/friends.get?user_id=" + Integer.valueOf(strId), Map.class);
            friendsSet.addAll(tmp.get("response"));
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
