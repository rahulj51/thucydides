package net.thucydides.core.reports.html.history;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class TestResultSnapshot implements Comparable<TestResultSnapshot> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_result_snapshot_seq")
    @SequenceGenerator(name="test_result_snapshot_seq",sequenceName="SNAPSHOT_SEQUENCE", allocationSize=1)
    private Long id;

    private DateTime time;
    private int specifiedSteps;
    private int passingSteps;
    private int failingSteps;
    private int skippedSteps;
    private String buildId;
    private String projectName;

    public TestResultSnapshot(final DateTime time,
                              final int specifiedSteps,
                              final int passingSteps,
                              final int failingSteps,
                              final int skippedSteps,
                              final String buildId) {
        this.time = time;
        this.specifiedSteps = specifiedSteps;
        this.passingSteps = passingSteps;
        this.failingSteps = failingSteps;
        this.skippedSteps = skippedSteps;
        this.buildId = buildId;
    }

    public TestResultSnapshot(final int specifiedSteps,
                              final int passingSteps,
                              final int failingSteps,
                              final int skippedSteps,
                              final String buildId) {
        this(DateTime.now(),specifiedSteps,passingSteps,failingSteps,skippedSteps,buildId);
    }

    public DateTime getTime() {
        return time;
    }

    public int getSpecifiedSteps() {
        return specifiedSteps;
    }

    public int getPassingSteps() {
        return passingSteps;
    }

    public int getFailingSteps() {
        return failingSteps;
    }

    public int getSkippedSteps() {
        return skippedSteps;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public void setSpecifiedSteps(int specifiedSteps) {
        this.specifiedSteps = specifiedSteps;
    }

    public void setPassingSteps(int passingSteps) {
        this.passingSteps = passingSteps;
    }

    public void setFailingSteps(int failingSteps) {
        this.failingSteps = failingSteps;
    }

    public void setSkippedSteps(int skippedSteps) {
        this.skippedSteps = skippedSteps;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int compareTo(TestResultSnapshot other) {
        if (this == other) {
            return 0;
        } else {
            return this.getTime().compareTo(other.getTime());
        }
    }
}
