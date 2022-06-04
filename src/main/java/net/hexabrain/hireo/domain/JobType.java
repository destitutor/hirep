package net.hexabrain.hireo.domain;

public enum JobType {
    FULLTIME("정규직"),
    PARTTIME("비정규직"),
    INTERNSHIP("인턴");

    private final String name;

    JobType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
