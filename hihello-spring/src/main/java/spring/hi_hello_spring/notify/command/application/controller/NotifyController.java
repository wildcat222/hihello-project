package spring.hi_hello_spring.notify.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.notify.command.application.service.NotifyServiceImpl;

@RestController
@RequestMapping("api/v1/notify")
@RequiredArgsConstructor
@Tag(name = "SSE 연결과 알림", description = "SSE 연결과 알림")
@Slf4j
public class NotifyController {

    private final NotifyServiceImpl notifyService;

    @Operation(summary = "sse 세션연결", description = "SSE의 세션을 연결한다.")
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId){

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        return notifyService.subscribe(String.valueOf(employeeSeq), lastEventId);
    }

    @Operation(summary = "알림 단일 읽음", description = "알림을 읽음 처리 한다.")
    @PutMapping("/{notiSeq}")
    public ApiResponse<?> readNoti(@PathVariable Long notiSeq) {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        notifyService.readNotify(employeeSeq, notiSeq);
        return ResponseUtil.successResponse("알림을 읽었습니다.").getBody();
    }

    @Operation(summary = "알림 단일 삭제",description = "알림을 삭제 처리 한다.")
    @DeleteMapping("/{notiSeq}")
    public ApiResponse<?> deleteNoti(@PathVariable Long notiSeq) {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        notifyService.deleteNotify(employeeSeq, notiSeq);
        return ResponseUtil.successResponse("알림을 삭제했습니다.").getBody();
    }

    @Operation(summary = "알림 전체 읽음",description = "나한테 온 알림들을 전체 읽음처리한다.")
    @PutMapping("/read")
    public ApiResponse<?> readAllNoti() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        notifyService.readAllNotify(employeeSeq);
        return ResponseUtil.successResponse("알림을 읽었습니다.").getBody();
    }

    @Operation(summary = "알림 전체 삭제",description = "나한테 온 알림들을 전체 삭제한다.")
    @DeleteMapping
    public ApiResponse<?> deleteAllNoti() {

        Long employeeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        notifyService.deleteAllNotify(employeeSeq);
        return ResponseUtil.successResponse("알림을 삭제했습니다.").getBody();
    }

}
