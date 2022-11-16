package PhoneBook.Objects.Resources;

import java.io.Serializable;

public class Friend implements Serializable {

    String firstName;
    String lastName;
    String address;
    String phone;
    String birthday;

    public Friend(String firstName, String lastName,
                  String address, String phone, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
    }

    public Friend(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Friend(String welcomeMessage) {
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", " + address + ", "+
                phone +", " + birthday;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
