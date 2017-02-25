/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2015 Infinite Automation Software. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * When signing a commercial license with Infinite Automation Software,
 * the following extension to GPL is made. A special exception to the GPL is
 * included to allow you to distribute a combined work that includes BAcnet4J
 * without being obliged to provide the source code for any proprietary components.
 *
 * See www.infiniteautomation.com for commercial license options.
 *
 * @author Matthew Lohbihler
 */
package com.serotonin.bacnet4j.type.primitive;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.serotonin.bacnet4j.util.sero.ByteQueue;

public class Time extends Primitive {
    public static final Time UNSPECIFIED = new Time(255, 255, 255, 255);

    public static final byte TYPE_ID = 11;

    private final int hour;
    private final int minute;
    private final int second;
    private final int hundredth;

    public Time(final int hour, final int minute, final int second, final int hundredth) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.hundredth = hundredth;
    }

    public Time() {
        this(new GregorianCalendar());
    }

    public Time(final GregorianCalendar now) {
        this.hour = now.get(Calendar.HOUR_OF_DAY);
        this.minute = now.get(Calendar.MINUTE);
        this.second = now.get(Calendar.SECOND);
        this.hundredth = now.get(Calendar.MILLISECOND) / 10;
    }

    public boolean isHourUnspecified() {
        return hour == 255;
    }

    public int getHour() {
        return hour;
    }

    public boolean isMinuteUnspecified() {
        return minute == 255;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isSecondUnspecified() {
        return second == 255;
    }

    public int getSecond() {
        return second;
    }

    public boolean isHundredthUnspecified() {
        return hundredth == 255;
    }

    public int getHundredth() {
        return hundredth;
    }

    /**
     * @param that
     *            The time with which to compare this
     * @return true if this < that.
     */
    public boolean before(final Time that) {
        if (!this.isHourUnspecified() && !that.isHourUnspecified()) {
            if (this.hour < that.hour)
                return true;
            if (this.hour > that.hour)
                return false;
        }

        if (!this.isMinuteUnspecified() && !that.isMinuteUnspecified()) {
            if (this.minute < that.minute)
                return true;
            if (this.minute > that.minute)
                return false;
        }

        if (!this.isSecondUnspecified() && !that.isSecondUnspecified()) {
            if (this.second < that.second)
                return true;
            if (this.second > that.second)
                return false;
        }

        if (this.isHundredthUnspecified() || that.isHundredthUnspecified())
            return false;

        return this.hundredth < that.hundredth;
    }

    /**
     * @param that
     *            The time with which to compare this
     * @return true if this > that
     */
    public boolean after(final Time that) {
        if (!this.isHourUnspecified() && !that.isHourUnspecified()) {
            if (this.hour > that.hour)
                return true;
            if (this.hour < that.hour)
                return false;
        }

        if (!this.isMinuteUnspecified() && !that.isMinuteUnspecified()) {
            if (this.minute > that.minute)
                return true;
            if (this.minute < that.minute)
                return false;
        }

        if (!this.isSecondUnspecified() && !that.isSecondUnspecified()) {
            if (this.second > that.second)
                return true;
            if (this.second < that.second)
                return false;
        }

        if (this.isHundredthUnspecified() || that.isHundredthUnspecified())
            return true;

        return this.hundredth > that.hundredth;
    }

    //
    // Reading and writing
    //
    public Time(final ByteQueue queue) {
        readTag(queue);
        hour = queue.popU1B();
        minute = queue.popU1B();
        second = queue.popU1B();
        hundredth = queue.popU1B();
    }

    @Override
    public void writeImpl(final ByteQueue queue) {
        queue.push((byte) hour);
        queue.push((byte) minute);
        queue.push((byte) second);
        queue.push((byte) hundredth);
    }

    @Override
    protected long getLength() {
        return 4;
    }

    @Override
    protected byte getTypeId() {
        return TYPE_ID;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + hour;
        result = PRIME * result + hundredth;
        result = PRIME * result + minute;
        result = PRIME * result + second;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Time other = (Time) obj;
        if (hour != other.hour)
            return false;
        if (hundredth != other.hundredth)
            return false;
        if (minute != other.minute)
            return false;
        if (second != other.second)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second) + "."
                + hundredth;

    }
}
