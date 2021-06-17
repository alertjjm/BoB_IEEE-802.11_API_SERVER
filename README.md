# provider
Spring-boot backend for attendance-checker

### Backend - Spring Boot(REST API)

## 구조도
### 1. System(Core)
![BoB 0x10_system structure](https://user-images.githubusercontent.com/46064193/122314619-78272a80-cf53-11eb-9a44-3a5f3b2382d1.png)
### 2. Web Service
![image](https://user-images.githubusercontent.com/46064193/122314800-d0f6c300-cf53-11eb-9664-01f4898fd663.png)

## 메인 화면
![image](https://user-images.githubusercontent.com/46064193/122314197-af490c00-cf52-11eb-8d7d-0f8922bfb6d0.png)

## 시간 정보 확인
![image](https://user-images.githubusercontent.com/46064193/122314070-6ee98e00-cf52-11eb-83eb-72d57c8752cb.png)

## 위치 정보 확인
![image](https://user-images.githubusercontent.com/46064193/122314275-e0294100-cf52-11eb-9e5c-1ad1917a843d.png)

## 날짜별 검색
![image](https://user-images.githubusercontent.com/46064193/122314392-0f3fb280-cf53-11eb-9ce7-c11b23a0107f.png)

## MAC 주소별 검색
![image](https://user-images.githubusercontent.com/46064193/122314475-39917000-cf53-11eb-8c1b-ccc6e6bf9fcc.png)

## 전체 기기(미등록 포함) 확인
![image](https://user-images.githubusercontent.com/46064193/122314440-27afcd00-cf53-11eb-8e6e-8408f1411281.png)

## 위치 측정 시연
![image](https://user-images.githubusercontent.com/46064193/122313887-039fbc00-cf52-11eb-8a05-0ba825997585.png)


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
