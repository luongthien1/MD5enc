package DESpackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class Programs extends JFrame implements ActionListener{
	JPanel pKey;
	JLabel lbKey;
	JTextField txtKey;
	
	JPanel pText;
	JLabel lbText;
	JTextArea taInput;
	JLabel lbResult;
	JTextArea taResult;
	JButton btnEncrypt;
	JButton btnDecrypt;
	
	JButton btnReset;
	
	public static void main(String[] args) {
		new Programs();
	}
	
	public Programs() {
		this.setTitle("DES");
		this.setSize(600,600);
		this.setLocation(300,100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);

		this.pKey = new JPanel();
		this.lbKey = new JLabel("Key:");
		this.txtKey = new JTextField("0133457799bbcdff");
		
		this.lbKey.setBounds(100,50,70,40);
		this.txtKey.setBounds(180,50,200,40);
		
		this.txtKey.setHorizontalAlignment(JTextField.CENTER);
		
		this.pKey.add(this.lbKey);
		this.pKey.add(this.txtKey);
		this.pKey.setLayout(null);

		this.pKey.setBounds(20,20,540,110);
		this.pKey.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.pText = new JPanel(); 
		this.pText.setLayout(null);
		this.lbText = new JLabel("Input:");
		this.taInput = new JTextArea();
		this.taResult = new JTextArea();
		this.lbResult = new JLabel("Result:");
		this.btnEncrypt = new JButton("Encrypt");
		this.btnDecrypt = new JButton("Decrypt");
		this.btnReset = new JButton("Reset");
		
		this.lbResult.setBounds(20,140,100,40);
		this.lbText.setBounds(20,20,100,40);
		this.taInput.setBounds(120,20,300,100);
		this.btnEncrypt.setBounds(430,20,100,40);
		this.btnDecrypt.setBounds(430,60,100,40);
		this.taResult.setBounds(120,140,300,200);
		this.taInput.setBorder(BorderFactory.createLineBorder(Color.black));
		this.taResult.setBorder(BorderFactory.createLineBorder(Color.black));
		this.btnReset.setBounds(435,345,100,40);
		
		this.pText.add(this.lbText);
		this.pText.add(this.taInput);
		this.pText.add(this.btnEncrypt);
		this.pText.add(this.btnDecrypt);
		this.pText.add(this.taResult);
		this.pText.add(this.lbResult);
		this.pText.add(this.btnReset);
		this.pText.setBounds(20,150,540,390);
		this.pText.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.add(this.pKey);
		this.add(this.pText);
		
		this.btnReset.addActionListener(this);
		this.btnDecrypt.addActionListener(this);
		this.btnEncrypt.addActionListener(this);
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Reset":
			this.taInput.setText("");
			this.taResult.setText("");
			break;
		case "Encrypt":
			this.taResult.setText("");
			String temp = this.taInput.getText();
			byte[] textToBytes = temp.getBytes();
			if (this.txtKey.getText().length()==16 && textToBytes.length!=0) {
				while (textToBytes.length%8 !=0)
				{	
					temp+=" ";
					textToBytes = temp.getBytes();
				}
				DES.key = this.txtKey.getText();
				try {
					for (int i=0; i<textToBytes.length/8; i++) {
						byte[] textBytes = Arrays.copyOfRange(textToBytes, i*8, (i+1)*8);
						String text="";
						for (int j=0; j<textBytes.length;j++) {
							if ((textBytes[j]<16 && textBytes[j]>0)||(textBytes[j]<-240)) text+= 0;
							if (textBytes[j]<0) text+= Integer.toHexString(256+textBytes[j]);
							else text+= Integer.toHexString(textBytes[j]);
						}
						this.taResult.setText(this.taResult.getText()+DES.Encryption(text));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				this.taResult.setText("Xâu ký tự đầu vào vẫn còn trống hoặc key không hợp lệ.");
			}
			break;
		case "Decrypt":
			this.taResult.setText("");
			if (this.txtKey.getText().length()==16 && this.taInput.getText().length()%16==0) {
				DES.key = this.txtKey.getText();
				String Criphertext = this.taInput.getText();
				byte[] bytes = new byte[this.taInput.getText().length()/2];
				try {
					for (int i=0; i<Criphertext.length()/16; i++) {
						String text = Criphertext.substring(i*16, (i+1)*16);
						String hextext = DES.Decryption(text);
						
						for (int j=0; j<hextext.length()/2;j++) {
							bytes[i*8+j] = (byte) Integer.parseUnsignedInt(hextext.substring(j*2, (j+1)*2), 16);
							if (bytes[i*8+j]>128) bytes[i*8+j] -= 256; 
						}
					}
					this.taResult.setText(this.taResult.getText()+new String(bytes,StandardCharsets.UTF_8));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				this.taResult.setText("Xâu ký tự đầu vào vẫn còn trống hoặc key không hợp lệ.");
			}
			break;
		}
	}
}
