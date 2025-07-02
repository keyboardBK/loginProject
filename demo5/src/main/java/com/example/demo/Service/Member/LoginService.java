package com.example.demo.Service.Member;

import com.example.demo.Entity.Member.MemberEntity;
import com.example.demo.Repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//로그인폼으로 받은 데이터를 데이터베이스에서 읽어서 security에 제공
@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userid) {
        //데이터베이스에 사용자를 조회해서
        //아이디, 비밀번호, 권한을 전달해요
        Optional<MemberEntity> memberEntity = memberRepository.findByUserid(userid);
        if(memberEntity.isPresent()) {
            return User.withUsername(memberEntity.get().getUserid())
                    .password(memberEntity.get().getPassword())
                    .roles(memberEntity.get().getRoleType().name())
                    .build();
        }
        throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
    }
}
