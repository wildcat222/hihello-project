package spring.hi_hello_spring.group.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import spring.hi_hello_spring.group.query.dto.TaskGroupListQueryDTO;
import spring.hi_hello_spring.group.query.dto.TaskGroupMembersQueryDTO;

import java.util.List;

@Mapper
public interface TaskGroupMapper {

    List<TaskGroupListQueryDTO> getTaskGroupList(Long taskSeq);

    List<TaskGroupMembersQueryDTO> getTaskGroupMembers(Long taskSubmitSeq);
}
