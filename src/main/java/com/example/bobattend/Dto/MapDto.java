package com.example.bobattend.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//출석 정보 리스트와 room 정보
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MapDto {
    int roomid;
    int count;
    List<AttendancemapDto> members;
    public void addattendancemap(AttendancemapDto item){
        this.members.add(item);
    }
}
