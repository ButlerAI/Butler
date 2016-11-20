/*
 * The MIT License
 *
 * Created by Jarrod Boyes on 24/03/14.
 * Copyright (c) 2014 LIFX Labs. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.besherman.lifx.impl.entities.internal.structle;

import com.github.besherman.lifx.impl.entities.internal.LFXByteUtils;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StructleTypes {

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public abstract static class LxProtocolTypeBase {
        protected static final int PAYLOAD_OFFSET = 36;
        public abstract void printMessageData();
        public abstract byte[] getBytes();
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class ProtocolField {

        public ProtocolField(byte[] data) {
        }

        public byte[] getBytes() {
            return new byte[]{};
        }

        public void printValue(String varName) {
            Logger.getLogger(ProtocolField.class.getName()).log(Level.FINE, varName + ": " + getValue());
        }

        public int getValue() {
            return 0;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class RoutingField {

        public RoutingField(byte[] data) {
        }

        public byte[] getBytes() {
            return new byte[]{};
        }

        public void printValue(String varName) {
            Logger.getLogger(RoutingField.class.getName()).log(Level.FINE, varName + ": " + getValue());
        }

        public int getValue() {
            return 0;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class Int8 {

        private byte[] data;

        public Int8(byte[] value) {
            set(value);
        }

        public Int8(byte value) {
            data = new byte[1];
            data[0] = value;
        }

        public void set(byte[] value) {
            this.data = value;
        }

        public byte[] getBytes() {
            return data;
        }

        public int getValue() {
            return (int) data[0];
        }

        public void printValue(String varName) {
            Logger.getLogger(RoutingField.class.getName()).log(Level.FINE, varName + ": " + getValue());

            StringBuilder builder = new StringBuilder();
            builder.append(", Hex: ( ");
            for (int i = 0; i < data.length; i++) {
                builder.append(String.format("%02X ", data[i]));
            }
            builder.append(")");
            Logger.getLogger(Int8.class.getName()).log(Level.FINE, builder.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class UInt8 {

        private byte[] data;

        public UInt8(byte[] value) {
            set(value);
        }

        public UInt8(int value) {
            data = new byte[1];

            byte[] buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
            data[0] = buffer[0];
        }

        public void set(byte[] value) {
            this.data = value;
        }

        public byte[] getBytes() {
            return data;
        }

        public int getValue() {
            int value = bytesToInt(new byte[]{data[0], 0x00, 0x00, 0x00});

            return value;
        }

        public void printValue(String varName) {
            StringBuilder builder = new StringBuilder();

            builder.append(varName + ": " + getValue());

            builder.append(", Hex: ( ");
            for (int i = 0; i < data.length; i++) {
                builder.append(String.format("%02X ", data[i]));
            }
            builder.append(String.format(")"));
            Logger.getLogger(getClass().getName()).log(Level.INFO, builder.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class Int16 {

        private byte[] data;

        public Int16(byte[] value) {
            set(value);
        }

        public Int16(short value) {
            data = new byte[2];

            data[0] = (byte) (value & 0xff);
            data[1] = (byte) ((value >> 8) & 0xff);
        }

        public void set(byte[] value) {
            this.data = value;
        }

        public byte[] getBytes() {
            return data;
        }

        public int getValue() {
            return (int) getShortValue(data[0], data[1]);
        }

        public void printValue(String varName) {
            StringBuilder builder = new StringBuilder();
            builder.append(varName + ": " + getValue());

            builder.append(", Hex: ( ");
            for (int i = 0; i < data.length; i++) {
                builder.append(String.format("%02X ", data[i]));
            }
            builder.append(String.format(")"));
            Logger.getLogger(getClass().getName()).log(Level.INFO, builder.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class UInt16 {

        public static final float MAX_U16_VALUE = 65535.0f;

        private byte[] data;

        public UInt16(byte[] value) {
            set(value);
        }

        public UInt16(int value) {
            data = new byte[2];

            short valueShort = (short) value;
            data[0] = (byte) (valueShort & 0xff);
            data[1] = (byte) ((valueShort >> 8) & 0xff);
        }

        public void set(byte[] value) {
            this.data = value;
        }

        public byte[] getBytes() {
            return data;
        }

        public int getValue() {
//			int value = (int) getShortValue( data[0], data[1]);
//			
//			if( value < 0)
//			{
//				value += 65536;
//			}

            int value = bytesToInt(new byte[]{data[0], data[1], 0x00, 0x00});

            return value;
        }

        public void printValue(String varName) {
            StringBuilder builder = new StringBuilder();
            builder.append(varName + ": " + getValue());

            builder.append(", Hex: ( ");
            for (int i = 0; i < data.length; i++) {
                builder.append(String.format("%02X ", data[i]));
            }
            builder.append(String.format(")"));
            Logger.getLogger(getClass().getName()).log(Level.INFO, builder.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class Int32 {

        private byte[] value;

        public Int32(byte[] value) {
            set(value);
        }

        public Int32(int value) {
            this.value = new byte[4];

            byte[] buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();

            this.value[0] = buffer[0];
            this.value[1] = buffer[1];
            this.value[2] = buffer[2];
            this.value[3] = buffer[3];
        }

        public void set(byte[] value) {
            this.value = value;
        }

        public byte[] getBytes() {
            return value;
        }

        public int getValue() {
            return (int) getIntValue(value[0], value[1], value[2], value[3]);
        }

        public void printValue(String varName) {
            StringBuilder builder = new StringBuilder();
            builder.append(varName + ": " + getValue());

            builder.append(", Hex: ( ");
            for (int i = 0; i < value.length; i++) {
                builder.append(String.format("%02X ", value[i]));
            }
            builder.append(String.format(")"));
            Logger.getLogger(getClass().getName()).log(Level.FINE, builder.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class UInt32 {

        private byte[] value;

        public UInt32(byte[] value) {
            set(value);
        }

        public UInt32(long value) {
            this.value = new byte[4];

            int valueInt = (int) value;

            byte[] buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(valueInt).array();

            this.value[0] = buffer[0];
            this.value[1] = buffer[1];
            this.value[2] = buffer[2];
            this.value[3] = buffer[3];
        }

        public void set(byte[] value) {
            this.value = value;
        }

        public byte[] getBytes() {
            return value;
        }

        public long getValue() {
//			long value = getIntValue( this.value[0], this.value[1], this.value[2], this.value[3]);
//			
//			if( value < 0)
//			{
//				value += 4294967296L;//2147483648L;
//			}
            long value = getLongValue(this.value[0], this.value[1], this.value[2], this.value[3], (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00);

            return value;
        }

        public void printValue(String varName) {
            StringBuilder builder = new StringBuilder();
            builder.append(varName + ": " + getValue());

            builder.append(", Hex: ( ");
            for (int i = 0; i < value.length; i++) {
                builder.append(String.format("%02X ", value[i]));
            }
            builder.append(String.format(")"));
            Logger.getLogger(getClass().getName()).log(Level.INFO, builder.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class Int64 {

        private byte[] value;

        public Int64(byte[] value) {
            set(value);
        }

        public Int64(long value) {
            this.value = new byte[8];

            byte[] buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(value).array();

            this.value[0] = buffer[0];
            this.value[1] = buffer[1];
            this.value[2] = buffer[2];
            this.value[3] = buffer[3];
            this.value[4] = buffer[4];
            this.value[5] = buffer[5];
            this.value[6] = buffer[6];
            this.value[7] = buffer[7];
        }
        
        public void set(byte[] value) {
            this.value = value;
        }

        public byte[] getBytes() {
            return value;
        }

        public long getValue() {
            return (long) getLongValue(value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7]);
        }
   
        public void printValue(String varName) {
            StringBuilder builder = new StringBuilder();
            builder.append(varName + ": " + getValue());

            builder.append(", Hex: ( ");
            for (int i = 0; i < value.length; i++) {
                builder.append(String.format("%02X ", value[i]));
            }
            builder.append(String.format(")"));
            Logger.getLogger(getClass().getName()).log(Level.FINE, builder.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class UInt64 {

        private byte[] value;

        public UInt64(byte[] value) {
            this.value = value;
            // TODO: validate
        }
        
        public UInt64(BigInteger big) {
            this.value = new byte[8];
            byte[] buffer = big.toByteArray();  
            
            for(int i = 0; i < value.length; i++) {
                if(i < buffer.length) {
                    value[i] = buffer[buffer.length - i - 1];
                } else {
                    value[i] = 0;
                }                
            }
        }
        
        public static UInt64 fromHex(String s) {
            return new UInt64(LFXByteUtils.hexStringToByteArray(s));            
        }

        public UInt64(long value) {
            this.value = new byte[8];

            byte[] buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(value).array();

            this.value[0] = buffer[0];
            this.value[1] = buffer[1];
            this.value[2] = buffer[2];
            this.value[3] = buffer[3];
            this.value[4] = buffer[4];
            this.value[5] = buffer[5];
            this.value[6] = buffer[6];
            this.value[7] = buffer[7];
        }

        public void set(byte[] value) {
            this.value = value;
        }

        public byte[] getBytes() {
            return value;
        }

        public long getSignedValue() {
            return (long) getLongValue(value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7]);
        }
        
        public BigInteger getBigIntegerValue() {
            byte[] buffer = new byte[8];
            buffer[0] = value[7];
            buffer[1] = value[6];
            buffer[2] = value[5];
            buffer[3] = value[4];
            buffer[4] = value[3];
            buffer[5] = value[2];
            buffer[6] = value[1];
            buffer[7] = value[0];
            return new BigInteger(buffer);
        }

        public boolean equals(UInt64 other) {
            for (int i = 0; i < this.value.length; i++) {
                if (other.value[i] != this.value[i]) {
                    return false;
                }
            }

            return true;
        }

        public void printValue(String varName) {
            StringBuilder builder = new StringBuilder();
            builder.append(varName + ": " + getSignedValue());

            builder.append(", Hex: ( ");
            for (int i = 0; i < value.length; i++) {
                builder.append(String.format("%02X ", value[i]));
            }
            builder.append(String.format(")"));
            Logger.getLogger(getClass().getName()).log(Level.FINE, builder.toString());
        }
        
        public String toHex() {
            return LFXByteUtils.byteArrayToHexString(value);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class Bool8 {

        private byte[] data;

        public Bool8(byte[] data) {
            set(data);
        }

        public Bool8(boolean value) {
            data = new byte[1];

            if (value) {
                data[0] = (byte) 0xff;
            } else {
                data[0] = (byte) 0x00;
            }
        }

        public void set(byte[] data) {
            this.data = data;
        }

        public byte[] getBytes() {
            return data;
        }

        public boolean getValue() {
            if (data[0] != 0) {
                return true;
            }

            return false;
        }

        public void printValue(String varName) {
            StringBuilder builder = new StringBuilder();
            builder.append(varName + ": " + getValue());

            builder.append(", Hex: ( ");
            for (int i = 0; i < data.length; i++) {
                builder.append(String.format("%02X ", data[i]));
            }
            builder.append(String.format(")"));
            Logger.getLogger(getClass().getName()).log(Level.INFO, builder.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class Float32 {

        private byte[] value;

        public Float32(byte[] value) {
            set(value);
        }

        public Float32(float value) {
            this.value = new byte[4];

            byte[] buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();

            this.value[0] = buffer[0];
            this.value[1] = buffer[1];
            this.value[2] = buffer[2];
            this.value[3] = buffer[3];
        }

        public void set(byte[] value) {
            this.value = value;
        }

        public byte[] getBytes() {
            return value;
        }

        public float getValue() {
            return getFloatValue(value[0], value[1], value[2], value[3]);
        }

        public void printValue(String varName) {
            StringBuilder builder = new StringBuilder();
            builder.append(varName + ": " + getValue());

            builder.append(", Hex: ( ");
            for (int i = 0; i < value.length; i++) {
                builder.append(String.format("%02X ", value[i]));
            }
            builder.append(String.format(")"));
            Logger.getLogger(getClass().getName()).log(Level.INFO, builder.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////
    public static class Double64 {

        private byte[] value;

        public Double64(byte[] value) {
            set(value);
        }

        public Double64(long value) {
            this.value = new byte[8];

            byte[] buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(value).array();

            this.value[0] = buffer[0];
            this.value[1] = buffer[1];
            this.value[2] = buffer[2];
            this.value[3] = buffer[3];
            this.value[4] = buffer[4];
            this.value[5] = buffer[5];
            this.value[6] = buffer[6];
            this.value[7] = buffer[7];
        }

        public void set(byte[] value) {
            this.value = value;
        }

        public byte[] getBytes() {
            return value;
        }

        public double getValue() {
            return getDoubleValue(value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7]);
        }

        public void printValue(String varName) {
            StringBuilder builder = new StringBuilder();
            builder.append(varName + ": " + getValue());

            builder.append(", Hex: ( ");
            for (int i = 0; i < value.length; i++) {
                builder.append(String.format("%02X ", value[i]));
            }
            builder.append(String.format(")"));
            Logger.getLogger(getClass().getName()).log(Level.FINE, builder.toString());
        }
    }

    public static long getLongValue(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7) {
        byte[] bytes = new byte[]{b0, b1, b2, b3, b4, b5, b6, b7};

        return bytesToLong(bytes);
    }

    public static float getFloatValue(byte b0, byte b1, byte b2, byte b3) {
        byte[] bytes = {b0, b1, b2, b3};
        float f = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        return f;
    }

    public static double getDoubleValue(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7) {
        byte[] bytes = {b0, b1, b2, b3, b4, b5, b6, b7};
        double f = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getDouble();
        return f;
    }

    public static int getIntValue(byte b0, byte b1, byte b2, byte b3) {
        byte[] bytes = {b0, b1, b2, b3};

        return bytesToInt(bytes);
    }

    public static int bytesToInt(byte[] bytes) {
        int i = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
        return i;
    }

    public static long bytesToLong(byte[] bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value += ((long) bytes[i] & 0xffL) << (8 * i);
        }
        return value;
    }
    
    public static String bytesToString(byte[] arr) {
        StringBuilder builder = new StringBuilder();
        for(byte b: arr) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }    

    public static short getShortValue(byte b0, byte b1) {
//        short value = b1;
//        value = (short) ((value << 8) | b0);
//
//        return value;
        // TODO: this is a serious, file upstream bug report
        return (short)( ((b1&0xFF)<<8) | (b0&0xFF) );
    }
}
