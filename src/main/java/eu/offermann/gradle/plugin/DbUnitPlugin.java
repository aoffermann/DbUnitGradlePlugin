package eu.offermann.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import eu.offermann.gradle.extension.DbUnitPluginExtension;
import eu.offermann.gradle.task.DbUnitExportTask;
import eu.offermann.gradle.task.DbUnitImportTask;

/**
 * @author off
 *
 */
public class DbUnitPlugin implements Plugin<Project> {

	@Override
	public void apply(Project target) {
		target.getExtensions().create("dbUnitPluginExtension", DbUnitPluginExtension.class);
		target.getTasks().create("dbUnitExport", DbUnitExportTask.class);
		target.getTasks().create("dbUnitImport", DbUnitImportTask.class);
	}
}