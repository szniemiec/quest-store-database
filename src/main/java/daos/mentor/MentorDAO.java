package daos.mentor;

import models.users.Mentor;

import java.util.List;

public interface MentorDAO {
    List<Mentor> getMentors() throws Exception;

    void getMentor(int id);

    void deleteMentor(int id);

}
