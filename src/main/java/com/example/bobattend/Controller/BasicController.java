package com.example.bobattend.Controller;

import com.example.bobattend.Dto.AttendanceDto;
import com.example.bobattend.Dto.UserListDto;
import com.example.bobattend.Entity.Attendance;
import com.example.bobattend.Entity.User;
import com.example.bobattend.Repository.AttendanceRepository;
import com.example.bobattend.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BasicController {
    @Autowired
    UserRepository userrepo;
    @Autowired
    AttendanceRepository attendrepo;


    /***************모든 personal 정보 출력*********************/
    @GetMapping("/all")
    public UserListDto showall(){
        List<User> userList;
        userList=userrepo.findAll();
        UserListDto jsonResult=new UserListDto(userList.size(),userList);
        return jsonResult;
    }
    /***************id를 통해 personal 정보 출력*********************/
    @GetMapping(value = "/{id}")
    public User showbyuserid(Model model, @PathVariable("id") String id){
        User user=userrepo.findById(id);
        return user;
    }
    /***************id와 날짜를 통해 attendance 정보 출력*********************/
    @GetMapping(value = "/{id}/{time}")
    public AttendanceDto showattendancebyid(Model model, @PathVariable("id") String id, @PathVariable("time") String time) throws ParseException {
        String timedate=time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8);
        int year=Integer.parseInt(time.substring(0,4));
        int month=Integer.parseInt(time.substring(4,6));
        int day=Integer.parseInt(time.substring(6,8));
        LocalDateTime startdate=LocalDateTime.of(year, month, day, 0,0,0);
        LocalDateTime enddate=LocalDateTime.of(year, month, day, 23,59,59);

        User temp=userrepo.findById(id);
        List<Attendance> attendanceList=attendrepo.findAllByPersonalidAndExittimeBetween(temp.getPersonal_id(),startdate,enddate);
        AttendanceDto jsonResult=new AttendanceDto(attendanceList.size(),attendanceList);
        return jsonResult;
    }
}
