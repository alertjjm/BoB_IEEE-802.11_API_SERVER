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

//등록 관리 위한 controller
@CrossOrigin(origins = "*")
@Controller
public class AddController {
    @Autowired
    UserRepository userRepository;  //사용자 repo
    @Autowired
    DeviceRepository deviceRepository;  //device repo
    @Autowired
    AttendanceRepository attendrepo;    //출석 repo
    //이미지 반환
    @GetMapping(value = "/img/{name}",
            produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] Img(@PathVariable(value = "name") String fileName) throws IOException {
        ClassPathResource resource;
        InputStream in;
        try{
            //fileName.png의 이미지
            resource = new ClassPathResource("test/"+fileName+".png");
            in = resource.getInputStream();
        }catch (Exception e){
            //존재하지 않을 경우 기본 이미지
            resource = new ClassPathResource("test/"+"blackUSER"+".png");
            in = resource.getInputStream();
        }
        byte[] byteArray = IOUtils.toByteArray(in);
        return byteArray;   //byteArray로 이미지 반환
    }
    //회원 등록
    @PostMapping(value="/user/signup")
    public String signup(MemberDto memberDto) throws Exception {
        //기등록자 여부 확인 위한 repo 조회
        Member temp=userRepository.findById(memberDto.getId());
        //예외 처리(기등록자, 입력 정보 부정확)
        if(temp!=null|| memberDto.getId().trim()==""|| memberDto.getPassword()==""|| memberDto.getName().trim()==""){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Error"
            );
        }
        //비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        //정상 등록 후 회원 정보 api로 리다이렉트
        return "redirect:/api/"+memberDto.getId();
    }
    //기기 등록
    @PostMapping(value="/device/add")
    public String adddevice(DeviceDto deviceDto) throws Exception {
        deviceDto.setMac(deviceDto.getMac().toLowerCase());
        Member member =userRepository.findById(deviceDto.getId());//회원 정보 얻기
        Device device=deviceRepository.findDeviceByMacaddr(deviceDto.getMac());//기등록 기기 여부를 찾아 기기 정보 얻기
        //예외 처리
        if(member ==null||deviceDto.getId().trim()==""||deviceDto.getMac()==""){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        //기등록 일 경우
        if(device!=null){//device의 personal id, attendance의 personal id
            int pid=device.getPersonal_id();//unknown dummy id
            Member temp=userRepository.findMemberByPersonalid(pid);//기등록되어 연결되어 있는 회원정보 조회
            if(temp.getName().equals("unknown")){   //unknown 회원인 경우
                List<Attendance> attendanceList=attendrepo.findAllByPersonalid(pid);
                for(Attendance a: attendanceList){
                    a.setPersonalid(member.getPersonalid());    //기존 출석 정보 변경
                }
                attendrepo.saveAll(attendanceList);
                device.setPersonal_id(member.getPersonalid());  //디바이스 회원정보 변경
                deviceRepository.save(device);
                temp.setName("deleted");    //기존의 unkonwn 회원 정보 삭제 처리
                userRepository.save(temp);
            }
            else    //이미 등록된 회원, 기기에 접근한 경우 에러처리
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                );
            //등록 후 정보 api로 리다이렉트
            return "redirect:/api/"+temp.getId();
        }
        else {  //device가 미등록인 경우 기기 신규 등록
            int len = member.getDeviceList().size();
            deviceDto.setPersonal_id(member.getPersonalid());
            deviceDto.setDevice_index(len + 1);
            deviceRepository.save(deviceDto.toEntity());
            //등록 후 정보 api로 리다이렉트
            return "redirect:/api/"+member.getId();
        }
    }
    //api 메인 화면
    @GetMapping(value="/")
    public String mainpage()  {
        return "index.html";
    }
    //api 화면에서 검색 처리를 위한 메소드
    @GetMapping(value="/search")
    public String search(@RequestParam(value = "id") String id,@RequestParam(value = "time") String time)  {
        if(time.trim().equals("")==true)
            return "redirect:/api/"+id;
        else
            return "redirect:/api/"+id+"/"+ time;
    }
    //api 화면에서 검색 처리를 위한 메소드
    @GetMapping(value="/searchbymac")
    public String searchbymac(@RequestParam(value = "mac") String mac)  {
        return "redirect:/api/mac/"+mac;
    }
}
