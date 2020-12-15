package com.example.bobattend.Repository;

import com.example.bobattend.Entity.Attendance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends CrudRepository<Attendance,Integer> {
    //personal id와 exit time 범위로 모든 출석 찾아서 entertime으로 정렬하는 메소드
    List<Attendance> findAllByPersonalidAndExittimeBetweenOrderByEntertime(int id, LocalDateTime start, LocalDateTime end);
    //personal id와 enter time 범위로 모든 출석 찾아서 entertime으로 정렬하는 메소드
    List<Attendance> findAllByPersonalidAndEntertimeBetweenOrderByEntertime(int id, LocalDateTime start, LocalDateTime end);
    //enter time 범위로 모든 출석 찾아서 entertime으로 정렬하는 메소드
    List<Attendance> findAllByEntertimeBetweenOrderByEntertime(LocalDateTime start, LocalDateTime end);
    //personal id와 exit time 범위로 모든 출석 찾는 메소드
    List<Attendance> findAllByExittimeBetweenAndPersonalid(LocalDateTime start, LocalDateTime end, int pid);
    //personal id로 모든 출석 찾는 메소드
    List<Attendance> findAllByPersonalid(int id);
    //모든 출석 정보 찾는 메소드
    List<Attendance> findAll();
    //enter time과 exit time으로 범위 지정하여 모든 출석 정보 찾는 메소드
    List<Attendance> findAllByEntertimeBeforeAndExittimeAfter(LocalDateTime time1,LocalDateTime time2);
    //enter time과 exit time으로 범위 지정하여 모든 출석 정보 찾는 메소드
    List<Attendance> findAllByEntertimeBetweenAndExittimeBetween(LocalDateTime time1,LocalDateTime time2,LocalDateTime time3,LocalDateTime time4);
}
