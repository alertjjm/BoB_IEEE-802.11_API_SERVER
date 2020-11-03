package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendancemapDto {
    String name;
    Attendance attinfo;
}