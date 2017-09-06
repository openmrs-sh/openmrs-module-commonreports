package org.openmrs.module.mksreports.reports;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.initializer.api.InitializerService;
import org.openmrs.module.mksreports.MKSReportManager;
import org.openmrs.module.mksreports.MKSReportsConstants;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.openmrs.module.reporting.report.service.ReportService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class OutpatientRecordBookTest extends BaseModuleContextSensitiveTest {
	
	public static final String COMPONENT_REPORTMANAGER_TEST = "mksreports.testReportManager";
	
	@Autowired
	private InitializerService iniz;
	
	@Autowired
	private ReportService rs;
	
	@Autowired
	@Qualifier(MKSReportsConstants.COMPONENT_REPORTMANAGER_OPDRECBOOK)
	private MKSReportManager opdRecBookReportManager;
	
	@Autowired
	@Qualifier(COMPONENT_REPORTMANAGER_TEST)
	private MKSReportManager testReportManager;
	
	@Before
	public void setUp() throws Exception {
		String path = getClass().getClassLoader().getResource("testAppDataDir").getPath() + File.separator;
		System.setProperty("OPENMRS_APPLICATION_DATA_DIRECTORY", path);
		
		PersonAttributeType pat = Context.getPersonService().getPersonAttributeTypeByUuid(
		    "b3b6d540-a32e-44c7-91b3-292d97667518");
		pat.setForeignKey(Context.getConceptService().getConcept(3).getConceptId());
		Context.getPersonService().savePersonAttributeType(pat);
		
		iniz.loadJsonKeyValues();
	}
	
	@Test
	public void setupReport_shouldSetupOPDRecBook() {
		
		// replay
		ReportManagerUtil.setupReport(opdRecBookReportManager);
		
		// verif
		List<ReportDesign> designs = rs.getAllReportDesigns(false);
		Assert.assertEquals(1, rs.getAllReportDesigns(false).size());
		ReportDefinition def = designs.get(0).getReportDefinition();
		Assert.assertEquals("6c74e2ab-0e9b-4469-8901-8221f7d4b498", def.getUuid());
	}
	
	@Test
	public void isActive_shouldParseFromJsonConfig() {
		Assert.assertFalse(testReportManager.isActive());
	}
}
