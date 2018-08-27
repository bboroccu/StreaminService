package com.bboroccu.service;

import com.bboroccu.model.dao.*;
import com.bboroccu.model.dao.push.ApnsPush;
import com.bboroccu.util.Utility;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jae-seongheo on 2016. 9. 4..
 */
@Service
public class RequestSongService extends CommonService {
    @Autowired
    MemberRepo memberRepo;
    @Autowired
    RequestSongRepo requestSongRepo;
    @Autowired
    RequestSongReplyRepo requestSongReplyRepo;

    private ApnsPush apnsPush;

    public String writedashboard(HttpServletRequest request) throws JSONException {
        String writer = request.getParameter("email");
        if (writer == null || writer.equals(""))
            return Utility.getRtnMsg(0, "Empty Email");
        Member member = memberRepo.findOne(writer);
        if (member == null)
            return Utility.getRtnMsg(0, "Not Member");
        String contents = request.getParameter("contents");
        if (contents == null || contents.equals(""))
            return Utility.getRtnMsg(0, "Empty Contents");
        RequestSong requestSong = new RequestSong();
        requestSong.setWriter(member.getEmail());
        requestSong.setWritername(member.getUsername());
        requestSong.setContents(contents);
        requestSong.setIscomplete("N");
        requestSong.setWritedate(Utility.getStrToDate(""));
        requestSongRepo.save(requestSong);
        try {
            SendPushtoEmail("bboroccu@nate.com", "새글 알림");
        } catch (Exception ex) {
            logger.error("push error : " + ex);
        }
        return Utility.getRtnMsg(1, "Register");
    }

    public String writereply(HttpServletRequest request) throws JSONException {
        String writer = request.getParameter("email");
        if (writer == null || writer.equals(""))
            return Utility.getRtnMsg(0, "Empty Email");
        Member member = memberRepo.findOne(writer);
        if (member == null)
            return Utility.getRtnMsg(0, "Not Member");
        String contents = request.getParameter("contents");
        if (contents == null || contents.equals(""))
            return Utility.getRtnMsg(0, "Empty Contents");
        String seq = request.getParameter("seq");
        if (seq == null || seq.equals(""))
            return Utility.getRtnMsg(0, "Not Seq");
        RequestSongReply requestSongReply = new RequestSongReply();
        requestSongReply.setWriter(member.getEmail());
        requestSongReply.setWritername(member.getUsername());
        requestSongReply.setContents(contents);
        requestSongReply.setRequestseq(Integer.parseInt(seq));
        requestSongReply.setWritedate(Utility.getStrToDate(""));
        requestSongReplyRepo.save(requestSongReply);
        RequestSong requestSong = requestSongRepo.findOne(requestSongReply.getRequestseq());
        requestSong.setIscomplete("Y");
        requestSongRepo.save(requestSong);
        try {
            SendPushtoEmail(requestSong.getWriter(), requestSongReply.getContents());
        } catch (Exception ex) {
            logger.error("push error : " + ex);
        }
        return Utility.getRtnMsg(1, "Register");
    }

    public String boardlist(HttpServletRequest request) throws JSONException {
        String nextPage = request.getParameter("page");
        int paging = 0;
        if (nextPage != null)
            paging = Integer.parseInt(nextPage);
        Gson gson = new Gson();
        JSONArray jsonArray = new JSONArray();
        Page<RequestSong> list = null;
        PageRequest pageRequest = new PageRequest(paging, 10, new Sort(Sort.Direction.DESC, "writedate"));
        list = requestSongRepo.findAll(pageRequest);
        if (list.getContent().size() > 0) {
            for (int i = 0; i < list.getContent().size(); i++) {
                RequestSong requestSong = list.getContent().get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", new JSONObject(gson.toJson(requestSong)));
                List<RequestSongReply> replyList = requestSongReplyRepo.findRequestSongReply(requestSong.getSeq());
                if (replyList != null)
                    jsonObject.put("reply", new JSONArray(gson.toJson(replyList)));
                jsonArray.put(jsonObject);
            }
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("list", jsonArray);
        resultObject.put("count", list.getContent().size());
        resultObject.put("code", 1);
        paging++;
        if (paging >= list.getTotalPages())
            resultObject.put("page", -1);
        else
            resultObject.put("page", paging);
        return resultObject.toString();
    }

    private void SendPushtoEmail(String email, String text) throws Exception {
        if (apnsPush == null)
            apnsPush = new ApnsPush();
        Member member = memberRepo.findMemberInfo(email);
        if (member != null) {
            String deviceToken = member.getToken();
            if (deviceToken != null) {
                apnsPush.sendPush(text, true, deviceToken);
            } else
                logger.info("Empty Token : " + email);
        }
    }
    public void testPush() throws Exception {
        if (apnsPush == null)
            apnsPush = new ApnsPush();
        Member member = memberRepo.findMemberInfo("bboroccu@nate.com");
        if (member != null) {
            String deviceToken = member.getToken();
            if (deviceToken != null) {
                apnsPush.sendPush("test", true, deviceToken);
            } else
                logger.info("Empty Token : " + "bboroccu@nate.com");
        }
    }
}
