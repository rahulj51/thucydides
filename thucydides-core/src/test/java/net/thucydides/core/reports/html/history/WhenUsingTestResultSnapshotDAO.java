package net.thucydides.core.reports.html.history;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.thucydides.core.guice.DatabaseConfig;
import net.thucydides.core.guice.EnvironmentVariablesDatabaseConfig;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.guice.ThucydidesModule;
import net.thucydides.core.pages.InternalSystemClock;
import net.thucydides.core.pages.SystemClock;
import net.thucydides.core.statistics.service.ClasspathTagProviderService;
import net.thucydides.core.statistics.service.TagProviderService;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.LocalPreferences;
import net.thucydides.core.util.MockEnvironmentVariables;
import net.thucydides.core.util.PropertiesFileLocalPreferences;
import net.thucydides.core.webdriver.Configuration;
import net.thucydides.core.webdriver.SystemPropertiesConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: rahul
 * Date: 9/18/12
 * Time: 7:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class WhenUsingTestResultSnapshotDAO {

    @Test
    public void should_be_able_to_find_all_timestamps_in_sorted_order() {

        TestResultSnapshotDAO testResultSnapshotDAO = Injectors.getInjector().getInstance(TestResultSnapshotDAO.class);

        List<Timestamp> timestamps = testResultSnapshotDAO.findAllTimestamps();
        List<Timestamp> sortedList = new ArrayList<Timestamp>(timestamps);
        Collections.sort(sortedList);
        assertThat(timestamps, is(sortedList));

    }

}
