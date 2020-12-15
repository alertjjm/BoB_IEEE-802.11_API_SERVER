package com.example.bobattend.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
//log entity
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="log")
public class Log {
    @Id
    @Column(name="index")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int index;
    @Column(name="node_id")
    int node_id;
    @Column(name="device_id")
    int deviceid;
    @Column(name="RSSI")
    int RSSI;
    @Column(name="time")
    LocalDateTime time;
}
