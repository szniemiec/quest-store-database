package daos.artifact;

import database.PostgreSQLJDBC;
import models.Artifact;

import java.sql.*;
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

    @Override
    public void addArtifact(Artifact artifact) {
        final String INSERT_SQL = "INSERT INTO \"Artifacts\" (\"Title\", \"Description\", \"Cost\")" +
                "VALUES (?, ?, ?);";
        String title = artifact.getTitle();
        String description = artifact.getDescription();
        String cost = artifact.getCost();

        try {
            PreparedStatement ps = this.postgreSQLJDBC.getConnection().prepareStatement(INSERT_SQL);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, cost);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public void editArtifact(Artifact artifact) {
        final String UPDATE_SQL = "UPDATE \"Artifacts\"" +
                "SET \"Title\" = ?, \"Description\" = ?, \"Cost\" = ?" +
                "WHERE id = ?;";
        try {
            PreparedStatement ps = this.postgreSQLJDBC.getConnection().prepareStatement(UPDATE_SQL);
            ps.setString(1, artifact.getTitle());
            ps.setString(2, artifact.getDescription());
            ps.setString(3, artifact.getCost());
            ps.setInt(4, artifact.getId());
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
            String cost = rs.getString("cost");
            Artifact artifact = new Artifact(id, title, description, cost);
            artifacts.add(artifact);
        }

        return artifacts;
    }

    public void setArtifact(Artifact artifact) {
        Connection c = postgreSQLJDBC.getConnection();
        final String QUERY_SQL = "INSERT INTO \"Artifacts\" (\"Title\", \"Description\", \"Cost\")" +
                "VALUES (?, ?, ?);";
        try {
            PreparedStatement ps = c.prepareStatement(QUERY_SQL);
            ps.setString(1, artifact.getTitle());
            ps.setString(2, artifact.getDescription());
            ps.setString(3, artifact.getCost());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}