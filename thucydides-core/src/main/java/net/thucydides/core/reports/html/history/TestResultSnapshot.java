package net.thucydides.core.reports.html.history;

import net.thucydides.core.Thucydides;
import net.thucydides.core.ThucydidesSystemProperty;
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
    private String projectKey;

    public TestResultSnapshot() {}

    public TestResultSnapshot(final DateTime time,
                              final int specifiedSteps,
                              final int passingSteps,
                              final int failingSteps,
                              final int skippedSteps,
                              final String buildId) {

        this(time,specifiedSteps,passingSteps,failingSteps,skippedSteps,buildId, "project");
    }

    public TestResultSnapshot(final int specifiedSteps,
                              final int passingSteps,
                              final int failingSteps,
                              final int skippedSteps,
                              final String buildId) {
        this(DateTime.now(),specifiedSteps,passingSteps,failingSteps,skippedSteps,buildId);
    }

    public TestResultSnapshot(final int specifiedSteps,
                              final int passingSteps,
                              final int failingSteps,
                              final int skippedSteps,
                              final String buildId,
                              final String projectKey) {
        this(DateTime.now(),specifiedSteps,passingSteps,failingSteps,skippedSteps,buildId, projectKey);

    }

    public TestResultSnapshot(final DateTime time,
                              final int specifiedSteps,
                              final int passingSteps,
                              final int failingSteps,
                              final int skippedSteps,
                              final String buildId,
                              final String projectKey) {
        this.time = time;
        this.specifiedSteps = specifiedSteps;
        this.passingSteps = passingSteps;
        this.failingSteps = failingSteps;
        this.skippedSteps = skippedSteps;
        this.buildId = buildId;
        this.projectKey = projectKey;
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

    public int compareTo(TestResultSnapshot other) {
        if (this == other) {
            return 0;
        } else {
            return this.getTime().compareTo(other.getTime());
        }
    }
}
