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
