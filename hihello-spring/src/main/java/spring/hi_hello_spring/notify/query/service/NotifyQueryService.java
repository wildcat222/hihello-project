package spring.hi_hello_spring.notify.query.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.notify.command.application.dto.NotifyDTO;
import spring.hi_hello_spring.notify.query.dto.AlarmCountDTO;
import spring.hi_hello_spring.notify.query.dto.ReadNotifyDTO;
import spring.hi_hello_spring.notify.query.mapper.NotifyMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotifyQueryService {

    private final NotifyMapper notifyMapper;

    // 알림 전체 조회
    public List<ReadNotifyDTO> notiAll(Long employeeSeq){

        return notifyMapper.findAllByEmployeeSeq(employeeSeq);
    }

    // 읽지 않은 알림 개수
    public AlarmCountDTO notiCount(Long employeeSeq) {

        return notifyMapper.findNotiCount(employeeSeq);
    }
}
