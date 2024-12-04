package spring.hi_hello_spring.group.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestWrapper {
    private List<TaskRequestDTO> tasks;
}
