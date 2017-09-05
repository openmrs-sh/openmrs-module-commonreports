package org.openmrs.module.mksreports.reports;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.initializer.api.InitializerService;
import org.openmrs.module.mksreports.MKSReportsConstants;
import org.openmrs.module.reporting.report.manager.BaseReportManager;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class OutpatientRecordBookTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	private InitializerService iniz;
	
	@Autowired
	@Qualifier(MKSReportsConstants.COMPONENT_REPORTMANAGER_OPDRECBOOK)
	private BaseReportManager reportManager;
	
	@Before
	public void setUp() throws Exception {
		String path = getClass().getClassLoader().getResource("testAppDataDir").getPath() + File.separator;
		System.setProperty("OPENMRS_APPLICATION_DATA_DIRECTORY", path);
		
		PersonAttributeType pat = Context.getPersonService().getPersonAttributeTypeByUuid(
		    "b3b6d540-a32e-44c7-91b3-292d97667518");
		pat.setForeignKey(Context.getConceptService().getConcept(3).getConceptId());
		Context.getPersonService().savePersonAttributeType(pat);
	}
	
	@Test
	public void setupReport_shouldSetupOPDRecBook() {
		
		// setup
		assumeOpenmrsPlatformVersion("2.0.0");
		iniz.loadJsonKeyValues();
		
		// replay
		ReportManagerUtil.setupReport(reportManager);
		
		// verif
		// TODO
	}
}
