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
package org.alice.ide.inputpanes;

/**
 * @author Dennis Cosgrove
 */
public class CreateParameterPane extends CreateDeclarationPane<edu.cmu.cs.dennisc.alice.ast.ParameterDeclaredInAlice> {
	private edu.cmu.cs.dennisc.alice.ast.CodeDeclaredInAlice ownerCode;
	public CreateParameterPane( edu.cmu.cs.dennisc.alice.ast.CodeDeclaredInAlice ownerCode ) {
		this.ownerCode = ownerCode;
	}
	@Override
	protected java.awt.Component[] createDeclarationRow() {
		return null;
	}
	@Override
	protected java.awt.Component[] createInitializerRow() {
		return null;
	}
	@Override
	protected java.awt.Component[] createValueTypeRow() {
		return null;
	}
	@Override
	protected edu.cmu.cs.dennisc.alice.ast.ParameterDeclaredInAlice getActualInputValue() {
		return null;
	}
//	@Override
//	protected edu.cmu.cs.dennisc.alice.ast.ParameterDeclaredInAlice getActualInputValue() {
//		return new edu.cmu.cs.dennisc.alice.ast.ParameterDeclaredInAlice( this.getNameText(), this.getValueType() );
//	}
//	@Override
//	protected boolean isNameAcceptable( java.lang.String name ) {
//		//todo: check parameters for collision
//		return true;
//	}
}
