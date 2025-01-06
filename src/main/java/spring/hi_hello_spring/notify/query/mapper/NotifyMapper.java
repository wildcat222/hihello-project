package spring.hi_hello_spring.notify.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spring.hi_hello_spring.notify.command.application.dto.NotifyDTO;
import spring.hi_hello_spring.notify.query.dto.AlarmCountDTO;
import spring.hi_hello_spring.notify.query.dto.ReadNotifyDTO;

import java.util.List;

@Mapper
public interface NotifyMapper {
    List<ReadNotifyDTO> findAllByEmployeeSeq(@Param("employeeSeq") Long employeeSeq);

    AlarmCountDTO findNotiCount(@Param("employeeSeq") Long employeeSeq);
}
