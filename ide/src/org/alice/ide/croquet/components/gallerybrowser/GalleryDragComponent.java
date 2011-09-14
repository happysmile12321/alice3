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

package org.alice.ide.croquet.components.gallerybrowser;

/**
 * @author Dennis Cosgrove
 */
public class GalleryDragComponent extends org.alice.ide.common.NodeLikeSubstance {
	public GalleryDragComponent( org.alice.ide.croquet.models.gallerybrowser.GalleryDragModel model ) {
		this.setDragModel( model );
		this.setLeftButtonClickModel( model.getLeftButtonClickModel() );
		org.lgna.croquet.components.Label label = new org.lgna.croquet.components.Label();
		label.setText( model.getText() );
		label.setIcon( model.getLargeIcon() );
		label.setVerticalTextPosition( org.lgna.croquet.components.VerticalTextPosition.BOTTOM );
		label.setHorizontalTextPosition( org.lgna.croquet.components.HorizontalTextPosition.CENTER );
		this.setBackgroundColor( new java.awt.Color( 0xf7e4b6 ) );
		this.internalAddComponent( label );
		this.setBorder( javax.swing.BorderFactory.createEmptyBorder( 0, 0, 8, 0 ) );
	}
	@Override
	protected java.awt.geom.RoundRectangle2D.Float createShape( int x, int y, int width, int height ) {
		return new java.awt.geom.RoundRectangle2D.Float( x, y, width-1, height-1, 8, 8 );
	}
	@Override
	protected void fillBounds(java.awt.Graphics2D g2, int x, int y, int width, int height) {
		g2.fill( this.createShape(x, y, width, height));
	}
	@Override
	protected int getInsetTop() {
		return 0;
	}
	@Override
	protected int getInsetRight() {
		return 0;
	}
	@Override
	protected int getInsetBottom() {
		return 0;
	}

	@Override
	protected int getDockInsetLeft() {
		return 0;
	}
	@Override
	protected int getInternalInsetLeft() {
		return 0;
	}
	@Override
	protected void paintPrologue(java.awt.Graphics2D g2, int x, int y, int width, int height) {
		java.awt.geom.RoundRectangle2D rr = new java.awt.geom.RoundRectangle2D.Float( x+1, y+1, width-3, height-3, 8, 8 );
		g2.fill( rr );
	}
}
