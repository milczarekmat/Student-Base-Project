package db.helperClasses;

import java.io.Serializable;

public class SubjectMeanInfo implements Serializable {
    private final String subjectName;
    private final Float mean;

    public SubjectMeanInfo(String subjectName, Float mean) {
        this.subjectName = subjectName;
        this.mean = mean;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Float getMean() {
        return mean;
    }

    @Override
    public String toString() {
        return "Subject: " + subjectName +
                ", grade: " + mean +
                ' ';
    }
}
