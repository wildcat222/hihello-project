package spring.hi_hello_spring.onboarding.command.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.onboarding.command.application.dto.CheckListUpdateDTO;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.CheckListStatus;
import spring.hi_hello_spring.onboarding.command.domain.repository.CheckListStatusRepository;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CheckListServiceTest {

    @Mock
    private CheckListStatusRepository checkListStatusRepository;

    @Mock
    private CustomUserUtils customUserUtils;

    @InjectMocks
    private CheckListService checkListService;

    @Test
    void checkListUpdate_shouldSaveCheckListStatus() {
        // Given
        Long employeeSeq = 100L;
        Long checklistSeq = 1L;
        Long checklistStatusSeq = 10L;
        Boolean listCheckedStatus = true;

        CheckListUpdateDTO checklistUpdateDTO = new CheckListUpdateDTO();
        checklistUpdateDTO.setChecklistSeq(checklistSeq);
        checklistUpdateDTO.setChecklistStatusSeq(checklistStatusSeq);
        checklistUpdateDTO.setListCheckedStatus(listCheckedStatus);

        // When
        checkListService.checkListUpdate(checklistUpdateDTO);

        // Then
        Mockito.verify(checkListStatusRepository, Mockito.times(1)).save(any(CheckListStatus.class));
    }
}