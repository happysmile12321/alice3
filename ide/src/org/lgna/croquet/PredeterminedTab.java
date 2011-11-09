/*
 * Copyright (c) 2006-2010, Carnegie Mellon University. All rights reserved.
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
 */
package org.lgna.croquet;

import org.lgna.croquet.components.JComponent;
import org.lgna.croquet.components.ScrollPane;

/**
 * @author Dennis Cosgrove
 */
public abstract class PredeterminedTab extends Composite {
	private String titleText;
	private javax.swing.Icon titleIcon;
	private JComponent< ? > mainComponent;

	private org.lgna.croquet.BooleanState booleanState;
	//todo: remove
	private org.lgna.croquet.components.AbstractButton< ?, org.lgna.croquet.BooleanState > button = null;
	
	public PredeterminedTab( java.util.UUID id ) {
		super( id );
	}
	@Override
	protected void localize() {
		this.setTitleText( this.getDefaultLocalizedText() );
	}
	@Override
	public org.lgna.croquet.components.View<?,?> createView() {
		System.err.println( "todo: createView " + this );
		return null;
	}
	public String getTitleText() {
		return this.titleText;
	}
	private void setTitleText( String titleText ) {
		this.titleText = titleText;
		this.updateTitleText();
	}
	public javax.swing.Icon getTitleIcon() {
		return this.titleIcon;
	}
	public void setTitleIcon( javax.swing.Icon titleIcon ) {
		this.titleIcon = titleIcon;
		this.updateTitleIcon();
	}
	private void updateTitleText() {
		if( this.button != null ) {
			this.booleanState.setTextForBothTrueAndFalse( this.titleText );
		}
	}
	private void updateTitleIcon() {
		if( this.button != null ) {
			this.button.getAwtComponent().setIcon( this.titleIcon );
		}
	}
	public void customizeTitleComponent( org.lgna.croquet.BooleanState booleanState, org.lgna.croquet.components.AbstractButton< ?, org.lgna.croquet.BooleanState > button ) {
		this.initializeIfNecessary();
		this.booleanState = booleanState;
		this.button = button;
		this.updateTitleText();
		this.updateTitleIcon();
	}
    public void releaseTitleComponent( org.lgna.croquet.BooleanState booleanState, org.lgna.croquet.components.AbstractButton< ?, org.lgna.croquet.BooleanState > button ) {
    }
	
	
	protected abstract JComponent<?> createMainComponent();
	public JComponent<?> getMainComponent() {
		if( this.mainComponent != null ) {
			//pass
		} else {
			this.mainComponent = this.createMainComponent();
		}
		return this.mainComponent;
	}
	public ScrollPane createScrollPane() {
		ScrollPane rv = new ScrollPane();
		rv.setBorder( javax.swing.BorderFactory.createEmptyBorder() );
		return rv;
	}
	@Override
	public boolean contains( Model model ) {
		System.err.println( "todo: PredeterminedTab contains" );
		return false;
	}
	@Override
	protected StringBuilder appendRepr( StringBuilder rv ) {
		rv.append( this.titleText );
		return rv;
	}
};
