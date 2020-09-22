package com.example.bobattend.Entity;

import lombok.*;

import javax.persistence.*;

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
    String macaddr;
    @Column(name="device_index")
    int device_index;
    @Builder
    public Device(int personal_id, String macaddr, int device_index){
        setPersonal_id(personal_id);
        setMacaddr(macaddr);
        setDevice_index(device_index);
    }
}
