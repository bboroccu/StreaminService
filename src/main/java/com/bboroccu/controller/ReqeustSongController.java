package com.bboroccu.controller;

import com.bboroccu.service.RequestSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jae-seongheo on 2016. 9. 4..
 */
@RestController
@RequestMapping("requestdashboard")
public class ReqeustSongController extends CommonController {
    @Autowired
    RequestSongService requestSongService;

    /**
     * 요청게시판 작성
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public
    @ResponseBody
    String writedashboard(HttpServletRequest request) throws Exception {
        return requestSongService.writedashboard(request);
    }

    /**
     * 요청 게시판 리플 작성
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/writereply", method = RequestMethod.POST)
    public
    @ResponseBody
    String writereply(HttpServletRequest request) throws Exception {
        return requestSongService.writereply(request);
    }

    /**
     * 요청 게시판 리스트
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardlist", method = RequestMethod.GET)
    public
    @ResponseBody
    String boardlist(HttpServletRequest request) throws Exception {
        return requestSongService.boardlist(request);
    }

    @RequestMapping(value ="/pushtest", method = RequestMethod.GET)
    public
    @ResponseBody
    String pushtest(HttpServletRequest request) throws Exception {
        requestSongService.testPush();
        return "success";
    }
}
