package com.example.bobattend.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AttendancebynameDto {
    String name;
    String id;
    int personalid;
    HashSet<String> datelist = new HashSet<>();
    int roomid;
    public void adddatelist(String date){
        this.datelist.add(date);
    }
}
