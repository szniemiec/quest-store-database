package services;

import com.google.gson.Gson;
import database.DatabaseCredentials;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JSONService {

    public DatabaseCredentials readEnviroment() {
        try {
            Gson gson = new Gson();

            BufferedReader reader = Files.newBufferedReader(Paths.get("enviroment.json"));

            DatabaseCredentials credentials = gson.fromJson(reader, DatabaseCredentials.class);

            reader.close();

            return credentials;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private DatabaseCredentials mapToCredentials(Map<String, String> map) {
        String host = map.get("host");
        String port = map.get("port");
        String database = map.get("database");
        String login = map.get("login");
        String password = map.get("password");
        return new DatabaseCredentials(host, port, database, login, password);
    }

}
