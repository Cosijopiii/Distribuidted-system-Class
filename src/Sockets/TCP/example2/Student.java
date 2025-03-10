package Sockets.TCP.example2;

import java.io.Serializable;
import java.util.List;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int age;
    List<Course> courses;
    Address address;

    public Student(String name, int age, List<Course> courses, Address address) {
        this.name = name;
        this.age = age;
        this.courses = courses;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age +
                ", courses=" + courses + ", address=" + address + "}";
    }
}
