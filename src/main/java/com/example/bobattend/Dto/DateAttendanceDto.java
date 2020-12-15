package com.example.bobattend.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//출석 정보 전달 위한 Dto
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DateAttendanceDto {
    String name;
    String id;
    Boolean status; //출석 여부
    int roomdid;
    int entertime;
    int exittime;
}