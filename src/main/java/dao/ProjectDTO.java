package dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class ProjectDTO {
    private int id;
    private int clientId;
    private LocalDate startDate;
    private LocalDate endDate;

}
