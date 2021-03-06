import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
  
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";

    public String value;
    public char sign='+';

    public BigInteger() {
        value = "not working";
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
                if(biggerNum[biggerLength-1-i]>'9') {
                    result[0] ='1';
                    result[biggerLength-i] = (byte)(biggerNum[biggerLength-1-i]-10);
                    continue;
                }
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

        if(biggerNumSign=='-' && smallerNumSign=='-') return new BigInteger("-"+(new String(result)).trim());

        return new BigInteger((new String(result)).trim());
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
            if(this.sign=='+') return new BigInteger((new String(result)).trim());
            else return new BigInteger(("-"+new String(result)).trim());
        } else {
            if(this.sign=='+') return new BigInteger(("-"+new String(result)).trim());
            else return new BigInteger((new String(result)).trim());
        }
    }
  
    public BigInteger multiply(BigInteger big)
    {
        // 처음에 '0'에 해당하는 값들을 빼준 후의 값들로 연산 -> 마지막에 다시 '0'을 더함.
        // add와 subtract도 수정 요망함.

        byte[] num1 = value.getBytes();
        byte[] num2 = big.value.getBytes();
        byte zero = '0';
        byte[] result = new byte[num1.length+num2.length];

        for(int i=0; i<num1.length; i++) {
            num1[i] = (byte)(num1[i]-zero);
        }

        for(int j=0; j<num2.length; j++) {
            num2[j] = (byte)(num2[j]-zero);
        }

        for(int i=(num1.length-1); i>=0; i--) {
            for(int j=(num2.length-1); j>=0; j--) {
                int multy = num1[i]*num2[j];
                result[i+j+1] += multy;
                if(result[i+j+1] >=10) {
                    result[i+j] += result[i+j+1]/10;
                    result[i+j+1] %= 10;
                }
            }
        }

        for(int i=0; i<result.length; i++) {
            result[i] += zero;
        }


        if(sign =='-') {
            if(big.sign=='+') return new BigInteger("-"+new String(result));
        } else {
            if(big.sign=='-') return new BigInteger("-"+new String(result));
        }
        return new BigInteger(new String(result));
    }
  
    @Override
    public String toString()
    {
        for(int i=0;i<value.length();i++) {
            if(value.charAt(i)!='0') {
                value = value.substring(i);
                break;
            }
            if(value.charAt(i)=='0' && i==value.length()-1) value="0";
        }
        if(sign=='-') {
            if(value=="0") return value.trim();
            return "-"+value.trim();
        }
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

//        System.out.println(nums[0].trim()+", "+nums[1].trim());
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
