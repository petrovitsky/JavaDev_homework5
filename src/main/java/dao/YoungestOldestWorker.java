package dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data @AllArgsConstructor
public class YoungestOldestWorker {
    private Type type;
    private String name;
    private LocalDate birthday;

    public enum Type{
        YOUNGEST, ELDEST
    }


}

