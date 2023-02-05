package dbservice;

import dao.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DatabasePopulateService {
    private Database db;

    public DatabasePopulateService(Database db) throws SQLException {
        this.db = db;
    }

    public static void main(String[] args) throws SQLException {

        List<WorkerDTO> workers = Arrays.asList(
                new WorkerDTO(1, "Bibby", LocalDate.parse("1990-05-20"), WorkerDTO.Level.SENIOR, 5000),
                new WorkerDTO(2, "Demetria", LocalDate.parse("1998-08-31"), WorkerDTO.Level.MIDDLE, 3400),
                new WorkerDTO(3, "Harrison", LocalDate.parse("1995-08-11"), WorkerDTO.Level.JUNIOR, 2200),
                new WorkerDTO(4, "Natalie", LocalDate.parse("1974-07-28"), WorkerDTO.Level.TRAINEE, 350),
                new WorkerDTO(5, "Gal", LocalDate.parse("1998-04-14"), WorkerDTO.Level.MIDDLE, 2800),
                new WorkerDTO(6, "Sula", LocalDate.parse("1974-06-03"), WorkerDTO.Level.SENIOR, 4750),
                new WorkerDTO(7, "Cullin", LocalDate.parse("1998-09-30"), WorkerDTO.Level.TRAINEE, 290),
                new WorkerDTO(8, "Sybilla", LocalDate.parse("1989-06-18"), WorkerDTO.Level.JUNIOR, 1500),
                new WorkerDTO(9, "Thorny", LocalDate.parse("1985-10-23"), WorkerDTO.Level.JUNIOR, 1800),
                new WorkerDTO(10, "Otha", LocalDate.parse("1991-01-29"), WorkerDTO.Level.JUNIOR, 1600));

        List<ClientDTO> clients = Arrays.asList(
                new ClientDTO(1, "Reckitt Benckiser LLC"),
                new ClientDTO(2, "HOMEOLAB USA INC."),
                new ClientDTO(3, "Energetix Corp"),
                new ClientDTO(4, "Life Line Home Care Services"),
                new ClientDTO(5, "Lupin Pharmaceuticals"));

        List<ProjectDTO> projects = Arrays.asList(
                new ProjectDTO(1, 5, LocalDate.parse("2022-10-05"), LocalDate.parse("2028-12-17")),
                new ProjectDTO(2, 2, LocalDate.parse("2022-02-25"), LocalDate.parse("2024-05-22")),
                new ProjectDTO(3, 2, LocalDate.parse("2022-01-10"), LocalDate.parse("2026-11-16")),
                new ProjectDTO(4, 4, LocalDate.parse("2022-07-30"), LocalDate.parse("2026-09-19")),
                new ProjectDTO(5, 3, LocalDate.parse("2022-07-23"), LocalDate.parse("2023-04-05")),
                new ProjectDTO(6, 2, LocalDate.parse("2022-06-27"), LocalDate.parse("2030-07-11")),
                new ProjectDTO(7, 1, LocalDate.parse("2022-02-17"), LocalDate.parse("2029-08-04")),
                new ProjectDTO(8, 3, LocalDate.parse("2022-07-22"), LocalDate.parse("2028-08-10")),
                new ProjectDTO(9, 5, LocalDate.parse("2022-04-28"), LocalDate.parse("2030-01-10")),
                new ProjectDTO(10, 5, LocalDate.parse("2022-04-11"), LocalDate.parse("2027-06-29")));

        List<ProjectWorkerDTO> projectsWorkers = Arrays.asList(
                new ProjectWorkerDTO(1, 7),
                new ProjectWorkerDTO(1, 8),
                new ProjectWorkerDTO(1, 6),
                new ProjectWorkerDTO(2, 9),
                new ProjectWorkerDTO(2, 10),
                new ProjectWorkerDTO(3, 9),
                new ProjectWorkerDTO(3, 8),
                new ProjectWorkerDTO(4, 7),
                new ProjectWorkerDTO(5, 9),
                new ProjectWorkerDTO(5, 8),
                new ProjectWorkerDTO(5, 6),
                new ProjectWorkerDTO(5, 7),
                new ProjectWorkerDTO(6, 9),
                new ProjectWorkerDTO(6, 7),
                new ProjectWorkerDTO(7, 7),
                new ProjectWorkerDTO(7, 8),
                new ProjectWorkerDTO(7, 6),
                new ProjectWorkerDTO(8, 7),
                new ProjectWorkerDTO(8, 9),
                new ProjectWorkerDTO(9, 6),
                new ProjectWorkerDTO(10, 6),
                new ProjectWorkerDTO(10, 5));

        DatabasePopulateService populateService = new DatabasePopulateService(Database.getInstance());

        populateService.insertIntotWorker(workers);
        populateService.insertIntoClients(clients);
        populateService.insertIntoProjects(projects);
        populateService.insertIntoProjectsWorkers(projectsWorkers);

    }

    private void insertIntoProjectsWorkers(List<ProjectWorkerDTO> projectsWorkers) throws SQLException {
        try (PreparedStatement insertIntoProjektWorkerSt =
                     db.getConnection()
                             .prepareStatement("INSERT INTO project_worker (project_id, worker_id) VALUES ( ? , ? )")) {
            for (int i = 0; i < projectsWorkers.size(); i++) {
                insertIntoProjektWorkerSt.setInt(1, projectsWorkers.get(i).getProjectId());
                insertIntoProjektWorkerSt.setInt(2, projectsWorkers.get(i).getWorkerId());
                insertIntoProjektWorkerSt.addBatch();
            }
            insertIntoProjektWorkerSt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntotWorker(List<WorkerDTO> workers) {
        try (PreparedStatement insertIntoWorkerSt =
                     db.getConnection()
                             .prepareStatement("INSERT INTO worker (name, birthday, level, salary) VALUES (? , ? , ? , ? )")) {
            for (int i = 0; i < workers.size(); i++) {
                insertIntoWorkerSt.setString(1, workers.get(i).getName());
                insertIntoWorkerSt.setString(2, workers.get(i).getBirthday().toString());
                insertIntoWorkerSt.setString(3, workers.get(i).getLevel().getDescription());
                insertIntoWorkerSt.setInt(4, workers.get(i).getSalary());
                insertIntoWorkerSt.addBatch();
            }
            insertIntoWorkerSt.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertIntoClients(List<ClientDTO> clients) {
        try (PreparedStatement insertIntoClientSt =
                     db.getConnection()
                             .prepareStatement("INSERT INTO client (name) VALUES (?)")) {
            for (int i = 0; i < clients.size(); i++) {
                insertIntoClientSt.setString(1, clients.get(i).getName());
                insertIntoClientSt.addBatch();
            }
            insertIntoClientSt.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertIntoProjects(List<ProjectDTO> projects) {
        try (PreparedStatement insertIntoProjectSt =
                     db.getConnection()
                             .prepareStatement("INSERT INTO project (client_id, start_date, finish_date) VALUES ( ?, ?, ?)")) {
            for (int i = 0; i < projects.size(); i++) {
                insertIntoProjectSt.setInt(1, projects.get(i).getClientId());
                insertIntoProjectSt.setString(2, projects.get(i).getStartDate().toString());
                insertIntoProjectSt.setString(3, projects.get(i).getEndDate().toString());
                insertIntoProjectSt.addBatch();
            }
            insertIntoProjectSt.executeBatch();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
