/**
 * Copyright (c) 2006-2012, Carnegie Mellon University. All rights reserved.
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
package org.alice.ide.croquet.models.project.views;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.BevelBorder;

import org.alice.ide.croquet.models.project.FindComposite;
import org.alice.ide.croquet.models.project.TreeNodesAndManagers.SearchObject;
import org.lgna.croquet.components.BorderPanel;
import org.lgna.croquet.components.GridPanel;
import org.lgna.croquet.components.List;
import org.lgna.croquet.components.ScrollPane;
import org.lgna.project.ast.Expression;
import org.lgna.project.ast.MethodInvocation;
import org.lgna.project.ast.UserLambda;
import org.lgna.project.ast.UserMethod;

import edu.cmu.cs.dennisc.math.GoldenRatio;

/**
 * @author Matt May
 */
public class FindView extends BorderPanel {

	public FindView( FindComposite composite ) {
		super( composite );
		this.addPageStartComponent( composite.getSearchState().createTextArea() );
		GridPanel panel = GridPanel.createGridPane( 1, 2 );
		panel.setPreferredSize( GoldenRatio.createWiderSizeFromHeight( 250 ) );
		List<SearchObject> searchResultsList = composite.getSearchResults().createList();
		List<Expression> resultReferencesList = composite.getReferenceResults().createList();
		searchResultsList.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
		resultReferencesList.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
		panel.addComponent( new ScrollPane( searchResultsList ) );
		searchResultsList.setCellRenderer( new org.alice.ide.croquet.models.project.views.renderers.SearchResultListCellRenderer() );
		resultReferencesList.setCellRenderer( new edu.cmu.cs.dennisc.javax.swing.renderers.ListCellRenderer<Object>() {

			@Override
			protected JLabel getListCellRendererComponent( JLabel rv, JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
				assert value instanceof Expression;
				Expression expression = (Expression)value;
				UserMethod userMethod = expression.getFirstAncestorAssignableTo( UserMethod.class );
				UserLambda userLambda = expression.getFirstAncestorAssignableTo( UserLambda.class );
				if( userLambda != null ) {
					rv.setText( userLambda.getFirstAncestorAssignableTo( MethodInvocation.class ).method.getValue().getName() );
				} else {
					rv.setText( userMethod.getName() );
				}
				return rv;
			}
		} );
		panel.addComponent( new ScrollPane( resultReferencesList ) );
		this.addCenterComponent( panel );
	}
}
