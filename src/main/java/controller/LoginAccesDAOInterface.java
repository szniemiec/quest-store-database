package controller;

import java.util.List;

public interface LoginAccesDAOInterface {
    List<Integer> readLoginData(String email, String pass);
}