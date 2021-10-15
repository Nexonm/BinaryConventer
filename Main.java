
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Приветствую тебя в программе \"Бинарный конвертер\".");
        System.out.println("Введи число через запятую и конвертер выведет тебе,");
        System.out.println("как число хранится в бирном виде.");
        String input = "";
        double num;
        while (true) {
            try {
                input = sc.nextLine().replace(",", ".");
                num = Double.parseDouble(input);
                if (  Long.parseLong(input)!=(long)num){
                    System.out.println("prooooblem!!!!!!!");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.printf("вы ввели \"%s\", это не является числом!\nПопробуйте снова.\n", input);
            }
        }


        if ((num < 0 ? -num : num) % 1 > 0.00000000000001)
            chooseTypeDouble(num, input);
        else {
            chooseTypeInteger(num, input);
            chooseTypeDouble(num, input);
        }


    }

    /**method calls all types with point
     * @param num double argument
     * @param input String input argument
     */
    static void chooseTypeDouble(double num, String input) {
        if (num >= -Float.MAX_VALUE && num <= Float.MAX_VALUE) {
            num((float) num, input);
        }
        num(num, input);

    }

    /**method calls all types without integer part
     *
     * @param num double argument
     */
    static void chooseTypeInteger(double num, String input) {
        //i convert input to num because double has problems in saving big data

        if (num >= Byte.MIN_VALUE && num <= Byte.MAX_VALUE)
            try {
                byte a = Byte.parseByte(input);
                num(a);
            }catch (NumberFormatException e){

            }
        if (num >= Short.MIN_VALUE && num <= Short.MAX_VALUE)
            try {
                short a = Short.parseShort(input);
                num(a);
            }catch (NumberFormatException e){

            }
        if (num >= Integer.MIN_VALUE && num <= Integer.MAX_VALUE)
            try {
                int a = Integer.parseInt(input);
                num(a);
            }catch (NumberFormatException e){

            }
        if (num >= Long.MIN_VALUE && num <= Long.MAX_VALUE)
            try {
                long a = Long.parseLong(input);
                num(a);
            }catch (NumberFormatException e){

            }

    }


    /**main method to convert input argument to binary
     * representation of double type
     *64 bits, 1 number singh, 11 order, 53 mantis
     *
     * @param num double argument
     * @param input input String line
     */
    static void num(double num, String input) {
        String binaryStr = "";

        //sight 0 if num is positive, 1 if negative
        if (num >= 0) {
            binaryStr = "0 ";
        } else {
            binaryStr = "1 ";
            num = -num;//we work with number module
        }

        if ((int) num != 0) {//if we have decimal part of number e.g. 3.12; 32.4; 43; 32.0

            String binaryDecimalNum = toBinaryString((int) num);//decimal part to binary note
            binaryDecimalNum = binaryDecimalNum.substring(1);//system ignores first 1, it is exists by default
            //for double 1023+binaryDecimalNum.length()-1
            String order = toBinaryString((short) (1023 + binaryDecimalNum.length()));
            //for double 11
            if (order.length() < 11)
                order = "0".concat(order);
            //order = String.format("%0" + (8 - order.length()) + "d%s", 0, order);//if order num is <=than 127

            binaryStr = binaryStr.concat(order.concat(" "));
            binaryStr = binaryStr.concat(binaryDecimalNum);

            //for double mantis = 52
            int mantis = 52 - binaryDecimalNum.length(); //as we already wrote decimal part to mantis

//write exact after point number to num
            if (input.contains(".") || Double.toString(num).contains(".")) {
                binaryStr+=mantis(input, mantis);
            } else {
                binaryStr = String.format("%s" + "%0" + (mantis) + "d", binaryStr, 0);
            }

        } else {//if there is no decimal part e.g. 0.3; 0.0032; 0.321

            String pointPart = "";

            pointPart += mantis(input, 70);
            int sum = 0;
            for (int i = 0; i < 52; i++) {
                if (pointPart.charAt(i) == '1') {
                    break;
                }
                sum++;
            }
            String order = "0".concat(toBinaryString((short) (1023 - sum - 1)));
            binaryStr = binaryStr.concat(order + " ");


            binaryStr = binaryStr.concat(pointPart.substring(sum + 1, 52 + sum + 1));

        }

        System.out.printf("%-6s: \"%s\"\n", "double", binaryStr);

    }

    /**main method to convert input argument to binary
     * representation of float type
     * 32 bits, 1 number singh, 8 order, 23 mantis
     *
     * @param num float argument
     * @param input input String line
     */
    static void num(float num, String input) {
        String binaryStr = "";

        //sight 0 if num is positive, 1 if negative
        if (num >= 0) {
            binaryStr = "0 ";
        } else {
            binaryStr = "1 ";
            num = -num;//we work with number module
        }

        if ((int) num != 0) {//if we have decimal part of number e.g. 3.12; 32.4; 43; 32.0

            String binaryDecimalNum = toBinaryString((int) num);//decimal part to binary note
            binaryDecimalNum = binaryDecimalNum.substring(1);//system ignores first 1, it is exists by default
            //for double 1023+binaryDecimalNum.length()-1
            String order = toBinaryString((short) (127 + binaryDecimalNum.length()));
            if (order.length() < 8)
                order = "0".concat(order);
            //order = String.format("%0" + (8 - order.length()) + "d%s", 0, order);//if order num is <=than 127

            binaryStr = binaryStr.concat(order.concat(" "));
            if (binaryDecimalNum.length()<23) {//if decimal representation is bigger than mantis
                binaryStr = binaryStr.concat(binaryDecimalNum);


                //write exact after point number to num
                if (input.contains(".")) {
                    binaryStr = mantis(input, 23 - binaryDecimalNum.length());//as we already wrote decimal part to mantis
                } else {
                    binaryStr = String.format("%s" + "%0" + (23 - binaryDecimalNum.length()) + "d", binaryStr, 0);//as we already wrote decimal part to mantis
                }
            }else{
                binaryStr = binaryStr.concat(binaryDecimalNum.substring(0,22));
            }

        } else {//if there is no decimal part e.g. 0.3; 0.0032; 0.321

            String pointPart = "";

            pointPart = mantis(input, 40);//use 40 for better result, cause don't have decimal part

            int sum = 0;
            for (int i = 0; i < 23; i++) {
                if (pointPart.charAt(i) == '1') {
                    break;
                }
                sum++;
            }
            String order = "0".concat(toBinaryString((short) (127 - sum - 1)));
            binaryStr = binaryStr.concat(order + " ");


            binaryStr = binaryStr.concat(pointPart.substring(sum + 1, 23 + sum + 1));

        }

        System.out.printf("%-6s: \"%s\"\n", "float", binaryStr);

    }

    /**counts double or float mantis with length accuracy
     *
     * @param input line with num
     * @param length accuracy of calculation
     * @return mantis line from 1 and 0
     */
    static String mantis(String input, int length) {
        if (input.contains("."))
        input = input.substring(input.indexOf(".") + 1);
        else input = input.substring(1);
        float num = (float) (Double.parseDouble(input) / (double) Math.pow(10, input.length()));

        String pointPart = "";
        while (length > 0) {
            num = num * 2;
            pointPart += (int) num;
            input = Double.toString(num);
            input = input.substring(input.indexOf(".") + 1);
            num = Float.parseFloat(input) / (float) Math.pow(10, input.length());

            length--;
        }
        return pointPart;
    }

    static void num(long num) {
        String binaryStr = "";

        if (num >= 0) {
            binaryStr = formatString(toBinaryString(num), 64);
        } else {
            num = (long) (-(num + 1));
            binaryStr = formatString(toBinaryString(num), 64);
            binaryStr = reverseZeroAndNull(binaryStr);
        }
        System.out.printf("%-6s: \"%s\"\n", "long", makeWhitespace(binaryStr));

    }

    static void num(int num) {
        String binaryStr = "";

        if (num >= 0) {
            binaryStr = formatString(toBinaryString(num), 32);
        } else {
            num = (int) (-(num + 1));
            binaryStr = formatString(toBinaryString(num), 32);
            binaryStr = reverseZeroAndNull(binaryStr);
        }
        System.out.printf("%-6s: \"%s\"\n", "int", makeWhitespace(binaryStr));
    }

    static void num(short num) {
        String binaryStr = "";

        if (num >= 0) {
            binaryStr = formatString(toBinaryString(num), 16);
        } else {
            num = (short) (-(num + 1));
            binaryStr = formatString(toBinaryString(num), 16);
            binaryStr = reverseZeroAndNull(binaryStr);
        }
        System.out.printf("%-6s: \"%s\"\n", "short", makeWhitespace(binaryStr));
    }

    static void num(byte num) {
        String binaryStr = "";

        if (num >= 0) {
            binaryStr = formatString(toBinaryString(num), 8);
        } else {
            num = (byte) (-(num + 1));
            binaryStr = formatString(toBinaryString(num), 8);
            binaryStr = reverseZeroAndNull(binaryStr);
        }
        System.out.printf("%-6s: \"%s\"\n", "byte", makeWhitespace(binaryStr));
    }


    /**converts any decimal num to its binary representation
     *
     * @param num any decimal num
     * @return converted decimal argument as a String line
     */
    static String toBinaryString(long num) {
        String str = "";
        while (num != 0) {
            str = (num % 2) + str;
            num >>= 1;
        }
        return str;
    }

    /**makes whitespace every 8 symbols
     *
     * @param str String line
     * @return converted String line
     */
    static String makeWhitespace(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if (i != 0 && i % 8 == 0) result = result.concat(" ");
            result += str.toCharArray()[i];

        }
        return result;
    }

    /**line argument converts by rule
     * if symbol==0, it became 1
     * if symbol==1, it became 0
     *
     * @param binaryStr line with from 1 and 0
     * @return converted line
     */
    static String reverseZeroAndNull(String binaryStr) {
        String reversedStr = "";
        for (char i : binaryStr.toCharArray()) {
            if (i == '0') reversedStr = reversedStr.concat("1");
            else reversedStr = reversedStr.concat("0");
        }
        return reversedStr;
    }

    /**adds zero before line to fit memory length
     *
     * @param str line from 1 and 0
     * @param bits num of bits needed to save data in computer memory
     * @return  0...bits-2...0+ line
     */
    static String formatString(String str, int bits) {
        return String.format("%0" + (bits - str.length()) + "d%s", 0, str);
    }
}
