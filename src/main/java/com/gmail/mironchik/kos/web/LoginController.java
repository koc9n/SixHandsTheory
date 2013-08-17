package com.gmail.mironchik.kos.web;

import org.apache.catalina.Session;
import org.springframework.http.HttpMethod;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Date: 17.08.13
 * Time: 16:54
 */
@Controller
public class LoginController {
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public void login (@RequestParam("access_token") String token, HttpSession session) {
        Facebook facebook = new FacebookTemplate(token);
    }
}
