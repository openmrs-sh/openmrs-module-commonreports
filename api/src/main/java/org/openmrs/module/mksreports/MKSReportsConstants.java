/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.mksreports;

public class MKSReportsConstants {
	
	/*
	 * Module ids
	 */
	public static final String MODULE_NAME = "MKS Reports";
	
	public static final String MODULE_ARTIFACT_ID = "mksreports";
	
	/*
	 * Spring components qualifiers
	 */
	public static final String COMPONENT_CONTEXT = MODULE_ARTIFACT_ID + ".MKSReportsContext";
	
	/*
	 * Global properties
	 */
	public static final String GP_PATIENT_IDENTIFIER_TYPE_UUID = MODULE_ARTIFACT_ID
	        + ".outpatientRecordBook.patientIdentfierTypeUuid";
	
}
