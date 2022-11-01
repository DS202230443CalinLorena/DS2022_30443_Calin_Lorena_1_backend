package com.example.assignment1backend.controller;

import com.example.assignment1backend.dto.DeviceDto;
import com.example.assignment1backend.exception.ResourceNotFoundEmailException;
import com.example.assignment1backend.exception.ResourceNotFoundException;
import com.example.assignment1backend.model.Device;
import com.example.assignment1backend.model.User;
import com.example.assignment1backend.repository.DeviceRepository;
import com.example.assignment1backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/devices")
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @PostMapping("/device")
    public Device createDevice(@RequestBody DeviceDto deviceDto) {
        User user = userRepository.findByEmail(deviceDto.getUserEmail())
                .orElseThrow(() -> new ResourceNotFoundEmailException(deviceDto.getUserEmail()));
        Device device = new Device();
        device.setAddress(deviceDto.getAddress());
        device.setDescription(deviceDto.getDescription());
        device.setMaximumHourlyEnergyConsumption(Integer.valueOf(deviceDto.getMaximumHourlyEnergyConsumption()));
        device.setUser(user);
        user.getDevices().add(device);
        return deviceRepository.save(device);
    }

    @GetMapping("/device/{id}")
    public Device getDeviceById(@PathVariable Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PutMapping("/device/{id}")
    public Device updateDevice(@RequestBody Device newDevice, @PathVariable Long id) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setAddress(newDevice.getAddress());
                    device.setDescription(newDevice.getDescription());
                    device.setMaximumHourlyEnergyConsumption(newDevice.getMaximumHourlyEnergyConsumption());
                    return deviceRepository.save(device);
                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @DeleteMapping("/device/{id}")
    public String deleteDevice(@PathVariable Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        deviceRepository.delete(device);
        return "Device with id = " + id + " was deleted.";
    }
}
