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
package org.alice.media;

import java.io.File;

import edu.cmu.cs.dennisc.matt.EventScript;

/**
 * @author Dennis Cosgrove
 */
public class ExportToYouTubeWizardDialogComposite extends org.lgna.croquet.WizardDialogComposite {
	private static class SingletonHolder {
		private static ExportToYouTubeWizardDialogComposite instance = new ExportToYouTubeWizardDialogComposite();
	}
	public static ExportToYouTubeWizardDialogComposite getInstance() {
		return SingletonHolder.instance;
	}
	
	private final EventRecordComposite eventRecordComposite = new EventRecordComposite( this );
	private final ImageRecordComposite imageRecordComposite = new ImageRecordComposite( this );
	private final UploadComposite uploadComposite = new UploadComposite( this );
	
	private org.lgna.project.Project project;
	private EventScript script;
	private File file = new File( "C:/Users/Matt/Desktop/videos/testOne.mov" );
	
	private ExportToYouTubeWizardDialogComposite() {
		super( java.util.UUID.fromString( "c3542871-3346-4228-a872-1c5641c14e9d" ), org.alice.ide.IDE.EXPORT_GROUP );
		this.addWizardPageComposite( this.eventRecordComposite );
		this.addWizardPageComposite( this.imageRecordComposite );
		this.addWizardPageComposite( this.uploadComposite );
	}
	public org.lgna.project.Project getProject() {
		return this.project;
	}
	public void setProject( org.lgna.project.Project project ) {
		this.project = project;
	}
	public static void main( final String[] args ) throws Exception {
		org.lgna.croquet.Application application = new org.lgna.croquet.simple.SimpleApplication();
		final org.lgna.project.Project project = org.lgna.project.io.IoUtilities.readProject( args[ 0 ] );
		javax.swing.SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				ExportToYouTubeWizardDialogComposite.getInstance().setProject( project );
				ExportToYouTubeWizardDialogComposite.getInstance().getOperation().fire();
				System.exit( 0 );
			}
		} );
	}
	public EventScript getScript() {
		return this.script;
	}
	public void setScript( EventScript script ) {
		this.script = script;
	}
	public File getFile() {
		return file;
	}
	public void setFile( File file ) {
		this.file = file;
	}
}
