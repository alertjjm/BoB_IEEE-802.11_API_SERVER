package com.example.bobattend.Dao;

import com.example.bobattend.Entity.Attendance;
import com.example.bobattend.Entity.Device;
import com.example.bobattend.Entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;
/*
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name="personal")
public class UserDao {
    @Id
    @Column(name="personal_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int personalid;
    @Column(name="name")
    String name;
    @Column(name="id")
    String id;
    @Column(name="password")
    String password;
    @Builder
    public UserDao(String name, String id, String password){
        setName(name);
        setId(id);
        setPassword(password);
    }
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="personal_id")
    List<Device> deviceList;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="personal_id")
    List<Attendance> attendanceList;
}
*/