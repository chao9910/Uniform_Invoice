import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Invoice extends JFrame implements ActionListener{
	Container c;
	JTextField txt,txt2,txt3,txt4;
	JLabel lab_p,lab_p2,lab_p3,lab_p4;
	String [] str = new String[4];//存放輸入的4組特、頭獎號碼
	JButton bot1;//對獎囉按鈕
	JButton bot2;//清除按鈕
	@SuppressWarnings("deprecation")
public Invoice(){
	super("發票對獎程式");
    c=getContentPane();//取得ContentPane
    //設定版面設定
    c.setLayout(new GridLayout(5,2,4,4));//設定為用5列2行的格子排列
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//設定關閉按鈕事件
    //初始化UI元件
    txt = new JTextField(10);
    txt2 = new JTextField(10);
    txt3 = new JTextField(10);
    txt4 = new JTextField(10);
    lab_p = new JLabel("特獎號碼");
    lab_p2 = new JLabel("第2組頭獎號碼");
    lab_p3 = new JLabel("第3組頭獎號碼");
    lab_p4 = new JLabel("第4組頭獎號碼");
    bot1 = new JButton("對獎囉");
    bot2 = new JButton("清除");
    //將發票輸入框與按鈕加入ContentPane
    c.add(lab_p);
    c.add(txt);
    c.add(lab_p2);
    c.add(txt2);
    c.add(lab_p3);
    c.add(txt3);
    c.add(lab_p4);
    c.add(txt4);
    c.add(bot1);
    c.add(bot2);
    bot1.addActionListener(this);
    setSize(200,200);//設定size
    show();
    bot2.addActionListener(this);
  }
public void actionPerformed(ActionEvent e){
	//對獎按鈕事件處理
	if(e.getSource() == bot1){
      str[0] = txt.getText();
      str[1] = txt2.getText();
      str[2] = txt3.getText();
      str[3] = txt4.getText();
      
      //判斷輸入的特、頭獎是否符合8位數 
	  if(str[0].length() == 8 && str[1].length() == 8 &&
	       str[2].length() == 8 && str[3].length() == 8){
		  try{
		     compare(str);
		  }catch(IOException ioe){
		    	  
		  }
	  }else{
		  showMessage("特、頭獎號碼未達8位數");
	  }
	}else if(e.getSource() == bot2){ //清除對獎號碼
	    txt.setText("");
	    txt2.setText("");
	    txt3.setText("");
	    txt4.setText("");
	}
}

//比對號碼
public static void compare(String[] prize) throws IOException{
	// 建立檔案輸入串流物件
    BufferedReader br = new BufferedReader(new FileReader("invoice.txt"));
    String number = new String(); //用來記錄每張發票的號碼
    String msgg = ""; //紀錄訊息
    try{
      next:
      while ((number = br.readLine()) != null){
    	// 比對特獎
        if (number.equals(prize[0])){
        	msgg += (number + "這張發票中了特獎!\n");
            continue next; // 這張中獎了, 換下一張發票繼續對獎
        }

        // 比對三組頭獎
        for (int i=1;i<prize.length;i++){
        	// 比對同一獎號的全部號碼...到末三碼
          for (int j=0;j<=5;j++){
        	  if ((number.substring(j)).equals (prize[i].substring(j))){
        		  if (j==0){
        			  msgg += (number + "這張發票中了頭獎!\n");
                  }else{
                	  msgg += (number + "這張發票中了" + (j+1) + "獎!\n");
                  }
        		  continue next; // 這張中獎了, 換下一張發票繼續對獎
        	  }
          } 
        }
      }
    }catch(EOFException e) {
    	
    }

    if(msgg.length() == 0)
    	msgg = "這次沒中獎，下次再加油吧!^^";
    showMessage(msgg);
    msgg = "";
  }

  public static void showMessage(String msg){
    JOptionPane.showOptionDialog(null,msg,
    "對獎訊息",
    JOptionPane.DEFAULT_OPTION,
    JOptionPane.INFORMATION_MESSAGE,
    null, null, null);
  }

  public static void main(String args[]){
	 Invoice app=new Invoice();
  }
}