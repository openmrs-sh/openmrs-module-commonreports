package org.openmrs.module.mksreports.definition.data.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.module.mksreports.definition.data.ObsOnAgeDataDefinition;
import org.openmrs.module.reporting.common.Age;
import org.openmrs.module.reporting.data.patient.service.PatientDataService;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.data.person.service.PersonDataService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.springframework.beans.factory.annotation.Autowired;

@Handler(supports = ObsOnAgeDataDefinition.class)
public class ObsOnAgeDataEvaluator implements PersonDataEvaluator {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	PatientDataService patientDataService;
	
	@Autowired
	PersonDataService personDataService;
	
	@Override
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		
		ObsOnAgeDataDefinition arithmeticDD = (ObsOnAgeDataDefinition) definition;
		
		EvaluatedPersonData evaluatedPersonData = new EvaluatedPersonData(arithmeticDD, context);
		
		EvaluatedPersonData obsData = personDataService.evaluate(arithmeticDD.getObs(), context);
		EvaluatedPersonData ageData = personDataService.evaluate(arithmeticDD.getAge(), context);
		
		for (Integer pid : obsData.getData().keySet()) {
			
			Obs obs = (Obs) obsData.getData().get(pid);
			Age age = (Age) ageData.getData().get(pid);
			
			Double result = (Double) null;
			
			if (obs.getValueNumeric() == null) {
				log.warn("The observation on which to apply the division operation "
				        + " is a non-numeric observation: [Obs #" + obs.getObsId() + "]. Returning null.");
			} else if (age.getFullYears() == 0) {
				log.warn("The patient's age [Patient #" + pid + "] is 0 years. Returning null.");
			} else {
				result = obs.getValueNumeric() / age.getFullYears();
			}
			evaluatedPersonData.getData().put(pid, result);
		}
		
		return evaluatedPersonData;
	}
	
}
