package eunhye.GooGoo.dto.user.request;

public class UserCreateRequest {
    private String name;
    private String email;
    private String phone;
    private String password;

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public String getPassword(){
        return password;
    }

}
