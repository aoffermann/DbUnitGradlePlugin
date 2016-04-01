package eu.offermann.gradle.task;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;

import eu.offermann.gradle.extension.DbUnitPluginExtension;

/**
 * @author Offermann Alexander
 *
 */
public class DbUnitExportTask extends DefaultTask {

	@TaskAction
	public void dbUnitExportTasks() throws TaskExecutionException {

		System.out.println("Starting DbUnit Export Task");

		try {

			DbUnitPluginExtension extension = (DbUnitPluginExtension) getProject().getExtensions().findByName("dbUnitExportExt");

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
			Class.forName(databaseDriver);
			Connection jdbcConnection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
			IDatabaseConnection databaseConnection = new DatabaseConnection(jdbcConnection);

			// PostgreSql DataType Factory
			databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
					new PostgresqlDataTypeFactory());

			ITableFilter filter = new DatabaseSequenceFilter(databaseConnection);
			IDataSet dataset = new FilteredDataSet(filter, databaseConnection.createDataSet());
			FlatXmlDataSet.write(dataset, new FileOutputStream(exportFilePath + "dataset.xml"));
			FlatDtdDataSet.write(dataset, new FileOutputStream(exportFilePath + "dataset.dtd"));

			System.out.println("Successfully completed DbUnit Export Task");

		} catch (Exception e) {
			System.out.println(e);
			throw new TaskExecutionException(this,
					new Exception("Exception occured while processing DbUnit Export Task", e));
		}
	}

}
