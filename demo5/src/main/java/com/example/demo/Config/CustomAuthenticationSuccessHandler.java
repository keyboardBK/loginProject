package com.example.demo.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    //인증성공처리 메소드를 수정
    //request(요청)-로그인->response(응답)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, SecurityException {
        //로그인에 사용된 아이디(username)을 읽는다.
        HttpSession session = request.getSession();
        if(session != null) {
            String userid = authentication.getName();   //로그인한 아이디를 저장
            session.setAttribute("userid", userid);
        }
        UserDetails user = (UserDetails) authentication.getPrincipal();


        response.sendRedirect("/");
    }
}
