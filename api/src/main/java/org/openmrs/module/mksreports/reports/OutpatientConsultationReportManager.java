package org.openmrs.module.mksreports.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.module.initializer.api.InitializerService;
import org.openmrs.module.mksreports.MKSReportManager;
import org.openmrs.module.mksreports.MKSReportsConstants;
import org.openmrs.module.reporting.cohort.definition.CodedObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.GenderCohortDefinition;
import org.openmrs.module.reporting.common.SetComparator;
import org.openmrs.module.reporting.dataset.definition.CohortCrossTabDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(MKSReportsConstants.COMPONENT_REPORTMANAGER_OPDCONSULT)
public class OutpatientConsultationReportManager extends MKSReportManager {
	
	@Autowired
	private InitializerService inizService;
	
	@Override
	public boolean isActive() {
		return inizService.getBooleanFromKey("report.opdconsult.active", true);
	}
	
	@Override
	public String getVersion() {
		return "1.0.0-SNAPSHOT";
	}
	
	@Override
	public String getUuid() {
		return "58d7a2ba-5b62-4e21-ac21-090e3758cce7";
	}
	
	@Override
	public String getName() {
		return "HC1 Outpatient Consultation";
	}
	
	@Override
	public String getDescription() {
		return "";
	}
	
	private Parameter getStartDateParameter() {
		return new Parameter("startDate", "Start Date", Date.class);
	}
	
	private Parameter getEndDateParameter() {
		return new Parameter("endDate", "End Date", Date.class);
	}
	
	@Override
	public List<Parameter> getParameters() {
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(getStartDateParameter());
		params.add(getEndDateParameter());
		return params;
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		
		rd.setParameters(getParameters());
		
		CohortCrossTabDataSetDefinition OPDConsult = new CohortCrossTabDataSetDefinition();
		
		Concept allDiags = inizService.getConceptFromKey("report.opdconsult.diagnosesList.concept");
		
		Map<String, Object> parameterMappings = new HashMap<String, Object>();
		parameterMappings.put("onOrAfter", "${startDate}");
		parameterMappings.put("onOrBefore", "${endDate}");
		
		// Add a row for each member of allDiags concept
		for (Concept member : allDiags.getSetMembers()) {
			CodedObsCohortDefinition diag = new CodedObsCohortDefinition();
			diag.addParameter(new Parameter("onOrAfter", "On Or After", Date.class));
			diag.addParameter(new Parameter("onOrBefore", "On Or Before", Date.class));
			diag.setOperator(SetComparator.IN);
			diag.setQuestion(inizService.getConceptFromKey("report.opdconsult.diagnosisQuestion.concept"));
			
			diag.setValueList(Arrays.asList(member));
			OPDConsult.addRow(member.getDisplayString(), diag, parameterMappings);
		}
		
		// Add the 'Males' column
		GenderCohortDefinition males = new GenderCohortDefinition();
		males.setMaleIncluded(true);
		OPDConsult.addColumn("Males", males, null);
		
		// Add the 'Females' column
		GenderCohortDefinition females = new GenderCohortDefinition();
		females.setFemaleIncluded(true);
		OPDConsult.addColumn("Females", females, null);
		
		rd.addDataSetDefinition("Outpatient Consultation", Mapped.mapStraightThrough(OPDConsult));
		return rd;
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		return Arrays.asList(ReportManagerUtil.createCsvReportDesign("42b32ac1-fcd0-473d-8fdb-71fd6fc2e26d",
		    reportDefinition));
	}
}
