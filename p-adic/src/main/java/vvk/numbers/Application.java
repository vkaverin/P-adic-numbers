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
import java.util.LinkedHashMap;
import java.util.Map;

public class Application {

    private static final BufferedReader READER;

    private PAdic x;
    private PAdic y;
    private int base;

    private PAdic addition;
    private PAdic subtraction;
    private PAdic multiplication;
    private PAdic division;

    static {
        READER = new BufferedReader(new InputStreamReader(System.in));
    }

    public void read() throws IOException {
        System.out.print("Base = ");
        this.base = Integer.valueOf(READER.readLine());
        new PAdic(1, this.base);
        String value;
        System.out.print("x = ");
        value = READER.readLine();
        String[] rational = value.split("/");
        x = new PAdic(Integer.valueOf(rational[0]), this.base);
        if (rational.length > 1) {
            x = x.divide(new PAdic(Integer.valueOf(rational[1]), this.base));
        }
        System.out.print("y = ");
        value = READER.readLine();
        rational = value.split("/");
        y = new PAdic(Integer.valueOf(rational[0]), this.base);
        if (rational.length > 1) {
            y = y.divide(new PAdic(Integer.valueOf(rational[1]), this.base));
        }
    }

    public void calculate() {
        addition = x.add(y);
        subtraction = x.subtract(y);
        multiplication = x.multiply(y);

        if (new PAdic("0", this.base).equals(y)) {
            division = null;
        } else {
            division = x.divide(y);
        }
    }

    public void print() {
        Map<String, PAdic> results = new LinkedHashMap<>();
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
            nextLine.append(" ").append(results.get(key));

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
