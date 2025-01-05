package spring.hi_hello_spring.notify.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.notify.command.application.dto.NotifyDTO;
import spring.hi_hello_spring.notify.query.dto.AlarmCountDTO;
import spring.hi_hello_spring.notify.query.dto.ReadNotifyDTO;
import spring.hi_hello_spring.notify.query.service.NotifyQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/notify")
@RequiredArgsConstructor
@Tag(name = "SSE 연결과 알림", description = "SSE 연결과 알림")
@Slf4j
public class NotifyQueryController {

    private final NotifyQueryService notifyQueryService;

    @Operation(summary = "알림 전체 조회", description = "내게 온 알림들을 조회한다.(삭제 상태 제외)")
    @GetMapping("/list")
    public ApiResponse<?> notiAll() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        List<ReadNotifyDTO> alarms = notifyQueryService.notiAll(employeeSeq);
        return ResponseUtil.successResponse("알림을 성공적으로 조회하였습니다.", alarms).getBody();
    }

    @Operation(summary = "읽지 않은 알림 개수 조회", description = "내게 온 알림 중 읽지 않은 알림 개수를 조회한다.")
    @GetMapping("/list/count")
    public ApiResponse<?> notiCount() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        AlarmCountDTO alarmCount = notifyQueryService.notiCount(employeeSeq);
        return ResponseUtil.successResponse("알림 갯수를 성공적으로 조회하였습니다.", alarmCount).getBody();
    }
}
