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
    int timespent;  //총 체류 시간
    //총 시간 누적 메소드
    public void addtime(int time){
        timespent+=time;
    }
}