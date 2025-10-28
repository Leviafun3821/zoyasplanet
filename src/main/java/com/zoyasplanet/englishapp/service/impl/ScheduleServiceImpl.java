package com.zoyasplanet.englishapp.service.impl;

import com.zoyasplanet.englishapp.dto.ScheduleDTO;
import com.zoyasplanet.englishapp.entity.Payment;
import com.zoyasplanet.englishapp.entity.PaymentStatus;
import com.zoyasplanet.englishapp.exception.TaskNotFoundException;
import com.zoyasplanet.englishapp.exception.UserNotFoundException;
import com.zoyasplanet.englishapp.mapper.ScheduleMapper;
import com.zoyasplanet.englishapp.repository.PaymentRepository;
import com.zoyasplanet.englishapp.repository.ScheduleRepository;
import com.zoyasplanet.englishapp.repository.TaskRepository;
import com.zoyasplanet.englishapp.repository.UserRepository;
import com.zoyasplanet.englishapp.service.PaymentService;
import com.zoyasplanet.englishapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        var user = userRepository.findById(scheduleDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(scheduleDTO.getUserId()));
        var task = taskRepository.findById(scheduleDTO.getTaskId())
                .orElseThrow(() -> new TaskNotFoundException(scheduleDTO.getTaskId()));
        var schedule = ScheduleMapper.INSTANCE.toEntity(scheduleDTO);
        schedule.setUser(user);
        schedule.setTask(task);
        // teacherId опционально, оставляем null, если не указан
        schedule = scheduleRepository.save(schedule);
        return ScheduleMapper.INSTANCE.toDto(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDTO getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .map(ScheduleMapper.INSTANCE::toDto)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO) {
        var existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));
        ScheduleMapper.INSTANCE.updateEntity(existingSchedule, scheduleDTO);
        existingSchedule = scheduleRepository.save(existingSchedule);
        return ScheduleMapper.INSTANCE.toDto(existingSchedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void schedulePaymentReminders() {
        List<Payment> pendingPayments = paymentRepository.findByStatus(PaymentStatus.PENDING);

        for (Payment payment : pendingPayments) {
            paymentService.checkAndSendReminder(payment);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDTO> getSchedulesByUserId(Long userId) {
        return scheduleRepository.findByUserId(userId)
                .stream()
                .map(ScheduleMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

}
