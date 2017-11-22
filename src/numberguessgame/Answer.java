package numberguessgame;
import java.lang.String;
import java.util.*;

public class Answer
{   //製造答案
    public String answer = "";
    int numberOfDigits;

    Answer(int digits)
    {   //設定幾位數
        this.numberOfDigits = digits;
        answer = "";
    }

    public void setAnswer(String s)
    {   //建立答案
        answer = s ;
    }

    public void generate()
    {   //產生一組數字(答案)
        Random rand =new Random();
        int num[]  = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int temp, change;

        for (int i=0; i<10; i++)
        {
            temp = num[i];
            num[i] = num[change = rand.nextInt(10)];
            num[change] = temp;
        }

        for(int j=0; j<numberOfDigits; j++)
        {
            answer += new String(""+ num[j]);
        }
    }
}
