import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OperationPuzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//for (int i = 0; i < 100; i++) 
		//	System.out.println(PuzzleFuncs.weighRandom(5));
		
		
		
		JFrame frame = new JFrame("OperationPuzzle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 500);
		
		JPanel topPanel = new JPanel();
		JPanel midPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JLabel text1 = new JLabel("problem numbers");
		JLabel text2 = new JLabel("wanted result");
		JTextField numbersField = new JTextField("30 20 10");
		JTextField resultField = new JTextField("100");
		JButton thisProb = new JButton("solve this prob");
		JButton solvableProb = new JButton("solvable problem");
		JButton randomProb = new JButton("fully random problem");
		topPanel.add(text1);
		topPanel.add(text2);
		midPanel.add(numbersField);
		midPanel.add(resultField);
		bottomPanel.add(thisProb);
		bottomPanel.add(solvableProb);
		bottomPanel.add(randomProb);
		
		frame.getContentPane().add(BorderLayout.NORTH, topPanel);
        frame.getContentPane().add(BorderLayout.CENTER, midPanel);        
        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        frame.setVisible(true);
        
        thisProb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String probS = numbersField.getText().toUpperCase();
				String res = resultField.getText();
				int wantedRes = 0;
				ArrayList<Integer> nums = new ArrayList<Integer>();
				String[] probs = probS.split(" ");
				for (int i = 0; i < probS.length(); i++) {
					try {
						int in = Integer.parseInt(probs[i]);
						nums.add(in);					
					}
					catch (Exception ex) {
						
					}
				}
				try {
					int in = Integer.parseInt(res);
					wantedRes = in;				
				}
				catch (Exception ex) {
					
				}
				String feedback = "" + nums + "->" + wantedRes + ": " + PuzzleFuncs.operationsGame(nums, wantedRes);
				JOptionPane.showMessageDialog(null, feedback);	
				
			}
        	
        });
        solvableProb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				ArrayList<Integer> generatedProb = PuzzleFuncs.generateProb();
				while (generatedProb == null)
					generatedProb = PuzzleFuncs.generateProb();
				int probSize = generatedProb.size();
				int probRes = generatedProb.get(probSize-1);
				generatedProb.remove(probSize-1);
				String feedback = "numbers: " + generatedProb + " wanted: " + probRes;
				String r = PuzzleFuncs.operationsGame(generatedProb, probRes);
				feedback += ":  " + r;
				
				JOptionPane.showMessageDialog(null, feedback);	
			}
        	
        });
        randomProb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				ArrayList<Integer> generatedProb = PuzzleFuncs.generateRandomProb();
				while (generatedProb == null)
					generatedProb = PuzzleFuncs.generateRandomProb();
				int probSize = generatedProb.size();
				int probRes = generatedProb.get(probSize-1);
				generatedProb.remove(probSize-1);
				String feedback = "numbers: " + generatedProb + " wanted: " + probRes;
				String r = PuzzleFuncs.operationsGame(generatedProb, probRes);
				feedback += ":  " + r;
				
				JOptionPane.showMessageDialog(null, feedback);	
			}
        	
        });
		
		
	}
	
	
	
	
	

}
