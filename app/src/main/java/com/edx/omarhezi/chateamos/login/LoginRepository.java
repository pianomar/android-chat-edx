package com.edx.omarhezi.chateamos.login;

/**
 * Created by Lawrence Cermeño on 05/04/17.
 */

public interface LoginRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
    void checkSession();
}
