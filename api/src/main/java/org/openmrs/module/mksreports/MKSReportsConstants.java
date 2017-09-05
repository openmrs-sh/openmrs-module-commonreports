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
	public static final String COMPONENT_CONTEXT = MODULE_ARTIFACT_ID + ".mksReportsContext";
	
	public static final String COMPONENT_REPORTMANAGER_OPDRECBOOK = MODULE_ARTIFACT_ID + ".outpatientRecordBook";
	
	/*
	 * Global properties
	 */
	
	public static final String GP_PHONE_NUMBER_UUID = MODULE_ARTIFACT_ID + ".phoneNumberUuid";
	
	public static final String GP_2ND_PHONE_NUMBER_UUID = MODULE_ARTIFACT_ID + ".2ndPhoneNumberUuid";
	
	/*
	 * UUIDs (not set as GP, since they are processed at the creation of the report (once and for all),
	 * therefore using a GP which is supposed to have runtime impact could lead to confusion.
	 */
	public static final String DISTANCE_FROM_HC_PERSON_ATTRIBUTE_TYPE_UUID = "1df3e8c6-564e-4da9-9094-5d36f68100c9";
	
	public static final String DRUG_ORDER_TYPE_UUID = "131168f4-15f5-102d-96e4-000c29c2a5d7";
}
