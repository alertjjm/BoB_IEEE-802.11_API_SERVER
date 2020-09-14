package com.example.bobattend.Entity;

import lombok.*;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="Log")
public class Log {
    @Id
    int index;
    String mac_addr;
    int device_id;
    int RSSI;
    LocalDateTime time;
}
