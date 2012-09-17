package net.thucydides.core.reports.html.history;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.thucydides.core.guice.DatabaseConfig;
import net.thucydides.core.guice.EnvironmentVariablesDatabaseConfig;
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

/**
 * Created with IntelliJ IDEA.
 * User: rahul
 * Date: 9/18/12
 * Time: 7:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class WhenUsingTestResultSnapshotDAO {

    Injector injector;
    MockEnvironmentVariables environmentVariables;
    ThucydidesModuleWithMockEnvironmentVariables guiceModule;

    class ThucydidesModuleWithMockEnvironmentVariables extends ThucydidesModule {
        @Override
        protected void configure() {
            clearEntityManagerCache();
            bind(EnvironmentVariables.class).to(MockEnvironmentVariables.class).in(Singleton.class);
            bind(DatabaseConfig.class).to(EnvironmentVariablesDatabaseConfig.class).in(Singleton.class);
            bind(LocalPreferences.class).to(PropertiesFileLocalPreferences.class).in(Singleton.class);
            bind(SystemClock.class).to(InternalSystemClock.class).in(Singleton.class);
            bind(TagProviderService.class).to(ClasspathTagProviderService.class).in(Singleton.class);
            bind(Configuration.class).to(SystemPropertiesConfiguration.class).in(Singleton.class);
        }
    }

    @Before
    public void setupInjectors() {
        guiceModule = new ThucydidesModuleWithMockEnvironmentVariables();
        injector = Guice.createInjector(guiceModule);
        environmentVariables = (MockEnvironmentVariables) injector.getInstance(EnvironmentVariables.class);
    }


    @Test
    public void should_be_able_to_find_all_timestamps_in_sorted_order() {

    }

}
