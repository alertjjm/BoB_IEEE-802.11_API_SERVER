package com.example.bobattend.Dto;

import com.example.bobattend.Entity.PureMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
