import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
  
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
  
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");
    public static String result;
    public static String value;
    public static char sign;

    public BigInteger() {
        result = "not working";
    }
  
  
    public BigInteger(int i)
    {
        result = "Done!"+i;
    }
  
    public BigInteger(int[] num1)
    {
    }
  
    public BigInteger(String s)
    {
        if(s.charAt(0)=='+') {
            value = s.substring(1);
            sign = '+';
        } else if(s.charAt(0)=='-') {
            value = s.substring(1);
            sign = '-';
        } else {
            value = s;
        }
    }
  
    public BigInteger add(BigInteger big)
    {
        return new BigInteger(1);
    }
  
    public BigInteger subtract(BigInteger big)
    {
        return new BigInteger(2);
    }
  
    public BigInteger multiply(BigInteger big)
    {
        return new BigInteger(3);
    }
  
    @Override
    public String toString()
    {
        return result;
    }
  
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        String operator="nope";
        // implement here
        // parse input
        // using regex is allowed
        for(int i=0; i<input.length(); i++) {
            if(input.charAt(i)=='+') {
                operator="\\+"; // +
                break;
            } else if(input.charAt(i)=='-') {
                operator="-"; // -
                break;
            } else if(input.charAt(i)=='*') {
                operator="\\*"; // *
                break;
            }

        }

        String[] nums = input.split(operator);

        BigInteger num1 = new BigInteger(nums[0].trim());
        BigInteger num2 = new BigInteger(nums[1].trim());

        BigInteger result = new BigInteger();
        switch (operator) {
            case "\\+":
                result = num1.add(num2);
                break;
            case "-":
                result = num1.subtract(num2);
                break;
            case "\\*":
                result = num1.multiply(num2);
                break;
            default:
                result = new BigInteger();
        }
        return result;

    }
  
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
  
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
  
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
  
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
  
            return false;
        }
    }
  
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
