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

import java.io.*;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

public class Application {

    private static final BufferedReader READER;

    private PAdic a;
    private PAdic b;
    private int base;

    private PAdic addition;
    private PAdic subtraction;
    private PAdic multiplication;
    private PAdic division;

    static {
        READER = new BufferedReader(new InputStreamReader(System.in));
    }

    private PAdic readNext() throws IOException {
        String value = READER.readLine();
        if (value.contains("/")) {
            final String[] rational = value.split("/");
            return new PAdic(new BigInteger(rational[0].trim()), base).divide(new PAdic(new BigInteger(rational[1].trim()), base));
        } else if (value.contains(",")) {
            final String[] sequence = value.split(",");
            final int[] digits = new int[sequence.length];
            for (int i = 0; i < sequence.length; ++i) {
                digits[i] = Integer.valueOf(sequence[i].trim());
            }
            System.out.print("Order = ");
            value = READER.readLine();
            final int order = Integer.valueOf(value.trim());
            return new PAdic(digits, order, base);
        } else {
            return  new PAdic(value.trim(), base);
        }
    }

    public void read() throws IOException {
        System.out.print("Base = ");
        String _base = READER.readLine().trim();

        if (_base.matches("^.*[.,].*$")) {
            throw new RuntimeException("Base must be integer.");
        }

        if (new BigInteger(_base).compareTo(new BigInteger("" + (1 << 16))) > -1) {
            throw new RuntimeException("Mmm, " + _base + " is too large number to be a base and I cannot be sure that it's prime. Enter a prime number that is less than " + (1 << 16) + ".");
        }

        this.base = Integer.valueOf(_base);

        // let's check that _base is prime
        PAdic.checkForPrime(this.base);

        boolean readSuccessfully = false;
        while (!readSuccessfully) {
            System.out.print("A = ");
            try {
                a = readNext();
                readSuccessfully = true;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                readSuccessfully = false;
            }
        }

        System.out.println("Variable A interpreted as " + a);

        readSuccessfully = false;
        while (!readSuccessfully) {
            System.out.print("B = ");
            try {
                b = readNext();
                readSuccessfully = true;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                readSuccessfully = false;
            }
        }
        System.out.println("Variable B interpreted as " + b);
     }

    public void calculate() {
        addition = a.add(b);
        subtraction = a.subtract(b);
        multiplication = a.multiply(b);

        if (new PAdic(new int[] {0}, 0, this.base).equals(b)) {
            division = null;
        } else {
            division = a.divide(b);
        }
    }

    public void print() {
        Map<String, PAdic> results = new LinkedHashMap<String, PAdic>();
        results.put("| Addition       |", addition);
        results.put("| Substraction   |", subtraction);
        results.put("| Multiplication |", multiplication);
        results.put("| Division       |", division);

        int totalLength = Math.max(Math.max(addition.toString().length(), subtraction.toString().length()), multiplication.toString().length()) + "| Multiplication |".length() + 3;

        if (division != null) {
            final int necessary = division.toString().length() + 3 + "| Division       |".length();
            if (totalLength < necessary) {
                totalLength = necessary;
            }
        } else {
            totalLength += 6;
        }

        if (totalLength < 27) { // Length of "| Operation      | Result |"
            totalLength = 27;
        }

        final StringBuilder footer = new StringBuilder();

        while (footer.length() < totalLength) {
            footer.append('-');
        }

        footer.setCharAt(0, '+');
        footer.setCharAt(17, '+');
        footer.setCharAt(footer.length() - 1, '+');

        System.out.println(footer.toString());

        final StringBuilder nextLine = new StringBuilder();
        nextLine.append("| Operation      | Result");

        while (nextLine.length() < totalLength - 1) {
            nextLine.append(' ');
        }
        nextLine.append('|');
        System.out.println(nextLine.toString());
        System.out.println(footer.toString());

        for (String key: results.keySet()) {
            nextLine.delete(0, nextLine.length());
            nextLine.append(key);

            final PAdic result = results.get(key);
            if (result == null) {
                nextLine.append(" ").append("N/A");
            } else {
                nextLine.append(" ").append(result);
            }

            while (nextLine.length() < totalLength - 2) {
                nextLine.append(' ');
            }

            nextLine.append(" |");
            System.out.println(nextLine.toString());
            System.out.println(footer.toString());
        }
    }

    private static void run(final Application app) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        app.read();
                    } catch (IOException e) {
                        System.out.println("Something went wrong while input was parsing.");
                        continue;
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    app.calculate();
                    app.print();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        final Application app = new Application();
        Application.run(app);
    }
}
