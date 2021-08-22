package com.example.bobattend.Controller;

import com.example.bobattend.Dto.DeviceDto;
import com.example.bobattend.Dto.MemberDto;
import com.example.bobattend.Entity.Attendance;
import com.example.bobattend.Entity.Device;
import com.example.bobattend.Entity.Member;
import com.example.bobattend.Repository.AttendanceRepository;
import com.example.bobattend.Repository.DeviceRepository;
import com.example.bobattend.Repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
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

    @GetMapping(value = "/img/{name}",
            produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody
    byte[] Img(@PathVariable(value = "name") String fileName) throws IOException {
        ClassPathResource resource;
        InputStream in;
        try {
            resource = new ClassPathResource("test/" + fileName + ".png");
            in = resource.getInputStream();
        } catch (Exception e) {
            resource = new ClassPathResource("test/" + "blackUSER" + ".png");
            in = resource.getInputStream();
        }
        byte[] byteArray = IOUtils.toByteArray(in);
        return byteArray;
    }

    @PostMapping(value = "/user/signup")
    public String signup(MemberDto memberDto) throws Exception {
        Member temp = userRepository.findById(memberDto.getId());
        if (temp != null || memberDto.getId().trim() == "" || memberDto.getPassword() == "" || memberDto.getName().trim() == "") {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Error"
            );
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return "redirect:/api/" + memberDto.getId();
    }

    @PostMapping(value = "/device/add")
    public String adddevice(DeviceDto deviceDto) throws Exception {
        deviceDto.setMac(deviceDto.getMac().toLowerCase());
        Member member = userRepository.findById(deviceDto.getId());
        Device device = deviceRepository.findDeviceByMacaddr(deviceDto.getMac());

        if (member == null || deviceDto.getId().trim() == "" || deviceDto.getMac() == "") {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        if (device != null) {
            int pid = device.getPersonal_id();
            Member temp = userRepository.findMemberByPersonalid(pid);
            if (temp.getName().equals("unknown")) {
                List<Attendance> attendanceList = attendrepo.findAllByPersonalid(pid);
                for (Attendance a : attendanceList) {
                    a.setPersonalid(member.getPersonalid());
                }
                attendrepo.saveAll(attendanceList);
                device.setPersonal_id(member.getPersonalid());
                deviceRepository.save(device);
                temp.setName("deleted");
                userRepository.save(temp);
            } else
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                );
            return "redirect:/api/" + temp.getId();
        } else {
            int len = member.getDeviceList().size();
            deviceDto.setPersonal_id(member.getPersonalid());
            deviceDto.setDevice_index(len + 1);
            deviceRepository.save(deviceDto.toEntity());
            return "redirect:/api/" + member.getId();
        }
    }

    @GetMapping(value = "/")
    public String mainpage() {
        return "index.html";
    }

    @GetMapping(value = "/search")
    public String search(@RequestParam(value = "id") String id, @RequestParam(value = "time") String time) {
        if (time.trim().equals("") == true)
            return "redirect:/api/" + id;
        else
            return "redirect:/api/" + id + "/" + time;
    }

    @GetMapping(value = "/searchbymac")
    public String searchbymac(@RequestParam(value = "mac") String mac) {
        return "redirect:/api/mac/" + mac;
    }
}
