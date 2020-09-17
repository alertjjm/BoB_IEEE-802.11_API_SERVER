package com.example.bobattend.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="attendance")
public class Attendance {
    @Id
    @Column(name="index")
    @GeneratedValue(strategy=GenerationType.IDENTITY) //id null로
    int index;
    @Column(name="personal_id")
    int personal_id;
    @Column(name="room_id")
    int room_id;
    @Column(name="enter_time")
    LocalDateTime enter_time;
    @Column(name="exit_time")
    LocalDateTime exit_time;
}
