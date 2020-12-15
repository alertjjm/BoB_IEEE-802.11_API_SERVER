package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Member;
import lombok.*;

//회원 관리 위한 memberdto
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    String name;
    String id;
    String password;
    //memberDto to Member
    public Member toEntity(){
        return Member.builder().name(name).id(id).password(password).build();
    }
}
