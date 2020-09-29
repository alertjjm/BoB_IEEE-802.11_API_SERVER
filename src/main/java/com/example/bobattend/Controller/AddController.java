package com.example.bobattend.Controller;
import com.example.bobattend.Dto.AttendanceDto;
import com.example.bobattend.Dto.AttendanceInterface;
import com.example.bobattend.Dto.DeviceDto;
import com.example.bobattend.Dto.UserDto;
import com.example.bobattend.Entity.Attendance;
import com.example.bobattend.Entity.Device;
import com.example.bobattend.Entity.User;
import com.example.bobattend.Repository.AttendanceRepository;
import com.example.bobattend.Repository.DeviceRepository;
import com.example.bobattend.Repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@Controller
public class AddController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    AttendanceRepository attendrepo;
    public static String sha256(String msg) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b: md.digest()) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
    @PostMapping(value="/user/signup")
    public String signup(UserDto userDto, Model model) throws Exception {
        System.out.println(userDto.getName());
        User temp=userRepository.findById(userDto.getId());
        if(temp!=null||userDto.getId().trim()==""||userDto.getPassword()==""||userDto.getName().trim()==""){
            return "redirect:/error";
        }
        userDto.setPassword(sha256(userDto.getPassword()));
        String newid=userRepository.save(userDto.toEntity()).getId();
        return "redirect:/api/"+newid;
    }
    @PostMapping(value="/device/add")
    public String adddevice(DeviceDto deviceDto) throws Exception {
        deviceDto.setMac(deviceDto.getMac().toLowerCase());
        User user=userRepository.findById(deviceDto.getId());
        Device device=deviceRepository.findDeviceByMacaddr(deviceDto.getMac());
        if(user==null||deviceDto.getId().trim()==""||deviceDto.getMac()==""||device!=null){
            return "redirect:/error";
        }
        int len=user.getDeviceList().size();
        deviceDto.setPersonal_id(user.getPersonal_id());
        deviceDto.setDevice_index(len+1);
        deviceRepository.save(deviceDto.toEntity());
        return "redirect:/api/"+deviceDto.getId();
    }
    @GetMapping(value="/")
    public String mainpage()  {
        return "index.html";
    }
    @GetMapping(value="/search")
    public String search(@RequestParam(value = "id") String id,@RequestParam(value = "time") String time)  {
        if(time.trim().equals("")==true)
            return "redirect:/api/"+id;
        else
            return "redirect:/api/"+id+"/"+ time;
    }
}
