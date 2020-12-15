package com.example.bobattend.Dto;
import com.example.bobattend.Entity.Device;
import lombok.*;
//device 정보와 회원 정보 전달 위한 devicedto
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeviceDto {
    int personal_id;
    int device_index;
    String id;
    String mac;
    public Device toEntity(){
        return Device.builder().personal_id(personal_id).device_index(device_index).macaddr(mac).build();
    }
}
