
package numberguessgame;
import java.util.Scanner;
import java.io.*;
import java.util.Vector;
import java.util.Random;

public class NumberGuessGame {

    private int numberOfDigits;
    private boolean isDupDigits=true;
    Answer lastAnswer=null;
    static Vector possibleAnswerSet;
    public String outStr = null;
    static Vector oldAnswerSet;
    static Vector outSet;

    NumberGuessGame()
    {
       this(4);
    }  
    
    NumberGuessGame(int d)
    {  //設定幾位數  建立可能答案資料庫
       setNumberOfDigits(d);
       possibleAnswerSet = new Vector();
       oldAnswerSet = new Vector();
       outSet= new Vector();
    }

    public Answer generateAnswer()
    {   //new出一個答案
        Answer temp = new Answer(numberOfDigits);
        temp.generate();
        return temp;
    }

    public boolean same(Answer a)
    {   //判斷是否輸入一樣
        for(int i = 0; i < this.numberOfDigits; i++)
        {
            for(int j=i+1; j < this.numberOfDigits; j++)
            {
                if(a.answer.charAt(i) == a.answer.charAt(j))
                {
                    return true;
                }
            }
        }
        return false;
    }

     public void start(Answer answer,Answer userGuess)
    {   //開始進入比較
        //建立資料庫
        if(!same(userGuess))
        {
            oldAnswerSet.add(userGuess);
            generateGuess(answer, outStr,userGuess);
            outStr = compare(userGuess,answer);
            outSet.add(outStr);
            WindowInterface.answerArea.append(outStr+"\t"+"NO."+WindowInterface.count+" \n");
            WindowInterface.count++;
            //WindowInterface.answerArea.append("size of possible answer set =" + possibleAnswerSet.size()+"\n");
      
            if(outStr.equals("" + this.numberOfDigits + "A0B"))
            {
                WindowInterface.answerArea.append("You Win and Game Over! \n");
                WindowInterface.answerArea.append("Game reStart... (please clicke submit) \n");
                WindowInterface.over=false;
            }
        }
        else
        {
            WindowInterface.answerArea.append("You guess answer is wrong~ Please guess angin \n");
        }
    }

    public String compare(Answer t, Answer a)
    {   //判斷數字是幾A幾B
        int numA = 0, numB = 0, numC = 0;
        String outString = "";
        //判斷數字是幾A
        for(int k = 0; k < this.numberOfDigits; k++)
        {
            if(t.answer.charAt(k) == a.answer.charAt(k))
            {
                numA++;
            }
        }
        //判斷數字是幾B
        for(int i=0; i < this.numberOfDigits; i++)
        {
            for(int j=0; j < this.numberOfDigits; j++)
            {
                if(t.answer.charAt(i) == a.answer.charAt(j))
                {
                    numC++;
                }
            }
        }
        numB = numC - numA;
        outString="" + numA + "A" + numB + "B";
    return outString;
    }
    
    public void generateGuess(Answer answer, String outStr,Answer userGuess)
    {   //建立可能的答案資料庫起始程式
       
        if(WindowInterface.count==1)
        {
            lastAnswer = userGuess;
        }
        else if(WindowInterface.count==2)
        {   //建立可能的答案資料庫
            Answer a = generateAnAnswer(answer, outStr);
            lastAnswer =a;
        }
        else
        {
            //從答案資料庫取出一個答案
            outStr=compare(answer,lastAnswer);
            Answer a = generateAnotherAnswer(answer, outStr);
            lastAnswer = a ;
        }

    }

    public Answer getUserGuess(String user_guess)
    {   //將輸入的字串新建成一個Answer的類別
        Answer temp = new Answer(this.numberOfDigits);
        temp.setAnswer(user_guess);
        return temp;
    }

    public void setNumberOfDigits(int d)
    {   //設定幾位數字
        numberOfDigits = d;
    }

    public int getNumberOfDigits()
    {
        return numberOfDigits;
    }

    public Answer generateAnAnswer(Answer answer, String outStr)
    {   //建立可能的答案資料庫
        String answerStr="";

        for(int w=0;w<10;w++)
        {
            for(int x=0;x<10;x++)
            {
                for(int y=0;y<10;y++)
                {
                    for(int z=0;z<10;z++)
                    {
                        answerStr= new String(""+ w + "" + x + "" + y + "" + z);
                        Answer temp = new Answer(this.numberOfDigits);
                        temp.setAnswer(answerStr);
                        String outStr2 = compare(temp, lastAnswer);
                        //把可能的答案add入資料庫
                        if(outStr2.equals(outStr))
                        {   //數字相同的可能資料不加入資料庫
                            if(!same(temp))
                            {
                                NumberGuessGame.possibleAnswerSet.add(temp);
                            }
                        }
                     }
                }
            }
        }
        Random rand =new Random();
        Answer temp = (Answer)(possibleAnswerSet.get(rand.nextInt(possibleAnswerSet.size())));

        return temp;
    }

    public Answer generateAnotherAnswer(Answer answer, String outStr)
    {
        String answerStr="";

        for(int w=0;w<possibleAnswerSet.size();w++)
        {
            Answer temp = (Answer)(possibleAnswerSet.get(w));
            String outStr2 = compare(temp, lastAnswer);
            //篩選答案
            if(!outStr2.equals(outStr))
            {   //把不是答案remove資料庫
                NumberGuessGame.possibleAnswerSet.remove(temp);
            }
        }
        Random rand =new Random();
        Answer temp = (Answer)(possibleAnswerSet.get(rand.nextInt(possibleAnswerSet.size())));

        return temp;
    }
}
