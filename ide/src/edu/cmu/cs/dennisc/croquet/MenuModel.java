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
package edu.cmu.cs.dennisc.croquet;

/**
 * @author Dennis Cosgrove
 */
public abstract class MenuModel extends Model {
	private static final Group MENU_GROUP = new Group( java.util.UUID.fromString( "4ed42b1f-b4ea-4f70-99d1-5bb2c3f11081" ), "MENU_GROUP" );
	public static final Model SEPARATOR = null;
	private class MenuListener implements javax.swing.event.MenuListener {
		private Menu<MenuModel> menu;
		public MenuListener( Menu<MenuModel> menu ) {
			this.menu = menu;
		}
		public void menuSelected( javax.swing.event.MenuEvent e ) {
			MenuModel.this.handleMenuSelected( e, this.menu );
		}
		public void menuDeselected( javax.swing.event.MenuEvent e ) {
			MenuModel.this.handleMenuDeselected( e, this.menu );
		}
		public void menuCanceled( javax.swing.event.MenuEvent e ) {
			MenuModel.this.handleMenuCanceled( e, this.menu );
		}
	};
	private Class<?> clsForI18N;
	private java.util.Map< Menu<MenuModel>, MenuListener > mapMenuToListener = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	private javax.swing.Action action = new javax.swing.AbstractAction() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
		}
	};
	public MenuModel( java.util.UUID individualId, Class<?> clsForI18N ) {
		super( MENU_GROUP, individualId );
		this.clsForI18N = clsForI18N;
		this.localize();
	}
	public MenuModel( java.util.UUID individualId ) {
		this( individualId, null );
	}
	
	@Override
	/*package-private*/ void localize() {
		if( clsForI18N != null ) {
			//pass
		} else {
			clsForI18N = this.getClass();
		}
		this.setName( getDefaultLocalizedText( clsForI18N ) );
		this.action.putValue( javax.swing.Action.MNEMONIC_KEY, getLocalizedMnemonicKey( clsForI18N ) );
		this.action.putValue( javax.swing.Action.ACCELERATOR_KEY, getLocalizedAcceleratorKeyStroke( clsForI18N ) );
	}
	
	public void setName( String name ) {
		this.action.putValue( javax.swing.Action.NAME, name );
	}
	public void setSmallIcon( javax.swing.Icon icon ) {
		this.action.putValue( javax.swing.Action.SMALL_ICON, icon );
	}
	
	protected void handleMenuSelected( javax.swing.event.MenuEvent e, Menu<MenuModel> menu ) {
		Component< ? > parent = menu.getParent();
		ModelContext< ? > parentContext;
		if( parent instanceof MenuBar ) {
			MenuBar menuBar = (MenuBar)parent;
			parentContext = menuBar.createMenuBarContext();
		} else {
			Application application = Application.getSingleton();
			parentContext = application.getCurrentContext();
		}
		MenuModelContext context = parentContext.createMenuModelContext( MenuModel.this, menu );
		context.handleMenuSelected( e );
	}
	protected void handleMenuDeselected( javax.swing.event.MenuEvent e, Menu<MenuModel> menu ) {
		Application application = Application.getSingleton();
		MenuModelContext context = (MenuModelContext)application.getCurrentContext();
		context.handleMenuDeselected( e );
	}
	protected void handleMenuCanceled( javax.swing.event.MenuEvent e, Menu<MenuModel> menu ) {
		Application application = Application.getSingleton();
		MenuModelContext context = (MenuModelContext)application.getCurrentContext();
		context.handleMenuCanceled( e );
	}
	/*package-private*/ Menu<MenuModel> createMenu() {
		Menu<MenuModel> rv = new Menu<MenuModel>( this ) {
			@Override
			protected void handleAddedTo(edu.cmu.cs.dennisc.croquet.Component<?> parent) {
				this.getAwtComponent().setAction( MenuModel.this.action );
				assert mapMenuToListener.containsKey( this ) == false;
				MenuListener menuListener = new MenuListener( this );
				MenuModel.this.mapMenuToListener.put( this, menuListener );
				this.getAwtComponent().addMenuListener( menuListener );
				super.handleAddedTo( parent );
			}

			@Override
			protected void handleRemovedFrom(edu.cmu.cs.dennisc.croquet.Component<?> parent) {
				super.handleRemovedFrom( parent );
				MenuModel.this.removeComponent( this );
				MenuListener menuListener = MenuModel.this.mapMenuToListener.get( this );
				assert menuListener != null;
				this.getAwtComponent().removeMenuListener( menuListener );
				MenuModel.this.mapMenuToListener.remove( this );
				this.getAwtComponent().setAction( null );
			}
		};
		return rv;
	};
}
