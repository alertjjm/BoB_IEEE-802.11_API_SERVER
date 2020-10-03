package com.example.bobattend.Controller;

import com.example.bobattend.Dto.*;
import com.example.bobattend.Entity.Attendance;
import com.example.bobattend.Entity.User;
import com.example.bobattend.Repository.AttendanceRepository;
import com.example.bobattend.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@CrossOrigin(origins = "*")
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
    /***************이름를 통해 personal 정보 출력*********************/
    @GetMapping(value = "/name/{name}",produces = "application/json")
    public String showbyusername(@PathVariable("name") String name){
        List<User> userList=userrepo.findAllByName(name);
        List<Attendance> attendanceList=attendrepo.findAll();

        if(userList.size()==0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        List<AttendancebynameDto> alist=new ArrayList<>();
        for(User u: userList){
            AttendancebynameDto atemp=new AttendancebynameDto();
            atemp.setId(u.getId());
            atemp.setName(u.getName());
            atemp.setPersonalid(u.getPersonal_id());
            alist.add(atemp);
        }
        for(AttendancebynameDto u: alist){
            HashSet<String> dateset=new HashSet<>();
            for(Attendance a: attendanceList){
                if(a.getPersonalid()==u.getPersonalid()){
                    System.out.println(a.toString());
                    String date= a.getEntertime().format(DateTimeFormatter.BASIC_ISO_DATE);
                    u.setRoomid(a.getRoomid());
                    dateset.add(date);
                }
                else{
                    System.out.println("Not matched "+a.toString());
                }
            }
            ArrayList<String> datelist=new ArrayList<>(dateset);
            datelist.sort(null);
            u.setDatelist(datelist);
        }
        Gson gson=new Gson();
        String i=gson.toJson(alist);
        return i;
    }
    /***************id와 날짜를 통해 attendance 정보 출력*********************/
    @GetMapping(value = "/{id}/{date}",produces = "application/json")
    public String showattendancebyid(Model model, @PathVariable("id") String id, @PathVariable("date") String date) throws ParseException {
        int year=Integer.parseInt(date.substring(0,4));
        int month=Integer.parseInt(date.substring(4,6));
        int day=Integer.parseInt(date.substring(6,8));
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
    /***************모든 date 정보 출력*********************/
    @GetMapping(value="/date/{date}", produces = "application/json")
    public String dateshow(@PathVariable("date") String date){
        if(date.length()!=8){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "date format error"
            );
        }
        int year=Integer.parseInt(date.substring(0,4));
        int month=Integer.parseInt(date.substring(4,6));
        int day=Integer.parseInt(date.substring(6,8));
        LocalDateTime startdate=LocalDateTime.of(year, month, day, 0,0,0);
        LocalDateTime enddate=LocalDateTime.of(year, month, day, 23,59,59);
        List<Attendance> attendanceList=attendrepo.findAllByExittimeBetween(startdate,enddate);
        List<User> userList;
        userList=userrepo.findAll();
        List<DateAttendanceDto> returnlist=new ArrayList<>();
        if(attendanceList.size()==0){
            for(User t:userList){
                DateAttendanceDto temp=new DateAttendanceDto();
                temp.setName(t.getName());
                temp.setStatus(Boolean.FALSE);
                temp.setId(t.getId());
                temp.setRoomdid(0);
                temp.setEntertime(0);
                temp.setExittime(0);
                returnlist.add(temp);
            }
        }
        else{
            for(User t:userList){
                DateAttendanceDto temp=new DateAttendanceDto();
                temp.setName(t.getName());
                temp.setStatus(Boolean.FALSE);
                temp.setId(t.getId());
                temp.setRoomdid(0);
                temp.setEntertime(86400);
                temp.setExittime(0);
                returnlist.add(temp);
                for(Attendance a: attendanceList){
                    int tempentertime=a.getEntertime().getHour()*3600+a.getEntertime().getMinute()*60+a.getEntertime().getSecond();
                    int tempexittime=a.getExittime().getHour()*3600+a.getExittime().getMinute()*60+a.getExittime().getSecond();
                    if(t.getPersonal_id()==a.getPersonalid()){
                        temp.setStatus(Boolean.TRUE);
                        temp.setRoomdid(a.getRoomid());
                        if(temp.getEntertime()>tempentertime)
                            temp.setEntertime(tempentertime);
                        if(temp.getExittime()<tempexittime)
                            temp.setExittime(tempexittime);
                    }
                }
            }
        }
        Gson gson=new Gson();
        String i=gson.toJson(returnlist);
        return i;
    }
    /***************모든 time 정보 출력*********************/
    @GetMapping(value="/timelist", produces = "application/json")
    public String timeshow(@RequestParam(value = "time",required = false) String time,@RequestParam(value = "date",required = false) String date){
        int parsedtime;
        LocalDateTime enddate=LocalDateTime.now();
        LocalDateTime startdate=enddate.minusMinutes(60);
        if(time!=null){
            parsedtime=Integer.parseInt(time);
            int hour=parsedtime/3600;
            parsedtime%=3600;
            int minute=parsedtime/60;
            int second=parsedtime%60;
            int year=Integer.parseInt(date.substring(0,4));
            int month=Integer.parseInt(date.substring(4,6));
            int day=Integer.parseInt(date.substring(6,8));
            enddate=LocalDateTime.of(year, month, day, hour,minute,second);
            startdate=enddate.minusMinutes(60);
            System.out.println(startdate);
            System.out.println(enddate);
        }
        List<AttendanceInterface> datalist=new ArrayList<>();
        List<Attendance> attendanceList=attendrepo.findAllByEntertimeBetweenOrderByEntertime(startdate,enddate);
        System.out.println(attendanceList.size());
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
