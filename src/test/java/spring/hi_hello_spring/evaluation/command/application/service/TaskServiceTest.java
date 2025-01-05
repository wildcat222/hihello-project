package spring.hi_hello_spring.evaluation.command.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.repository.FileRepository;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskCreateDTO;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskSubmitDTO;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskUpdateDTO;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.Task;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskSubmit;
import spring.hi_hello_spring.evaluation.command.domain.aggregate.entity.TaskType;
import spring.hi_hello_spring.evaluation.command.domain.repository.TaskRepository;
import spring.hi_hello_spring.common.aggregate.entity.File;
import spring.hi_hello_spring.evaluation.command.domain.service.EvalListDomainService;
import spring.hi_hello_spring.group.command.application.service.GroupMatchService;
import spring.hi_hello_spring.onboarding.command.domain.repository.TemplateRepository;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TemplateRepository templateRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private GroupMatchService groupMatchService;

    @Mock
    private EvalListDomainService evalListDomainService;

    @InjectMocks
    private TaskService taskService;

    @Test
    void submitTask_whenUploadFileIsProvided_shouldSaveTaskAndFile() {
        // Given
        Long taskSeq = 1L;
        Long employeeSeq = 2L;
        String uploadFile = "http://example.com/uploaded-file";

        TaskSubmitDTO taskSubmitDTO = new TaskSubmitDTO();
        taskSubmitDTO.setTaskSubmitContent("Task content");
        taskSubmitDTO.setFileName("uploaded-file.txt");

        TaskSubmit savedTaskSubmit = TaskSubmit.builder()
                .taskSeq(taskSeq)
                .employeeSeq(employeeSeq)
                .taskSubmitContent("Task content")
                .build();

        Mockito.when(taskRepository.save(Mockito.any(TaskSubmit.class))).thenReturn(savedTaskSubmit);

        // When
        taskService.submitTask(taskSubmitDTO, taskSeq, uploadFile);

        // Then
        Mockito.verify(taskRepository, Mockito.times(1)).save(Mockito.any(TaskSubmit.class));
        Mockito.verify(fileRepository, Mockito.times(1)).save(Mockito.any(File.class));
    }

    @Test
    void updateTask_whenUploadFileIsProvided_shouldUpdateTaskAndSaveFile() {
        // Given
        Long taskSeq = 1L;
        String uploadFile = "http://example.com/uploaded-file";

        TaskUpdateDTO taskUpdateDTO = new TaskUpdateDTO();
        taskUpdateDTO.setTemplateSeq(10L);
        taskUpdateDTO.setTaskType(TaskType.GROUP);
        taskUpdateDTO.setDepartmentSeq(2L);
        taskUpdateDTO.setTaskTitle("Updated Task Title");
        taskUpdateDTO.setTaskContent("Updated Task Content");

        Template template = new Template(); // 필요한 필드만 채워도 됨
        Task updatedTask = Task.builder()
                .taskSeq(taskSeq)
                .taskType(taskUpdateDTO.getTaskType())
                .departmentSeq(taskUpdateDTO.getDepartmentSeq())
                .templateSeq(taskUpdateDTO.getTemplateSeq())
                .taskTitle(taskUpdateDTO.getTaskTitle())
                .taskContent(taskUpdateDTO.getTaskContent())
                .build();

        Mockito.when(templateRepository.findByTemplateSeq(taskUpdateDTO.getTemplateSeq()))
                .thenReturn(template);

        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(updatedTask);

        // When
        taskService.updateTask(taskUpdateDTO, taskSeq, uploadFile);

        // Then
        Mockito.verify(templateRepository, Mockito.times(1)).findByTemplateSeq(taskUpdateDTO.getTemplateSeq());
        Mockito.verify(taskRepository, Mockito.times(1)).save(Mockito.any(Task.class));
        Mockito.verify(fileRepository, Mockito.times(1)).save(Mockito.any(File.class));
    }

    @Test
    void deleteTask_whenTaskExists_shouldDeleteTask() {
        // Given
        Long taskSeq = 1L;

        Mockito.when(taskRepository.existsById(taskSeq)).thenReturn(true);

        // When
        taskService.deleteTask(taskSeq);

        // Then
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(taskSeq);
    }




}