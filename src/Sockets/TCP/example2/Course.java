package Sockets.TCP.example2;

import java.io.Serializable;

class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    String courseName;
    int credits;

    public Course(String courseName, int credits) {
        this.courseName = courseName;
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Course{name='" + courseName + "', credits=" + credits + "}";
    }
}
