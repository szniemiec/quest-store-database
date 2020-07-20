package daos.artifact;

import models.Artifact;

import java.sql.SQLException;
import java.util.List;

public interface ArtifactDAO {

   List<Artifact> getArtifacts() throws SQLException;

   Artifact getArtifact(int id) throws SQLException;

   void addArtifact(Artifact artifact);

   void deleteArtifact(int id);

   void editArtifact(Artifact artifact);

}
