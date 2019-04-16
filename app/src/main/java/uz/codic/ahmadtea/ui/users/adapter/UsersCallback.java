package uz.codic.ahmadtea.ui.users.adapter;

public interface UsersCallback {
    void onItemClick();
    void setId_employee(String id_employee, String role);
    void setToken(String token);
}
