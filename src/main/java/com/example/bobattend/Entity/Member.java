package com.example.bobattend.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "personal")
public class Member {
    @Id
    @Column(name = "personal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int personalid;
    @Column(name = "name")
    String name;
    @Column(name = "id")
    String id;
    @Column(name = "password")
    String password;

    @Builder
    public Member(String name, String id, String password) {
        setName(name);
        setId(id);
        setPassword(password);
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "personal_id", updatable = false)
    private List<Device> deviceList = new ArrayList<>();
}
