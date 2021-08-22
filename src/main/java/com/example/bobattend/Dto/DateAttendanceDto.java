package com.example.bobattend.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DateAttendanceDto {
    String name;
    String id;
    Boolean status;
    int roomdid;
    int entertime;
    int exittime;
}