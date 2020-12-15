package com.example.bobattend.Repository;

import com.example.bobattend.Entity.Attendance;
import com.example.bobattend.Entity.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PositionRepository extends CrudRepository<Position,Integer> {
    //출석 시간과 device id로 index 내림차순으로 위치 정보 찾는 메소드
    public List<Position> findTop1ByAttendtimeBetweenAndDeviceidOrderByIndexDesc(LocalDateTime start, LocalDateTime end, int deviceid);
}
