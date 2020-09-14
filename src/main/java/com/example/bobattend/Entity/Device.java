package com.example.bobattend.Entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name="Personal_Device")
public class Device {
    int num;
    @Id
    String personal_id;
    String mac_addr;
    int device_index;
}
