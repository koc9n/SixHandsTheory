package com.gmail.mironchik.kos.web;

import com.gmail.mironchik.kos.dto.FriendsRequest;
import com.gmail.mironchik.kos.dto.HandShake;
import com.gmail.mironchik.kos.dto.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Date: 17.08.13
 * Time: 15:53
 */
@Controller
public class SixHandsWayController {

    private Set<Integer> owners = new HashSet<Integer>();

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/getuser/{id}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable String id, HttpSession session){
        session.invalidate();
        Map<String, List<Map<String, Object>>> tmp = restTemplate.getForObject("https://api.vk.com/method/users.get?user_ids=" + id + "&fields=screen_name,photo_200_orig", Map.class);
        User user = new User();
        Map resp = (Map) ((List) tmp.get("response")).get(0);
        fillUser(user, resp);
        return user;
    }

    private User fillUser(User user, Map resp) {
        user.setId((Integer) resp.get("uid"));
        user.setName((String)resp.get("first_name") + resp.get("last_name"));
        user.setPhlink((String) resp.get("photo_200_orig"));
        return user;
    }

    @RequestMapping(value = "/getchain/{step}/{id}", method = RequestMethod.GET)
    public @ResponseBody Map getChain(@PathVariable Integer step, @PathVariable Integer id, HttpSession session){
        List<User> userList = new ArrayList<User>();
        User user = new User();
        user.setId(id);
        for (int i = step; i > 0; i--) {
            Map<String, List<String>> tmp = restTemplate
                    .getForObject("https://api.vk.com/method/friends.get?user_id=" + Integer.valueOf(user.getId()), Map.class);

            List<Integer> curOwners = new ArrayList((Collection) session.getAttribute("step_" + i));
            List tmpResp = tmp.get("response");
            tmpResp.retainAll(curOwners);
            List<Integer> intersection = tmpResp;
            if (!intersection.isEmpty()) {
                user = new User();
                user.setId(intersection.get(0));
                userList.add(user);
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("chain", userList);
        session.invalidate();
        return result;
    }
    private Map findMutualFriendAndStep(Integer firstId, Integer secondId) {
        int step = 0;

        Set<Integer> firstFriends = new HashSet<Integer>();
        Set<Integer> secondFriends = new HashSet<Integer>();
        Set<Integer> firstParents = new HashSet<Integer>();
        Set<Integer> secondParents = new HashSet<Integer>();
        firstFriends.add(firstId);
        secondFriends.add(secondId);
        firstParents.add(firstId);
        secondParents.add(secondId);
        while (getMutualFriend(firstFriends, secondFriends) == null) {
            ++step;
            switch (step%2) {
                case 1 :
                    secondFriends.addAll(findFriends(secondParents));
                    Set tmp1 = secondParents;
                    secondParents = findFriends(secondParents);
                    secondParents.removeAll(tmp1);
                    break;
                case 0:
                    firstFriends.addAll(findFriends(firstParents));
                    Set tmp2 = firstParents;
                    firstParents = findFriends(firstParents);
                    firstParents.removeAll(tmp2);
                    break;
            }
        }
        Map res = new HashMap();
        res.put("step", step);
        res.put("friend", getMutualFriend(firstFriends, secondFriends).iterator().next());
//        res.put("startStep",1);
//        res.put("first", firstId);
//        res.put("second", secondId);
//        res.put("first", firstId);
//        res.put("second", secondId);
        return res;
    }

    private Map calculateChain(Map chain, Integer step) {
        Integer first = null;
        Integer second = null;
        Integer firstS = null;
        Integer secondS = null;
        for(Integer i = 0; i <= step; i++){
            if (second == null){
                if (first == null) {
                    if(chain.get(i) == null) {
                        first = i-1;
                        firstS = (Integer) chain.get(i - 1);
                    }
                } else {
                    if(chain.get(i) != null) {
                        second = i;
                        secondS = (Integer) chain.get(i);
                    }
                }
            }
        }
        if (first == null) return chain;
        Map mfS = findMutualFriendAndStep(firstS, secondS);
        chain.put((first + second)/2,mfS.get("friend"));
        return calculateChain(chain, step);
    }

    @RequestMapping(value = "/findchain/{firstId}/{secondId}", method = RequestMethod.GET)
    public @ResponseBody Map findchain(@PathVariable Integer firstId, @PathVariable Integer secondId) {
        Map findMutualResult = new HashMap();
        findMutualResult = findMutualFriendAndStep(firstId,secondId);
        Integer step = (Integer) findMutualResult.get("step");
        Map chain = new HashMap();
        chain.put(0,firstId);
        chain.put(step, secondId);
        chain.put(step / 2, findMutualResult.get("friend"));
        chain = calculateChain(chain, (Integer) findMutualResult.get("step"));
        Map chainResult = new HashMap();
        chainResult.put("step", step);
        chainResult.put("chain", chain);
        return chainResult;
    }

    private Set<Integer> findFriends(Set<Integer> ids) {
        Set<Integer> result = new HashSet();
        for (Integer id : ids) {
            if (id != null){
                Collection tmp = (Collection) restTemplate
                        .getForObject("https://api.vk.com/method/friends.get?user_id="
                                + id, Map.class).get("response");
                if (tmp != null) {
                    result.addAll(tmp);
                }
            }
        }
        return  result;
    }

    private Collection getMutualFriend(Set<Integer> f_ids, Set<Integer> s_ids){
        if (f_ids == null || s_ids == null || f_ids.isEmpty() || s_ids.isEmpty()){
            return null;
        }
        Collection result = CollectionUtils.intersection(f_ids, s_ids);
        if (result == null || result.isEmpty()){
            return null;
        } else {
            return result;
        }
    }


    @RequestMapping( value = "/getfriends", method = RequestMethod.POST)
    public @ResponseBody Map getFriends(@RequestBody FriendsRequest request, HttpSession session) throws InterruptedException {
        HandShake handShake = new HandShake();
        handShake.setStep(request.getStep());
        Set<String> friendsSet = new HashSet<String>();
        String[] strIds = StringUtils.split(request.getIds(), ",");
        Set<Integer> tmpOwners = new HashSet<Integer>();
        for (String strId : strIds){
            synchronized (restTemplate) {
                if (session.getAttribute("owners") != null) {
                    owners = (Set<Integer>) session.getAttribute("owners");

                }
                tmpOwners.add(Integer.valueOf(strId));
                Map<String, List<String>> tmp = restTemplate.getForObject("https://api.vk.com/method/friends.get?user_id=" + Integer.valueOf(strId), Map.class);
                if (tmp.get("response") != null) friendsSet.addAll(tmp.get("response"));
                restTemplate.wait(250L);
            }
        }

        friendsSet.removeAll(owners);
        owners.addAll(tmpOwners);

        handShake.setOwners(new ArrayList<Integer>(tmpOwners));

        session.setAttribute("step_" + request.getStep(), tmpOwners);
        session.setAttribute("owners", owners);
        Map<String, Set<String>> result = new HashMap<String, Set<String>>();
        result.put("friends", friendsSet) ;
        return result;
    }
}
