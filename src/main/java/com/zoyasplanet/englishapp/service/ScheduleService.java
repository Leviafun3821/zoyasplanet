package com.zoyasplanet.englishapp.service;

import com.zoyasplanet.englishapp.dto.ScheduleDTO;
import java.util.List;

public interface ScheduleService {
    ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);
    ScheduleDTO getScheduleById(Long id);
    List<ScheduleDTO> getAllSchedules();
    ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO);
    void deleteSchedule(Long id);

    // Новый метод для автоматизации напоминаний о платеже
    void schedulePaymentReminders();

    // Новый метод
    List<ScheduleDTO> getSchedulesByUserId(Long userId);
}
