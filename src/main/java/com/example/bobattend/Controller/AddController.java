package com.example.bobattend.Controller;
import com.example.bobattend.Dto.DeviceDto;
import com.example.bobattend.Dto.MemberDto;
import com.example.bobattend.Entity.Device;
import com.example.bobattend.Entity.Member;
import com.example.bobattend.Repository.AttendanceRepository;
import com.example.bobattend.Repository.DeviceRepository;
import com.example.bobattend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;

@CrossOrigin(origins = "*")
@Controller
public class AddController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    AttendanceRepository attendrepo;

    @PostMapping(value="/user/signup")
    public String signup(MemberDto memberDto) throws Exception {
        System.out.println(memberDto.getName());
        Member temp=userRepository.findById(memberDto.getId());
        if(temp!=null|| memberDto.getId().trim()==""|| memberDto.getPassword()==""|| memberDto.getName().trim()==""){
            return "redirect:/error";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        String newid=userRepository.save(memberDto.toEntity()).getId();
        return "redirect:/api/"+newid;
    }
    @PostMapping(value="/device/add")
    public String adddevice(DeviceDto deviceDto) throws Exception {//개발 필요
        deviceDto.setMac(deviceDto.getMac().toLowerCase());
        Member member =userRepository.findById(deviceDto.getId());
        Device device=deviceRepository.findDeviceByMacaddr(deviceDto.getMac());
        if(member ==null||deviceDto.getId().trim()==""||deviceDto.getMac()==""){
            return "redirect:/error";
        }
        if(device!=null){
            int pid=device.getPersonal_id();
            Member temp=userRepository.findMemberByPersonalid(pid);
            if(temp.getName().equals("정종민")){
                temp.setId(member.getId());
                temp.setPassword(member.getPassword());
                temp.setName(member.getName());
                userRepository.save(temp);
                userRepository.delete(member);
            }
            else
                return "redirect:/error";
        }
        else {
            int len = member.getDeviceList().size();
            deviceDto.setPersonal_id(member.getPersonalid());
            deviceDto.setDevice_index(len + 1);
            deviceRepository.save(deviceDto.toEntity());
        }
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
    @GetMapping(value="/searchbymac")
    public String searchbymac(@RequestParam(value = "mac") String mac)  {
        return "redirect:/api/mac/"+mac;
    }
}
