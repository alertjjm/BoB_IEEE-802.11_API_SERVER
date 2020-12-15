package com.example.bobattend.Entity;

import lombok.*;

import javax.persistence.*;
//필수 정보만 포함한 user entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="personal")
public class PureMember {
    @Id
    @Column(name="personal_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int personalid;
    @Column(name="name")
    String name;
    @Column(name="id")
    String id;
}
