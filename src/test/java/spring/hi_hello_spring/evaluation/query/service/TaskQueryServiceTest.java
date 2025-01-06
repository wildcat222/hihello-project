package spring.hi_hello_spring.evaluation.query.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.evaluation.query.dto.*;
import spring.hi_hello_spring.evaluation.query.mapper.TaskQueryMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskQueryServiceTest {

    @Mock
    private TaskQueryMapper taskQueryMapper;

    @Mock
    private CustomUserUtils customUserUtils;

    @InjectMocks
    private TaskQueryService taskQueryService;

    @Test
    void getHrAllTaskList_shouldReturnTaskList() {
        // Given
        TaskAllListQueryDTO task1 = new TaskAllListQueryDTO();
        TaskAllListQueryDTO task2 = new TaskAllListQueryDTO();
        List<TaskAllListQueryDTO> expectedTaskList = Arrays.asList(task1, task2);

        when(taskQueryMapper.findHrAllTask()).thenReturn(expectedTaskList);

        // When
        List<TaskAllListQueryDTO> result = taskQueryService.getHrAllTaskList();

        // Then
        verify(taskQueryMapper, times(1)).findHrAllTask();
        assert result.size() == 2;
    }

    @Test
    void getMentorTaskDetail_shouldReturnTaskDetail() {
        // Given
        Long taskSeq = 123L;
        Long mentorSeq = 100L; // Mocked mentor sequence
        TaskMentorDetailQueryDTO taskMentorDetail = new TaskMentorDetailQueryDTO();
        List<TaskMentorDetailQueryDTO> expectedTaskDetailList = Arrays.asList(taskMentorDetail);

        try (var mock = mockStatic(CustomUserUtils.class)) {
            mock.when(CustomUserUtils::getCurrentEmployeeSeq).thenReturn(mentorSeq);

            Map<String, Object> params = new HashMap<>();
            params.put("employee_seq", mentorSeq);
            params.put("task_seq", taskSeq);
            when(taskQueryMapper.findMentorTaskDetail(params)).thenReturn(expectedTaskDetailList);

            // When
            List<TaskMentorDetailQueryDTO> result = taskQueryService.getMentorTaskDetail(taskSeq);

            // Then
            verify(taskQueryMapper, times(1)).findMentorTaskDetail(params);
            assert result.size() == 1;
        }
    }

    @Test
    void getMentorAllTaskList_shouldReturnTaskList() {
        // Given
        Long mentorSeq = 100L; // Mocked mentor sequence
        TaskAllListQueryDTO task = new TaskAllListQueryDTO();
        List<TaskAllListQueryDTO> expectedTaskList = Arrays.asList(task);


        try (var mock = mockStatic(CustomUserUtils.class)) {

            mock.when(CustomUserUtils::getCurrentEmployeeSeq).thenReturn(mentorSeq);


            when(taskQueryMapper.findMentorAllTask(mentorSeq)).thenReturn(expectedTaskList);

            // When
            List<TaskAllListQueryDTO> result = taskQueryService.getMentorAllTaskList();

            // Then
            verify(taskQueryMapper, times(1)).findMentorAllTask(mentorSeq);
            assert result.size() == 1;
        }
    }

    @Test
    void getMenteeTaskDetail_shouldReturnMenteeTaskDetail() {
        // Given
        Long taskSeq = 1L;
        Long employeeSeq = 100L;
        TaskMenteeDetailQueryDTO menteeDetail = new TaskMenteeDetailQueryDTO();
        List<TaskMenteeDetailQueryDTO> expectedMenteeDetails = List.of(menteeDetail);


        try (var mock = mockStatic(CustomUserUtils.class)) {

            mock.when(CustomUserUtils::getCurrentEmployeeSeq).thenReturn(employeeSeq);

            Map<String, Object> params = new HashMap<>();
            params.put("employee_seq", employeeSeq);
            params.put("task_seq", taskSeq);
            when(taskQueryMapper.findMenteeTaskDetail(params)).thenReturn(expectedMenteeDetails);

            // When
            List<TaskMenteeDetailQueryDTO> result = taskQueryService.getMenteeTaskDetail(taskSeq);

            // Then
            verify(taskQueryMapper, times(1)).findMenteeTaskDetail(params);
            assert result.size() == 1;
        }
    }

    @Test
    void getSearchTask_shouldReturnSearchResult() {
        // Given
        String searchQuery = "test";
        TaskSearchQueryDTO taskSearchResult = new TaskSearchQueryDTO();
        List<TaskSearchQueryDTO> expectedSearchResults = List.of(taskSearchResult);

        when(taskQueryMapper.findSearchTask(searchQuery)).thenReturn(expectedSearchResults);

        // When
        List<TaskSearchQueryDTO> result = taskQueryService.getSearchTask(searchQuery);

        // Then
        verify(taskQueryMapper, times(1)).findSearchTask(searchQuery);
        assert result.size() == 1;
    }

    @Test
    void getTaskGroupPartner_shouldReturnTaskGroupPartners() {
        // Given
        Long taskGroupSeq = 1L;
        TaskGroupPartnerQueryDTO taskGroupPartner = new TaskGroupPartnerQueryDTO();
        List<TaskGroupPartnerQueryDTO> expectedPartners = List.of(taskGroupPartner);

        when(taskQueryMapper.findTaskGroupPartner(taskGroupSeq)).thenReturn(expectedPartners);

        // When
        List<TaskGroupPartnerQueryDTO> result = taskQueryService.getTaskGroupPartner(taskGroupSeq);

        // Then
        verify(taskQueryMapper, times(1)).findTaskGroupPartner(taskGroupSeq);
        assert result.size() == 1;
    }

    @Test
    void getTaskDetail_shouldReturnTaskDetail() {
        // Given
        Long taskSeq = 1L;
        TaskDetailQueryDTO taskDetail = new TaskDetailQueryDTO();
        List<TaskDetailQueryDTO> expectedTaskDetails = List.of(taskDetail);

        when(taskQueryMapper.findTaskDetail(taskSeq)).thenReturn(expectedTaskDetails);

        // When
        List<TaskDetailQueryDTO> result = taskQueryService.getTaskDetail(taskSeq);

        // Then
        verify(taskQueryMapper, times(1)).findTaskDetail(taskSeq);
        assert result.size() == 1;
    }
}
