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
public class ConfigurationPreferencePaneProxy extends PreferenceProxy<org.alice.ide.preferences.programming.Configuration> {
	class ConfigurationSelectionOperation extends org.alice.ide.operations.AbstractItemSelectionOperation<org.alice.ide.preferences.programming.Configuration> {
		public ConfigurationSelectionOperation( org.alice.ide.preferences.programming.Configuration... panes ) {
			super( new javax.swing.DefaultComboBoxModel( panes ) );
		}
		
		@Override
		protected void handleSelectionChange(org.alice.ide.preferences.programming.Configuration value) {
			ConfigurationPreferencePaneProxy.this.preview.updateValues(value);
		}
	}

	class ConfigurationComboBox extends edu.cmu.cs.dennisc.zoot.ZComboBox {
		public ConfigurationComboBox( org.alice.ide.preferences.programming.Configuration[] configurations ) {
			super( new ConfigurationSelectionOperation( configurations ) );
			//this.setCellRenderer( new PerspectiveListCellRenderer() );
		}
	}
	
	class ConfigurationPreview extends edu.cmu.cs.dennisc.croquet.swing.FormPane {
		private javax.swing.JLabel isDefaultFieldNameGenerationDesiredLabel;
		private javax.swing.JLabel isSyntaxNoiseDesiredLabel;
		private void ensureLabelsExist() {
			if( this.isDefaultFieldNameGenerationDesiredLabel != null ) {
				//pass
			} else {
				this.isDefaultFieldNameGenerationDesiredLabel = edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel();
				this.isDefaultFieldNameGenerationDesiredLabel.setForeground( java.awt.Color.GRAY );
			}
			if( this.isSyntaxNoiseDesiredLabel != null ) {
				//pass
			} else {
				this.isSyntaxNoiseDesiredLabel = edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel();
				this.isSyntaxNoiseDesiredLabel.setForeground( java.awt.Color.GRAY );
			}
		}
		@Override
		protected java.util.List<java.awt.Component[]> addComponentRows(java.util.List<java.awt.Component[]> rv) {
			this.ensureLabelsExist();
			rv.add( edu.cmu.cs.dennisc.swing.SpringUtilities.createRow( edu.cmu.cs.dennisc.swing.SpringUtilities.createColumn0Label( "isDefaultFieldNameGenerationDesired:" ), this.isDefaultFieldNameGenerationDesiredLabel ) );
			rv.add( edu.cmu.cs.dennisc.swing.SpringUtilities.createRow( edu.cmu.cs.dennisc.swing.SpringUtilities.createColumn0Label( "isSyntaxNoiseDesired:" ), this.isSyntaxNoiseDesiredLabel ) );
			return rv;
		}
		
		public void updateValues( org.alice.ide.preferences.programming.Configuration value ) {
			this.ensureLabelsExist();
			this.isDefaultFieldNameGenerationDesiredLabel.setText( Boolean.toString( value.isDefaultFieldNameGenerationDesired() ).toUpperCase() );
			this.isSyntaxNoiseDesiredLabel.setText( Boolean.toString( value.isSyntaxNoiseDesired() ).toUpperCase() );
		}
	}
	private edu.cmu.cs.dennisc.croquet.swing.PageAxisPane pane;
	private ConfigurationPreview preview;
	
	abstract class PreferencesActionOperation extends org.alice.ide.operations.AbstractActionOperation {
		public PreferencesActionOperation() {
			super( org.alice.ide.IDE.PREFERENCES_GROUP );
		}
	}
	class EditVariantOperation extends PreferencesActionOperation {
		public EditVariantOperation() {
			this.putValue( javax.swing.Action.NAME, "Edit..." );
		}
		public void perform(edu.cmu.cs.dennisc.zoot.ActionContext actionContext) {
		}
	}
	class RemoveVariantOperation extends PreferencesActionOperation {
		public RemoveVariantOperation() {
			this.putValue( javax.swing.Action.NAME, "Remove" );
		}
		public void perform(edu.cmu.cs.dennisc.zoot.ActionContext actionContext) {
		}
	}
	class NewVariantOperation extends PreferencesActionOperation {
		public NewVariantOperation() {
			this.putValue( javax.swing.Action.NAME, "New..." );
		}
		public void perform(edu.cmu.cs.dennisc.zoot.ActionContext actionContext) {
		}
	}
	class ImportVariantOperation extends PreferencesActionOperation {
		public ImportVariantOperation() {
			this.putValue( javax.swing.Action.NAME, "Import..." );
		}
		public void perform(edu.cmu.cs.dennisc.zoot.ActionContext actionContext) {
		}
	}
	public ConfigurationPreferencePaneProxy( edu.cmu.cs.dennisc.preference.Preference<org.alice.ide.preferences.programming.Configuration> preference ) {
		super( preference );
		org.alice.ide.preferences.programming.Configuration[] configurations = org.alice.ide.preferences.ProgrammingPreferences.getSingleton().getBuiltInPreferenceNodes();
		ConfigurationComboBox activeConfigurationComboBox = new ConfigurationComboBox( configurations );
		activeConfigurationComboBox.setSelectedIndex( 0 );
		EditVariantOperation editVariantOperation = new EditVariantOperation();
		editVariantOperation.setEnabled( false );
		editVariantOperation.setToolTipText( "coming soon" );
		RemoveVariantOperation removeVariantOperation = new RemoveVariantOperation();
		removeVariantOperation.setEnabled( false );
		removeVariantOperation.setToolTipText( "coming soon" );
		NewVariantOperation newVariantOperation = new NewVariantOperation();
		newVariantOperation.setEnabled( false );
		newVariantOperation.setToolTipText( "coming soon" );
		
		ImportVariantOperation importVariantOperation = new ImportVariantOperation();
		importVariantOperation.setEnabled( false );
		importVariantOperation.setToolTipText( "coming soon" );

		edu.cmu.cs.dennisc.croquet.swing.LineAxisPane northTopPane = new edu.cmu.cs.dennisc.croquet.swing.LineAxisPane( 
				activeConfigurationComboBox, 
				edu.cmu.cs.dennisc.zoot.ZManager.createButton( editVariantOperation ), 
				edu.cmu.cs.dennisc.zoot.ZManager.createButton( removeVariantOperation ) );
		edu.cmu.cs.dennisc.croquet.swing.LineAxisPane northBottomPane = new edu.cmu.cs.dennisc.croquet.swing.LineAxisPane( 
				edu.cmu.cs.dennisc.zoot.ZManager.createButton( newVariantOperation ), 
				edu.cmu.cs.dennisc.zoot.ZManager.createButton( importVariantOperation ), 
				javax.swing.Box.createHorizontalGlue() );

		this.preview = new ConfigurationPreview();
		this.preview.updateValues( (org.alice.ide.preferences.programming.Configuration)activeConfigurationComboBox.getSelectedItem() );
		this.pane = new edu.cmu.cs.dennisc.croquet.swing.PageAxisPane(
				edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel( "active variant:" ),
				javax.swing.Box.createVerticalStrut( 4 ),  
				northTopPane, 
				javax.swing.Box.createVerticalStrut( 4 ),  
				northBottomPane,
				javax.swing.Box.createVerticalStrut( 32 ),
				//edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel( "preview:" ),
				//javax.swing.Box.createVerticalStrut( 4 ),  
				this.preview
		);
		this.pane.setBorder( javax.swing.BorderFactory.createEmptyBorder( 4, 8, 4, 4 ) );
	}
	@Override
	public java.awt.Component getAWTComponent() {
		return this.pane;
	}
	@Override
	public void setAndCommitValue() {
		edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: ConfigurationPreferencePaneProxy" );
	}
}
