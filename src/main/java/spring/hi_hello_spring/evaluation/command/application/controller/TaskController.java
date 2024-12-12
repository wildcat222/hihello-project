package spring.hi_hello_spring.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskCreateDTO;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskUpdateDTO;
import spring.hi_hello_spring.evaluation.command.application.service.TaskService;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Tag(name = "Task API", description = "과제 관련 API")
public class TaskController {

    private final TaskService taskService;

    // 과제 등록
    @PostMapping("/task")
    @Operation(summary = "과제 생성", description = "과제를 생성하는 기능입니다.")
    public ApiResponse<?> createTask(@RequestBody TaskCreateDTO taskContent) {

        taskService.createTask(taskContent);
        return ResponseUtil.successResponse("과제가 성공적으로 등록되었습니다.").getBody();
    }

    // 과제 수정
    @PutMapping("/task/{taskSeq}")
    @Operation(summary = "과제 수정", description = "과제를 수정하는 기능입니다.")
    public ApiResponse<?> updateTask(@RequestBody TaskUpdateDTO taskUpdateDTO,
                                     @PathVariable("taskSeq") Long taskSeq) {
        taskService.updateTask(taskUpdateDTO, taskSeq);
        return ResponseUtil.successResponse("과제가 성공적으로 수정되었습니다.").getBody();
    }

    // 과제 삭제
    @DeleteMapping("/task/{taskSeq}")
    @Operation(summary = "과제 삭제", description = "과제를 삭제하는 기능입니다.")
    public ApiResponse<?> updateTask(@PathVariable Long taskSeq) {
        taskService.deleteTask(taskSeq);
        return ResponseUtil.successResponse("과제가 성공적으로 삭제되었습니다.").getBody();
    }
}
