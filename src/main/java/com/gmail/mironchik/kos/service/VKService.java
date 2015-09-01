package com.gmail.mironchik.kos.service;

import com.gmail.mironchik.kos.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by kmironchyk on 9/1/2015.
 */
@Service
public class VKService {

    @Autowired
    private RestTemplate restTemplate;

    public User getUserById(String id) {
        Map<String, List<Map<String, Object>>> tmp = restTemplate
                .getForObject("https://api.vk.com/method/users.get?user_ids="
                        + id + "&fields=screen_name,photo_200_orig", Map.class);
        Map resp = (Map) ((List) tmp.get("response")).get(0);
        return convertToUser(resp);
    }

    public List getFriendsByUserId(String id) {
        return (List) restTemplate
                .getForObject("https://api.vk.com/method/friends.get?user_id="
                        + id, Map.class).get("response");

    }


    private User convertToUser(Map resp) {
        User user = new User();
        user.setId((Integer) resp.get("uid"));
        user.setName((String) resp.get("first_name") + resp.get("last_name"));
        user.setPhotoLink((String) resp.get("photo_200_orig"));
        return user;
    }
}
