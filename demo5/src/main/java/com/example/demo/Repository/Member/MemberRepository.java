package com.example.demo.Repository.Member;

import com.example.demo.Entity.Member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository <MemberEntity, Integer>{
    Optional<MemberEntity> findByUserid(String userid);
    void deleteByUserid(String userid);
}
