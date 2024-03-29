package com.example.bobattend.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @Column(name = "index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int index;
    @Column(name = "personal_id")
    int personalid;
    @Column(name = "room_id")
    int roomid;
    @Column(name = "device_id")
    int deviceid;
    @Column(name = "enter_time")
    LocalDateTime entertime;
    @Column(name = "exit_time")
    LocalDateTime exittime;

    @Override
    public String toString() {
        return "Attendance{" +
                "index=" + index +
                ", personalid=" + personalid +
                ", roomid=" + roomid +
                ", entertime=" + entertime +
                ", exittime=" + exittime +
                '}';
    }
}
