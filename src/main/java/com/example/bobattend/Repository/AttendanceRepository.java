package com.example.bobattend.Repository;

import com.example.bobattend.Entity.Attendance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends CrudRepository<Attendance,Integer> {
    List<Attendance> findAllByPersonalidAndExittimeBetweenOrderByEntertime(int id, LocalDateTime start, LocalDateTime end);
    List<Attendance> findAllByPersonalidAndEntertimeBetweenOrderByEntertime(int id, LocalDateTime start, LocalDateTime end);
    List<Attendance> findAllByEntertimeBetweenOrderByEntertime(LocalDateTime start, LocalDateTime end);
    List<Attendance> findAllByExittimeBetween(LocalDateTime start, LocalDateTime end);
    List<Attendance> findAllByExittimeBetweenOrderByPersonalid(LocalDateTime start, LocalDateTime end);
    List<Attendance> findAllByExittimeBetweenAndPersonalid(LocalDateTime start, LocalDateTime end, int pid);
    List<Attendance> findAllByPersonalid(int id);
    List<Attendance> findAll();
}
