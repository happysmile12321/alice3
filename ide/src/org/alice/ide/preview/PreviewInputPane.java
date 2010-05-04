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
package org.alice.ide.preview;

/**
 * @author Dennis Cosgrove
 */
public abstract class PreviewInputPane<T> extends org.alice.ide.RowsInputPanel< T > {
//	protected static javax.swing.JLabel createLabel( String s ) {
//		javax.swing.JLabel rv = edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel( s );
//		rv.setHorizontalAlignment( javax.swing.SwingConstants.TRAILING );
//		return rv;
//	}
	class PreviewPane extends org.alice.ide.Component {
		public void refresh() {
			this.forgetAndRemoveAllComponents();
//			java.awt.Component component = new edu.cmu.cs.dennisc.croquet.swing.LineAxisPane(
//					PreviewInputPane.this.createPreviewSubComponent(),
//					javax.swing.Box.createHorizontalGlue()
//			);
			this.addComponent( PreviewInputPane.this.createPreviewSubComponent(), java.awt.BorderLayout.WEST );
			this.revalidateAndRepaint();
		}
		@Override
		protected java.awt.LayoutManager createLayoutManager( javax.swing.JPanel jPanel ) {
			return new java.awt.BorderLayout();
		}
		@Override
		protected boolean contains( int x, int y, boolean jContains ) {
			return super.contains( x, y, jContains );
		}
		@Override
		protected void adding() {
			super.adding();
			this.refresh();
		}
		@Override
		protected void removed() {
			this.forgetAndRemoveAllComponents();
			super.removed();
		}
	}

	private java.awt.Component rowsSpringPane;
	private PreviewPane previewPane;
	private edu.cmu.cs.dennisc.croquet.KComponent< ? > spacer;

	public PreviewInputPane() {
		final int INSET = 16;
		this.setBorder( javax.swing.BorderFactory.createEmptyBorder( INSET, INSET, INSET, INSET ) );
	}
	protected abstract edu.cmu.cs.dennisc.croquet.KComponent< ? > createPreviewSubComponent();
	private void updatePreview() {
		if( this.previewPane != null ) {
			this.previewPane.refresh();
		}
	}
//	@Override
//	public java.awt.Dimension getPreferredSize() {
//		return edu.cmu.cs.dennisc.java.awt.DimensionUtilties.constrainToMinimumWidth( super.getPreferredSize(), 320 );
//	}

	protected abstract java.util.List< edu.cmu.cs.dennisc.croquet.KComponent< ? >[] > updateInternalComponentRows( java.util.List< edu.cmu.cs.dennisc.croquet.KComponent< ? >[] > rv );

	@Override
	protected final java.util.List< edu.cmu.cs.dennisc.croquet.KComponent< ? >[] > updateComponentRows( java.util.List< edu.cmu.cs.dennisc.croquet.KComponent< ? >[] > rv, edu.cmu.cs.dennisc.croquet.KRowsSpringPanel panel ) {
		this.previewPane = new PreviewPane();
		this.spacer = this.getIDE().createRigidArea( new java.awt.Dimension( 0, 32 ) );
		rv.add( 
				edu.cmu.cs.dennisc.croquet.SpringUtilities.createRow(
						edu.cmu.cs.dennisc.croquet.SpringUtilities.createTrailingLabel( "preview:" ),
						this.previewPane
				) 
		);
		this.updateInternalComponentRows( rv );
		rv.add( 
				edu.cmu.cs.dennisc.croquet.SpringUtilities.createRow(
						this.spacer,
						null
				) 
		);
		panel.setAlignmentX( 0.0f );
		return rv;
	}
//	private java.awt.Component createRowsSpringPane() {
//		return rv;
//	}
//	@Override
//	public void addNotify() {
//		super.addNotify();
//		if( this.rowsSpringPane != null ) {
//			//pass
//		} else {
//			this.rowsSpringPane = this.createRowsSpringPane();
//			this.add( this.rowsSpringPane, java.awt.BorderLayout.WEST );
//		}
//		this.updateOKButton();
//	}

	@Override
	public void updateOKButton() {
		super.updateOKButton();
		this.updatePreview();
//todo: croquet switch
//		this.updateSizeIfNecessary();
	}
	
	@Override
	protected boolean paintComponent( java.awt.Graphics2D g2 ) {
		if( this.spacer != null ) {
			int y = this.spacer.getY() + this.spacer.getHeight();
			java.awt.Insets insets = this.getInsets();
			g2.setColor( java.awt.Color.GRAY );
			g2.drawLine( insets.left, y, this.getWidth()-insets.right, y );
		}
		return super.paintComponent( g2 );
	}
}
