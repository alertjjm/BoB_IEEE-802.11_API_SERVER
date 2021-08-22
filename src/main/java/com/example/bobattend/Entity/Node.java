package com.example.bobattend.Entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "node")
public class Node {
    @Id
    @Column(name = "node_id")
    int node_id;
    @Column(name = "mac_addr")
    String mac_addr;
    @Column(name = "room_id")
    int room_id;
}
