package daos.artifact;

import database.PostgreSQLJDBC;
import models.Artifact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAOImpl implements ArtifactDAO {

    private PostgreSQLJDBC postgreSQLJDBC;

    public ArtifactDAOImpl(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    public List<Artifact> getArtifacts() throws SQLException {
        final String SELECT_SQL = "SELECT * FROM \"Artifacts\";";

        Statement st = postgreSQLJDBC.getConnection().createStatement();

        List<Artifact> artifacts = new ArrayList<>();

        try {
            ResultSet rs = st.executeQuery(SELECT_SQL);
            artifacts = createArtifactList(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return artifacts;
    }


    public Artifact getArtifact(int id) throws SQLException {
        final String SELECT_SQL = "SELECT * FROM \"Artifacts\" WHERE id = '" + id + "';";

        Statement st = postgreSQLJDBC.getConnection().createStatement();

        List<Artifact> artifacts = new ArrayList<>();

        try {
            ResultSet rs = st.executeQuery(SELECT_SQL);
            artifacts = createArtifactList(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return artifacts.get(0);
    }

    public void deleteArtifact(int id) {
        final String DELETE_SQL = "DELETE FROM \"Artifacts\" WHERE id = ?;";

        try {
            PreparedStatement ps = this.postgreSQLJDBC.getConnection().prepareStatement(DELETE_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Artifact> createArtifactList(ResultSet rs) throws SQLException {
        List<Artifact> artifacts = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            int cost = rs.getInt("cost");

            Artifact artifact = new Artifact(id, title, description, cost);

            artifacts.add(artifact);
        }

        return artifacts;
    }

}
