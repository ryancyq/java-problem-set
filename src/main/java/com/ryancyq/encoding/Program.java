package com.ryancyq.encoding;

public class Program {
    public boolean isDebug = false;

    public String toOctal(int decimal) {
        if (decimal == 0) return "0";

        StringBuilder octal = new StringBuilder();
        while (decimal > 0) {
            int rem = decimal % 8;
            octal.insert(0, rem);
            decimal -= rem;
            decimal /= 8;
        }
        return octal.toString();
    }

    public Integer fromHex(String hex) {
        int sum = 0;
        char[] chars = hex.toCharArray();
        for (int c = chars.length - 1; c >= 0; c--) {
            char ch = chars[c];
            int decimal;
            if (ch <= '9' && ch >= '0') {
                decimal = ch - '0';
            } else if (ch <= 'F' && ch >= 'A') {
                decimal = ch - 'A' + 10;
            } else {
                decimal = ch - 'a' + 10;
            }
            sum += (int) (decimal * Math.pow(16, chars.length - c - 1));
        }
        return sum;
    }

    public String transcode(String input) {
        StringBuilder output = new StringBuilder();

        int chunkSize = 6;
        int hexSize = 4;
        int octalSize = 3;
        int octalChunkSize = (chunkSize * hexSize) / octalSize;
        for (int i = input.length() - 1; i >= 0; i -= chunkSize) {
            int start = Math.max(i - chunkSize + 1, 0);
            String hex = input.substring(start, i + 1);
            int hexDecimal = fromHex(hex);
            StringBuilder octal = new StringBuilder();
            String octalString = toOctal(hexDecimal);
            octal.append(octalString);

            if (isDebug) {
                System.out.println("hex: " + hexDecimal + " lib: " + Integer.parseInt(hex, 16));
                System.out.println("oct: " + octalString + " lib: " + Integer.toOctalString(hexDecimal));
            }

            if (i >= chunkSize) {
                while (octal.length() < octalChunkSize) {
                    octal.insert(0, "0");
                }
            }
            output.insert(0, octal);
        }

        return output.toString();
    }

    public void run(String input, String expected) {
        String output = transcode(input);
        System.out.println("input: " + input + ", output: " + output);
        System.out.println("match: " + output.equals(expected) + ", expected: " + expected);
    }

    public static void main(String[] args) {
        new Program().run("0", "0");
        new Program().run("8", "10");
        new Program().run("10", "20");
        new Program().run("123456789ABCDEF", "4432126361152746757");
    }
}

