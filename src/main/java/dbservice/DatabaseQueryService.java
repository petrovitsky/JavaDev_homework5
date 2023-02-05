package dbservice;

import dao.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private Database db = Database.getInstance();

    public List<MaxProjectCountClient> findMaxProjectsClient() throws SQLException {
        PreparedStatement preparedStatement = db.getConnection()
                .prepareStatement("SELECT NAME, COUNT(PROJECT.ID) AS PROJECT_COUNT\n" +
                        "FROM  CLIENT\n" +
                        "JOIN PROJECT ON CLIENT.ID = PROJECT.CLIENT_ID\n" +
                        "GROUP BY CLIENT_ID\n" +
                        "HAVING COUNT(PROJECT.ID) IN (\n" +
                        "    SELECT COUNT(PROJECT.ID)\n" +
                        "    FROM PROJECT\n" +
                        "    GROUP BY CLIENT_ID\n" +
                        "    ORDER BY COUNT(PROJECT.ID) DESC\n" +
                        "    LIMIT 1)");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<MaxProjectCountClient> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new MaxProjectCountClient(
                    resultSet.getNString("name"),
                    resultSet.getInt("project_count")
            ));
        }
        db.close();
        return result;
    }


    public List<LongestProject> findlongestProject() throws IOException, SQLException {
        PreparedStatement statement = db.getConnection()
                .prepareStatement("SELECT ID AS NAME,  DATEDIFF (month, START_DATE , FINISH_DATE  ) AS MONTH_COUNT\n" +
                        "FROM PROJECT\n" +
                        "ORDER BY MONTH_COUNT DESC\n" +
                        "LIMIT\n" +
                        "  SELECT COUNT (ID)\n" +
                        "  FROM PROJECT\n" +
                        "  GROUP BY DATEDIFF (month, START_DATE , FINISH_DATE)\n" +
                        "  ORDER BY DATEDIFF (month, START_DATE , FINISH_DATE) DESC\n" +
                        "  LIMIT 1");
        ResultSet resultSet = statement.executeQuery();
        List<LongestProject> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new LongestProject(
                    resultSet.getNString("name"),
                    resultSet.getInt("month_count")
            ));
        }
        db.close();
        return result;
    }


    public List<MaxSalary> finaMaxSalary() throws IOException, SQLException {
        PreparedStatement ps = db.getConnection()
                .prepareStatement("SELECT NAME, SALARY\n" +
                        "FROM WORKER\n" +
                        "WHERE SALARY IN (\n" +
                        "  SELECT MAX(SALARY) FROM WORKER)");
        ResultSet resultSet = ps.executeQuery();
        List<MaxSalary> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new MaxSalary(
                    resultSet.getNString("name"),
                    resultSet.getInt("salary")
            ));
        }
        db.close();
        return result;
    }

    public List<YoungestOldestWorker> findYoungestOldestWorker() throws IOException, SQLException {
        PreparedStatement ps = db.getConnection()
                .prepareStatement("SELECT\n" +
                        "'ELDEST' AS TYPE,\n" +
                        "NAME ,\n" +
                        "BIRTHDAY\n" +
                        "FROM WORKER\n" +
                        "WHERE BIRTHDAY = (\n" +
                        "  SELECT MIN(BIRTHDAY) FROM WORKER)\n" +
                        "UNION\n" +
                        "SELECT\n" +
                        "'YOUNGEST' AS TYPE,\n" +
                        "NAME ,\n" +
                        "BIRTHDAY\n" +
                        "FROM WORKER\n" +
                        "WHERE BIRTHDAY = (\n" +
                        "  SELECT MAX(BIRTHDAY) FROM WORKER)\n" +
                        "ORDER BY BIRTHDAY DESC\n");
        ResultSet resultSet = ps.executeQuery();
        List<YoungestOldestWorker> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new YoungestOldestWorker(
                    YoungestOldestWorker.Type.valueOf(resultSet.getString("type")),
                    resultSet.getNString("name"),
                    LocalDate.parse(resultSet.getNString("birthday"))
            ));
        }
        db.close();
        return result;
    }

    public List<ProjectCosts> getAllProjectswithCosts() throws IOException, SQLException {
        PreparedStatement ps = db.getConnection()
                .prepareStatement("SELECT PROJECT.ID AS NAME,\n" +
                        "SUM(SALARY * DATEDIFF (month, START_DATE, FINISH_DATE)) AS PRICE\n" +
                        "FROM PROJECT_WORKER\n" +
                        "JOIN WORKER\n" +
                        "ON PROJECT_WORKER.WORKER_ID = WORKER.ID\n" +
                        "JOIN PROJECT\n" +
                        "ON PROJECT_WORKER.PROJECT_ID = PROJECT.ID\n" +
                        "GROUP BY PROJECT.ID\n" +
                        "ORDER BY PRICE DESC");
        ResultSet resultSet = ps.executeQuery();
        List<ProjectCosts> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new ProjectCosts(
                    resultSet.getNString("name"),
                    resultSet.getInt("price")
            ));
        }
        db.close();
        return result;
    }

}
