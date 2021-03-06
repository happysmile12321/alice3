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
package org.alice.ide.common;

import edu.cmu.cs.dennisc.java.awt.ComponentUtilities;
import edu.cmu.cs.dennisc.java.awt.GraphicsContext;
import org.alice.ide.croquet.models.ui.formatter.FormatterState;
import org.alice.ide.x.AstI18nFactory;
import org.alice.ide.x.components.StatementListPropertyView;
import org.lgna.croquet.DragModel;
import org.lgna.croquet.views.imp.JDragView;
import org.lgna.project.ast.DoTogether;
import org.lgna.project.ast.Statement;
import org.lgna.project.ast.StatementListProperty;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;

/**
 * @author Dennis Cosgrove
 */
public class DefaultStatementPane extends AbstractStatementPane {
  private int maxYForIfBlock = -1;

  public DefaultStatementPane(DragModel model, AstI18nFactory factory, Statement statement, StatementListProperty owner) {
    super(model, factory, statement, owner);
    this.addComponent(factory.createComponent(statement));
  }

  public int getMaxYForIfBlock() {
    return this.maxYForIfBlock;
  }

  public void setMaxYForIfBlock(int maxYForIfBlock) {
    this.maxYForIfBlock = maxYForIfBlock;
  }

  @Override
  protected void paintEpilogue(Graphics2D g2, int x, int y, int width, int height) {
    super.paintEpilogue(g2, x, y, width, height);
    if (FormatterState.isJava()) {
      Statement statement = this.getStatement();
      if (statement instanceof DoTogether) {
        JDragView jDragView = this.getAwtComponent();
        StatementListPropertyView.FeedbackJPanel feedbackJPanel = ComponentUtilities.findFirstMatch(jDragView, StatementListPropertyView.FeedbackJPanel.class);
        if (feedbackJPanel != null) {
          Insets insets = this.getInsets();
          GraphicsContext gc = GraphicsContext.getInstanceAndPushGraphics(g2);
          gc.pushAndSetTextAntialiasing(true);
          gc.pushPaint();
          g2.setColor(Color.BLACK);
          try {
            final int N = feedbackJPanel.getComponentCount();
            for (int i = 1; i < N; i++) {
              Component componentI = feedbackJPanel.getComponent(i);
              Point p = new Point(0, 0);
              Point pThis = SwingUtilities.convertPoint(componentI, p, jDragView);
              g2.drawString("}, () -> {", insets.left + x, pThis.y - 6);
            }
          } finally {
            gc.popAll();
          }
          //        for( org.lgna.croquet.views.AwtComponentView<?> awtComponentView : ( (org.lgna.croquet.views.AwtContainerView<?>)( (org.lgna.croquet.views.AwtContainerView<?>)this.getComponent( 0 ) ).getComponent( 1 ) ).getComponents() ) {
          //          edu.cmu.cs.dennisc.java.util.logging.Logger.outln( awtComponentView );
          //          if( awtComponentView instanceof org.alice.ide.x.components.StatementListPropertyView ) {
          //            org.alice.ide.x.components.StatementListPropertyView statementListPropertyView = (org.alice.ide.x.components.StatementListPropertyView)awtComponentView;
          //          }
        }
      }
    }
  }
}
