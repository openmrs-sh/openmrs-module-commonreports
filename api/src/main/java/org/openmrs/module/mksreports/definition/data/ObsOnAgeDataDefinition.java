package org.openmrs.module.mksreports.definition.data;

import org.openmrs.module.reporting.data.BaseDataDefinition;
import org.openmrs.module.reporting.data.person.definition.AgeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ObsForPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;

public class ObsOnAgeDataDefinition extends BaseDataDefinition implements PersonDataDefinition {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ConfigurationProperty(required = true)
	private ObsForPersonDataDefinition obs;
	
	@ConfigurationProperty(required = true)
	private AgeDataDefinition age;
	
	/**
	 * Default Constructor
	 */
	public ObsOnAgeDataDefinition() {
		super();
	}
	
	@Override
	public Class<?> getDataType() {
		return Object.class;
	}
	
	/**
	 * Getters and setters
	 */
	public ObsForPersonDataDefinition getObs() {
		return obs;
	}
	
	public void setObs(ObsForPersonDataDefinition obs) {
		this.obs = obs;
	}
	
	public AgeDataDefinition getAge() {
		return age;
	}
	
	public void setAge(AgeDataDefinition age) {
		this.age = age;
	}
	
}
