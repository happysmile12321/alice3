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

package org.alice.ide.croquet.models.ast;

import edu.cmu.cs.dennisc.java.util.Maps;
import org.alice.ide.IDE;
import org.alice.ide.cascade.ExpressionCascadeContext;
import org.alice.ide.cascade.ExpressionPropertyContext;
import org.alice.ide.croquet.codecs.NodeCodec;
import org.lgna.croquet.Application;
import org.lgna.croquet.CascadeBlankChild;
import org.lgna.croquet.CustomItemStateWithInternalBlank;
import org.lgna.croquet.imp.cascade.BlankNode;
import org.lgna.project.ast.Expression;
import org.lgna.project.ast.UserField;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Dennis Cosgrove
 */
public class FieldInitializerState extends CustomItemStateWithInternalBlank<Expression> {
  private static Map<UserField, FieldInitializerState> map = Maps.newHashMap();

  public static synchronized FieldInitializerState getInstance(UserField field) {
    FieldInitializerState rv = map.get(field);
    if (rv == null) {
      rv = new FieldInitializerState(field);
      map.put(field, rv);
    }
    return rv;
  }

  private final UserField field;
  private ExpressionCascadeContext pushedContext;

  private FieldInitializerState(UserField field) {
    super(Application.PROJECT_GROUP, UUID.fromString("7df7024e-5eef-4ed0-b463-da3719955e7a"), field.initializer.getValue(), NodeCodec.getInstance(Expression.class));
    this.field = field;
  }

  @Override
  protected void prologue() {
    pushedContext = new ExpressionPropertyContext(field.initializer);
    IDE.getActiveInstance().getExpressionCascadeManager().pushContext(pushedContext);
    super.prologue();
  }

  @Override
  protected void epilogue() {
    super.epilogue();
    IDE.getActiveInstance().getExpressionCascadeManager().popAndCheckContext(pushedContext);
    pushedContext = null;
  }

  @Override
  protected Expression getSwingValue() {
    return this.field.initializer.getValue();
  }

  @Override
  protected void setSwingValue(Expression nextValue) {
    this.field.initializer.setValue(nextValue);
  }

  @Override
  protected void updateBlankChildren(List<CascadeBlankChild> blankChildren, BlankNode<Expression> blankNode) {
    IDE.getActiveInstance().getExpressionCascadeManager().appendItems(blankChildren, blankNode, this.field.getValueType(), null);
  }
}
