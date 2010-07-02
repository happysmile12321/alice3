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
package org.alice.stageide.gallerybrowser;

/**
 * @author Dennis Cosgrove
 */
public class GalleryDragComponent extends org.alice.ide.common.NodeLikeSubstance {
//	public static GalleryDragComponent getInstance( edu.cmu.cs.dennisc.javax.swing.models.TreeNode<String> treeNode ) {
//		return new GalleryDragComponent( treeNode );
//	}
	
	private edu.cmu.cs.dennisc.javax.swing.models.TreeNode<String> treeNode;
	public GalleryDragComponent( edu.cmu.cs.dennisc.javax.swing.models.TreeNode<String> treeNode, String name ) {
		this.treeNode = treeNode;
		edu.cmu.cs.dennisc.croquet.Label label = new edu.cmu.cs.dennisc.croquet.Label();
		label.setIcon( ResourceManager.getLargeIcon( this.treeNode ) );
		label.setText( name );
		label.setVerticalTextPosition( edu.cmu.cs.dennisc.croquet.VerticalTextPosition.BOTTOM );
		label.setHorizontalTextPosition( edu.cmu.cs.dennisc.croquet.HorizontalTextPosition.CENTER );
		this.setAlignmentY( 0.0f );
		this.setBackgroundColor( new java.awt.Color( 0xf7e4b6 ) );
		this.addComponent( label );
		this.setLeftButtonClickOperation( GalleryFileActionOperation.getInstance( treeNode ) );
		this.setDragAndDropOperation( new org.alice.ide.operations.DefaultDragOperation( edu.cmu.cs.dennisc.alice.Project.GROUP ) );
		this.getAwtComponent().setOpaque( false );
	}
	
	public edu.cmu.cs.dennisc.javax.swing.models.TreeNode<String> getTreeNode() {
		return this.treeNode;
	}

	@Override
	protected void fillBounds(java.awt.Graphics2D g2, int x, int y, int width, int height) {
		g2.fillRoundRect(x, y, width, height, 8, 8);
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
	protected int getInsetBottom() {
		return 8;
	}

	@Override
	protected int getInsetRight() {
		return 0;
	}

	@Override
	protected int getInsetTop() {
		return 0;
	}

	@Override
	protected void paintPrologue(java.awt.Graphics2D g2, int x, int y, int width, int height) {
		java.awt.geom.RoundRectangle2D rr = new java.awt.geom.RoundRectangle2D.Float( x+1, y+1, width-3, height-3, 8, 8 );
		g2.fill( rr );
	}
	protected void paintOutline( java.awt.Graphics2D g2, java.awt.geom.RoundRectangle2D.Float rr ) {
		java.awt.Stroke prevStroke = g2.getStroke();
		if( this.isActive() ) {
			g2.setPaint( java.awt.Color.BLUE );
			g2.setStroke( new java.awt.BasicStroke( 3.0f ) );
		} else {
			g2.setPaint( java.awt.Color.GRAY );
		}
		g2.draw( rr );
		g2.setStroke( prevStroke );
	}
	@Override
	protected void paintEpilogue( java.awt.Graphics2D g2, int x, int y, int width, int height ) {
		super.paintEpilogue( g2, x, y, width, height );
		this.paintOutline( g2, new java.awt.geom.RoundRectangle2D.Float( x+1, y+1, width-3, height-3, 8, 8 ) );
	}
}

