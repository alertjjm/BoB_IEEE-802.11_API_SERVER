package com.example.bobattend.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//출석 정보
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AttendancebynameDto {
    String name;
    String id;
    int personalid;
    List<String> datelist = new ArrayList<>();
    int roomid;
    public void adddatelist(String date){
        this.datelist.add(date);
    }
}
