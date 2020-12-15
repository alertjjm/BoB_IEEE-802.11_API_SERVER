package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Device;
import com.example.bobattend.Entity.Log;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
//디바이스 정보와 로그 정보
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeviceLogDto {
    int count;
    Device device;
    List<Log> loglist;
}
