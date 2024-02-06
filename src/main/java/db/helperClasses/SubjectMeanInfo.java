package db.helperClasses;

import java.io.Serializable;

public class SubjectMeanInfo implements Serializable {
    private final String subjectName;
    private final float mean;

    public SubjectMeanInfo(String subjectName, float mean) {
        this.subjectName = subjectName;
        this.mean = mean;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public float getMean() {
        return mean;
    }

    @Override
    public String toString() {
        return "Subject: " + subjectName +
                ", grade: " + mean +
                ' ';
    }
}
