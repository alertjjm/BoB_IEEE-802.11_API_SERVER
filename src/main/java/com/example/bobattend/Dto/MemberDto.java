package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Member;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    String name;
    String id;
    String password;

    public Member toEntity() {
        return Member.builder().name(name).id(id).password(password).build();
    }
}
