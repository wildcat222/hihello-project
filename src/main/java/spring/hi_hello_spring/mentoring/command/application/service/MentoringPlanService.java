package spring.hi_hello_spring.mentoring.command.application.service;

import com.sun.jna.platform.win32.WinDef;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.aggregate.entity.File;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.repository.FileRepository;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.mentoring.command.application.dto.MentoringPlanRequestDTO;
import spring.hi_hello_spring.mentoring.command.application.dto.MentoringPlanUpdateDTO;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.Planning;
import spring.hi_hello_spring.mentoring.command.domain.aggregate.entity.PlanningStatus;
import spring.hi_hello_spring.mentoring.command.domain.repository.PlanningRepository;
import spring.hi_hello_spring.notify.command.application.service.NotifyService;

import static spring.hi_hello_spring.notify.command.domain.aggregate.entity.NotiType.*;

@Service
@RequiredArgsConstructor
public class MentoringPlanService {

    private final PlanningRepository planningRepository;
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final NotifyService notifyService;

    @Transactional
    public void createMentoringPlan(MentoringPlanRequestDTO mentoringPlanRequestDTO, String uploadFile) {

        Planning planning = Planning.builder()
                .employeeSeq(mentoringPlanRequestDTO.getEmployeeSeq())
                .planningName(mentoringPlanRequestDTO.getPlanningName())
                .planningContent(mentoringPlanRequestDTO.getPlanningContent())
                .build();
        Planning savedPlanning = planningRepository.save(planning);

        File file = File.builder()
                .planningSeq(savedPlanning.getPlanningSeq())
                .fileName(mentoringPlanRequestDTO.getFileName())
                .fileUrl(uploadFile)
                .build();
        fileRepository.save(file);

        Long senderSeq = mentoringPlanRequestDTO.getEmployeeSeq();
        Employee sender = employeeRepository.findByEmployeeSeq(senderSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        Employee receiver = employeeRepository.findByDepartmentSeqAndPositionSeq(sender.getDepartmentSeq(), 1L)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        notifyService.send(sender, receiver, WRITTEN_PLANER_BY_MENTOR, "/mentoring/planning/" + savedPlanning.getPlanningSeq());
    }

    @Transactional
    public void deleteMentoringPlan(Long planningSeq) {

        Planning savedPlanning = planningRepository.findById(planningSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        fileRepository.deleteByPlanningSeq(planningSeq);
        planningRepository.delete(savedPlanning);
    }

    public void modifyMentoringPlan(Long planningSeq, MentoringPlanUpdateDTO mentoringPlanUpdateDTO) {

        Planning modifyPlanning = planningRepository.findById(planningSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        modelMapper.map(mentoringPlanUpdateDTO, modifyPlanning);

        planningRepository.save(modifyPlanning);

        Long senderSeq = CustomUserUtils.getCurrentEmployeeSeq();
        Employee sender = employeeRepository.findByEmployeeSeq(senderSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        Employee receiver = employeeRepository.findByEmployeeSeq(modifyPlanning.getEmployeeSeq())
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        PlanningStatus planningStatus = mentoringPlanUpdateDTO.getPlanningStatus();
        if (planningStatus == PlanningStatus.APPROVE) {
            notifyService.send(sender, receiver, ALLOW_PLANER_BY_LEADER, "/mentoring/planning/" + planningSeq);
        } else {
            notifyService.send(sender, receiver, REJECT_PLANER_BY_LEADER, "/mentoring/planning/" + planningSeq);
        }
    }
}
