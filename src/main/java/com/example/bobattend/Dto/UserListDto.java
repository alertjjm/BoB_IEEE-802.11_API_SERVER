package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class UserListDto {
    int count;
    List<Member> data;
}
