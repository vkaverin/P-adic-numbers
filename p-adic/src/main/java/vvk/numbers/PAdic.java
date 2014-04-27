/*
The MIT License (MIT)

Copyright (c) 2014 Vladislav Kaverin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package vvk.numbers;

import java.util.Arrays;

public final class PAdic {

    private final int base;
    private static final int len;

    private final int digits[];
    private final int order;

    private static enum Operation {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION
    }

    static {
        len = (1 << 6);
    }

    /**
     * Constructs p-adic number from integer value.
     * @param value integer value in base 10.
     * @param base base of field of p-adic numbers.
     *             Notice that base must be a prime number.
     */
    public PAdic(final long value, final int base) {
        this.digits = new int[PAdic.len];
        this.base = base;

        final boolean isNegative = (value < 0);
        long current = Math.abs(value);
        int pos = 0;

        while (current != 0) {
            digits[pos] = (int) current % base;
            current /= base;
            ++pos;
        }

        if (isNegative) {
            toNegative();
        }

        int order = 0;

        while (order < PAdic.len && digits[order] == 0) {
            ++order;
        }

        this.order = order < PAdic.len ? order : 0;
    }

    /**
     * Constructs p-adic number from its string representation.
     * @param value string that represents p-adic number.
     *              It can be either integer value or floating point value.
     *              Notice that point can be defined by '.' symbol only.
     * @param base base of field of p-adic numbers.
     *             Notice that base must be a prime number.
     */
    public PAdic(final String value, final int base) {
        this.digits = new int[PAdic.len];
        this.base = base;

        final int pointAt = value.lastIndexOf('.');
        int posInString = value.length() - 1;
        int posInDigits = 0;

        while (posInString >= 0) {

            if (posInString == pointAt) {
                --posInString;
                continue;
            }

            if (!Character.isDigit(value.charAt(posInString))) {
                throw new RuntimeException("There must be only digits in the number, no letters or spectial symbols except of one floating point");
            }

            digits[posInDigits] = value.charAt(posInString) - '0';

            --posInString;
            ++posInDigits;
        }

        int pos = 0;

        while (pos < PAdic.len && digits[pos] == 0) {
            ++pos;
        }

        int order;

        if (pointAt != -1 ) {
            order = -(value.length() - pointAt - 1);

            if (order < 0 ) {
                final int offset = Math.min(-order, pos);
                for (int i = 0; i + offset < PAdic.len; ++i) {
                    digits[i] = digits[i + offset];
                }

                order += offset;
            }
        } else {
            if (pos == PAdic.len) {
                pos = 0;
            }

            order = pos;
        }

        this.order = order;
    }

    /**
     * Constructs p-adic number from rational fraction.
     * @param numerator numerator of the fraction in base 10. Must be integer value.
     * @param denominator denominator of the fracture in base 10. Denominator must be positive.
     * @param base base of field of p-adic numbers.
     *             Notice that base must be a prime number.
     */
    public PAdic(final int numerator, final int denominator, final int base) {
        final int g = gcd(Math.abs(numerator), Math.abs(denominator));
        final int actualNumerator = numerator / g;
        final int actualDenominator = denominator / g;

        final PAdic pAdicNumerator = new PAdic(actualNumerator, base);
        final PAdic pAdicDenominator = new PAdic(actualDenominator, base);

        final PAdic pAdicResult = pAdicNumerator.divide(pAdicDenominator);

        this.digits = Arrays.copyOfRange(pAdicResult.digits, 0, PAdic.len);
        this.order = pAdicResult.order;
        this.base = pAdicResult.base;
    }

    private PAdic(final int[] digits, final int order, final int base) {
        this.base = base;
        this.digits = Arrays.copyOfRange(digits, 0, PAdic.len);
        this.order = order;
    }

    private void toNegative() {
        int pos = 0;

        while (pos < PAdic.len && digits[pos] == 0) {
            ++pos;
        }

        if (pos < PAdic.len) {
            digits[pos] = base - digits[pos];
        }

        for (int i = pos + 1; i < PAdic.len; ++i) {
            digits[i] = base - digits[i] - 1;
        }
    }

    /**
     * Returns result of sum this p-adic number with <code>added</code>.
     * @param added p-adic number to be added.
     * @return p-adic number that is result of sum.
     */
    public PAdic add(final PAdic added) {
        if (this.getOrder() < 0 || added.getOrder() < 0) {
            final int leftOperandOrder = Math.min(this.getOrder(), 0);
            final int rightOperandOrder = Math.min(added.getOrder(), 0);
            final int diff = leftOperandOrder - rightOperandOrder;
            final int offset = Math.abs(diff);
            return diff < 0 ? this.add(added, offset) : added.add(this, offset);
        }

        return add(added, 0);
    }

    private PAdic add(final PAdic added, int offset) {
        final int[] result = new int[PAdic.len];
        int toNext = 0;

        for (int i = 0; i < offset; ++i) {
            result[i] = digits[i];
        }

        for (int i = 0; i + offset < PAdic.len; ++i) {
            final int next = digits[i + offset] + added.digits[i] + toNext;
            toNext = next / base;
            result[i + offset] = next % base;
        }

        final int order = PAdic.calculateOrder(result, this.getOrder(), added.getOrder(), Operation.ADDITION);

        return new PAdic(result, order, this.base);
    }

    /**
     * Returns difference of p-adic number and <code>subtracted</code> value.
     * @param subtracted p-adic number to be subtracted.
     * @return p-adic number that is result of subtraction.
     */
    public PAdic subtract(final PAdic subtracted) {
        PAdic actual = null;
        final int[] digits = new int[PAdic.len];
        boolean haveActual = false;

        if (subtracted.getOrder() < 0 && subtracted.getOrder() < this.getOrder()) {
            final int diff = Math.abs(subtracted.getOrder() - Math.min(this.getOrder(), 0));

            for (int i = PAdic.len - 1; i - diff >= 0; --i) {
                final int idx = i - diff;
                digits[i] = this.digits[idx];
            }

            final int newOrder = Math.min(this.getOrder(), 0) - diff;
            actual = new PAdic(digits, newOrder, this.base);
            haveActual = true;
        }

        if (!haveActual) {
            actual = (PAdic) this.clone();
        }

        if (actual.getOrder() < 0 && subtracted.getOrder() >= 0) {
                return actual.subtract(subtracted, -actual.getOrder());
        }

        final int offset;
        if (actual.getOrder() < 0 || subtracted.getOrder() < 0) {

            // Need to shift digits in such way that point was exactly under point.
            // Example:
            // _12345.67890  =>  _12345.67890           _      100000   =>    _100000.00000
            //       123.45  =>     123.45000    OR       12345.12345   =>      12345.12345
            //  ^^^^^^^^^^^       ^^^^^^^^^^^             ^^^^^^^^^^^          ^^^^^^^^^^^^

            final int leftOperandOrder = Math.min(actual.getOrder(), 0);
            final int rightOperandOrder = Math.min(subtracted.getOrder(), 0);
            offset = Math.abs(leftOperandOrder - rightOperandOrder);
        } else {
            offset = 0;
        }

        return actual.subtract(subtracted, offset);
    }

    private PAdic subtract(final PAdic subtracted, final int offset) {
        final int[] result = new int[PAdic.len];
        boolean takeOne;

        for (int i = 0; i < offset; ++i) {
            result[i] = digits[i];
        }

        for (int i = 0; i + offset < PAdic.len; ++i) {
            final int idx = i + offset;
            if (digits[idx] < subtracted.digits[i]) {
                takeOne = true;
                int j = idx + 1;

                while (j < PAdic.len && takeOne) {
                    if (digits[j] == 0) {
                        digits[j] = base - 1;
                    } else {
                        --digits[j];
                        takeOne = false;
                    }

                    ++j;
                }
                digits[idx] += base;
            }
            result[idx] = digits[idx] - subtracted.digits[i];
        }

        final int order = PAdic.calculateOrder(result, this.getOrder(), subtracted.getOrder(), Operation.SUBTRACTION);

        return new PAdic(result, order, this.base);
    }

    /**
     * Returns result of multiplication of this p-adic number by <code>multiplier</code> value.
     * @param multiplier value to multiply this p-adic number by.
     * @return p-adic number that is result of multiplication.
     */
    public PAdic multiply(final PAdic multiplier) {

        PAdic result = new PAdic("0", this.base);

        for (int i = 0; i < PAdic.len; ++i) {
            final int temp[] = multiplyToInteger(digits, multiplier.digits[i]);
            final PAdic adder = new PAdic(temp, 0, this.base);
            result = result.add(adder, i);
        }

        // In some cases when we multiply numbers, it may happens that
        // real index of the most right non-zero coefficient gets greater than it must be.
        // For example, multiplying ...00000.1 (order = -1)  by ...000010 (order = 1)
        // we will get ...0000010 (order = 1) that's incorrect, because its order must be zero.
        // So, the order calculated correctly, but we need to shift the result a little to the right.

        final int minOrder = Math.min(this.getOrder(), multiplier.getOrder());
        final int maxOrder = Math.max(this.getOrder(), multiplier.getOrder());

        if (minOrder < 0 && 0 < maxOrder) {
            int pos = 0;

            while (pos < -minOrder && result.digits[pos] == 0) {
                ++pos;
            }

            for (int i = 0; i + pos < PAdic.len; ++i) {
                result.digits[i] = result.digits[i + pos];
            }
        }

        final int order = PAdic.calculateOrder(result.digits, this.getOrder(), multiplier.getOrder(), Operation.MULTIPLICATION);

        return new PAdic(result.digits, order, this.base);
    }

    /**
     * Returns result of division of this p-adic number by <code>divisor</code> value.
     * @param divisor value to divide this p-adic number by.
     * @return p-adic number that is result of division.
     */
    public PAdic divide(final PAdic divisor) {
        final int[] result = new int[PAdic.len];

        PAdic divided = new PAdic(this.digits, 0, this.base);

        int pos = 0;

        while (pos < PAdic.len && divisor.digits[pos] == 0) {
            ++pos;
        }

        final int[] temp = new int[PAdic.len];

        for (int i = 0; i + pos < PAdic.len; ++i) {
            temp[i] = divisor.digits[i + pos];
        }

        for (int i = PAdic.len - pos; i < PAdic.len; ++i) {
            temp[i] = temp[PAdic.len - pos - 1];
        }

        final PAdic actualDivisor = new PAdic(temp, divisor.getOrder() - pos, this.base);

        for (int i = 0; i < PAdic.len; ++i) {
            final int digit = findMultiplier(divided.digits[i], actualDivisor.digits[0]);

            if (digit == -1) {
                throw new RuntimeException("Bullshit, that shouldn't happened.");
            }

            final int[] tmp = multiplyToInteger(actualDivisor.digits, digit);

            result[i] = digit;
            divided = divided.subtract(new PAdic(tmp, 0, this.base), i);
        }

        final int order = PAdic.calculateOrder(result, this.getOrder() - pos, actualDivisor.getOrder(), Operation.DIVISION);

        return new PAdic(result, order, this.base);
    }

    /**
     * Returns order of p-adic number.
     * Order of p-adic number is maximal power <i>n</i> of <i>p</i> so <i>p</i> in power <i>n</i> divides the number.
     * @return order of the number.
     */
    public int getOrder() {
        return order;
    }

    private static int calculateOrder(final int[] digits, final int firstOrder, final int secondOrder, Operation operation) {
        int order = 0;

        if (operation == Operation.ADDITION || operation == Operation.SUBTRACTION){
            order = Math.min(firstOrder, secondOrder);
        }

        if (operation == Operation.MULTIPLICATION) {
            order = firstOrder + secondOrder;
        }

        if (operation == Operation.DIVISION) {
            order = firstOrder - secondOrder;
        }

        int pos = 0;

        while (pos < PAdic.len && digits[pos] == 0) {
            ++pos;
        }

        if (pos == PAdic.len) {
            return 0;
        }

        if (0 <= order && order < pos) {
            order = pos;
        }

        if (order < 0) {
            final int min = Math.min(-order, pos);
            for (int i = 0; i + min < PAdic.len; ++i) {
                digits[i] = digits[i + min];
            }
            order += pos;
        }

        return order;
    }

    private int[] multiplyToInteger(final int[] number, final int multiplier) {
        int toNext = 0;

        final int[] result = new int[PAdic.len];
        for (int i = 0; i < number.length; ++i) {
            final int next = number[i] * multiplier + toNext;
            toNext = next / base;
            result[i] = next % base;
        }

        return result;
    }

    private int findMultiplier(final int mod, final int multiplier) {
        for (int i = 0; i < base; ++i) {
            if ((multiplier * i) % base == mod) {
                return i;
            }
        }

        return -1;
    }

    private int gcd(final int a, final int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    @Override
    protected Object clone(){
        return new PAdic(this.digits, this.order, this.base);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(PAdic.len);

        int pos = PAdic.len - 1;

        while (pos >= 0 && digits[pos] == 0) {
            --pos;
        }

        if (pos == -1) {
            ++pos;
        }

        for (int i = pos; i >= 0; --i) {
            result.append(digits[i]);
        }

        if (order < 0) {
            while (result.length() < -order) {
                result.insert(0, '0');
            }
            result.insert(result.length() + order, '.');
        }

        if (result.charAt(0) == '.') {
            result.insert(0, '0');
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PAdic)) {
            return false;
        }

        PAdic number = (PAdic) obj;

        if (this.base != number.base) {
            return false;
        }

        if (this.order != number.order) {
            return false;
        }

        for (int i = 0; i < PAdic.len; ++i) {
            if (this.digits[i] != number.digits[i]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        final int prime = 31;

        for (int i = 0; i < PAdic.len; ++i) {
            hash = hash * prime + digits[i];
        }

        hash = hash * prime + order;
        hash = hash * prime + base;

        return hash;
    }
}
