package com.bboroccu.service;

import com.bboroccu.model.dao.Member;
import com.bboroccu.model.dao.MemberRepo;
import com.bboroccu.util.Utility;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jae-seongheo on 2016. 4. 16..
 */
@Service
public class AuthService extends CommonService {
    @Autowired
    private MemberRepo userInfoRepo;

    /**
     * 로그인
     * @param request
     * @return 결과 Json String
     * @throws Exception
     */
    public String login(HttpServletRequest request) throws JSONException {
        String eMail = request.getParameter("email");
        if (eMail == null || eMail.equals(""))
            return Utility.getRtnStrAndMsg("failed", "Empty Email");
        String userPw = request.getParameter("pass");
        if (userPw == null || userPw.equals(""))
            return Utility.getRtnStrAndMsg("failed", "Empty userPw");
        String userGb = request.getParameter("usergb");
        Member member = userInfoRepo.findMemberInfo(eMail);
        if (member != null) {
            if (member.getUseyn().equals("N"))
                return Utility.getRtnStrAndMsg("joinning", "joinning");
            else if (member.getUseyn().equals("R"))
                return Utility.getRtnStrAndMsg("rejected", "rejected");
            else if (!member.getPassword().equals(userPw))
                return Utility.getRtnStrAndMsg("wrong", "password failed");
            if (userGb != null && !userGb.equals(""))
                member.setUsergb(userGb);
            userInfoRepo.save(member);
            return Utility.getRtnStrAndMsg("success", "success");
        } else
            return Utility.getRtnStrAndMsg("not join", "not join");
    }

    /**
     * 회원가입
     * @param request
     * @return  결과 Json String
     * @throws Exception
     */
    public String join(HttpServletRequest request) throws JSONException {
        String eMail = request.getParameter("email");
        if (eMail == null || eMail.equals(""))
            return Utility.getRtnStrAndMsg("failed", "Empty Email");
        String userName = request.getParameter("name");
        if (userName == null || userName.equals(""))
            return Utility.getRtnStrAndMsg("failed", "Empty Name");
        String userPw = request.getParameter("pass");
        if (userPw == null || userPw.equals(""))
            return Utility.getRtnStrAndMsg("failed", "Empty Password");
        String userGb = request.getParameter("usergb");
        if(userGb == null)
            userGb = "I";
        String phone = request.getParameter("phone");
        if (phone == null || phone.equals(""))
            return Utility.getRtnStrAndMsg("failed", "Empty PhoneNumber");
        Member member = userInfoRepo.findOne(eMail);
        if (member == null) {
            member = new Member();
            member.setEmail(eMail);
            member.setPassword(userPw);
            member.setUsername(userName);
            member.setUsergb(userGb);
            member.setUseyn("N");
            member.setPhone(phone);
            member.setRegdate(Utility.getStrToDate(""));
            userInfoRepo.save(member);
            JSONObject object = new JSONObject();
            object.put("result", "success");
            object.put("email", member.getEmail());
            object.put("pass", member.getPassword());
            object.put("name", member.getUsername());
            return object.toString();
        } else
            return Utility.getRtnStrAndMsg("failed", "EXIST");
    }

    /**
     * 토큰 넣기
     * @param request
     * @return
     * @throws JSONException
     */
    public String inserttoken(HttpServletRequest request) throws JSONException {
        String email = request.getParameter("email");
        String token = request.getParameter("token");
        Member member = userInfoRepo.findOne(email);
        if (member != null) {
            member.setToken(token);
            userInfoRepo.save(member);
            return Utility.getRtnStrAndMsg("success", "success");
        } else
            return Utility.getRtnStrAndMsg("faield", "not user");
    }
}
