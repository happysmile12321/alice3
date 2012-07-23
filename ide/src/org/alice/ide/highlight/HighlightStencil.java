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

package org.alice.ide.highlight;

/**
 * @author Dennis Cosgrove
 */
public class HighlightStencil extends org.lgna.croquet.components.LayerStencil {
	private static final int INSET = 4;
	private static final java.awt.Insets INSETS = new java.awt.Insets( INSET, INSET, INSET, INSET );
	private static final java.awt.Color STENCIL_BASE_COLOR = new java.awt.Color( 181, 140, 140, 150 );
	private static final java.awt.Color STENCIL_LINE_COLOR = new java.awt.Color( 92, 48, 24, 63 );
	private static java.awt.Paint createStencilPaint() {
		int width = 8;
		int height = 8;
		java.awt.image.BufferedImage image = new java.awt.image.BufferedImage( width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB );
		java.awt.Graphics2D g2 = (java.awt.Graphics2D)image.getGraphics();
		g2.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_OFF );
		g2.setColor( STENCIL_BASE_COLOR );
		g2.fillRect( 0, 0, width, height );
		g2.setColor( STENCIL_LINE_COLOR );
		g2.drawLine( 0, height, width, 0 );
		g2.fillRect( 0, 0, 1, 1 );
		g2.dispose();
		return new java.awt.TexturePaint( image, new java.awt.Rectangle( 0, 0, width, height ) );
	}
	private static final java.awt.Paint stencilPaint = createStencilPaint();

	private final org.lgna.cheshire.simple.ScrollRenderer scrollRenderer = new org.lgna.cheshire.simple.SimpleScrollRenderer();
	private final org.lgna.stencil.Note note = new org.lgna.stencil.Note();
	public HighlightStencil( org.lgna.croquet.components.AbstractWindow<?> window, Integer layerId ) {
		super( window, layerId );
		this.note.setActive( true );
		this.internalAddComponent( this.note );
	}
	@Override
	protected boolean contains( int x, int y, boolean superContains ) {
		return superContains;
	}
	@Override
	protected java.awt.LayoutManager createLayoutManager( javax.swing.JPanel jPanel ) {
		return new java.awt.FlowLayout() {
			@Override
			public void layoutContainer( java.awt.Container target ) {
				super.layoutContainer( target );
				note.setLocation( note.calculateLocation( HighlightStencil.this ) );
			}
		};
	}
	
	@Override
	protected void paintComponentPrologue( java.awt.Graphics2D g2 ) {
		java.awt.Shape prevClip = g2.getClip();
		java.awt.Paint prevPaint = g2.getPaint();
		java.awt.Stroke prevStroke = g2.getStroke();

		java.awt.Shape shape = prevClip;
		java.awt.geom.Area area = new java.awt.geom.Area( shape );
		for( org.lgna.cheshire.simple.Feature feature : note.getFeatures() ) {
			java.awt.geom.Area featureAreaToSubtract = feature.getAreaToSubstractForPaint( HighlightStencil.this );
			if( featureAreaToSubtract != null ) {
				area.subtract( featureAreaToSubtract );
				shape = area;
			}
		}
		g2.setPaint( stencilPaint );
		g2.fill( shape );

		g2.setStroke( prevStroke );
		g2.setPaint( prevPaint );
	}
	@Override
	protected void paintComponentEpilogue( java.awt.Graphics2D g2 ) {
		if( note.isActive() ) {
			for( org.lgna.cheshire.simple.Feature feature : note.getFeatures() ) {
				feature.paint( g2, HighlightStencil.this, note );
			}
		}
	}

	@Override
	protected void paintEpilogue( java.awt.Graphics2D g2 ) {
		if( note.isActive() ) {
			for( org.lgna.cheshire.simple.Feature feature : note.getFeatures() ) {
				org.lgna.croquet.components.TrackableShape trackableShape = feature.getTrackableShape();
				if( trackableShape != null ) {
					if( trackableShape.isInView() ) {
						//pass
					} else {
						if( scrollRenderer != null ) {
							java.awt.Shape repaintShape = scrollRenderer.renderScrollIndicators( g2, HighlightStencil.this, trackableShape );
							if( repaintShape != null ) {
								//todo: repaint?
							}
						}
					}
				}
			}
		}
	}
	@Override
	protected void processMouseEvent( java.awt.event.MouseEvent e ) {
		if( e.getID() == java.awt.event.MouseEvent.MOUSE_PRESSED ) {
			this.setStencilShowing( false );
		}
	}
	public void show( org.lgna.croquet.resolvers.RuntimeResolver< org.lgna.croquet.components.TrackableShape > trackableShapeResolver, final String noteText ) {
		this.note.removeAllFeatures();
		org.lgna.cheshire.simple.stencil.features.Hole hole = new org.lgna.cheshire.simple.stencil.features.Hole( trackableShapeResolver, org.lgna.cheshire.simple.Feature.ConnectionPreference.NORTH_SOUTH ) {
			@Override
			protected boolean isPathRenderingDesired() {
				return noteText.length() > 0;
			}
		};
		this.note.addFeature( hole );
		this.note.setText( noteText );
		this.note.reset();
		this.setStencilShowing( true );
	}
}
