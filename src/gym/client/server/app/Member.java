/*
Central Queensland University
COIT13229 - Applied Distributed Systems (2024 Term 1)
Campus: External
Assignment 1 - Java Client/Server Application
Student ID: 12184305
Student Name: Daniel Barros
 */
package gym.client.server.app;

import java.io.Serializable;

/**
 * The Member class represents a gym member in the context of the Java
 * Client/Server Application project. Each member has a first name, last name,
 * address, and phone number. This class implements the Serializable interface
 * to allow instances of Member objects to be serialised (TCPServer class) and
 * deserialised (UDPServer class) for storage.
 *
 * @author Daniel Barros
 * @version 1.0
 */
public class Member implements Serializable {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    /**
     * Default constructor for the Member class. Creates a new Member object
     * with no values for the member attributes. The values are assigned after
     * instantiating Member objects at run time.
     */
    public Member() {
        // Intentionally blank.
    }

    /**
     * Get the first name of the member.
     *
     * @return the first name of the member
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of the member.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the member.
     *
     * @return the last name of the member
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the member.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the address of the member.
     *
     * @return the address of the member
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address of the member.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the phone number of the member.
     *
     * @return the phone number of the member
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the phone number of the member.
     *
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns a string representation of the Member object. Overrides the
     * toString() method in the Object class. The string contains the first
     * name, last name, address, and phone number of the member separated by
     * colons.
     *
     * @return a string representation of the Member object
     */
    @Override
    public String toString() {
        return firstName + ":" + lastName + ":" + address + ":" + phoneNumber;
    }
}
