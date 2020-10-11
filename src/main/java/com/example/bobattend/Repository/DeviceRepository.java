package com.example.bobattend.Repository;

import com.example.bobattend.Entity.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DeviceRepository extends CrudRepository<Device,Integer> {
    Device findDeviceByMacaddr(String mac);
    @Transactional
    public void deleteDeviceByMacaddr(String mac);
}
