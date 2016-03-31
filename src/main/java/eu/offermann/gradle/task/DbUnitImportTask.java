package eu.offermann.gradle.task;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;

import eu.offermann.gradle.extension.DbUnitPluginExtension;

/**
 * @author off
 *
 */
public class DbUnitImportTask extends DefaultTask {

	@TaskAction
	public void dbUnitImportTasks() throws TaskExecutionException {

		System.out.println("Starting DbUnit Import Task");
		try {

			DbUnitPluginExtension extension = getProject().getExtensions().findByType(DbUnitPluginExtension.class);

			if (extension == null) {
				extension = new DbUnitPluginExtension();
			}

			String databaseUrl = extension.getUrl();
			System.out.println("The database URL is: {" + databaseUrl + "}");
			String databaseDriver = extension.getDriver();
			System.out.println("The database driver is: {" + databaseDriver + "}");
			String databaseUser = extension.getUser();
			System.out.println("The database user is: {" + databaseUser + "}");
			String databasePassword = extension.getPassword();
			System.out.println("The database Password is: {" + databasePassword + "}");
			String exportFilePath = extension.getFilePath();
			System.out.println("The export File Path is: {" + exportFilePath + "}");

			// Database connection
			Class driverClass = Class.forName(databaseDriver);
			Connection jdbcConnection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			IDatabaseConnection databaseConnection = new DatabaseConnection(jdbcConnection);

			// PostgreSql DataType Factory
			databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
					new PostgresqlDataTypeFactory());

			IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(exportFilePath));
			DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);

			System.out.println("Successfully completed DbUnit Import Task");

		} catch (Exception e) {
			System.out.println(e);
			throw new TaskExecutionException(this,
					new Exception("Exception occured while processing DbUnit Import Task", e));
		}
	}
}
