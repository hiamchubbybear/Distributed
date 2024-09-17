public class User {
    Long id;
    String firstName;
    String lastName;
    String address;
    Long age;
    String email;

    public User() {
    }

    public User(String address, Long age, String email, String firstName, Long id, String lastName) {
        this.address = address;
        this.age = age;
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User id :" + id + ", name :" + firstName + " " + lastName + ", address:" + address
                + ", age :" + age + ", email :" + email;
    }

}
