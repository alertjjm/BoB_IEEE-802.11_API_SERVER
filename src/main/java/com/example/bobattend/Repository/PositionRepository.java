package com.example.bobattend.Repository;

import com.example.bobattend.Entity.Attendance;
import com.example.bobattend.Entity.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PositionRepository extends CrudRepository<Position,Integer> {
    public List<Position> findTop1ByAttendtimeBetweenAndPersonalidOrderByAttendtimeDesc(LocalDateTime start, LocalDateTime end, int personalid);
    public List<Position> findTop1ByAttendtimeBetweenAndDeviceidOrderByIndexDesc(LocalDateTime start, LocalDateTime end, int deviceid);

}
