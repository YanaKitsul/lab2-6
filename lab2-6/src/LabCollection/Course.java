package LabCollection;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

public class Course implements Comparable {

    // enum
    public enum State {
        NEW, PROCESS, PASSED, FAILED
    }

    // ���������
    private static final String PATTERN = "^[A-Z][a-z]+";
    private static final int CURRENT_YEAR = LocalDate.now().getYear();

    // ����
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private State state;

    // �������
    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public State getState() {
        return state;
    }

    // �����������
    private Course() {
    }

    private Course(Builder b) {
        title = b.title;
        startDate = b.startDate;
        endDate = b.endDate;
        state = b.state;
    }

    // �����
    public static class Builder {

        // ����
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private State state;

        // �������
        public Builder setTitle(String title) {
            Pattern p = Pattern.compile(PATTERN);
            if (p.matcher(title).matches())
                this.title = title;
            else
                throw new IllegalArgumentException("Illegal Argument");
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            if (startDate.getYear() > CURRENT_YEAR - 100)
                this.startDate = startDate;
            else
                throw new IllegalArgumentException("Illegal Argument");
            return this;
        }

        public Builder setEndDate(LocalDate endDate) {
            if (endDate.getYear() > startDate.getYear()) {
                this.endDate = endDate;
            } else if (endDate.getYear() == startDate.getYear() && endDate.getDayOfYear() >= startDate.getDayOfYear()) {
                this.endDate = endDate;
            } else
                throw new IllegalArgumentException("Illegal Argument");
            return this;
        }

        public Builder setState(State state) {
            switch (state) {
                case PASSED:
                    if (DAYS.between(endDate, LocalDate.now()) >= 0) {
                        this.state = state;
                        return this;
                    }
                    break;
                case FAILED:
                    if (DAYS.between(endDate, LocalDate.now()) >= 0) {
                        this.state = state;
                        return this;
                    }
                    break;
                case PROCESS:
                    if (DAYS.between(LocalDate.now(), endDate) >= 0) {
                        this.state = state;
                        return this;
                    }
                    break;
                case NEW:
                    if (DAYS.between(LocalDate.now(), startDate) >= 0) {
                        this.state = state;
                        return this;
                    }
                    break;
            }
            throw new IllegalArgumentException("Illegal Argument");
        }

        public Course createCourse() {
            return new Course(this);
        }
    }

    // ������������ ������
    @Override
    public String toString() {
        String res = "�����: " + title +
                "\n���� �������: " + startDate +
                "\n���� ���������: " + endDate +
                "\n����: " + state + "\n";
        return res;
    }

    @Override
    public int compareTo(Object o) {
        Course s = (Course) o;
        int c = this.getTitle().compareTo(s.getTitle());
        if (c == 0)
            c = this.getStartDate().compareTo(s.getStartDate());
        if (c == 0)
            c = this.getEndDate().compareTo(s.getEndDate());
        return c;
    }

    @Override
    public boolean equals(Object obj) {

        return title.equals(((Course) obj).title) &&
                startDate.equals(((Course) obj).startDate) &&
                endDate.equals(((Course) obj).endDate) &&
                state.equals(((Course) obj).state);
    }
}
