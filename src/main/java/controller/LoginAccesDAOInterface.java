package controller;




import models.users.Mentor;

import java.util.List;


public interface LoginAccesDAOInterface {

    List<Integer> readLoginData(String email, String pass);

    void addMentor(Mentor mentor);
    void editMentor(Mentor mentor, String id);
    void deleteMentor(String id);



    Mentor getMentorById(String id);

}