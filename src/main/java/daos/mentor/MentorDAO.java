package daos.mentor;

import models.users.Codecooler;
import models.users.Mentor;

import java.util.List;

public interface MentorDAO {

    public List<Mentor> getMentors();

    public void getMentor(int id);

    public void deleteMentor(int id);

}
