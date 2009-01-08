/*
 * Copyright (c) 2006-2009, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 * 3. Products derived from the software may not be called "Alice",
 *    nor may "Alice" appear in their name, without prior written
 *    permission of Carnegie Mellon University.
 * 
 * 4. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes software developed by Carnegie Mellon University"
 */
package org.alice.apis.storytelling;

/**
 * @author Dennis Cosgrove
 */
public enum ClapStyle {
	NORMAL( Adult.Cycle.CLAP_BECAUSE_YOU_ARE_EXPECTED_TO_CLAP ),
	WITH_VIGOR( Adult.Cycle.CLAP_ENTHUSIASTICALLY ),
	WITH_EXTRA_VIGOR( Adult.Cycle.APPLAUD ),
	WITH_FIST_PUMP( Adult.Cycle.CLAP_WITH_FIST_PUMP ),
	QUIET( Adult.Cycle.CLAP_QUIETLY );
	private Adult.Cycle m_subjectCycle;
	ClapStyle( Adult.Cycle subjectCycle ) {
		m_subjectCycle = subjectCycle;
	}
	public Adult.Cycle getSubjectCycle() {
		return m_subjectCycle;
	}
	public static ClapStyle getRandom() {
		return edu.cmu.cs.dennisc.random.RandomUtilities.getRandomEnumConstant( ClapStyle.class );
	}
}
	
