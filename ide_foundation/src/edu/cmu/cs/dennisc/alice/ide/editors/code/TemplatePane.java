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
package edu.cmu.cs.dennisc.alice.ide.editors.code;

/**
 * @author Dennis Cosgrove
 */
public class TemplatePane extends edu.cmu.cs.dennisc.zoot.ZPageAxisPane {
	//public TemplatePane() {
	//}
	public TemplatePane( edu.cmu.cs.dennisc.property.InstancePropertyOwner owner ) {
		setTo( owner );
	}
	public void setTo( edu.cmu.cs.dennisc.property.InstancePropertyOwner owner ) {
		this.removeAll();
		if( owner != null ) {
			Class< ? > cls = owner.getClass();
			String value = edu.cmu.cs.dennisc.util.ResourceBundleUtilities.getStringFromSimpleNames( cls, "edu.cmu.cs.dennisc.alice.ast.Templates" );
			Template template = new Template( value );
			for( Line line : template.getLines() ) {
				this.add( new LinePane( owner, line ) );
			}
		} else {
			edu.cmu.cs.dennisc.alice.ide.editors.common.Label label = new edu.cmu.cs.dennisc.alice.ide.editors.common.Label( "null" );
			label.setOpaque( true );
			label.setBackground( java.awt.Color.RED );
			this.add( label );
		}
		this.revalidate();
		this.repaint();
	}
}
