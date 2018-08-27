package com.bboroccu.controller;

import com.bboroccu.service.AuthService;
import com.bboroccu.util.Utility;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by bboroccu on 16. 1. 20..
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends CommonController{
    @Autowired
    private AuthService authService;

    /**
     * 로그인
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public
    @ResponseBody
    String login(HttpServletRequest request) throws Exception {
        return authService.login(request);
    }

    /**
     * 회원가입
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public
    @ResponseBody
    String join(HttpServletRequest request) throws Exception {
        return authService.join(request);
    }

    /**
     * 토큰 넣기 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "inserttoken", method = RequestMethod.POST)
    public
    @ResponseBody
    String inserttoken(HttpServletRequest request) throws Exception {
        return authService.inserttoken(request);
    }
}
