package org.example;

import dbservice.DatabaseQueryService;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        DatabaseQueryService databaseQueryService = new DatabaseQueryService();

        System.out.println(databaseQueryService.getAllProjectswithCosts());
        System.out.println("--------- -------- ------- --------");
        System.out.println(databaseQueryService.findMaxProjectsClient());
        System.out.println("--------- -------- ------- --------");
        System.out.println(databaseQueryService.findYoungestOldestWorker());
        System.out.println("--------- -------- ------- --------");
        System.out.println(databaseQueryService.findlongestProject());
        System.out.println("--------- -------- ------- --------");
        System.out.println(databaseQueryService.finaMaxSalary());
    }
}