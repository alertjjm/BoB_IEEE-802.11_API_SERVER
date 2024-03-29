package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AttendancebymacDto {
    int count;
    List<AttendanceInterface> data;
    List<Device> deviceList;
}
