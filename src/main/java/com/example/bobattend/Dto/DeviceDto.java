package com.example.bobattend.Dto;
import com.example.bobattend.Entity.Device;
import lombok.*;

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
