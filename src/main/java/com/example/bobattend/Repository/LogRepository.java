package com.example.bobattend.Repository;

import com.example.bobattend.Entity.Device;
import com.example.bobattend.Entity.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log,Integer> {
    List<Log> findAllByDeviceid(int did);
}
