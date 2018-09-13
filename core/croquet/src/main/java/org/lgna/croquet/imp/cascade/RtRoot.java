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

package org.lgna.croquet.imp.cascade;

import edu.cmu.cs.dennisc.java.lang.reflect.ReflectionUtilities;
import edu.cmu.cs.dennisc.java.util.logging.Logger;
import org.lgna.croquet.CancelException;
import org.lgna.croquet.CascadeRoot;
import org.lgna.croquet.CompletionModel;
import org.lgna.croquet.history.UserActivity;
import org.lgna.croquet.triggers.ActionEventTrigger;
import org.lgna.croquet.triggers.PopupMenuEventTrigger;
import org.lgna.croquet.views.MenuItemContainer;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.event.ActionEvent;

/**
 * @author Dennis Cosgrove
 */
public class RtRoot<T, CM extends CompletionModel> extends RtBlankOwner<T[], T, CascadeRoot<T, CM>, RootNode<T, CM>> {
	public RtRoot( CascadeRoot<T, CM> element ) {
		super( element, RootNode.createInstance( element ), null, -1 );
	}

	@Override
	public RtRoot<T, CM> getRtRoot() {
		return this;
	}

	@Override
	public RtBlank<?> getNearestBlank() {
		return null;
	}

	@Override
	public void select() {
	}

	public final T[] createValues( Class<T> componentType ) {
		RtBlank<T>[] rtBlanks = this.getBlankChildren();
		T[] rv = ReflectionUtilities.newTypedArrayInstance( componentType, rtBlanks.length );
		for( int i = 0; i < rtBlanks.length; i++ ) {
			T value = rtBlanks[ i ].createValue();
			try {
				rv[ i ] = value;
			} catch( ArrayStoreException ase ) {
				Logger.errln( "value:", value, "componentType:", componentType );
				throw ase;
			}
		}
		return rv;
	}

	public void cancel( UserActivity activity ) {
		getElement().handleCancel( activity );
		activity.cancel();
	}

	public void complete( UserActivity activity ) {
		try {
			getElement().handleCompletion( activity, this );
		} catch( CancelException ce ) {
			cancel( activity );
		}
	}

	void handleActionPerformed( ActionEvent e ) {
		this.complete( ActionEventTrigger.createUserActivity( e ) );
	}

	public PopupMenuListener createPopupMenuListener( final MenuItemContainer menuItemContainer ) {
		return new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible( PopupMenuEvent e ) {
				RtRoot.this.addNextNodeMenuItems( menuItemContainer );
			}

			@Override
			public void popupMenuWillBecomeInvisible( PopupMenuEvent e ) {
				RtRoot.this.removeAll( menuItemContainer );
			}

			@Override
			public void popupMenuCanceled( PopupMenuEvent e ) {
				RtRoot.this.cancel( PopupMenuEventTrigger.createUserActivity( null, e ) );
			}
		};
	}
}
