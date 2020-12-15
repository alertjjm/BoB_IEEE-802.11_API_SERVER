package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
//회원 정보 요구 시 필요한 정보
@AllArgsConstructor
@Getter
@Setter
public class UserListDto {
    int count;  //데이터 총 개수
    List<Member> data;  //member 리스트
}
