package eunhye.GooGoo.dto.user.response;

import eunhye.GooGoo.domain.user.User;

public class UserResponse {
    private long id;
    private String name;
    private String email;
    private String phone;
    private String password;

    public UserResponse(long id, User user) {
        this.id = id;
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.password = user.getPassword();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
