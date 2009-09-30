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
package org.alice.ide.preferencesinputpane;

/**
 * @author Dennis Cosgrove
 */
public class InnerPreferencesPane extends edu.cmu.cs.dennisc.croquet.PageAxisPane {
	private java.util.prefs.Preferences prefs;
	private edu.cmu.cs.dennisc.preference.Preference<?>[] preferences;
	public InnerPreferencesPane( Class<?> clsWithinPackage, edu.cmu.cs.dennisc.preference.Preference<?>[] preferences ) {
		this.prefs = java.util.prefs.Preferences.userNodeForPackage( clsWithinPackage );
		this.preferences = preferences;
		for( edu.cmu.cs.dennisc.preference.Preference<?> preference : this.preferences ) {
			this.add( createUIFor( preference ) );
		}
		this.add( javax.swing.Box.createVerticalGlue() );
	}
	private java.awt.Component createUIFor( edu.cmu.cs.dennisc.preference.Preference<?> preference ) {
		java.awt.Component rv;
		if( preference instanceof edu.cmu.cs.dennisc.preference.BooleanPreference ) {
			edu.cmu.cs.dennisc.preference.BooleanPreference booleanPreference = (edu.cmu.cs.dennisc.preference.BooleanPreference)preference;
			javax.swing.JCheckBox checkbox = new javax.swing.JCheckBox( booleanPreference.getKey() );
			checkbox.setSelected( booleanPreference.getValue(this.prefs) );
			rv = checkbox;
		} else if( preference instanceof edu.cmu.cs.dennisc.preference.IntPreference ) {
			edu.cmu.cs.dennisc.preference.IntPreference intPreference = (edu.cmu.cs.dennisc.preference.IntPreference)preference;
			edu.cmu.cs.dennisc.zoot.ZLabel label = edu.cmu.cs.dennisc.zoot.ZLabel.acquire( preference.getKey() + ": " );
			javax.swing.SpinnerNumberModel model = new javax.swing.SpinnerNumberModel( (int)intPreference.getValue( this.prefs ), 0, 16, 1 );
			javax.swing.JSpinner spinner = new javax.swing.JSpinner( model );
			rv = new edu.cmu.cs.dennisc.croquet.LineAxisPane( label, javax.swing.Box.createHorizontalStrut( 8 ), spinner );
		} else {
			rv = new javax.swing.JLabel( preference.getKey() );
		}
		return rv;
	}
}
