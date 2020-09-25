package com.example.bobattend.Controller;

import com.example.bobattend.Dto.AttendanceDto;
import com.example.bobattend.Dto.AttendanceInterface;
import com.example.bobattend.Dto.UserListDto;
import com.example.bobattend.Entity.Attendance;
import com.example.bobattend.Entity.User;
import com.example.bobattend.Repository.AttendanceRepository;
import com.example.bobattend.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @GetMapping(value="/all", produces = "application/json")
    public String showall(){
        List<User> userList;
        userList=userrepo.findAll();
        UserListDto jsonResult=new UserListDto(userList.size(),userList);
        Gson gson=new Gson();
        String i=gson.toJson(jsonResult);
        return i;
    }
    /***************id를 통해 personal 정보 출력*********************/
    @GetMapping(value = "/{id}",produces = "application/json")
    public String showbyuserid(@PathVariable("id") String id){
        User user=userrepo.findById(id);
        if(user==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        Gson gson=new Gson();
        String i=gson.toJson(user);
        return i;
    }
    /***************id와 날짜를 통해 attendance 정보 출력*********************/
    @GetMapping(value = "/{id}/{time}",produces = "application/json")
    public String showattendancebyid(Model model, @PathVariable("id") String id, @PathVariable("time") String time) throws ParseException {
        int year=Integer.parseInt(time.substring(0,4));
        int month=Integer.parseInt(time.substring(4,6));
        int day=Integer.parseInt(time.substring(6,8));
        LocalDateTime startdate=LocalDateTime.of(year, month, day, 0,0,0);
        LocalDateTime enddate=LocalDateTime.of(year, month, day, 23,59,59);
        User temp=userrepo.findById(id);
        List<AttendanceInterface> datalist=new ArrayList<>();
        List<Attendance> attendanceList=attendrepo.findAllByPersonalidAndExittimeBetweenOrderByEntertime(temp.getPersonal_id(),startdate,enddate);
        if(attendanceList.size()>0){
            for(Attendance a:attendanceList){
                LocalDateTime temptime=a.getEntertime();
                int ent=temptime.getHour()*3600+temptime.getMinute()*60+temptime.getSecond();
                temptime=a.getExittime();
                int ext=temptime.getHour()*3600+temptime.getMinute()*60+temptime.getSecond();
                AttendanceInterface tempinterface=new AttendanceInterface(a.getPersonalid(),a.getRoomid(),ent,ext);
                datalist.add(tempinterface);
            }
            AttendanceDto jsonResult=new AttendanceDto(attendanceList.size(),datalist);
            Gson gson=new Gson();
            String i=gson.toJson(jsonResult);
            return i;
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }
}
