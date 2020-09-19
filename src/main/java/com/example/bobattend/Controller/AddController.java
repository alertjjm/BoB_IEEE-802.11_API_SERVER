package com.example.bobattend.Controller;
import com.example.bobattend.Dto.DeviceDto;
import com.example.bobattend.Dto.UserDto;
import com.example.bobattend.Entity.User;
import com.example.bobattend.Repository.DeviceRepository;
import com.example.bobattend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.MessageDigest;

@Controller
public class AddController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeviceRepository deviceRepository;
    public static String sha256(String msg) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b: md.digest()) {
            builder.append(String.format("%02x  ", b));
        }
        return builder.toString();
    }
    @PostMapping(value="/user/signup")
    public String signup(UserDto userDto) throws Exception {
        userDto.setPassword(sha256(userDto.getPassword()));
        String newid=userRepository.save(userDto.toEntity()).getId();
        return "redirect:/api/"+newid;
    }
    @PostMapping(value="/device/add")
    public String adddevice(DeviceDto deviceDto) throws Exception {
        User user=userRepository.findById(deviceDto.getId());
        int len=user.getDeviceList().size();
        deviceDto.setPersonal_id(user.getPersonal_id());
        deviceDto.setDevice_index(len+1);
        deviceRepository.save(deviceDto.toEntity());
        return "redirect:/api/"+deviceDto.getId();
    }
}
