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

package edu.cmu.cs.dennisc.scenegraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.cmu.cs.dennisc.codec.BinaryDecoder;
import edu.cmu.cs.dennisc.codec.BinaryEncodableAndDecodable;
import edu.cmu.cs.dennisc.codec.BinaryEncoder;
import edu.cmu.cs.dennisc.math.AffineMatrix4x4;
import edu.cmu.cs.dennisc.math.Vector3;

public class WeightInfo implements BinaryEncodableAndDecodable {
  private Map<String, InverseAbsoluteTransformationWeightsPair> mapReferencesToInverseAbsoluteTransformationWeightsPairs;

  public WeightInfo() {
    this.mapReferencesToInverseAbsoluteTransformationWeightsPairs = new HashMap<String, InverseAbsoluteTransformationWeightsPair>();
  }

  public void addReference(String reference, InverseAbsoluteTransformationWeightsPair poInverseAbsoluteTransformationWeightsPair) {
    this.mapReferencesToInverseAbsoluteTransformationWeightsPairs.put(reference, poInverseAbsoluteTransformationWeightsPair);
  }

  public InverseAbsoluteTransformationWeightsPair getWeightInfoForJoint(Joint joint) {
    return this.mapReferencesToInverseAbsoluteTransformationWeightsPairs.get(joint.jointID.getValue());
  }

  public Map<String, InverseAbsoluteTransformationWeightsPair> getMap() {
    return this.mapReferencesToInverseAbsoluteTransformationWeightsPairs;
  }

  public void scale(Vector3 scale) {
    Map<String, InverseAbsoluteTransformationWeightsPair> mapReferencesToInverseAbsoluteTransformationWeightsPairs = getMap();
    for (Entry<String, InverseAbsoluteTransformationWeightsPair> pair : mapReferencesToInverseAbsoluteTransformationWeightsPairs.entrySet()) {
      InverseAbsoluteTransformationWeightsPair iatwp = pair.getValue();
      AffineMatrix4x4 originalInverseTransform = iatwp.getInverseAbsoluteTransformation();
      AffineMatrix4x4 newTransform = AffineMatrix4x4.createInverse(originalInverseTransform);
      //These need to have the scale removed just from the translation
      newTransform.translation.multiply(scale);
      newTransform.invert();
      iatwp.setInverseAbsoluteTransformation(newTransform);
    }
  }

  public WeightInfo createCopy() {
    WeightInfo newWeightInfo = new WeightInfo();
    for (Entry<String, InverseAbsoluteTransformationWeightsPair> pair : mapReferencesToInverseAbsoluteTransformationWeightsPairs.entrySet()) {
      newWeightInfo.mapReferencesToInverseAbsoluteTransformationWeightsPairs.put(pair.getKey(), pair.getValue().createCopy());
    }
    return newWeightInfo;
  }

  public void decode(BinaryDecoder binaryDecoder) {
    int count = binaryDecoder.decodeInt();
    this.mapReferencesToInverseAbsoluteTransformationWeightsPairs.clear();
    for (int i = 0; i < count; i++) {
      String reference = binaryDecoder.decodeString();
      InverseAbsoluteTransformationWeightsPair inverseAbsoluteTransformationWeightsPair = binaryDecoder.decodeBinaryEncodableAndDecodable();
      this.mapReferencesToInverseAbsoluteTransformationWeightsPairs.put(reference, inverseAbsoluteTransformationWeightsPair);
      //            Object o = binaryDecoder.decodeBinaryEncodableAndDecodable();
      //            assert o instanceof InverseAbsoluteTransformationWeightsPair;
      //            this.mapReferencesToInverseAbsoluteTransformationWeightsPairs.put(reference, (InverseAbsoluteTransformationWeightsPair)o);
    }
  }

  @Override
  public void encode(BinaryEncoder binaryEncoder) {
    binaryEncoder.encode(this.mapReferencesToInverseAbsoluteTransformationWeightsPairs.size());
    for (Entry<String, InverseAbsoluteTransformationWeightsPair> entry : this.mapReferencesToInverseAbsoluteTransformationWeightsPairs.entrySet()) {
      binaryEncoder.encode(entry.getKey());
      binaryEncoder.encode(entry.getValue());
    }
  }

}
