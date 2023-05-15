package eunhye.GooGoo.domain.user;

public class User {
    private String name;
    private String email;
    private String phone;
    private String password;

    public User(String name, String email, String phone, String password) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 email(%s)이 들어왔습니다", name));
        }

        if (phone == null || phone.isBlank() || phone.length() < 10) {
            throw new IllegalArgumentException(String.format("잘못된 phone(%s)이 들어왔습니다", name));
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 password(%s)이 들어왔습니다", name));
        }

        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
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
