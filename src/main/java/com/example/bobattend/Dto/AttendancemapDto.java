package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Attendance;
import com.example.bobattend.Entity.Position;
import com.example.bobattend.Entity.PureMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//회원, 출석, 위치 정보 모두 제공
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendancemapDto {
    PureMember member;
    Attendance attinfo;
    Position position;
}