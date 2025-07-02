package com.example.demo.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    //암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //권한설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth)->{
            //자원 공유(static)->자동으로 폴더 검색
            auth.requestMatchers("/assets/**", "/css/**", "/js/**").permitAll();
            //초기개발시(모든 페이지 접근가능)->중기때는 삭제, Controller의 맵핑명
//            auth.requestMatchers("/**").permitAll();
            //H2콘솔 접근
            auth.requestMatchers("/h2-console/**").permitAll();
            //api 정의서에서 작업별로 권한에 맞게 지정
            //개발중기시 각 맵핑별 세부 권한 지정
            //공통으로 접근하는 곳
            auth.requestMatchers("/", "/login", "/register", "/password","/member/**").permitAll();
            //사용자, 운영자, 관리자별로 권한 부여
            auth.requestMatchers("/board/**", "/product/**").authenticated();
            auth.requestMatchers("/manager/**").hasAnyRole("ADMIN", "OPERATOR");
            auth.requestMatchers("/admin/**").hasAnyRole("ADMIN");

        });
        http.headers((headers) -> headers
                .frameOptions().sameOrigin()
        );
        //로그인 설정(username(변경가능), password(변경불가) 구성)
        http.formLogin(login->login
                .loginPage("/login")  //사용자가 만든 로그인페이지의 맵핑명
                .defaultSuccessUrl("/") //로그인성공시 이동할 맵핑명
                .usernameParameter("userid") //로그인폼에서 아이디에 해당하는 name명(기본 username자동인식)
                .permitAll() //로그인폼에 접근권한
                .successHandler(new CustomAuthenticationSuccessHandler())  //로그인 성공후 처리할 클래스
        );//로그인폼에 대한 설정
        //csrf 변조방지
        http.csrf(AbstractHttpConfigurer::disable); //비활성화
        //로그아웃 처리
        http.logout(logout->logout
                .logoutUrl("/logout") //로그아웃 맵핑명
                .logoutSuccessUrl("/login") //로그아웃 성공시 이동할 맵핑
        );
        return http.build();
    }
}
