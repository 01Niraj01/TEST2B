public class LoginUser {
    private final SimpleStringProperty email;
    private final SimpleStringProperty password;

    public LoginUser(String email, String password) {
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
    }

    public String getEmail() {
        return email.get();
    }

    public String getPassword() {
        return password.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }
}
