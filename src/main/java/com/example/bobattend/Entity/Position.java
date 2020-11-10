package com.example.bobattend.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="position")
public class Position {
    @Id
    @Column(name="index")
    @GeneratedValue(strategy=GenerationType.IDENTITY) //id null로
            int index;
    @Column(name="personal_id")
    int personalid;
    @Column(name="device_id")
    int deviceid;
    @Column(name="x")
    float x;
    @Column(name="y")
    float y;
    @Column(name="time")
    LocalDateTime attendtime;
}
