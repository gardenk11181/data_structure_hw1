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
    public String value;
    public char sign='+';

    public BigInteger() {
        value = "not working";
    }
  
  
    public BigInteger(int i)
    {
        value = "Done!"+i;
    }
  
    public BigInteger(int[] num1)
    {
    }
  
    public BigInteger(String s)
    {
        if(s.charAt(0)=='+') {
            value = s.substring(1).trim();
            sign = '+';
        } else if(s.charAt(0)=='-') {
            value = s.substring(1).trim();
            sign = '-';
        } else {
            value = s.trim();
        }
    }

    public BigInteger[] compareBigIntegerABS(BigInteger[] ints) { // 절댓값 비교함수
        if(ints[0].value.getBytes().length<ints[1].value.getBytes().length) {
            return new BigInteger[]{ints[1],ints[0]};
        } else if (ints[0].value.getBytes().length>ints[1].value.getBytes().length) {
            return ints;
        } else {
            for(int i=0; i<ints[0].value.getBytes().length; i++) {
                if(ints[0].value.getBytes()[i]<ints[1].value.getBytes()[i]) return new BigInteger[]{ints[1],ints[0]};
                if(ints[0].value.getBytes()[i]>ints[1].value.getBytes()[i]) return ints;
            }
            return ints;
        }
    }
  
    public BigInteger add(BigInteger big)
    {
        if(this.sign == '+' && big.sign == '-') {
            big.sign = '+';
            return subtract(big);
        } else if(this.sign == '-' && big.sign == '+') {
            this.sign = '+';
            return big.subtract(this);
        }

        BigInteger[] nums = compareBigIntegerABS(new BigInteger[]{this,big});
        byte[] biggerNum = nums[0].value.getBytes(); // 둘 중에 더 큰 수
        byte[] smallerNum = nums[1].value.getBytes();
        int smallerLength = nums[1].value.getBytes().length; // 둘 중에 더 작은 길
        int biggerLength = nums[0].value.getBytes().length;
        char biggerNumSign =  nums[0].sign;
        char smallerNumSign = nums[1].sign;


        byte[] result = new byte[biggerLength+1];

        for(int i=0 ; i<biggerLength; i++) {
            if(i>=smallerLength) {
                result[biggerLength-i] = biggerNum[biggerLength-1-i];
                continue;
            }
            byte sum = (byte)(biggerNum[biggerLength-1-i]+smallerNum[smallerLength-1-i]-'0');
            if(sum>'9') { // ascii of '9'
                if(i==biggerLength-1) {
                    result[biggerLength-i] = (byte)(sum-10);
                    result[0] = '1';
                    continue;
                }
                result[biggerLength-i] = (byte)(sum-10);
                biggerNum[biggerLength-2-i] +=1;
            } else {
                result[biggerLength-i] = sum;
            }
        }

        if(biggerNumSign=='-' && smallerNumSign=='-') return new BigInteger("-"+new String(result));

        return new BigInteger(new String(result));
    }
  
    public BigInteger subtract(BigInteger big)
    {

        if(this.sign =='+' && big.sign == '-') {
            big.sign = '+';
            return add(big);
        } else if(this.sign=='-' && big.sign == '+')  {
            big.sign = '-';
            return add(big);
        }

        BigInteger[] nums = compareBigIntegerABS(new BigInteger[]{this,big});
        byte[] biggerNum = nums[0].value.getBytes(); // 둘 중에 더 큰 수
        byte[] smallerNum = nums[1].value.getBytes();
        int smallerLength = nums[1].value.getBytes().length; // 둘 중에 더 작은 길
        int biggerLength = nums[0].value.getBytes().length;
        char biggerNumSign =  nums[0].sign;
        char smallerNumSign = nums[1].sign;

        byte[] result = new byte[biggerLength+1];



        for(int i=0 ; i<biggerLength; i++) {
            if(i>=smallerLength) {
                if(biggerNum[biggerLength-1-i]<'0') {
                    result[biggerLength-i] = (byte)(biggerNum[biggerLength-1-i]+10);
                    biggerNum[biggerLength-2-i] -= 1;
                } else {
                    result[biggerLength-i] = biggerNum[biggerLength-1-i];
                }
                continue;
            }
            byte sub = (byte)(biggerNum[biggerLength-1-i]-smallerNum[smallerLength-1-i]+'0');
            if(sub<'0') {
                result[biggerLength-i] = (byte)(sub+10);
                biggerNum[biggerLength-2-i] -= '1'-'0';
            } else {
                result[biggerLength-i] = sub;
            }
        }

        if(this.value.equals(nums[0].value)) {
            if(this.sign=='+') return new BigInteger(new String(result));
            else return new BigInteger("-"+new String(result));
        } else {
            if(this.sign=='+') return new BigInteger("-"+new String(result));
            else return new BigInteger(new String(result));
        }
    }
  
    public BigInteger multiply(BigInteger big)
    {
        return new BigInteger(3);
    }
  
    @Override
    public String toString()
    {
        for(int i=0;i<value.length();i++) {
            if(value.charAt(i)!='0') value = value.substring(i);
            break;
        }
        if(sign=='-') return "-"+value.trim();
        return value.trim();
    }
  
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        String operator="nope";
        // implement here
        // parse input
        // using regex is allowed
        String trimInput = input.trim();
        String[] nums = new String[2];

        for(int i=1; i<trimInput.length(); i++) {
            if(trimInput.charAt(i)=='+') {
                operator="\\+"; // +
                nums[0] = trimInput.substring(0,i);
                nums[1] = trimInput.substring(i+1);
                break;
            } else if(trimInput.charAt(i)=='-') {
                operator="-"; // -
                nums[0] = trimInput.substring(0,i);
                nums[1] = trimInput.substring(i+1);
                break;
            } else if(trimInput.charAt(i)=='*') {
                operator="\\*"; // *
                nums[0] = trimInput.substring(0,i);
                nums[1] = trimInput.substring(i+1);
                break;
            }

        }

        System.out.println(nums[0]+", "+nums[1]);
        BigInteger num1 = new BigInteger(nums[0].trim());
        BigInteger num2 = new BigInteger(nums[1].trim());

        BigInteger result;
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
