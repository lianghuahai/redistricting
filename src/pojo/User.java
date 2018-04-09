package pojo;

public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Party preferParty;
    private UserRole role;
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Party getPreferParty() {
        return preferParty;
    }
    public void setPreferParty(Party preferParty) {
        this.preferParty = preferParty;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    private int id;
    @Override
    public String toString() {
        return "User [email=" + email + ", password=" + password + "]";
    }

}
