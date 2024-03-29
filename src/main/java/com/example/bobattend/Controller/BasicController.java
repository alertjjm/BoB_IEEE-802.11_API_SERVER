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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class BasicController {
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

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/all", produces = "application/json")
    public String showall() {
        List<Member> memberList;
        memberList = userrepo.findAllByPersonalidBetween(0, 30);
        UserListDto jsonResult = new UserListDto(memberList.size(), memberList);
        Gson gson = new Gson();
        String i = gson.toJson(jsonResult);
        return i;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/name/{name}", produces = "application/json")
    public String showbyusername(@PathVariable("name") String name) {
        if (name.equals("unknown") || name.equals("deleted")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "entity not found"
            );
        }
        List<Member> memberList = userrepo.findAllByName(name);
        List<Attendance> attendanceList = attendrepo.findAll();
        if (memberList.size() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        List<AttendancebynameDto> alist = new ArrayList<>();
        for (Member u : memberList) {
            AttendancebynameDto atemp = new AttendancebynameDto();
            atemp.setId(u.getId());
            atemp.setName(u.getName());
            atemp.setPersonalid(u.getPersonalid());
            alist.add(atemp);
        }
        for (AttendancebynameDto u : alist) {
            HashSet<String> dateset = new HashSet<>();
            for (Attendance a : attendanceList) {
                if (a.getPersonalid() == u.getPersonalid()) {
                    String date = a.getEntertime().format(DateTimeFormatter.BASIC_ISO_DATE);
                    u.setRoomid(a.getRoomid());
                    dateset.add(date);
                }
            }
            ArrayList<String> datelist = new ArrayList<>(dateset);
            datelist.sort(null);
            u.setDatelist(datelist);
        }
        Gson gson = new Gson();
        String i = gson.toJson(alist);
        return i;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/name/{name}/month/{ymonth}", produces = "application/json")
    public String showbyusernamemonth(@PathVariable("name") String name, @PathVariable("ymonth") String ymonth) {
        if (name.equals("unknown") || name.equals("deleted")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "entity not found"
            );
        }
        List<Member> memberList = userrepo.findAllByName(name);
        if (memberList.size() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        int year = Integer.parseInt(ymonth.substring(0, 4));
        int month = Integer.parseInt(ymonth.substring(4, 6));
        LocalDateTime startdate = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime enddate = LocalDateTime.of(year, month, 1, 0, 0, 0).plusMonths(1);
        List<Attendance> attendanceList = attendrepo.findAllByPersonalidAndExittimeBetweenOrderByEntertime(memberList.get(0).getPersonalid(), startdate, enddate);
        List<NameMonthDto> alist = new ArrayList<>();
        int index = 0;
        for (index = 0; index < attendanceList.size(); index++) {
            Attendance a = attendanceList.get(index);
            String date = Integer.toString(a.getEntertime().getYear() * 10000 + a.getEntertime().getMonthValue() * 100 + a.getEntertime().getDayOfMonth());
            int entertime = a.getEntertime().getHour() * 3600 + a.getEntertime().getMinute() * 60 + a.getEntertime().getSecond();
            int exittime = a.getExittime().getHour() * 3600 + a.getExittime().getMinute() * 60 + a.getExittime().getSecond();
            if (alist.size() > 0 && alist.get(alist.size() - 1).getDate().equals(date)) {
                if (alist.get(alist.size() - 1).getEntertime() > entertime)
                    alist.get(alist.size() - 1).setEntertime(entertime);
                if (alist.get(alist.size() - 1).getExittime() < exittime)
                    alist.get(alist.size() - 1).setExittime(exittime);
                if (exittime < entertime)
                    exittime += 24 * 3600;
                alist.get(alist.size() - 1).addtime(exittime - entertime);
            } else {
                NameMonthDto temp = new NameMonthDto(date, a.getRoomid(), entertime, exittime, exittime - entertime);
                if (exittime < entertime)
                    temp.addtime(24 * 3600);
                alist.add(temp);
            }
        }
        Gson gson = new Gson();
        String i = gson.toJson(alist);
        return i;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public String showbyuserid(@PathVariable("id") String id) {
        Member member = userrepo.findById(id);
        if (member == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        Gson gson = new Gson();
        String i = gson.toJson(member);
        return i;
    }

    @GetMapping(value = "/mac/{mac}", produces = "application/json")
    public String showbymac(@PathVariable("mac") String mac) {
        Device device = devicerepo.findDeviceByMacaddr(mac.toLowerCase());
        if (device == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        List<Log> logList = logrepo.findAllByDeviceid(device.getDeviceid());
        DeviceLogDto data = new DeviceLogDto();
        data.setCount(logList.size());
        data.setDevice(device);
        data.setLoglist(logList);
        Gson gson = new Gson();
        String i = gson.toJson(data);
        return i;
    }

    @GetMapping(value = "/{id}/{date}", produces = "application/json")
    public String showattendancebyid(@PathVariable("id") String id, @PathVariable("date") String date) throws ParseException {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        LocalDateTime startdate = LocalDateTime.of(year, month, day, 0, 0, 0);
        LocalDateTime enddate = LocalDateTime.of(year, month, day, 23, 59, 59);
        Member temp = userrepo.findById(id);
        List<AttendanceInterface> datalist = new ArrayList<>();
        List<Attendance> attendanceList = attendrepo.findAllByPersonalidAndExittimeBetweenOrderByEntertime(temp.getPersonalid(), startdate, enddate);
        if (attendanceList.size() > 0) {
            for (Attendance a : attendanceList) {
                LocalDateTime temptime = a.getEntertime();
                int ent = temptime.getHour() * 3600 + temptime.getMinute() * 60 + temptime.getSecond();
                temptime = a.getExittime();
                int ext = temptime.getHour() * 3600 + temptime.getMinute() * 60 + temptime.getSecond();
                if (ext < ent)
                    ext = 3600 * 24;
                AttendanceInterface tempinterface = new AttendanceInterface(a.getPersonalid(), a.getRoomid(), ent, ext);
                datalist.add(tempinterface);
            }
            AttendanceDto jsonResult = new AttendanceDto(attendanceList.size(), datalist);
            Gson gson = new Gson();
            String i = gson.toJson(jsonResult);
            return i;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    @GetMapping(value = "/name/{name}/date/{date}", produces = "application/json")
    public String showbynameanddate(@PathVariable("name") String name, @PathVariable("date") String date) throws ParseException {
        if (name.equals("unknown") || name.equals("deleted")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "entity not found"
            );
        }
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        LocalDateTime startdate = LocalDateTime.of(year, month, day, 0, 0, 0);
        LocalDateTime enddate = LocalDateTime.of(year, month, day, 23, 59, 59);
        List<Member> memberList = userrepo.findAllByName(name);
        if (memberList.size() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        Member temp = memberList.get(0);
        List<AttendanceInterface> datalist = new ArrayList<>();
        List<Attendance> attendanceList = attendrepo.findAllByPersonalidAndExittimeBetweenOrderByEntertime(temp.getPersonalid(), startdate, enddate);
        if (attendanceList.size() > 0) {
            for (Attendance a : attendanceList) {
                LocalDateTime temptime = a.getEntertime();
                int ent = temptime.getHour() * 3600 + temptime.getMinute() * 60 + temptime.getSecond();
                temptime = a.getExittime();
                int ext = temptime.getHour() * 3600 + temptime.getMinute() * 60 + temptime.getSecond();
                if (ext < ent)
                    ent = 0;
                AttendanceInterface tempinterface = new AttendanceInterface(a.getPersonalid(), a.getRoomid(), ent, ext);
                datalist.add(tempinterface);
            }
            AttendanceDto jsonResult = new AttendanceDto(attendanceList.size(), datalist);
            Gson gson = new Gson();
            String i = gson.toJson(jsonResult);
            return i;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    @GetMapping(value = "/mac/{mac}/date/{date}", produces = "application/json")
    public String showbymacanddate(@PathVariable("mac") String mac, @PathVariable("date") String date) throws ParseException {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        LocalDateTime startdate = LocalDateTime.of(year, month, day, 0, 0, 0);
        LocalDateTime enddate = LocalDateTime.of(year, month, day, 23, 59, 59);
        Device device = devicerepo.findDeviceByMacaddr(mac);
        Member temp = userrepo.findMemberByPersonalid(device.getPersonal_id());
        List<AttendanceInterface> datalist = new ArrayList<>();
        List<Attendance> attendanceList = attendrepo.findAllByPersonalidAndExittimeBetweenOrderByEntertime(temp.getPersonalid(), startdate, enddate);
        if (attendanceList.size() > 0) {
            for (Attendance a : attendanceList) {
                int ent;
                LocalDateTime temptime = a.getEntertime();
                ent = temptime.getHour() * 3600 + temptime.getMinute() * 60 + temptime.getSecond();
                temptime = a.getExittime();
                int ext = temptime.getHour() * 3600 + temptime.getMinute() * 60 + temptime.getSecond();
                if (a.getExittime().getDayOfYear() - a.getEntertime().getDayOfYear() > 0)
                    ent = 0;
                AttendanceInterface tempinterface = new AttendanceInterface(a.getPersonalid(), a.getRoomid(), ent, ext);
                datalist.add(tempinterface);
            }
        }
        List<Attendance> attendanceListfiter = attendrepo.findAllByPersonalidAndEntertimeBetweenOrderByEntertime(temp.getPersonalid(), startdate, enddate);
        if (attendanceListfiter.size() > 0 || attendanceList.size() > 0) {
            for (Attendance a : attendanceListfiter) {
                if (a.getExittime().getDayOfYear() - a.getEntertime().getDayOfYear() > 0) {
                    LocalDateTime temptime = a.getEntertime();
                    int ent = temptime.getHour() * 3600 + temptime.getMinute() * 60 + temptime.getSecond();
                    int ext = 86400;
                    AttendanceInterface tempinterface = new AttendanceInterface(a.getPersonalid(), a.getRoomid(), ent, ext);
                    datalist.add(tempinterface);
                }
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        AttendancebymacDto jsonResult = new AttendancebymacDto(datalist.size(), datalist, temp.getDeviceList());
        Gson gson = new Gson();
        String i = gson.toJson(jsonResult);
        return i;
    }

    @GetMapping(value = "/getdevice/name/{name}", produces = "application/json")
    public String showdevicebyname(@PathVariable("name") String name) {
        if (name.equals("unknown") || name.equals("deleted")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "entity not found"
            );
        }
        List<Member> memberList = userrepo.findAllByName(name);
        if (memberList.size() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        Member member = memberList.get(0);
        Gson gson = new Gson();
        String i = gson.toJson(member);
        return i;
    }

    @GetMapping(value = "/map/date/{date}", produces = "application/json")
    public String showmapbydate(@PathVariable("date") String date) throws ParseException {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        int hour = Integer.parseInt(date.substring(8, 10));
        int minute = Integer.parseInt(date.substring(10, 12));
        LocalDateTime enddate = LocalDateTime.of(year, month, day, hour, minute, 0).plusMinutes(1);
        LocalDateTime startdate = LocalDateTime.of(year, month, day, hour, minute, 0).minusMinutes(3);
        List<MapDto> mapDtoList = new ArrayList<>();
        List<Attendance> attendanceList = attendrepo.findAllByEntertimeBeforeAndExittimeAfter(startdate, startdate);
        List<Attendance> attendanceList1 = attendrepo.findAllByEntertimeBetweenAndExittimeBetween(startdate, enddate, startdate, enddate);
        attendanceList.addAll(attendanceList1);
        for (Attendance a : attendanceList) {
            AttendancemapDto temp = new AttendancemapDto();
            PureMember tempmember = pureMemberrepo.findPureMemberByPersonalid(a.getPersonalid());
            if (!(tempmember.getName().equals("unknown") || tempmember.getName().equals("deleted"))) {
                int count = 0;
                temp.setMember(tempmember);
                temp.setAttinfo(a);
                List<Position> positionList;
                positionList = positionRepository.findTop1ByAttendtimeBetweenAndDeviceidOrderByIndexDesc(startdate, enddate, a.getDeviceid());
                if (positionList.size() > 0) {
                    temp.setPosition(positionList.get(0));
                } else {
                    Position position = new Position(-1, -1, -1, -1, -1, null);
                    temp.setPosition(position);
                }
                for (MapDto item : mapDtoList) {
                    if (item.getRoomid() == a.getRoomid()) {
                        item.addattendancemap(temp);
                        item.setCount(item.getCount() + 1);
                        count++;
                        break;
                    }
                }
                if (count == 0) {
                    List<AttendancemapDto> members = new ArrayList<>();
                    members.add(temp);
                    MapDto newmapdto = new MapDto(a.getRoomid(), 1, members);
                    mapDtoList.add(newmapdto);
                }
            }
        }
        Gson gson = new Gson();
        String i = gson.toJson(mapDtoList);
        return i;
    }

    @GetMapping(value = "/date/{date}", produces = "application/json")
    public String dateshow(@PathVariable("date") String date) {
        if (date.length() != 8) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "date format error"
            );
        }
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        LocalDateTime startdate = LocalDateTime.of(year, month, day, 0, 0, 0);
        LocalDateTime enddate = LocalDateTime.of(year, month, day, 23, 59, 59);
        List<DateAttendanceDto> returnlist = new ArrayList<>();
        List<Member> memberList = userrepo.findAllByNameIsNot("unknown");
        for (Member member : memberList) {
            if (!member.getName().equals("deleted")) {
                DateAttendanceDto temp = new DateAttendanceDto();
                temp.setName(member.getName());
                temp.setStatus(Boolean.FALSE);
                temp.setId(member.getId());
                temp.setRoomdid(0);
                temp.setEntertime(86400);
                temp.setExittime(0);
                List<Attendance> attendanceList = attendrepo.findAllByExittimeBetweenAndPersonalid(startdate, enddate, member.getPersonalid());
                for (Attendance a : attendanceList) {
                    int tempentertime = a.getEntertime().getHour() * 3600 + a.getEntertime().getMinute() * 60 + a.getEntertime().getSecond();
                    int tempexittime = a.getExittime().getHour() * 3600 + a.getExittime().getMinute() * 60 + a.getExittime().getSecond();
                    temp.setStatus(Boolean.TRUE);
                    temp.setRoomdid(a.getRoomid());
                    if (temp.getEntertime() > tempentertime)
                        temp.setEntertime(tempentertime);
                    if (temp.getExittime() < tempexittime)
                        temp.setExittime(tempexittime);
                }
                returnlist.add(temp);
            }
        }
        Gson gson = new Gson();
        String i = gson.toJson(returnlist);
        return i;
    }

    @GetMapping(value = "/timelist", produces = "application/json")
    public String timeshow(@RequestParam(value = "time", required = false) String time, @RequestParam(value = "date", required = false) String date) {
        int parsedtime;
        LocalDateTime enddate = LocalDateTime.now();
        LocalDateTime startdate = enddate.minusMinutes(60);
        if (time != null) {
            parsedtime = Integer.parseInt(time);
            int hour = parsedtime / 3600;
            parsedtime %= 3600;
            int minute = parsedtime / 60;
            int second = parsedtime % 60;
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6, 8));
            enddate = LocalDateTime.of(year, month, day, hour, minute, second);
            startdate = enddate.minusMinutes(60);
        }
        List<AttendanceInterface> datalist = new ArrayList<>();
        List<Attendance> attendanceList = attendrepo.findAllByEntertimeBetweenOrderByEntertime(startdate, enddate);
        if (attendanceList.size() > 0) {
            for (Attendance a : attendanceList) {
                LocalDateTime temptime = a.getEntertime();
                int ent = temptime.getHour() * 3600 + temptime.getMinute() * 60 + temptime.getSecond();
                temptime = a.getExittime();
                int ext = temptime.getHour() * 3600 + temptime.getMinute() * 60 + temptime.getSecond();
                AttendanceInterface tempinterface = new AttendanceInterface(a.getPersonalid(), a.getRoomid(), ent, ext);
                datalist.add(tempinterface);
            }
            AttendanceDto jsonResult = new AttendanceDto(attendanceList.size(), datalist);
            Gson gson = new Gson();
            String i = gson.toJson(jsonResult);
            return i;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }
}
