package services;

import database.DatabaseCredentials;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVService {

    public CSVService() {

    }

    public DatabaseCredentials readDatabaseEnviroment() {
        DatabaseCredentials databaseCredentials = null;

        try (BufferedReader br = Files.newBufferedReader(Paths.get("enviroment.csv"))) {

            String line = br.readLine();
            String[] databaseEnviromentInfo = line.split(",");
            databaseCredentials = new DatabaseCredentials(databaseEnviromentInfo[0],
                    databaseEnviromentInfo[1],
                    databaseEnviromentInfo[2],
                    databaseEnviromentInfo[3],
                    databaseEnviromentInfo[4]);

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return databaseCredentials;
    }
}
