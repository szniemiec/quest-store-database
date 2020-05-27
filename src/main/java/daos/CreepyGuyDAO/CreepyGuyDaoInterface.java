package daos.CreepyGuyDAO;


import models.users.Mentor;

public interface CreepyGuyDaoInterface {

    void addMentor(Mentor mentor);
    void editMentor(Mentor mentor, String id);
    void deleteMentor(String id);



    Mentor getMentorById(String id);

}
