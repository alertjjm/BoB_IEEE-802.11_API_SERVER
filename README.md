# provider
Spring-boot backend for attendance-checker

    ├─java
    │  └─com
    │      └─example
    │          └─bobattend
    │              │  BobattendApplication.java
    │              │
    │              ├─Config
    │              │      SecurityConfig.java
    │              │
    │              ├─Controller
    │              │      AddController.java
    │              │      BasicController.java
    │              │      tokencontroller.java
    │              │
    │              ├─Dto
    │              │      AttendancebymacDto.java
    │              │      AttendancebynameDto.java
    │              │      AttendanceDto.java
    │              │      AttendanceInterface.java
    │              │      AttendancemapDto.java
    │              │      DateAttendanceDto.java
    │              │      DeviceDto.java
    │              │      DeviceLogDto.java
    │              │      MapDto.java
    │              │      MemberDto.java
    │              │      NameMonthDto.java
    │              │      UserListDto.java
    │              │
    │              ├─Entity
    │              │      Attendance.java
    │              │      Device.java
    │              │      Log.java
    │              │      Member.java
    │              │      Node.java
    │              │      Position.java
    │              │      PureMember.java
    │              │      Role.java
    │              │
    │              ├─Repository
    │              │      AttendanceRepository.java
    │              │      DeviceRepository.java
    │              │      LogRepository.java
    │              │      PositionRepository.java
    │              │      PureMemberRepository.java
    │              │      UserRepository.java
    │              │
    │              └─Service
    │                      MemberService.java
    │
    └─resources
        │  application.properties
        │  banner.txt
        │
        ├─templates
        └─     index.html
        
        
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https://github.com/alertjjm/provider)](https://hits.seeyoufarm.com) 
