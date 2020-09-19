package com.example.bobattend.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.print.DocFlavor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name="device")
public class Device {
    @Id
    @Column(name="device_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int device_id;
    @Column(name="personal_id")
    int personal_id;
    @Column(name="mac_addr")
    String mac_addr;
    @Column(name="device_index")
    int device_index;
    @Builder
    public Device(int personal_id, String mac_addr, int device_index){
        setPersonal_id(personal_id);
        setMac_addr(mac_addr);
        setDevice_index(device_index);
    }
}
