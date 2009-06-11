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
package edu.cmu.cs.dennisc.math;

/**
 * @author Dennis Cosgrove
 */
public class AngleInRadians implements Angle {
	private double m_radians;
	public AngleInRadians( double radians ) {
		m_radians = radians;
	}
	public AngleInRadians( Angle other ) {
		this( other.getAsRadians() );
	}
	
	public void decode(edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder) {
		m_radians = binaryDecoder.decodeDouble();
	}
	public void encode(edu.cmu.cs.dennisc.codec.BinaryEncoder binaryEncoder) {
		binaryEncoder.encode( m_radians );
	}

	@Override
	public boolean equals( Object obj ) {
		if( obj instanceof Angle ) {
			Angle a = (Angle)obj;
			return m_radians == a.getAsRadians();
		} else {
			return false;
		}
	}
	public boolean isNaN() {
		return Double.isNaN( m_radians );
	}
	public void setNaN() {
		m_radians = Double.NaN;
	}
	public double getAsRadians() {
		return m_radians;
	}
	public double getAsDegrees() {
		return edu.cmu.cs.dennisc.math.AngleUtilities.radiansToDegrees( m_radians );
	}
	public double getAsRevolutions() {
		return edu.cmu.cs.dennisc.math.AngleUtilities.radiansToRevolutions( m_radians );
	}
	public void setAsRadians( double radians ) {
		m_radians = radians;
	}
	public void setAsDegrees( double degrees ) {
		m_radians = AngleUtilities.degreesToRadians( degrees );
	}
	public void setAsRevolutions( double revolutions ) {
		m_radians = AngleUtilities.revolutionsToRadians( revolutions );
	}
	public Angle createCopy() {
		return new AngleInRadians( this );
	}
	public void set( Angle other ) {
		setAsRadians( other.getAsRadians() );
	}
	
	public void setToInterpolation(Angle a0, Angle a1, double portion) {
		setAsRadians( InterpolationUtilities.interpolate( a0.getAsRadians(), a1.getAsRadians(), portion ) );
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( getClass().getName() );
		sb.append( "[" );
		sb.append( m_radians );
		sb.append( "]" );
		return sb.toString();
	}
}
