package spring.hi_hello_spring.onboarding.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.aggregate.entity.File;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.onboarding.command.application.dto.CheckListUpdateDTO;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.CheckListStatus;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Checklist;
import spring.hi_hello_spring.onboarding.command.domain.repository.CheckListRepository;


@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void checkListUpdate(CheckListUpdateDTO checklistUpdateDTO) {
        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();

        CheckListStatus checkListStatus = CheckListStatus.builder()
                .checklistStatusSeq(checklistUpdateDTO.getChecklistStatusSeq())
                .employeeSeq(employeeSeq)
                .checklistSeq(checklistUpdateDTO.getChecklistStatusSeq())
                .listCheckedStatus(checklistUpdateDTO.getListCheckedStatus())
                .build();
        checkListRepository.save(checkListStatus);
    }
}
