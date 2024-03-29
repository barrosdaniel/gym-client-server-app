package gym.client.server.app;

import java.io.Serializable;

public class Member implements Serializable {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public Member() {

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return firstName + ":" + lastName  + ":" + address + ":" + phoneNumber;
    }
}
