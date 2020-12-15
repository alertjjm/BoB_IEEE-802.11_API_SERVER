package com.example.bobattend.Controller;

import com.example.bobattend.Dto.*;
import com.example.bobattend.Entity.*;
import com.example.bobattend.Repository.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//rest api controller
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class BasicController {
    /***************repo 기본 정보 bean으로 등록****************/
    @Autowired
    UserRepository userrepo;
    @Autowired
    AttendanceRepository attendrepo;
    @Autowired
    LogRepository logrepo;
    @Autowired
    DeviceRepository devicerepo;
    @Autowired
    PureMemberRepository pureMemberrepo;
    @Autowired
    PositionRepository positionRepository;
    /***************모든 personal 정보 출력*********************/
    @CrossOrigin(origins="*")
    @GetMapping(value="/all", produces = "application/json")
    public String showall(){
        List<Member> memberList;
        /********성능을 위해 30개만 보여주기******/
        memberList =userrepo.findAllByPersonalidBetween(0,30);
        UserListDto jsonResult=new UserListDto(memberList.size(), memberList);
        Gson gson=new Gson();
        String i=gson.toJson(jsonResult);
        return i;
    }
    /***************이름를 통해 personal 정보 출력*********************/
    @CrossOrigin(origins="*")
    @GetMapping(value = "/name/{name}",produces = "application/json")
    public String showbyusername(@PathVariable("name") String name){
        //이름이 unkonwn이거나 deleted인 경우
        if(name.equals("unknown")||name.equals("deleted")){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "entity not found"
            );
        }
        //이름으로 찾기
        List<Member> memberList =userrepo.findAllByName(name);
        List<Attendance> attendanceList=attendrepo.findAll();
        //찾는 이름이 없는 경우 예외처리
        if(memberList.size()==0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        //출석 정보 업데이트
        List<AttendancebynameDto> alist=new ArrayList<>();
        for(Member u: memberList){
            AttendancebynameDto atemp=new AttendancebynameDto();
            atemp.setId(u.getId());
            atemp.setName(u.getName());
            atemp.setPersonalid(u.getPersonalid());
            alist.add(atemp);
        }
        //hash map에 출석 정보 저장
        for(AttendancebynameDto u: alist){
            HashSet<String> dateset=new HashSet<>();
            for(Attendance a: attendanceList){
                if(a.getPersonalid()==u.getPersonalid()){
                    String date= a.getEntertime().format(DateTimeFormatter.BASIC_ISO_DATE);
                    u.setRoomid(a.getRoomid());
                    dateset.add(date);
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
    /***************이름과 month를 통해 personal 정보 출력*********************/
    @CrossOrigin(origins="*")
    @GetMapping(value = "/name/{name}/month/{ymonth}",produces = "application/json")
    public String showbyusernamemonth(@PathVariable("name") String name,@PathVariable("ymonth") String ymonth){
        //이름이 unkonwn이거나 deleted인 경우
        if(name.equals("unknown")||name.equals("deleted")){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "entity not found"
            );
        }
        List<Member> memberList =userrepo.findAllByName(name);
        //찾는 이름이 없는 경우 예외처리
        if(memberList.size()==0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        //날짜 파싱
        int year=Integer.parseInt(ymonth.substring(0,4));
        int month=Integer.parseInt(ymonth.substring(4,6));
        //검색 날짜 범위 지정
        LocalDateTime startdate=LocalDateTime.of(year, month, 1, 0,0,0);
        LocalDateTime enddate=LocalDateTime.of(year, month, 1, 0,0,0).plusMonths(1);
        //월 정보 범위안에 있는 출석 정보 조회
        List<Attendance> attendanceList=attendrepo.findAllByPersonalidAndExittimeBetweenOrderByEntertime(memberList.get(0).getPersonalid(),startdate,enddate);
        List<NameMonthDto> alist=new ArrayList<>();
        int index=0;
        //NameMonthDto list 정보 setting
        for(index=0; index<attendanceList.size(); index++){
            Attendance a=attendanceList.get(index);
            //시간 정보를 변환
            String date=Integer.toString(a.getEntertime().getYear()*10000+a.getEntertime().getMonthValue()*100+a.getEntertime().getDayOfMonth());
            int entertime=a.getEntertime().getHour()*3600+a.getEntertime().getMinute()*60+a.getEntertime().getSecond();
            int exittime=a.getExittime().getHour()*3600+a.getExittime().getMinute()*60+a.getExittime().getSecond();
            //날짜가 넘어간 경우 보정
            if(alist.size()>0 && alist.get(alist.size()-1).getDate().equals(date)){
                if(alist.get(alist.size()-1).getEntertime()>entertime)
                    alist.get(alist.size()-1).setEntertime(entertime);
                if(alist.get(alist.size()-1).getExittime()<exittime)
                    alist.get(alist.size()-1).setExittime(exittime);
                if(exittime<entertime)
                    exittime+=24*3600;
                alist.get(alist.size()-1).addtime(exittime-entertime);
            }
            else{
                NameMonthDto temp=new NameMonthDto(date,a.getRoomid(),entertime,exittime,exittime-entertime);
                if(exittime<entertime)
                    temp.addtime(24*3600);  //체류시간 업데이트
                alist.add(temp);
            }
        }
        Gson gson=new Gson();
        String i=gson.toJson(alist);
        return i;
    }
    /***************id를 통해 personal 정보 출력*********************/
    @GetMapping(value = "/{id}",produces = "application/json")
    public String showbyuserid(@PathVariable("id") String id){
        Member member =userrepo.findById(id);
        //예외처리
        if(member ==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        Gson gson=new Gson();
        String i=gson.toJson(member);
        return i;
    }
    /***************mac를 통해 log 정보 출력*********************/
    @GetMapping(value = "/mac/{mac}",produces = "application/json")
    public String showbymac(@PathVariable("mac") String mac){
        //mac 주소를 전부 소문자로 변환
        Device device =devicerepo.findDeviceByMacaddr(mac.toLowerCase());
        //예외처리
        if(device ==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        //device id로 로그 정보 조회
        List<Log> logList=logrepo.findAllByDeviceid(device.getDeviceid());
        DeviceLogDto data=new DeviceLogDto();
        data.setCount(logList.size());
        data.setDevice(device);
        data.setLoglist(logList);
        Gson gson=new Gson();
        String i=gson.toJson(data);
        return i;
    }
    /***************id와 날짜를 통해 attendance 정보 출력*********************/
    @GetMapping(value = "/{id}/{date}",produces = "application/json")
    public String showattendancebyid(@PathVariable("id") String id, @PathVariable("date") String date) throws ParseException {
        //날짜파싱
        int year=Integer.parseInt(date.substring(0,4));
        int month=Integer.parseInt(date.substring(4,6));
        int day=Integer.parseInt(date.substring(6,8));
        //시간 범위 지정
        LocalDateTime startdate=LocalDateTime.of(year, month, day, 0,0,0);
        LocalDateTime enddate=LocalDateTime.of(year, month, day, 23,59,59);
        Member temp=userrepo.findById(id);
        List<AttendanceInterface> datalist=new ArrayList<>();
        List<Attendance> attendanceList=attendrepo.findAllByPersonalidAndExittimeBetweenOrderByEntertime(temp.getPersonalid(),startdate,enddate);
        if(attendanceList.size()>0){
            for(Attendance a:attendanceList){
                //시간 변환
                LocalDateTime temptime=a.getEntertime();
                int ent=temptime.getHour()*3600+temptime.getMinute()*60+temptime.getSecond();
                temptime=a.getExittime();
                int ext=temptime.getHour()*3600+temptime.getMinute()*60+temptime.getSecond();
                if(ext<ent) //날짜가 넘어간 경우 보정
                    ext=3600*24;
                AttendanceInterface tempinterface=new AttendanceInterface(a.getPersonalid(),a.getRoomid(),ent,ext);
                datalist.add(tempinterface);
            }
            AttendanceDto jsonResult=new AttendanceDto(attendanceList.size(),datalist);
            Gson gson=new Gson();
            String i=gson.toJson(jsonResult);
            return i;
        }
        else{   //예외처리
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }
    /***************name과 날짜를 통해 attendance 정보 출력*********************/
    @GetMapping(value = "/name/{name}/date/{date}",produces = "application/json")
    public String showbynameanddate(@PathVariable("name") String name, @PathVariable("date") String date) throws ParseException {
        //이름이 unknown이거나 deleted인 경우 예외처리
        if(name.equals("unknown")||name.equals("deleted")){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "entity not found"
            );
        }
        //날짜파싱
        int year=Integer.parseInt(date.substring(0,4));
        int month=Integer.parseInt(date.substring(4,6));
        int day=Integer.parseInt(date.substring(6,8));
        //시간 범위 지정
        LocalDateTime startdate=LocalDateTime.of(year, month, day, 0,0,0);
        LocalDateTime enddate=LocalDateTime.of(year, month, day, 23,59,59);
        List<Member> memberList =userrepo.findAllByName(name);
        if(memberList.size()==0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        Member temp=memberList.get(0);
        List<AttendanceInterface> datalist=new ArrayList<>();
        //이름으로 찾은 회원정보와 시간 범위로 출석정보 조회
        List<Attendance> attendanceList=attendrepo.findAllByPersonalidAndExittimeBetweenOrderByEntertime(temp.getPersonalid(),startdate,enddate);
        if(attendanceList.size()>0){
            for(Attendance a:attendanceList){
                LocalDateTime temptime=a.getEntertime();
                //시간 변환
                int ent=temptime.getHour()*3600+temptime.getMinute()*60+temptime.getSecond();
                temptime=a.getExittime();
                int ext=temptime.getHour()*3600+temptime.getMinute()*60+temptime.getSecond();
                if(ext<ent)
                    ent=0;
                AttendanceInterface tempinterface=new AttendanceInterface(a.getPersonalid(),a.getRoomid(),ent,ext);
                datalist.add(tempinterface);
            }
            AttendanceDto jsonResult=new AttendanceDto(attendanceList.size(),datalist);
            Gson gson=new Gson();
            String i=gson.toJson(jsonResult);
            return i;
        }
        else{   //예외처리
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }
    /***************mac과 날짜를 통해 attendance 정보 출력*********************/
    @GetMapping(value = "/mac/{mac}/date/{date}",produces = "application/json")
    public String showbymacanddate(@PathVariable("mac") String mac, @PathVariable("date") String date) throws ParseException {
        //날짜 변환
        int year=Integer.parseInt(date.substring(0,4));
        int month=Integer.parseInt(date.substring(4,6));
        int day=Integer.parseInt(date.substring(6,8));
        //시간 범위 지정
        LocalDateTime startdate=LocalDateTime.of(year, month, day, 0,0,0);
        LocalDateTime enddate=LocalDateTime.of(year, month, day, 23,59,59);
        //mac주소로 기기 정보 조회
        Device device=devicerepo.findDeviceByMacaddr(mac);
        //기기의 personalid로 사용자 조회
        Member temp=userrepo.findMemberByPersonalid(device.getPersonal_id());
        List<AttendanceInterface> datalist=new ArrayList<>();
        //사용자 정보와 시간 범위로 출석 정보 조회
        List<Attendance> attendanceList=attendrepo.findAllByPersonalidAndExittimeBetweenOrderByEntertime(temp.getPersonalid(),startdate,enddate);
        if(attendanceList.size()>0){
            for(Attendance a:attendanceList){
                int ent;
                LocalDateTime temptime=a.getEntertime();
                //시간 변환
                ent=temptime.getHour()*3600+temptime.getMinute()*60+temptime.getSecond();
                temptime=a.getExittime();
                int ext=temptime.getHour()*3600+temptime.getMinute()*60+temptime.getSecond();
                if(a.getExittime().getDayOfYear() - a.getEntertime().getDayOfYear() > 0)
                    ent=0;
                AttendanceInterface tempinterface=new AttendanceInterface(a.getPersonalid(),a.getRoomid(),ent,ext);
                datalist.add(tempinterface);
            }
        }
        //사용자 정보와 시간 범위로 출석 정보 조회
        List<Attendance> attendanceListfiter=attendrepo.findAllByPersonalidAndEntertimeBetweenOrderByEntertime(temp.getPersonalid(),startdate,enddate);
        if(attendanceListfiter.size()>0 || attendanceList.size()>0) {
            for (Attendance a : attendanceListfiter) {
                if (a.getExittime().getDayOfYear() - a.getEntertime().getDayOfYear() > 0) {
                    LocalDateTime temptime = a.getEntertime();
                    //시간 변환
                    int ent = temptime.getHour() * 3600 + temptime.getMinute() * 60 + temptime.getSecond();
                    int ext = 86400;
                    AttendanceInterface tempinterface = new AttendanceInterface(a.getPersonalid(), a.getRoomid(), ent, ext);
                    datalist.add(tempinterface);
                }
            }
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        AttendancebymacDto jsonResult=new AttendancebymacDto(datalist.size(),datalist,temp.getDeviceList());
        Gson gson=new Gson();
        String i=gson.toJson(jsonResult);
        return i;
    }
    /***************이름 통해 device 정보 출력*********************/
    @GetMapping(value = "/getdevice/name/{name}",produces = "application/json")
    public String showdevicebyname(@PathVariable("name") String name){
        //unknown, deleted인 경우 예외처리
        if(name.equals("unknown")||name.equals("deleted")){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "entity not found"
            );
        }
        //이름으로 사용자 정보 조회
        List<Member> memberList =userrepo.findAllByName(name);
        if(memberList.size()==0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        Member member=memberList.get(0);
        Gson gson=new Gson();
        String i=gson.toJson(member);
        return i;
    }
    /***************날짜 통해 위치 정보 출력*********************/
    @GetMapping(value = "/map/date/{date}",produces = "application/json")
    public String showmapbydate(@PathVariable("date") String date) throws ParseException{
        //날짜, 시간 정보 파싱
        int year=Integer.parseInt(date.substring(0,4)); //2020
        int month=Integer.parseInt(date.substring(4,6)); //11
        int day=Integer.parseInt(date.substring(6,8)); //03
        int hour=Integer.parseInt(date.substring(8,10));//18
        int minute=Integer.parseInt(date.substring(10,12));//45
        //시간 범위 지정
        LocalDateTime enddate=LocalDateTime.of(year, month, day, hour,minute,0).plusMinutes(1);
        LocalDateTime startdate=LocalDateTime.of(year, month, day, hour,minute,0).minusMinutes(3);
        List<MapDto> mapDtoList=new ArrayList<>();
        //시간 범위로 출석 정보 조회
        List<Attendance> attendanceList=attendrepo.findAllByEntertimeBeforeAndExittimeAfter(startdate,startdate);
        List<Attendance> attendanceList1=attendrepo.findAllByEntertimeBetweenAndExittimeBetween(startdate,enddate,startdate,enddate);
        attendanceList.addAll(attendanceList1);
        //출석정보를 순회하며 반환할 정보 setting
        for(Attendance a:attendanceList){
            AttendancemapDto temp=new AttendancemapDto();
            //회원정보 조회
            PureMember tempmember=pureMemberrepo.findPureMemberByPersonalid(a.getPersonalid());
            //이름 예외처리
            if(!(tempmember.getName().equals("unknown")||tempmember.getName().equals("deleted"))){
                int count=0;
                temp.setMember(tempmember);
                temp.setAttinfo(a);
                List<Position> positionList;
                //device id와 시간으로 가장 최신 위치 정보 조회
                positionList=positionRepository.findTop1ByAttendtimeBetweenAndDeviceidOrderByIndexDesc(startdate,enddate,a.getDeviceid());
                if(positionList.size()>0){
                    temp.setPosition(positionList.get(0));
                }
                else{   //위치 정보 없는 경우
                    Position position=new Position(-1,-1,-1,-1,-1,null);
                    temp.setPosition(position);
                }
                //room id별로 나누기
                for(MapDto item:mapDtoList){ 
                    if(item.getRoomid()==a.getRoomid()){
                        item.addattendancemap(temp);
                        item.setCount(item.getCount()+1);
                        count++;
                        break;
                    }
                }
                if(count==0){
                    List<AttendancemapDto> members=new ArrayList<>();
                    members.add(temp);
                    MapDto newmapdto=new MapDto(a.getRoomid(),1,members);
                    mapDtoList.add(newmapdto);
                }
            }
        }
        Gson gson=new Gson();
        String i=gson.toJson(mapDtoList);
        return i;
    }
    /***************모든 date 정보 출력*********************/
    @GetMapping(value="/date/{date}", produces = "application/json")
    public String dateshow(@PathVariable("date") String date){
        //예외처리
        if(date.length()!=8){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "date format error"
            );
        }
        //날짜 파싱
        int year=Integer.parseInt(date.substring(0,4));
        int month=Integer.parseInt(date.substring(4,6));
        int day=Integer.parseInt(date.substring(6,8));
        //시간 범위 지정
        LocalDateTime startdate=LocalDateTime.of(year, month, day, 0,0,0);
        LocalDateTime enddate=LocalDateTime.of(year, month, day, 23,59,59);
        List<DateAttendanceDto> returnlist=new ArrayList<>();
        //시간 범위 내에 unknown이 아닌 것 조회
        List<Member> memberList=userrepo.findAllByNameIsNot("unknown");
        for(Member member: memberList){
            if(!member.getName().equals("deleted")) {
                DateAttendanceDto temp = new DateAttendanceDto();
                //기본 정보 setting
                temp.setName(member.getName());
                temp.setStatus(Boolean.FALSE);  //출석 여부
                temp.setId(member.getId());
                temp.setRoomdid(0);
                temp.setEntertime(86400);
                temp.setExittime(0);
                //시간 범위로 출석 정보 조회
                List<Attendance> attendanceList = attendrepo.findAllByExittimeBetweenAndPersonalid(startdate, enddate, member.getPersonalid());
                for (Attendance a : attendanceList) {
                    //시간 변환
                    int tempentertime = a.getEntertime().getHour() * 3600 + a.getEntertime().getMinute() * 60 + a.getEntertime().getSecond();
                    int tempexittime = a.getExittime().getHour() * 3600 + a.getExittime().getMinute() * 60 + a.getExittime().getSecond();
                    temp.setStatus(Boolean.TRUE);   //출석 여부
                    temp.setRoomdid(a.getRoomid());
                    if (temp.getEntertime() > tempentertime)
                        temp.setEntertime(tempentertime);
                    if (temp.getExittime() < tempexittime)
                        temp.setExittime(tempexittime);
                }
                returnlist.add(temp);
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
        LocalDateTime enddate=LocalDateTime.now();  //현재 시간
        LocalDateTime startdate=enddate.minusMinutes(60);
        if(time!=null){
            parsedtime=Integer.parseInt(time);
            //시간 정보 변환
            int hour=parsedtime/3600;
            parsedtime%=3600;
            int minute=parsedtime/60;
            int second=parsedtime%60;
            int year=Integer.parseInt(date.substring(0,4));
            int month=Integer.parseInt(date.substring(4,6));
            int day=Integer.parseInt(date.substring(6,8));
            //시간 범위 지정
            enddate=LocalDateTime.of(year, month, day, hour,minute,second);
            startdate=enddate.minusMinutes(60);
        }
        //시간정보로 출석 정보 조회
        List<AttendanceInterface> datalist=new ArrayList<>();
        List<Attendance> attendanceList=attendrepo.findAllByEntertimeBetweenOrderByEntertime(startdate,enddate);
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
