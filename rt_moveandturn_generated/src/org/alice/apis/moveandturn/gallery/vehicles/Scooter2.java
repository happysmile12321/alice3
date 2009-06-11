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
package org.alice.apis.moveandturn.gallery.vehicles;
	
public class Scooter2 extends org.alice.apis.moveandturn.gallery.GalleryModel {
	public Scooter2() {
		super( "Vehicles/Scooter2" );
	}
	public enum Part {
		Ring( "Ring" ),
		RightFloor( "RightFloor" ),
		TailPipe_RightWheel( "TailPipe", "RightWheel" ),
		TailPipe_LeftWheel( "TailPipe", "LeftWheel" ),
		TailPipe_TailPipe( "TailPipe", "TailPipe" ),
		TailPipe( "TailPipe" ),
		Shaft_RightGrip( "Shaft", "RightGrip" ),
		Shaft_Shaft( "Shaft", "Shaft" ),
		Shaft_FrontWheel( "Shaft", "FrontWheel" ),
		Shaft_Dash( "Shaft", "Dash" ),
		Shaft_LeftGrip( "Shaft", "LeftGrip" ),
		Shaft( "Shaft" ),
		LeftFloor( "LeftFloor" ),
		Lid_Knob( "Lid", "Knob" ),
		Lid_Lid( "Lid", "Lid" ),
		Lid( "Lid" );
		private String[] m_path;
		Part( String... path ) {
			m_path = path;
		}
		public String[] getPath() {
			return m_path;
		}
	}
	public org.alice.apis.moveandturn.Model getPart( Part part ) {
		return getDescendant( org.alice.apis.moveandturn.Model.class, part.getPath() );
	}

}
