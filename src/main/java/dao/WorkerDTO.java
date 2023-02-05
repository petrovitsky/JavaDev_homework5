package dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data @AllArgsConstructor
public class WorkerDTO {
    private int id;
    private String name;
    private LocalDate birthday;
    private Level level;
    private int salary;


    public enum Level {
        TRAINEE("Trainee"), JUNIOR("Junior"), MIDDLE("Middle"), SENIOR("Senior");

        private final String description;

        private Level(String level) {
            this.description = level;
        }

        public String getDescription() {
            return description;
        }
    }
}
