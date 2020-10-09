package com.example.bobattend.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class NameMonthDto {
    String date;
    int roomid;
    int entertime;
    int exittime;
    int timespent;
    public void addtime(int time){
        timespent+=time;
    }
}
