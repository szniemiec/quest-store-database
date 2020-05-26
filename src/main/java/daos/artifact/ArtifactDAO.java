package daos.artifact;

import models.Artifact;

import java.util.List;

public interface ArtifactDAO {

    public List<Artifact> getArtifacts();

    public Artifact getArtifact(int id);

    public void deleteArtifact(int id);

}
