package com.example.demo.Entity.Member;

import com.example.demo.Constant.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String userid;      //securityConfig에 formLogin에 지정한 이름
    private String password;    //이름변경 불가능
    private String username;
    private String usertel;
    private RoleType roleType;
}
