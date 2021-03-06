/*
 * Copyright (c) 2002-2017 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.values;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.neo4j.values.Values.booleanArray;
import static org.neo4j.values.Values.booleanValue;
import static org.neo4j.values.Values.byteArray;
import static org.neo4j.values.Values.byteValue;
import static org.neo4j.values.Values.charArray;
import static org.neo4j.values.Values.charValue;
import static org.neo4j.values.Values.doubleArray;
import static org.neo4j.values.Values.doubleValue;
import static org.neo4j.values.Values.floatArray;
import static org.neo4j.values.Values.floatValue;
import static org.neo4j.values.Values.intArray;
import static org.neo4j.values.Values.intValue;
import static org.neo4j.values.Values.longArray;
import static org.neo4j.values.Values.longValue;
import static org.neo4j.values.Values.shortArray;
import static org.neo4j.values.Values.shortValue;
import static org.neo4j.values.Values.stringArray;
import static org.neo4j.values.Values.stringValue;

public class ValuesTest
{
    @Test
    public void shouldBeEqualToItself()
    {
        assertEqual( booleanValue( false ), booleanValue( false ) );
        assertEqual( byteValue( (byte)0 ), byteValue( (byte)0 ) );
        assertEqual( shortValue( (short)0 ), shortValue( (short)0 ) );
        assertEqual( intValue( 0 ), intValue( 0 ) );
        assertEqual( longValue( 0 ), longValue( 0 ) );
        assertEqual( floatValue( 0.0f ), floatValue( 0.0f ) );
        assertEqual( doubleValue( 0.0 ), doubleValue( 0.0 ) );
        assertEqual( stringValue( "" ), stringValue( "" ) );

        assertEqual( booleanValue( true ), booleanValue( true ) );
        assertEqual( byteValue( (byte)1 ), byteValue( (byte)1 ) );
        assertEqual( shortValue( (short)1 ), shortValue( (short)1 ) );
        assertEqual( intValue( 1 ), intValue( 1 ) );
        assertEqual( longValue( 1 ), longValue( 1 ) );
        assertEqual( floatValue( 1.0f ), floatValue( 1.0f ) );
        assertEqual( doubleValue( 1.0 ), doubleValue( 1.0 ) );
        assertEqual( charValue( 'x' ), charValue( 'x' ) );
        assertEqual( stringValue( "hi" ), stringValue( "hi" ) );

        assertEqual( booleanArray( new boolean[]{} ), booleanArray( new boolean[]{} ) );
        assertEqual( byteArray( new byte[]{} ), byteArray( new byte[]{} ) );
        assertEqual( shortArray( new short[]{} ), shortArray( new short[]{} ) );
        assertEqual( intArray( new int[]{} ), intArray( new int[]{} ) );
        assertEqual( longArray( new long[]{} ), longArray( new long[]{} ) );
        assertEqual( floatArray( new float[]{} ), floatArray( new float[]{} ) );
        assertEqual( doubleArray( new double[]{} ), doubleArray( new double[]{} ) );
        assertEqual( charArray( new char[]{} ), charArray( new char[]{} ) );
        assertEqual( stringArray( new String[]{} ), stringArray( new String[]{} ) );

        assertEqual( booleanArray( new boolean[]{true} ), booleanArray( new boolean[]{true} ) );
        assertEqual( byteArray( new byte[]{1} ), byteArray( new byte[]{1} ) );
        assertEqual( shortArray( new short[]{1} ), shortArray( new short[]{1} ) );
        assertEqual( intArray( new int[]{1} ), intArray( new int[]{1} ) );
        assertEqual( longArray( new long[]{1} ), longArray( new long[]{1} ) );
        assertEqual( floatArray( new float[]{1.0f} ), floatArray( new float[]{1.0f} ) );
        assertEqual( doubleArray( new double[]{1.0} ), doubleArray( new double[]{1.0} ) );
        assertEqual( charArray( new char[]{'x'} ), charArray( new char[]{'x'} ) );
        assertEqual( stringArray( new String[]{"hi"} ), stringArray( new String[]{"hi"} ) );
    }

    private void assertEqual( Value a, Value b )
    {
        assertTrue( "should be equal", a.equals( b ) );
        assertTrue( "should be equal", b.equals( a ) );
        assertTrue( "should have same has", a.hashCode() == b.hashCode() );
    }
}
