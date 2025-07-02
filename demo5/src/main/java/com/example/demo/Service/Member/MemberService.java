package com.example.demo.Service.Member;

import com.example.demo.Constant.RoleType;
import com.example.demo.DTO.Member.MemberDTO;
import com.example.demo.Entity.Member.MemberEntity;
import com.example.demo.Repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder; //암호화

    //전체조회
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntities = memberRepository.findAll();
        List<MemberDTO> memberDTOS = Arrays.asList(modelMapper.map(memberEntities, MemberDTO[].class));
        return memberDTOS;
    }

    //개별조회(관리자, 운영자=> 목록, 사용자=> 회원정보)
    public MemberDTO findByUserId(String userid) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUserid(userid);
        MemberDTO memberDTO = modelMapper.map(memberEntity.get(), MemberDTO.class);
        return memberDTO;
    }

    //삽입
    public void insert(MemberDTO memberDTO) {
        Optional<MemberEntity> read = memberRepository.findByUserid(memberDTO.getUserid());
        if(read.isPresent()) {
            throw new IllegalStateException("등록된 회원이 존재합니다");
        }
        MemberEntity memberEntity = modelMapper.map(memberDTO, MemberEntity.class);
        memberEntity.setPassword(passwordEncoder.encode(memberEntity.getPassword()));
        if(memberRepository.count() == 0) { //등록된 회원이 한명도 없으면
            memberEntity.setRoleType(RoleType.ADMIN);
        }else {
            memberEntity.setRoleType(RoleType.USER);
        }

        memberRepository.save(memberEntity);
    }

    //수정
    public void update(MemberDTO memberDTO) {
        Optional<MemberEntity> read = memberRepository.findByUserid(memberDTO.getUserid());
        if(read.isPresent()) {
            //Optional이 있을
            read.get().setPassword(passwordEncoder.encode(memberDTO.getPassword()));
            read.get().setUserid(memberDTO.getUserid());
            read.get().setUsername(memberDTO.getUsername());
            read.get().setUsertel(memberDTO.getUsertel());
            read.get().setRoleType(memberDTO.getRoleType());
            memberRepository.save(read.get());

        }
    }

    //삭제
    public void deleteByUserid(String userid) {
        memberRepository.deleteByUserid(userid);
    }
}
