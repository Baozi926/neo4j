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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

import static java.lang.String.format;

/**
 * Entry point to the values library.
 * <p>
 * The values library centers around the Value class, which represents a value in Neo4j. Values can be correctly
 * checked for equality over different primitive representations, including consistent hashCodes and sorting.
 * <p>
 * To create Values use the factory methods in the Values class.
 * <p>
 * Values come in two major categories: Storable and Virtual. Storable values are valid values for
 * node, relationship and graph properties. Virtual values are not supported as property values, but might be created
 * and returned as part of cypher execution. These include Node, Relationship and Path.
 */
@SuppressWarnings( "WeakerAccess" )
public final class Values
{
    public static final Value MIN_NUMBER = Values.doubleValue( Double.NEGATIVE_INFINITY );
    public static final Value MAX_NUMBER = Values.doubleValue( Double.NaN );
    public static final Value MIN_STRING = Values.stringValue( "" );
    public static final Value MAX_STRING = Values.booleanValue( false );

    private Values()
    {
    }

    /**
     * Default value comparator. Will correctly compare all storable values and order the value groups according the
     * to comparability group.
     */
    public static final Comparator<Value> COMPARATOR = new ValueComparator( ValueGroup::compareTo );

    public static boolean isNumberValue( Object value )
    {
        return value instanceof NumberValue;
    }

    public static boolean isBooleanValue( Object value )
    {
        return value instanceof BooleanValue;
    }

    public static boolean isTextValue( Object value )
    {
        return value instanceof TextValue;
    }

    public static boolean isArrayValue( Value value )
    {
        return value instanceof ArrayValue;
    }

    public static double coerceToDouble( Value value )
    {
        if ( value instanceof IntegralValue )
        {
            return ((IntegralValue) value).longValue();
        }
        if ( value instanceof FloatingPointValue )
        {
            return ((FloatingPointValue) value).doubleValue();
        }
        throw new UnsupportedOperationException( format( "Cannot coerce %s to double", value ) );
    }

    // DIRECT FACTORY METHODS

    public static final Value NO_VALUE = NoValue.NO_VALUE;

    public static TextValue stringValue( String value )
    {
        return new StringValue.Direct( value );
    }

    public static Value stringOrNoValue( String value )
    {
        if ( value == null )
        {
            return NO_VALUE;
        }
        else
        {
            return new StringValue.Direct( value );
        }
    }

    public static Value numberValue( Number number )
    {
        if ( number instanceof Long )
        {
            return longValue( number.longValue() );
        }
        if ( number instanceof Integer )
        {
            return intValue( number.intValue() );
        }
        if ( number instanceof Double )
        {
            return doubleValue( number.doubleValue() );
        }
        if ( number instanceof Byte )
        {
            return byteValue( number.byteValue() );
        }
        if ( number instanceof Float )
        {
            return floatValue( number.floatValue() );
        }
        if ( number instanceof Short )
        {
            return shortValue( number.shortValue() );
        }
        if ( number == null )
        {
            return NO_VALUE;
        }

        throw new UnsupportedOperationException( "Unsupported type of Number " + number.toString() );
    }

    public static Value longValue( long value )
    {
        return new LongValue( value );
    }

    public static Value intValue( int value )
    {
        return new IntValue( value );
    }

    public static Value shortValue( short value )
    {
        return new ShortValue( value );
    }

    public static Value byteValue( byte value )
    {
        return new ByteValue( value );
    }

    public static Value booleanValue( boolean value )
    {
        return new BooleanValue( value );
    }

    public static Value charValue( char value )
    {
        return new CharValue( value );
    }

    public static Value doubleValue( double value )
    {
        return new DoubleValue( value );
    }

    public static Value floatValue( float value )
    {
        return new FloatValue( value );
    }

    public static Value stringArray( String[] value )
    {
        return new StringArray.Direct( value );
    }

    public static Value byteArray( byte[] value )
    {
        return new ByteArray.Direct( value );
    }

    public static Value longArray( long[] value )
    {
        return new LongArray.Direct( value );
    }

    public static Value intArray( int[] value )
    {
        return new IntArray.Direct( value );
    }

    public static Value doubleArray( double[] value )
    {
        return new DoubleArray.Direct( value );
    }

    public static Value floatArray( float[] value )
    {
        return new FloatArray.Direct( value );
    }

    public static Value booleanArray( boolean[] value )
    {
        return new BooleanArray.Direct( value );
    }

    public static Value charArray( char[] value )
    {
        return new CharArray.Direct( value );
    }

    public static Value shortArray( short[] value )
    {
        return new ShortArray.Direct( value );
    }

    // BOXED FACTORY METHODS

    /**
     * Generic value factory method.
     * <p>
     * Beware, this method is intended for converting externally supplied values to the internal Value type, and to
     * make testing convenient. Passing a Value as in parameter should never be needed, and will throw an
     * UnsupportedOperationException.
     * <p>
     * This method does defensive copying of arrays, while the explicit *Array() factory methods do not.
     *
     * @param value Object to convert to Value
     * @return the created Value
     */
    public static Value of( Object value )
    {
        return of( value, true );
    }

    public static Value of( Object value, boolean allowNull )
    {
        if ( value instanceof String )
        {
            return stringValue( (String) value );
        }
        if ( value instanceof Object[] )
        {
            return arrayValue( (Object[]) value );
        }
        if ( value instanceof Long )
        {
            return longValue( (Long) value );
        }
        if ( value instanceof Integer )
        {
            return intValue( (Integer) value );
        }
        if ( value instanceof Boolean )
        {
            return booleanValue( (Boolean) value );
        }
        if ( value instanceof Double )
        {
            return doubleValue( (Double) value );
        }
        if ( value instanceof Float )
        {
            return floatValue( (Float) value );
        }
        if ( value instanceof Short )
        {
            return shortValue( (Short) value );
        }
        if ( value instanceof Byte )
        {
            return byteValue( (Byte) value );
        }
        if ( value instanceof Character )
        {
            return charValue( (Character) value );
        }
        if ( value instanceof byte[] )
        {
            return byteArray( ((byte[]) value).clone() );
        }
        if ( value instanceof long[] )
        {
            return longArray( ((long[]) value).clone() );
        }
        if ( value instanceof int[] )
        {
            return intArray( ((int[]) value).clone() );
        }
        if ( value instanceof double[] )
        {
            return doubleArray( ((double[]) value).clone() );
        }
        if ( value instanceof float[] )
        {
            return floatArray( ((float[]) value).clone() );
        }
        if ( value instanceof boolean[] )
        {
            return booleanArray( ((boolean[]) value).clone() );
        }
        if ( value instanceof char[] )
        {
            return charArray( ((char[]) value).clone() );
        }
        if ( value instanceof short[] )
        {
            return shortArray( ((short[]) value).clone() );
        }
        if ( value == null )
        {
            if ( allowNull )
            {
                return NoValue.NO_VALUE;
            }
            throw new IllegalArgumentException( "[null] is not a supported property value" );
        }
        if ( value instanceof Value )
        {
            throw new UnsupportedOperationException(
                    "Converting a Value to a Value using Values.of() is not supported." );
        }

        // otherwise fail
        throw new IllegalArgumentException(
                format( "[%s:%s] is not a supported property value", value, value.getClass().getName() ) );
    }

    /**
     * Generic value factory method.
     * <p>
     * Converts an array of object values to the internal Value type. See {@link Values#of}.
     */
    public static Value[] values( Object... objects )
    {
        return Arrays.stream( objects )
                .map( Values::of )
                .toArray( Value[]::new );
    }

    @Deprecated
    public static Object asObject( Value value )
    {
        return value == null ? null : value.asObject();
    }

    public static Object[] asObjects( Value[] propertyValues )
    {
        Object[] legacy = new Object[propertyValues.length];

        for ( int i = 0; i < propertyValues.length; i++ )
        {
            legacy[i] = propertyValues[i].asObjectCopy();
        }

        return legacy;
    }

    private static Value arrayValue( Object[] value )
    {
        if ( value instanceof String[] )
        {
            return stringArray( copy( value, new String[value.length] ) );
        }
        if ( value instanceof Byte[] )
        {
            return byteArray( copy( value, new byte[value.length] ) );
        }
        if ( value instanceof Long[] )
        {
            return longArray( copy( value, new long[value.length] ) );
        }
        if ( value instanceof Integer[] )
        {
            return intArray( copy( value, new int[value.length] ) );
        }
        if ( value instanceof Double[] )
        {
            return doubleArray( copy( value, new double[value.length] ) );
        }
        if ( value instanceof Float[] )
        {
            return floatArray( copy( value, new float[value.length] ) );
        }
        if ( value instanceof Boolean[] )
        {
            return booleanArray( copy( value, new boolean[value.length] ) );
        }
        if ( value instanceof Character[] )
        {
            return charArray( copy( value, new char[value.length] ) );
        }
        if ( value instanceof Short[] )
        {
            return shortArray( copy( value, new short[value.length] ) );
        }
        throw new IllegalArgumentException(
                format( "%s[] is not a supported property value type",
                        value.getClass().getComponentType().getName() ) );
    }

    private static <T> T copy( Object[] value, T target )
    {
        for ( int i = 0; i < value.length; i++ )
        {
            if ( value[i] == null )
            {
                throw new IllegalArgumentException( "Property array value elements may not be null." );
            }
            Array.set( target, i, value[i] );
        }
        return target;
    }
}
