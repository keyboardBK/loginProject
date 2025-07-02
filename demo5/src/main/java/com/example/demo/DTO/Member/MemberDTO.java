package com.example.demo.DTO.Member;

import com.example.demo.Constant.RoleType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberDTO {
    private Integer id;

    private String userid;      //securityConfig에 formLogin에 지정한 이름
    private String password;    //이름변경 불가능
    private String username;
    private String usertel;
    private RoleType roleType;
}
