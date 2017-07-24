package org.openmrs.module.mksreports.definition.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openmrs.PatientIdentifierType;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.mksreports.MKSReportsConstants;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.patient.library.BuiltInPatientDataLibrary;
import org.openmrs.module.reporting.data.visit.definition.VisitDataDefinition;
import org.openmrs.module.reporting.data.visit.library.BuiltInVisitDataLibrary;
import org.openmrs.module.reporting.dataset.definition.VisitDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.manager.BaseReportManager;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutpatientRecordBook extends BaseReportManager {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private BuiltInPatientDataLibrary builtInPatientData;
	
	@Autowired
	private BuiltInVisitDataLibrary builtInVisitData;
	
	@Override
	public String getUuid() {
		return "6c74e2ab-0e9b-4469-8901-8221f7d4b498";
	}
	
	@Override
	public String getName() {
		return "HIS Outpatient Record Book";
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
	
	public OutpatientRecordBook() {
	};
	
	@Override
	public ReportDefinition constructReportDefinition() {
		
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		
		rd.setParameters(getParameters());
		
		// Visit details
		VisitDataSetDefinition vdsd = new VisitDataSetDefinition();
		
		// Visit ID
		VisitDataDefinition vdd = builtInVisitData.getVisitId();
		vdsd.addColumn("Visit ID", vdd, ObjectUtil.toString(Mapped.straightThroughMappings(vdd), "=", ","));
		
		// Patient ID
		vdsd.addColumn("Patient ID", builtInPatientData.getPatientId(),
		    ObjectUtil.toString(Mapped.straightThroughMappings(builtInPatientData.getPatientId()), "=", ","));
		
		// Patient Identifier
		PatientIdentifierType type = patientService.getPatientIdentifierTypeByUuid(Context.getAdministrationService()
		        .getGlobalProperty(MKSReportsConstants.GP_PATIENT_IDENTIFIER_TYPE_UUID));
		PatientIdentifierDataDefinition pidd = new PatientIdentifierDataDefinition();
		pidd.setTypes(Arrays.asList(type));
		
		vdsd.addColumn(MessageUtil.translate("${project.parent.artifactId}.report.registerLogbook.identifier.label"), pidd,
		    ObjectUtil.toString(Mapped.straightThroughMappings(pidd), "=", ","));
		
		rd.addDataSetDefinition("visits", Mapped.mapStraightThrough(vdsd));
		
		return rd;
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		ReportDesign design = ReportManagerUtil.createCsvReportDesign("9873e45d-f8a0-4682-be78-243b8c9b848c",
		    reportDefinition);
		List<ReportDesign> list = new ArrayList<ReportDesign>();
		list.add(design);
		return list;
	}
	
	@Override
	public String getVersion() {
		return "0.1.0-SNAPSHOT";
	}
	
}
