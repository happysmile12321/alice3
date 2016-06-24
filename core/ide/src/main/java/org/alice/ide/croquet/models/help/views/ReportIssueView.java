/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may
 *    "Alice" appear in their name, without prior written permission of
 *    Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software
 *    developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is
 *    contributed by Electronic Arts Inc. and may be used for personal,
 *    non-commercial, and academic use only. Redistributions of any program
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in
 *    The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
 * ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT
 * SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package org.alice.ide.croquet.models.help.views;

import java.awt.Color;
import java.util.List;

import javax.swing.Icon;

import org.alice.ide.croquet.models.help.ReportIssueComposite;
import org.alice.ide.issue.swing.views.HeaderPane;
import org.lgna.croquet.views.BorderPanel;
import org.lgna.croquet.views.FormPanel;
import org.lgna.croquet.views.Hyperlink;
import org.lgna.croquet.views.Label;
import org.lgna.croquet.views.LabeledFormRow;
import org.lgna.croquet.views.PageAxisPanel;
import org.lgna.croquet.views.VerticalAlignment;

import edu.cmu.cs.dennisc.javax.swing.IconUtilities;

/**
 * @author Matt May
 */
public class ReportIssueView extends AbstractIssueView {
	private final static Icon headerIcon = IconUtilities.createImageIcon( HeaderPane.class.getResource( "images/logo.png" ) );

	public ReportIssueView( final ReportIssueComposite composite ) {
		super( composite );
		final org.lgna.croquet.views.TextArea environmentTextArea = composite.getEnvironmentState().createTextArea();
		//environmentTextArea.getAwtComponent().setEditable( false );
		//environmentTextArea.setToolTipText( edu.cmu.cs.dennisc.toolkit.issue.IssueReportPane.getEnvironmentShortDescription() );
		FormPanel centerComponent = new FormPanel() {
			@Override
			protected void appendRows( List<LabeledFormRow> rows ) {
				rows.add( new LabeledFormRow( composite.getVisibilityState().getSidekickLabel(), composite.getVisibilityState().createVerticalDefaultRadioButtons(), VerticalAlignment.TOP ) );
				rows.add( new LabeledFormRow( composite.getReportTypeState().getSidekickLabel(), composite.getReportTypeState().getPrepModel().createComboBoxWithItemCodecListCellRenderer(), VerticalAlignment.CENTER, false ) );
				rows.add( new LabeledFormRow( composite.getSummaryState().getSidekickLabel(), composite.getSummaryState().createTextField() ) );
				rows.add( new LabeledFormRow( composite.getDescriptionState().getSidekickLabel(), createScrollPaneTextArea( composite.getDescriptionState() ), VerticalAlignment.TOP ) );
				rows.add( new LabeledFormRow( composite.getStepsState().getSidekickLabel(), createScrollPaneTextArea( composite.getStepsState() ), VerticalAlignment.TOP ) );
				rows.add( new LabeledFormRow( composite.getEnvironmentState().getSidekickLabel(), environmentTextArea, VerticalAlignment.TOP ) );
				rows.add( new LabeledFormRow( composite.getAttachmentState().getSidekickLabel(), composite.getAttachmentState().createVerticalDefaultRadioButtons(), VerticalAlignment.TOP ) );
			}
		};

		Color backgroundColor = Color.DARK_GRAY;
		Label headerLabel = new Label();
		headerLabel.setIcon( headerIcon );
		headerLabel.setAlignmentX( 0.5f );
		Hyperlink link = composite.getBrowserOperation().createHyperlink();
		link.setForegroundColor( Color.LIGHT_GRAY );
		link.getAwtComponent().setBackground( backgroundColor );
		link.setAlignmentX( 0.5f );

		PageAxisPanel lineStartPanel = new PageAxisPanel( headerLabel, link );

		BorderPanel header = new BorderPanel();
		header.addLineStartComponent( lineStartPanel );

		org.alice.ide.IDE ide = org.alice.ide.IDE.getActiveInstance();
		if( ide != null ) {
			org.alice.ide.IdeConfiguration ideConfiguration = ide.getIdeConfiguration();
			org.lgna.issue.IssueReportingHub issueReportingHub = ideConfiguration.getIssueReportingHub();
			if( issueReportingHub.isLoginSupported() ) {
				header.addLineEndComponent( composite.getLogInOutCardComposite().getView() );
			}
		}
		header.setBackgroundColor( backgroundColor );
		header.setBorder( javax.swing.BorderFactory.createEmptyBorder( 8, 8, 8, 8 ) );
		centerComponent.setBorder( javax.swing.BorderFactory.createEmptyBorder( 8, 8, 8, 8 ) );

		this.addPageStartComponent( header );
		this.addCenterComponent( centerComponent );
	}
}
