package com.bboroccu.service;

import com.bboroccu.model.dao.*;
import com.bboroccu.util.Utility;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jae-seongheo on 2016. 5. 22..
 */
@Service
public class PlayListService extends CommonService{
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private SongListRepo playListInfoRepo;
    @Autowired
    private SongInfoRepo songInfoRepo;
    @Autowired
    SongRatingRepo songRatingRepo;

    /**
     * 개인 플레이리스트 저장
     * @param request
     * @return
     * @throws JSONException
     */
    public String saveList(HttpServletRequest request) throws JSONException {
        String eMail = request.getParameter("email");
        if (eMail == null || eMail.equals(""))
            return Utility.getRtnMsg(0, "Empty eMail");
        String title = request.getParameter("title");
        if (title == null || title.equals(""))
            title = "제목없음";
        String playList = request.getParameter("playlist");
        if (playList == null || playList.equals(""))
            return Utility.getRtnMsg(0, "Empty playlist");
        String seq = request.getParameter("seq");
        SongList playListInfo = null;
        if (seq != null && seq.equals(""))
            playListInfo = playListInfoRepo.findOne(Integer.parseInt(seq));
        if (playListInfo == null)
            playListInfo = new SongList();
        Member member = memberRepo.findOne(eMail);
        playListInfo.setMemberseq(member.getSeq());
        playListInfo.setPlaylist(playList);
        playListInfo.setTitle(title);
        playListInfoRepo.save(playListInfo);
        return Utility.getRtnMsg(1, "SUCCESS");
    }

    /**
     * 개인 플레이리스트 불러오기
     * @param request
     * @return
     * @throws JSONException
     */
    public String loadList(HttpServletRequest request) throws JSONException {
        String eMail = request.getParameter("email");
        if (eMail == null || eMail.equals(""))
            return Utility.getRtnMsg(0, "Empty eMail");
        Member member = memberRepo.findOne(eMail);
        List<SongList> playList = playListInfoRepo.findPlayListToSeq(member.getSeq());
        Gson gson = new Gson();
        return gson.toJson(playList);
    }

    /**
     * 노래 추천 등록
     * @param request
     * @return
     * @throws JSONException
     */
    public String recommendSong(HttpServletRequest request) throws JSONException {
        String eMail = request.getParameter("email");
        if (eMail == null || eMail.equals(""))
            return Utility.getRtnMsg(0, "Empty eMail");
        String seq = request.getParameter("seq");
        if (seq == null || seq.equals(""))
            return Utility.getRtnMsg(0, "Empty SEQ");
        String upAndDown = request.getParameter("ud");
        if (upAndDown == null || upAndDown.equals(""))
            return Utility.getRtnMsg(0, "Empty ud");
        SongInfo songInfo = songInfoRepo.findOne(Integer.parseInt(seq));
        if (songInfo != null)
        {
            SongRating songRating = songRatingRepo.findSongRatingToSeq(songInfo.getSeq());
            if (songRating == null)
                songRating = new SongRating();
            if (upAndDown.equals("1"))
                songRating.setLikecount(songRating.getLikecount() + 1);
            else
                songRating.setLikecount(songRating.getLikecount() - 1);
            songRatingRepo.save(songRating);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 1);
            jsonObject.put("seq", seq);
            jsonObject.put("msg", "SUCCESS");
            return jsonObject.toString();
        } else
            return Utility.getRtnMsg(0, "Song Not Found");
    }

    /**
     * 노래 재생 카운트 등록
     * @param request
     * @return
     * @throws JSONException
     */
    public String updatePlayCount(HttpServletRequest request) throws JSONException {
        String eMail = request.getParameter("email");
        if (eMail == null || eMail.equals(""))
            return Utility.getRtnMsg(0, "Empty eMail");
        String seq = request.getParameter("seq");
        if (seq == null || seq.equals(""))
            return Utility.getRtnMsg(0, "Empty SEQ");
        SongInfo songInfo = songInfoRepo.findOne(Integer.parseInt(seq));
        if (songInfo != null)
        {
            SongRating songRating = songRatingRepo.findSongRatingToSeq(songInfo.getSeq());
            if (songRating == null)
                songRating = new SongRating();
            songRating.setPlaycount(songRating.getPlaycount() + 1);
            songRatingRepo.save(songRating);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 1);
            jsonObject.put("seq", seq);
            jsonObject.put("msg", "SUCCESS");
            return jsonObject.toString();
        } else
            return Utility.getRtnMsg(0, "Song Not Found");
    }

    /**
     * 노래 다운 카운트 등록
     * @param request
     * @return
     * @throws JSONException
     */
    public String updateDownCount(HttpServletRequest request) throws JSONException {
        String eMail = request.getParameter("email");
        if (eMail == null || eMail.equals(""))
            return Utility.getRtnMsg(0, "Empty eMail");
        String seq = request.getParameter("seq");
        if (seq == null || seq.equals(""))
            return Utility.getRtnMsg(0, "Empty SEQ");
        SongInfo songInfo = songInfoRepo.findOne(Integer.parseInt(seq));
        if (songInfo != null) {
            SongRating songRating = songRatingRepo.findSongRatingToSeq(songInfo.getSeq());
            if (songRating == null)
                songRating = new SongRating();
            songRating.setDowncount(songRating.getDowncount() + 1);
            songRatingRepo.save(songRating);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 1);
            jsonObject.put("seq", seq);
            jsonObject.put("msg", "SUCCESS");
            return jsonObject.toString();
        } else
            return Utility.getRtnMsg(0, "Song Not Found");
    }

    /**
     * 추천 순위
     * @param request
     * @return
     * @throws JSONException
     */
    public String recommendChart(HttpServletRequest request) throws JSONException {
        String nextPage = request.getParameter("page");
        int paging = 0;
        if (nextPage != null)
            paging = Integer.parseInt(nextPage);
        String type = request.getParameter("type");
        Page<SongRating> list = null;
        PageRequest pageRequest = null;
        if (type.equals("new"))
            pageRequest = new PageRequest(paging, 10, new Sort(Sort.Direction.DESC, "songseq"));
        else if (type.equals("rating"))
            pageRequest = new PageRequest(paging, 10, new Sort(Sort.Direction.DESC, "likecount", "songseq"));
         else if (type.equals("down"))
            pageRequest = new PageRequest(paging, 10, new Sort(Sort.Direction.DESC, "downcount", "songseq"));
        list = songRatingRepo.findAll(pageRequest);
        if (list.getContent().size() > 0) {
            List<SongInfo> songList = new ArrayList<SongInfo>();
            for (int i=0;i<list.getContent().size();i++) {
                SongRating songRating = list.getContent().get(i);
                SongInfo songInfo = songInfoRepo.findOne(songRating.getSeq());
                if (songInfo != null)
                    songList.add(songInfo);
            }
            Gson gson = new Gson();
            JSONObject returnJson = new JSONObject();
            paging++;
            if (paging >= list.getTotalPages())
                returnJson.put("page", -1);
            else
                returnJson.put("page", paging);
            returnJson.put("data", gson.toJson(songList));
            return  returnJson.toString();
        } else {
            Page<SongInfo> songList = songInfoRepo.findAll(new PageRequest(0, 10));
            Gson gson = new Gson();
            JSONObject returnJson = new JSONObject();
            returnJson.put("page", -1);
            returnJson.put("data", gson.toJson(songList.getContent()));
            return returnJson.toString();
        }
    }

    /**
     * 전체 노래 리스트
     * @param request
     * @return
     * @throws JSONException
     */
    public String fullSongList(HttpServletRequest request) throws JSONException {
        String nextPage = request.getParameter("page");
        int paging = 0;
        if (nextPage != null)
            paging = Integer.parseInt(nextPage);
        Page<SongInfo> list = songInfoRepo.findAll(new PageRequest(paging, 10));
        Gson gson = new Gson();
        JSONObject returnJson = new JSONObject();
        paging++;
        if (paging >= list.getTotalPages())
            returnJson.put("page", -1);
        else
            returnJson.put("page", paging);
        returnJson.put("data", gson.toJson(list.getContent()));
        return returnJson.toString();
    }

    /**
     * 노래 검색
     * @param request
     * @return 검색 결과
     * @throws JSONException
     */
    public String searchSongList(HttpServletRequest request) throws JSONException {
        String searchWord = request.getParameter("searchword");
        if (searchWord != null && !searchWord.equals("")) {
            List<SongInfo> list = songInfoRepo.searchSongList(searchWord, searchWord);
            Gson gson = new Gson();
            JSONObject returnJson = new JSONObject();
            returnJson.put("count", list.size());
            returnJson.put("data", gson.toJson(list));
            return returnJson.toString();
        } else
            return Utility.getRtnMsg(0, "searchword wrong");
    }
}
