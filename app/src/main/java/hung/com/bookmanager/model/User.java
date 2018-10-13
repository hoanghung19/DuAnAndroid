package hung.com.bookmanager.model;

public class User {
    private String userName;
    private String Password;
    private String Phone;
    private String hoTen;

    public User() {
    }

    public User(String userName, String password, String phone, String hoTen) {
        this.userName = userName;
        Password = password;
        Phone = phone;
        this.hoTen = hoTen;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
}