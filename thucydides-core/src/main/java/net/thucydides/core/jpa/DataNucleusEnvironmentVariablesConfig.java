package net.thucydides.core.jpa;

import com.google.inject.Inject;
import net.thucydides.core.statistics.database.LocalDatabase;
import net.thucydides.core.util.EnvironmentVariables;
import org.eclipse.persistence.config.TargetDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Rahul
 * Date: 6/13/12
 * Time: 11:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataNucleusEnvironmentVariablesConfig extends AbstractJPAProviderConfig {

    private final EnvironmentVariables environmentVariables;
    private final LocalDatabase localDatabase;

    private static final JPAProvider PROVIDER = JPAProvider.DataNucleus;


    @Inject
    public DataNucleusEnvironmentVariablesConfig(EnvironmentVariables environmentVariables,
                                                 LocalDatabase localDatabase) {
        this.environmentVariables = environmentVariables;
        this.localDatabase = localDatabase;
    }

    @Override
    public void setProperties(Properties properties) {

        String driver = environmentVariables.getProperty("thucydides.statistics.driver_class", localDatabase.getDriver());
        String url = environmentVariables.getProperty("thucydides.statistics.url", localDatabase.getUrl());
        String username = environmentVariables.getProperty("thucydides.statistics.username", localDatabase.getUsername());
        String password = environmentVariables.getProperty("thucydides.statistics.password", localDatabase.getPassword());
        String dialect = environmentVariables.getProperty("thucydides.statistics.dialect", localDatabase.getDataNucleusDBAdapterName());

        properties.put("datanucleus.ConnectionDriverName", driver);
        properties.put("datanucleus.ConnectionURL", url);
        properties.put("datanucleus.ConnectionUserName", username);
        properties.put("datanucleus.ConnectionPassword", password);
        properties.put("org.datanucleus.rdbms.datastoreAdapterClassName", dialect);
        properties.put("datanucleus.connectionPoolingType", "C3P0");
        properties.put("datanucleus.connectionPool.initialPoolSize", "1");
        properties.put("datanucleus.connectionPool.maxPoolSize", "10");

        boolean databaseIsConfigured = databaseIsConfigured(properties);
        if (isUsingLocalDatabase() || !databaseIsConfigured) {
            properties.put("datanucleus.autoCreateSchema", "true");
        } else {
            properties.put("datanucleus.autoCreateSchema", "false");
            properties.put("datanucleus.validateTables", "true");
            properties.put("datanucleus.validateColumns", "true");
            properties.put("datanucleus.validateConstraints", "true");
        }

    }

    private boolean databaseIsConfigured(Properties targetConfiguration) {
        Properties connectionProps = new Properties();
        connectionProps.put("user", targetConfiguration.getProperty("datanucleus.ConnectionUserName"));
        connectionProps.put("password", targetConfiguration.getProperty("datanucleus.ConnectionPassword"));
        String jdbcConnection = targetConfiguration.getProperty("datanucleus.ConnectionURL");
        try {
            Connection conn = DriverManager.getConnection(jdbcConnection, connectionProps);
            List<String> tables = getTablesFrom(conn);
            return tables.contains("TESTRUN");
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean isUsingLocalDatabase() {
        return isUsingLocalDatabase(environmentVariables);
    }

    @Override
    public JPAProvider getProvider() {
        return  PROVIDER;
    }
}
