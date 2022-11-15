package PhoneBook.Server;

public class Friend {

    String name;
    String address;
    String phone;
    String birthday;

    public Friend(String name, String address, String phone, String birthday) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
    }

    public Friend(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
