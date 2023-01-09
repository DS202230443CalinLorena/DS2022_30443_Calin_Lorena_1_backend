package com.example.assignment1backend.rabbitmq;

import com.example.assignment1backend.repository.DeviceRepository;
import com.example.assignment1backend.repository.MessageRepository;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private DeviceRepository deviceRepository;

    private int counter = 0;
    private Double totalHourlyConsumption = 0.0;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(Message message) throws IOException, TimeoutException {
        //System.out.println(message);
        messageRepository.save(message);

        Timestamp timestamp = message.getTimestamp();
        Long deviceId = message.getDeviceId();
        Double measurementValue = message.getMeasurementValue();

        Optional<Device> currentDevice = deviceRepository.findById(deviceId);
        Double maximumHourlyEnergyConsumption = currentDevice.get().getMaximumHourlyEnergyConsumption();
        counter++;
        totalHourlyConsumption += measurementValue;
        if (counter > 6) {
            counter = 0;
            if(totalHourlyConsumption > maximumHourlyEnergyConsumption){
                totalHourlyConsumption = 0.0;
                simpMessagingTemplate.convertAndSend("/topic/message",
                        new TextMessageDto(timestamp + ": Your device with id: " + currentDevice.get().getDeviceId() + " has an hourly consumption greater than the fixed threshold = " + currentDevice.get().getMaximumHourlyEnergyConsumption(), currentDevice.get().getUser().getUserId(), currentDevice.get().getDeviceId(), timestamp));
                System.out.println(timestamp + ": Your device with id: " + currentDevice.get().getDeviceId() + " has an hourly consumption greater than the fixed threshold = " + currentDevice.get().getMaximumHourlyEnergyConsumption());
                logger.info(timestamp + ": Your device with id: " + currentDevice.get().getDeviceId() + " has an hourly consumption greater than the fixed threshold = " + currentDevice.get().getMaximumHourlyEnergyConsumption());
            }
        }
    }
}