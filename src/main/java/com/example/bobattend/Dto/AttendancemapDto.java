package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Attendance;
import com.example.bobattend.Entity.Position;
import com.example.bobattend.Entity.PureMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendancemapDto {
    PureMember member;
    Attendance attinfo;
    Position position;
}