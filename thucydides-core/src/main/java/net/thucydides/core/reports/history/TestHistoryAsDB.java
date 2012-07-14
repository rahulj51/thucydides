package net.thucydides.core.reports.history;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import net.thucydides.core.Thucydides;
import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.reports.TestOutcomes;
import net.thucydides.core.reports.html.history.TestResultSnapshot;
import net.thucydides.core.reports.html.history.TestResultSnapshotDAO;
import net.thucydides.core.util.EnvironmentVariables;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Keep track of the test results over time.
 */
public class TestHistoryAsDB {

    private static final String BUILD_ID = "BUILD_ID";
    private final String projectKey;

    protected EnvironmentVariables environmentVariables;
    private TestResultSnapshotDAO testResultSnapshotDAO;

    public TestHistoryAsDB() {
        this(Injectors.getInjector().getInstance(EnvironmentVariables.class),
             Injectors.getInjector().getInstance(TestResultSnapshotDAO.class));
    }
    public TestHistoryAsDB(final EnvironmentVariables environmentVariables,
                           final TestResultSnapshotDAO testResultSnapshotDAO) {
        this.environmentVariables = environmentVariables;
        this.testResultSnapshotDAO = testResultSnapshotDAO;
        this.projectKey = getProjectKey();
    }

    public void updateData(TestOutcomes testOutcomes) {
        int totalStepCount = testOutcomes.getStepCount();
        int passingSteps =  testOutcomes.getPassingTests().getStepCount();
        int failingSteps = testOutcomes.getFailingTests().getStepCount();
        int skippedSteps = totalStepCount - passingSteps - failingSteps;
        String buildId = getEnvironmentVariables().getValue(BUILD_ID, "MANUAL");

        TestResultSnapshot newSnapshot = new TestResultSnapshot(totalStepCount,
                passingSteps,
                failingSteps,
                skippedSteps,
                buildId,
                projectKey);

        testResultSnapshotDAO.saveSnapshot(newSnapshot);

    }

    public List<TestResultSnapshot> getHistory() {

        return testResultSnapshotDAO.findAll();

    }

    public void clearHistory() {
        testResultSnapshotDAO.clearAll();
    }

    protected EnvironmentVariables getEnvironmentVariables() {
        return environmentVariables;
    }

    private String getProjectKey() {
        return ThucydidesSystemProperty.PROJECT_KEY.from(environmentVariables,
                Thucydides.getDefaultProjectKey());
    }
}
