package daos.artifact;

import models.Artifact;

import java.sql.SQLException;
import java.util.List;

public interface ArtifactDAO {

    public List<Artifact> getArtifacts() throws SQLException;

    public Artifact getArtifact(int id) throws SQLException;

    public void deleteArtifact(int id);

}
