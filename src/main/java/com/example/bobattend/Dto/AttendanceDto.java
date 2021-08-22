package com.example.bobattend.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AttendanceDto {
    int count;
    List<AttendanceInterface> data;
}
