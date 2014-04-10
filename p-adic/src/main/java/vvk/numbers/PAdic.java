package vvk.numbers;

public final class PAdic {

    public static final PAdic ZERO;
    private final int base;
    private static final int len;

    final int digits[];
    final int order;

    private static enum Operation {
        ADDITION,
        SUBSTRACTION,
        MULTIPLICATION,
        DIVISION
    }

    static {
        len = 64;
        ZERO = new PAdic("0");
    }

    {
        base = 5;
        digits = new int[PAdic.len];
    }

    /**
     * Constructs p-adic number from integer value.
     * @param value integer value in base 10.
     */
    public PAdic(final long value) {
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
     *              Note, that point can be defined by '.' symbol only.
     */
    public PAdic(final String value) {
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
                order = 0;
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
     */
    public PAdic(final int numerator, final int denominator) {
        final int g = gcd(Math.abs(numerator), Math.abs(denominator));
        final int actualNumerator = numerator / g;
        final int actualDenominator = denominator / g;

        final PAdic pAdicNumerator = new PAdic(actualNumerator);
        final PAdic pAdicDenominator = new PAdic(actualDenominator);

        final PAdic pAdicResult = pAdicNumerator.divide(pAdicDenominator);

        for (int i = 0; i < PAdic.len; ++i) {
            this.digits[i] = pAdicResult.digits[i];
        }

        order = pAdicResult.order;
    }

    private PAdic(final int[] digits, final int order) {
        for (int i = 0; i < Math.min(PAdic.len, digits.length); ++i) {
            this.digits[i] = digits[i];
        }

        this.order = order;
    }

    private void toNegative() {
        int pos = 0;

        while (pos < PAdic.len && digits[pos] == 0)
            ++pos;

        if (pos < PAdic.len){
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
            final int diff = this.getOrder() - added.getOrder();
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

        return new PAdic(result, order);
    }

    /**
     * Returns difference of p-adic number and <code>substracted</code> value.
     * @param substracted p-adic number to be substracted.
     * @return p-adic number that is result of substraction.
     */
    public PAdic substract(final PAdic substracted) {

        PAdic current = null;
        final int[] digits = new int[PAdic.len];
        boolean haveActual = false;

        if (substracted.getOrder() < 0 && substracted.getOrder() < this.getOrder()) {
            final int diff = Math.abs(substracted.getOrder() - this.getOrder());

            for (int i = PAdic.len - 1; i - diff >= 0; --i) {
                final int idx = i - diff;
                digits[i] = this.digits[idx];
            }

            current = new PAdic(digits, this.getOrder() - diff);
            haveActual = true;
        }

        if (!haveActual) {
            current = this;
        }

        if (current.getOrder() < 0) {

            if (substracted.getOrder() >= 0) {
                return current.substract(substracted, -current.getOrder());
            }
        }

        final int offset = current.getOrder() - substracted.getOrder();

        return current.substract(substracted, offset);
    }

    private PAdic substract(final PAdic substracted, final int offset) {
        final int[] result = new int[PAdic.len];
        boolean takeOne;

        for (int i = 0; i < offset; ++i) {
            result[i] = digits[i];
        }

        for (int i = 0; i + offset < PAdic.len; ++i) {
            final int idx = i + offset;
            if (digits[idx] < substracted.digits[i]) {
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
            result[idx] = digits[idx] - substracted.digits[i];
        }

        final int order = PAdic.calculateOrder(result, this.getOrder(), substracted.getOrder(), Operation.SUBSTRACTION);

        return new PAdic(result, order);
    }

    /**
     * Returns result of multiplication of this p-adic number by <code>multiplier</code> value.
     * @param multiplier value to multiply this p-adic number by.
     * @return p-adic number that is result of multiplication.
     */
    public PAdic multiply(final PAdic multiplier) {

        PAdic result = PAdic.ZERO;

        for (int i = 0; i < PAdic.len; ++i) {
            final int temp[] = multiplyToInteger(digits, multiplier.digits[i]);
            final PAdic adder = new PAdic(temp, 0);
            result = result.add(adder, i);
        }

        // In some cases when we multiply numbers, it may happens that
        // real index of the most right non-zero coefficient gets greater than it must be.
        // For example, multiplying ...00000.1 (order = -1)  by ...000010 (order = 1)
        // we will get ...0000010, that is incorrect, because its order must be zero.
        // So, the order calculated correctly, but we need to shift the result a little to the right.

        final int minOrder = Math.min(this.getOrder(), multiplier.getOrder());
        final int maxOrder = Math.max(this.getOrder(), multiplier.getOrder());

        if (minOrder < 0 && 0 < maxOrder) {
            int pos = 0;

            while (pos < -Math.min(this.getOrder(), multiplier.getOrder()) && result.digits[pos] == 0) {
                ++pos;
            }

            for (int i = 0; i + pos < PAdic.len; ++i) {
                result.digits[i] = result.digits[i + pos];
            }
        }

        final int order = PAdic.calculateOrder(result.digits, this.getOrder(), multiplier.getOrder(), Operation.MULTIPLICATION);

        return new PAdic(result.digits, order);
    }

    /**
     * Returns result of division of this p-adic number by <code>divisor</code> value.
     * @param divisor value to divide this p-adic number by.
     * @return p-adic number that is result of division.
     */
    public PAdic divide(final PAdic divisor) {
        final int[] result = new int[PAdic.len];

        PAdic divided = new PAdic(this.digits, 0);

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

        final PAdic actualDivisor = new PAdic(temp, divisor.getOrder() - pos);

        for (int i = 0; i < PAdic.len; ++i) {
            final int digit = findMultiplier(divided.digits[i], actualDivisor.digits[0]);

            if (digit == -1) {
                throw new RuntimeException("Bullshit, that shouldn't happened.");
            }

            final int[] tmp = multiplyToInteger(actualDivisor.digits, digit);

            result[i] = digit;
            divided = divided.substract(new PAdic(tmp, 0), i);
        }

        final int order = PAdic.calculateOrder(result, this.getOrder() - pos, actualDivisor.getOrder(), Operation.DIVISION);

        return new PAdic(result, order);
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

        if (operation == Operation.ADDITION || operation == Operation.SUBSTRACTION){
            order = Math.min(firstOrder, secondOrder);

            int pos = 0;

            while (pos < PAdic.len && digits[pos] == 0) {
                ++pos;
            }

            if (pos == PAdic.len) {
                return order = 0;
            }

            if (order >= 0 && order < pos) {
                order = pos;
            }

            if (order < 0) {
                final int min = Math.min(pos, -order);
                for (int i = 0; i + min < PAdic.len; ++i) {
                    digits[i] = digits[i + min];
                }

                order += pos;
            }

            return order;
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
            return order = 0;
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
    public String toString() {
        StringBuffer result = new StringBuffer(PAdic.len);

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
            result.insert(result.length() + order, '.');
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

        if (this.getOrder() != number.getOrder()) {
            return false;
        }

        for (int i = 0; i < PAdic.len; ++i) {
            if (this.digits[i] != number.digits[i]) {
                return false;
            }
        }

        return true;
    }
}
