package br.com.rmsa.benchmark.model.transport;


import com.univocity.parsers.annotations.Parsed;

public class MetadataDTO {

    @Parsed(field = "firstname")
    private String firstname;

    @Parsed(field = "lastname")
    private String lastname;

    @Parsed(field = "email")
    private String email;

    @Parsed(field = "age")
    private Integer age;

    @Parsed(field = "gender")
    private String gender;

    @Parsed(field = "phone")
    private String phone;

    @Parsed(field = "address")
    private String address;

    @Parsed(field = "city")
    private String city;

    @Parsed(field = "state")
    private String state;

    @Parsed(field = "zipCode")
    private String zipCode;

    @Parsed(field = "country")
    private String country;

    @Parsed(field = "job")
    private String job;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
