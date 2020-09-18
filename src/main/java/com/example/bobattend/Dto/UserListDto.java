package com.example.bobattend.Dto;

import com.example.bobattend.Entity.Device;
import com.example.bobattend.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class UserListDto {
    int count;
    List<User> data;
}
