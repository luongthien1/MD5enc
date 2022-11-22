package MD5Package;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import MD5Package.md5;

public class Program extends JFrame implements ActionListener {
	
	JLabel lbInput1;
	JLabel lbInput2;
	JTextField txtInput1;
	JTextField txtInput2;
	JRadioButton rbInput1;
	JRadioButton rbInput2;
	JButton btnInput2;
	JButton btnEncryp;
	JTextField txtRs;
	ButtonGroup btngroup;
	
	public static void main(String[] args) {
		new Program();
	}
	
	public Program() {
		this.setTitle("MD5");
		this.setSize(550,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(300,100);
		this.setLayout(null);
		
		this.rbInput1 = new JRadioButton();
		this.lbInput1 = new JLabel("Text:");
		this.txtInput1 = new JTextField();
		this.rbInput2 = new JRadioButton();
		this.lbInput2 = new JLabel("File:");
		this.btnInput2 = new JButton("Upload");
		this.txtInput2 = new JTextField();
		this.btnEncryp = new JButton("Encrypt");
		this.txtRs = new JTextField();
		this.btngroup = new ButtonGroup();
			this.btngroup.add(this.rbInput1);
			this.btngroup.add(this.rbInput2);
		
		this.rbInput1.setBounds(20,50,50,50);
		this.lbInput1.setBounds(70,50,100,50);
		this.txtInput1.setBounds(170,50,330,50);
		this.rbInput2.setBounds(20,120,50,50);
		this.lbInput2.setBounds(70,120,100,50);
		this.txtInput2.setBounds(170,120,250,50);
		this.btnInput2.setBounds(420,120,80,50);
		this.btnEncryp.setBounds((this.size().width-100)/2, 200, 100, 50);
		this.txtRs.setBounds(20,270,480,50);
		
		this.btnInput2.addActionListener(this);
		this.btnEncryp.addActionListener(this);
		
		this.add(rbInput1);
		this.add(rbInput2);
		this.add(txtInput1);
		this.add(txtInput2);
		this.add(lbInput1);
		this.add(lbInput2);
		this.add(btnInput2);
		this.add(btnEncryp);
		this.add(txtRs);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.btnInput2) {
			JFileChooser Upload_File = new JFileChooser();
			Upload_File.setDialogTitle("MD5 upload file");
			
			int res = Upload_File.showOpenDialog(null);
			
			if(res == JFileChooser.APPROVE_OPTION) {
				this.txtInput2.setText(Upload_File.getSelectedFile().getAbsolutePath());
			}
		}
		else if (e.getSource() == this.btnEncryp) {
			if (this.rbInput1.isSelected())
				this.txtRs.setText(md5.MD5(this.txtInput1.getText()));
			else if (this.rbInput2.isSelected()) {
				File file = new File(this.txtInput2.getText());
				try {
					FileInputStream fin = new FileInputStream(file);
					this.txtRs.setText(md5.MD5(new String(fin.readAllBytes())));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
}
