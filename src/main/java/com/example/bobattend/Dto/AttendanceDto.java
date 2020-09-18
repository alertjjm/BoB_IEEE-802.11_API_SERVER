package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Attendance;
import com.fasterxml.jackson.annotation.JsonInclude;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AttendanceDto {
    int count;
    List<AttendanceInterface> data;
}
