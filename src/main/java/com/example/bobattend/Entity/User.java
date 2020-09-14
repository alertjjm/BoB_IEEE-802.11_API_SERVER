package com.example.bobattend.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Personal_Info")
public class User {
    int num;
    @Id
    String personal_id;
    String name;
}
