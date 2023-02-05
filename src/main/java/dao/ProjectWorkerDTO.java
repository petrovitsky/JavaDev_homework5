package dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ProjectWorkerDTO {
    private int projectId;
    private int workerId;

}
