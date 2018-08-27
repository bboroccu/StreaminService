package com.bboroccu.controller;

import com.bboroccu.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bboroccu on 16. 1. 26..
 */
@RestController
@RequestMapping("/playlist")
public class PlayListController extends CommonController {
    @Autowired
    private PlayListService playListService;
    /**
     * 개인 플레이리스트 저장
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savelist", method = RequestMethod.POST)
    public
    @ResponseBody
    String savelist(HttpServletRequest request) throws Exception {
        return playListService.saveList(request);
    }

    /**
     * 개인 플레이리스트 불러오기
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loadlist", method = RequestMethod.GET)
    public
    @ResponseBody
    String loadlist(HttpServletRequest request) throws Exception {
        return playListService.loadList(request);
    }

    /**
     * 노래 추천 등록
     * @param request
     * @return 성공 여부
     * @throws Exception
     */
    @RequestMapping(value = "/recommendsong", method = RequestMethod.GET)
    public
    @ResponseBody
    String recommendsong(HttpServletRequest request) throws Exception {
        return playListService.recommendSong(request);
    }

    /**
     * 노래 재생 카운트 등록
     * @param request
     * @return 성공 여부
     * @throws Exception
     */
    @RequestMapping(value = "/updateplaycount", method = RequestMethod.GET)
    public
    @ResponseBody
    String updateplaycount(HttpServletRequest request) throws Exception {
        return playListService.updatePlayCount(request);
    }

    /**
     * 노래 다운 카운트 등록
     * @param request
     * @return 성공 여부
     * @throws Exception
     */
    @RequestMapping(value = "/updatedowncount", method = RequestMethod.GET)
    public
    @ResponseBody
    String updatedowncount(HttpServletRequest request) throws Exception {
        return playListService.updateDownCount(request);
    }

    /**
     * 추천 순위
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ratinglist", method = RequestMethod.GET)
    public
    @ResponseBody
    String recommendchart(HttpServletRequest request) throws Exception {
        return playListService.recommendChart(request);
    }

    /**
     * 노래 전체 리스트
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/fulllist", method = RequestMethod.GET)
    public
    @ResponseBody
    String fullSongList(HttpServletRequest request) throws Exception {
        return playListService.fullSongList(request);
    }

    /**
     * 노래 검색
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchlist", method = RequestMethod.GET)
    public
    @ResponseBody
    String searchSongList(HttpServletRequest request) throws Exception {
        return playListService.searchSongList(request);
    }
}
