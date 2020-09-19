package com.example.bobattend.Dto;

import com.example.bobattend.Entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    String name;
    String id;
    String password;
    public User toEntity(){
        return User.builder().name(name).id(id).password(password).build();
    }
}
