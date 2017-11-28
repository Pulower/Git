package hangman;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Hangman extends JFrame{

	private JPanel charsPanel, mainPanel;
	private JLabel theWord, typeHere;
	private JButton enter;
	private JPasswordField textField;
	private ArrayList<Character> charList = new ArrayList<>();
	private int length, point = 0, toWin = 0;
	private String toCheck, sticks, buttonName;
	private boolean miss = false;
	private char[] charArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'u', 'w', 'x', 'y', 'z'};
	private ArrayList<JButton> buttonList = new ArrayList<>();
	
	public static void main(String[] args) {
		
		new Hangman();

	}
	public Hangman()
	{
		this.setTitle("Hangman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		typeHere = new JLabel("Type Here: ", SwingConstants.RIGHT);
		textField = new JPasswordField("", 10);

		//PANELS
		mainPanel = new JPanel();		//MAIN
		mainPanel.setLayout(new BorderLayout());
		
		JPanel labelPanel = new JPanel();		//TEXT FIELD AT END	
		labelPanel.setLayout(new GridLayout(1, 2));
		labelPanel.add(typeHere);
		labelPanel.add(textField);
		
		
		charsPanel = new JPanel();		//ALL CHARCTERS
		charsPanel.setLayout(new GridLayout(3, 8));
		ListenForButton listener = new ListenForButton();
		
		for(char c : charArray)
		{
			buttonName = "" + c;
			makeButton(buttonName, listener);
		}
		
		
		enter = new JButton("Start");
		enter.addActionListener(listener);
		labelPanel.add(enter);
		
		
		theWord = new JLabel(textField.getText(), SwingConstants.CENTER);
		Font font = new Font("Helvetica", 0, 20);
		theWord.setFont(font);
		
		Box theBox = Box.createHorizontalBox();
		theBox.add(Box.createHorizontalStrut(20));
		theBox.add(theWord);
		theBox.add(Box.createHorizontalStrut(20));
		
		mainPanel.add(theBox);
		mainPanel.add(charsPanel, BorderLayout.NORTH);
		mainPanel.add(labelPanel, BorderLayout.SOUTH);
		
		this.add(mainPanel);
		this.setVisible(true);
	}
	
	private void makeButton(String label, ActionListener listener)
	{
		JButton button = new JButton(label.toUpperCase());
		button.addActionListener(listener);
		charsPanel.add(button);
		buttonList.add(button);
	}
	
		private class ListenForButton implements ActionListener
		{
			public void actionPerformed(ActionEvent event) {
				
				if(event.getSource() == enter)
				{
					enableAll(charArray, buttonList);
					
					sticks = "";
					toCheck = textField.getText().toLowerCase();
					
					for(char c : toCheck.toCharArray())
					{
						charList.add(c);
					}
					
					length = textField.getText().length();
					textField.setText("");
					for(int i = 0; i < length; i++)
					{
						sticks +=" _";
					}
					theWord.setText(sticks); // -_-_-_-_
					point = 0;
					toWin = 0;
					charsPanel.setVisible(true);
				}
				
				
				checkSource(event.getSource(), charArray, buttonList);
				
			}
		}
		
		public void enableAll(char[] array, ArrayList<JButton> buttonList)
		{
			for(int i = 0; i < array.length; i++)
			{
				buttonList.get(i).setEnabled(true);
			}
		}
		
		public void checkSource(Object source, char[] array, ArrayList<JButton> buttonList)
		{
			for(int i = 0; i < array.length; i++)
			{
				if(source == buttonList.get(i))
				{
					check(array[i]);
					buttonList.get(i).setEnabled(false);
				}
			}
		}
		
		public void check(char c)
		{
			char character = c;
			StringBuilder temp = new StringBuilder(sticks);
			for(int i=0; i < length; i++)	
				{
					if(toCheck.charAt(i) == character)
					{
						temp.setCharAt(i+i+1, character);
						toWin += 1;
						miss = true;
					}
					
				}
			if(toWin == length){
				JOptionPane.showMessageDialog(Hangman.this,"YOU WINNN!!", "YOU WINNN!! ", JOptionPane.INFORMATION_MESSAGE);
				mainPanel.updateUI();
				charsPanel.setVisible(false);
			}
			if(!miss){
				point+=1;
				paintComponent(getGraphics(), point);
			}
			
			miss = false;
			
			if(point == 6){
				charsPanel.setVisible(false);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException exc) {
					exc.printStackTrace();
				}
				JOptionPane.showMessageDialog(Hangman.this,"YOU LOSEEEE", "YOU LOSEEEE", JOptionPane.INFORMATION_MESSAGE);
				mainPanel.updateUI();
			}
			
			sticks = temp.toString();
			theWord.setText(sticks);
		}
		public void paintComponent(Graphics g, int points)
		{
			Graphics2D g2 = (Graphics2D) g;
			if(point == 1)
			{
				g2.drawLine(375, 275, 400, 300);
				g2.drawLine(350, 300, 375, 275);
			}
			if(point == 2)
			{
				g2.drawLine(375, 275, 375, 175);	
			}
			if(point == 3)
			{
				g2.drawLine(375, 175, 475, 175);	
				g2.drawLine(475, 200, 475, 175);	
			}
			if(point == 4)
			{
				g2.drawOval(465, 200, 20, 20);
				g2.drawLine(475, 250, 475, 220);	
					
			}
			if(point == 5)
			{
				g2.drawLine(475, 222, 485, 240);	
				g2.drawLine(475, 222, 465, 240);
					
			}
			if(point == 6)
			{
				g2.drawLine(475, 250, 485, 268);	
				g2.drawLine(475, 250, 465, 268);
					
			}
			
		}
}
