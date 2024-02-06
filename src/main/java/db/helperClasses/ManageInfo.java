package db.helperClasses;

import java.io.Serializable;

public class ManageInfo implements Serializable {
    private final int studentId;
    private final int subjectId;
    private final float gradeValue;

    public ManageInfo(int studentId, int subjectId, float gradeValue) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.gradeValue = gradeValue;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public float getGradeValue() {
        return gradeValue;
    }
}
