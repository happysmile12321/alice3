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

package org.alice.ide.ast.resource;

/**
 * @author Dennis Cosgrove
 */
public abstract class ResourceImportValueCreator<R extends org.lgna.common.Resource> extends org.lgna.croquet.ImportValueCreator<R> {
	private final java.util.Set<String> lowerCaseExtensions;
	public ResourceImportValueCreator( java.util.UUID migrationId, String... lowerCaseSupportedExtensions ) {
		super( migrationId );
		this.lowerCaseExtensions = java.util.Collections.unmodifiableSet( edu.cmu.cs.dennisc.java.util.Collections.newHashSet( lowerCaseSupportedExtensions ) );
	}
	protected abstract R createResourceFromFile( java.io.File file ) throws java.io.IOException;
	@Override
	protected R createValue( java.io.File file ) {
		String extension = edu.cmu.cs.dennisc.java.io.FileUtilities.getExtension( file );
		if( extension != null && this.lowerCaseExtensions.contains( extension.toLowerCase() ) ) {
			try {
				return this.createResourceFromFile( file );
			} catch( java.io.IOException ioe ) {
				//todo
				throw new RuntimeException( file.getAbsolutePath(), ioe );
			}
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append( "File extension for \"" );
			sb.append( file.getName() );
			sb.append( "\" is not in the supported set: { " );
			String prefix = "";
			for( String s : this.lowerCaseExtensions ) {
				sb.append( prefix );
				sb.append( s );
				prefix = ", ";
			}
			sb.append( " }." );
			org.alice.ide.IDE.getActiveInstance().showMessageDialog( sb.toString(), "Content Type Not Supported", org.lgna.croquet.MessageType.ERROR );
			return null;
		}
	}
}
