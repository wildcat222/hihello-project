package spring.hi_hello_spring.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.evaluation.query.dto.*;
import spring.hi_hello_spring.evaluation.query.service.TaskQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Tag(name = "Task API", description = "과제 관련 API")
public class TaskQueryController {

    private final TaskQueryService taskQueryService;

    @GetMapping("/hr/task")
    @Operation(summary = "담당자 과제 리스트 조회", description = "담당자가 조회하는 과제 리스트 조회 기능입니다.")
    public ApiResponse<?> getAllTaskList() {

        List<TaskAllListQueryDTO> taskAllListQueryDTO = taskQueryService.getHrAllTaskList();
        return ResponseUtil.successResponse("(담당자)멘티의 과제 리스트 전체가 성공적으로 조회되었습니다.", taskAllListQueryDTO).getBody();
    }

    @GetMapping("/mentor/task")
    @Operation(summary = "멘토 과제 리스트 조회", description = "멘토가 조회하는 과제 리스트 조회 기능입니다.")
    public ApiResponse<?> getAllMentorTaskList() {

        List<TaskAllListQueryDTO> taskAllListQueryDTO = taskQueryService.getMentorAllTaskList();
        return ResponseUtil.successResponse("(멘토)멘티의 과제 리스트 전체가 성공적으로 조회되었습니다.", taskAllListQueryDTO).getBody();
    }

    @GetMapping("mentor/task/{taskSeq}")
    @Operation(summary = "멘토가 멘티의 과제 상세 조회", description = "멘토가 멘티의 과제를 상세조회하는 기능입니다.")
    public ApiResponse<?> getMentorTaskDetail(@PathVariable Long taskSeq) {

        List<TaskMentorDetailQueryDTO> taskMentorDetailQueryDTO = taskQueryService.getMentorTaskDetail(taskSeq);
        return ResponseUtil.successResponse("멘티의 과제를 성공적으로 조회되었습니다.", taskMentorDetailQueryDTO).getBody();
    }

    @GetMapping("mentee/task/{taskSeq}")
    @Operation(summary = "멘티 본인의 과제 상세 조회", description = "멘티가 본인의 과제를 상세조회하는 기능입니다.")
    public ApiResponse<?> getMenteeTaskDetail(@PathVariable Long taskSeq) {

        List<TaskMenteeDetailQueryDTO> taskMenteeDetailQueryDTO = taskQueryService.getMenteeTaskDetail(taskSeq);
        return ResponseUtil.successResponse("멘티 본인의 과제를 성공적으로 조회되었습니다.", taskMenteeDetailQueryDTO).getBody();
    }

    @GetMapping("hr/group/task")
    @Operation(summary = "그룹 과제 제목 리스트 조회", description = "그룹 과제별 과제 제목 리스트 조회 기능입니다.")
    public ApiResponse<?> getAllGroupTaskTitle(){

        List<GroupTaskAllQueryDTO> queryDTO = taskQueryService.getGroupTaskTitle();
        return ResponseUtil.successResponse("그룹 과제 제목 리스트를 성공적으로 조회했습니다.", queryDTO).getBody();
    }

    @GetMapping("task")
    @Operation(summary = "과제 검색", description = "담당자, 멘토가 과제를 검색할 수 있는 기능입니다.")
    public ApiResponse<?> getSearchTask(@RequestParam String taskContent) {

        List<TaskSearchQueryDTO> taskSearchQueryDTO = taskQueryService.getSearchTask(taskContent);
        return ResponseUtil.successResponse("과제를 성공적으로 검색하였습니다.", taskSearchQueryDTO).getBody();
    }

    @GetMapping("mentee/task/group/{taskGroupSeq}")
    @Operation(summary = "그룹 과제 동료 조회", description = "그룹과제를 같이 수행할 동료를 조회합니다.")
    public ApiResponse<?> getTaskGroupPartner(@PathVariable Long taskGroupSeq) {
        List<TaskGroupPartnerQueryDTO> taskGroupPartner = taskQueryService.getTaskGroupPartner(taskGroupSeq);
        return ResponseUtil.successResponse("그룹 과제 제목 리스트를 성공적으로 조회했습니다.", taskGroupPartner).getBody();
    }

    @GetMapping("task/{taskSeq}")
    @Operation(summary = "과제 수정에 필요한 조회", description = "과제 수정에 필요한 조회를 합니다.")
    public ApiResponse<?> getTaskDetail(@PathVariable Long taskSeq) {
        List<TaskDetailQueryDTO> taskDetailQueryDTO = taskQueryService.getTaskDetail(taskSeq);
        return ResponseUtil.successResponse("그룹 과제 제목 리스트를 성공적으로 조회했습니다.", taskDetailQueryDTO).getBody();
    }

}
