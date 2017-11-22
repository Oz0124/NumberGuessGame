
package numberguessgame;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class WindowInterface {

    Frame mainWin;
    static TextArea answerArea,menu;
    Panel inputPanel, digitInputPanel, operationPanel,lab,op;
    Button num[];
    Button clear, back, submit;
    Label digits[];
    int cur=0;
    int i;
    String user_guess;
    static int count=1;
    public Answer answer;
    public NumberGuessGame theGame;
    String ans;
    static boolean over=true;
    public int Digits=4;

    public void init()
    {
        theGame = new NumberGuessGame(Digits);
        answer = theGame.generateAnswer();
    }

    WindowInterface()
    {
        mainWin = new Frame("Number Guessing Game English  v.0.10");
        mainWin.setLocation(100,100);
        mainWin.setSize(520, 420);
        mainWin.setLayout(new BorderLayout());

        //theGame = new NumberGuessGame(Digits);
        //answer = theGame.generateAnswer();

        answerArea = new TextArea("Welcome play Number Guessing Game! \n", 10, 25, TextArea.SCROLLBARS_VERTICAL_ONLY);
        //menu文字區域
        menu=new TextArea("Menu \n \n", 10, 15, TextArea.SCROLLBARS_NONE);
        menu.append("input:\n \n *0000 Peek Answer \n \n *1111 reStart \n \n *2222 Cue \n \n *3333 History guess \n \n *5555 Computer \t takes over");
        
        //唯讀
        answerArea.setEditable(false);
        menu.setEditable(false);
        //背景顏色
        //answerArea.setBackground(Color orange);

        inputPanel = new Panel();
        inputPanel.setLayout(new BorderLayout());
        this.digitInputPanel = new Panel();
        this.digitInputPanel.setLayout(new GridLayout(2,5));
        num = new Button[10];

        for(i=0;i<10;i++)
        {   //建立0~9的數字鍵功能
            num[i] = new Button("" + i);
            num[i].addMouseListener( new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    if(cur<4)
                    {
                        digits[cur].setText( ((Button)(e.getSource())).getLabel());
                        cur++;
                    }
                }
            });
            digitInputPanel.add(num[i]);
        }

        this.operationPanel = new Panel();
        this.operationPanel.setLayout(new BorderLayout());

        this.lab = new Panel();
        
        digits = new Label[4];

        for(i=0;i<4;i++)
        {
            digits[i] = new Label("?");
            this.lab.add(digits[i]);
        }

        back = new Button("back");
        clear = new Button("Clear");
        submit = new Button("Submit");

        this.op = new Panel();
        this.op.setLayout(new BorderLayout());

        op.add(back,BorderLayout.WEST);
        op.add(clear,BorderLayout.CENTER);
        op.add(submit,BorderLayout.EAST);

        back.addMouseListener( new MouseAdapter()
        {   //建立back往後刪除鍵功能
            public void mouseClicked(MouseEvent e)
            {
                if(cur>=0)
                {
                    if(cur!=0)
                    {
                        cur--;
                    }
                    digits[cur].setText("?");
                }
            }
        });

        clear.addMouseListener( new MouseAdapter()
        {   //建立清除鍵功能
            public void mouseClicked(MouseEvent e)
            {
                for(cur--;cur>=0;cur--)
                {
                    if(cur>=0)
                    {
                         digits[cur].setText("?");
                    }
                }
                cur++;
            }
        });

        submit.addMouseListener( new MouseAdapter()
        {   //輸入鍵功能
            public void mouseClicked(MouseEvent e)
            {
                String user_guess="";
                for(int i=0;i<Digits;i++)
                {
                    user_guess=user_guess+digits[i].getText();
                }
                
                if(over){
                    if(cur==Digits)
                    {
                        if(user_guess.equals("0000"))
                        {   //偷看答案
                            answerArea.append("Answer is "+answer.answer+"\n");
                        }
                        else if(user_guess.equals("1"+"1"+"1"+"1"))
                        {   //重新開始
                            over=false;
                            answerArea.append("(please clicke submit angin) \n");
                        }
                        else if(user_guess.equals("2"+"2"+"2"+"2"))
                        {   //提示
                            if(count>2)
                            {
                            Random rand =new Random();
                            Answer temp = (Answer)(NumberGuessGame.possibleAnswerSet.get(rand.nextInt(NumberGuessGame.possibleAnswerSet.size())));
                            answerArea.append("Cue Answer is "+temp.answer+"\n");
                            }
                            else
                            {
                                answerArea.append("No Cue! (please guess angin) \n");
                            }
                        }
                        else if(user_guess.equals("3"+"3"+"3"+"3"))
                        {   //觀看歷史答案
                            if(count>1)
                            {
                                answerArea.append("History guess: \n");
                                for(int i=0;i<NumberGuessGame.oldAnswerSet.size();i++)
                                {
                                    Answer temp = (Answer)(NumberGuessGame.oldAnswerSet.get(i));
                                    String str=(String)(NumberGuessGame.outSet.get(i));
                                    answerArea.append(temp.answer+"\t"+str+"\n");
                                }
                            }
                            else
                            {
                                answerArea.append("No History guess \n");
                            }
                        }
                        else if(user_guess.equals("5"+"5"+"5"+"5"))
                        {   //電腦接手
                            if(count>2)
                            {
                                while(over)
                                {
                                    Random rand =new Random();
                                    Answer temp = (Answer)(NumberGuessGame.possibleAnswerSet.get(rand.nextInt(NumberGuessGame.possibleAnswerSet.size())));
                                    answerArea.append(temp.answer+"\t");
                                    theGame.start(answer,temp);
                                }
                            }
                            else
                            {
                                answerArea.append("Computer computation has not completed...(please guess angin) \n");
                            }
                        }
                        else
                        {   //進入判斷
                            answerArea.append(user_guess+"\t");
                            Answer inputguess=theGame.getUserGuess(user_guess);
                            theGame.start(answer,inputguess);
                        }
                    }
                    else
                    {   //輸入不完全
                        answerArea.append("After please complete the input, presses down Submit again! \n");
                    }
                }
                else
                {   //重新設定答案  遊戲重新開始
                    answer = theGame.generateAnswer();
                    answerArea.append("Game reStart... OK! \n");
                    count=1;
                    over=true;
                    //清空記憶體(歷史答案庫and可能答案庫)
                    NumberGuessGame.oldAnswerSet.clear();
                    NumberGuessGame.outSet.clear();
                    NumberGuessGame.possibleAnswerSet.clear();
               }
                //清除
                for(cur--;cur>=0;cur--)
                {
                    if(cur>=0)
                    {
                         digits[cur].setText("?");
                    }
                }
                cur++;
            }
        
        });

        operationPanel.add(this.lab, BorderLayout.CENTER);
        operationPanel.add(this.op, BorderLayout.SOUTH);

        inputPanel.add(this.digitInputPanel, BorderLayout.CENTER);
        inputPanel.add(this.operationPanel, BorderLayout.EAST);

        mainWin.add(inputPanel, BorderLayout.SOUTH);
        mainWin.add(answerArea,BorderLayout.CENTER);
        mainWin.add(menu,BorderLayout.WEST);

        mainWin.addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        init();

        mainWin.setVisible(true);

    }

}

