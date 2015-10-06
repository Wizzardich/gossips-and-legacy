package org.rug.masdesign;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Gui {

	public static void main(String[] args) {
		new Gui();
	}

	public Gui() {
		JFrame guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Gossip Simulation");
		guiFrame.setSize(600, 400);
		guiFrame.setLocationRelativeTo(null);
		
		String[] execOptions = { "1", "2", "3", "4","5", "6", "7", "8", "9" };
		String[] sizeOptions = {"10","20","30","40","50","60","70","80","90","100",
				"110","120","130","140","150","160","170","180","190","200",
				"210","220","230","240","250","260","270","280","290","300",
		};
		
		
		final JPanel comboPanel = new JPanel();
		JLabel comboLbl = new JLabel("Executions Per Size:");
		JComboBox <String>executions = new JComboBox<>(execOptions);
		executions.setSelectedIndex(4);
		comboPanel.add(comboLbl);
		comboPanel.add(executions);
		comboPanel.setVisible(true);
		
		
		final JPanel listPanel = new JPanel();
		listPanel.setVisible(false);
		JLabel listLbl = new JLabel("Population Sizes:");
		JList <String>sizes = new JList<>(sizeOptions);
		sizes.setFixedCellHeight(20);
		sizes.setFixedCellWidth(50);
		listPanel.setVisible(true);

		
		
		DefaultListCellRenderer renderer =  (DefaultListCellRenderer)sizes.getCellRenderer();  
		renderer.setHorizontalAlignment(JLabel.CENTER); 
		
		sizes.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listPanel.add(listLbl);
		listPanel.add(sizes);
		
		JTabbedPane  jtp = new JTabbedPane();;

		JButton execBut = new JButton("Execute");
		JButton backBut = new JButton("Back");

		
		execBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				
				
				List<String> values = sizes.getSelectedValuesList();
				String[] selected = values.toArray(new String[values.size()]);
		        
				
				populateTabbedPane(jtp, Integer.parseInt((String)executions.getSelectedItem()),selected);
				guiFrame.add(jtp, BorderLayout.CENTER);

				jtp.setVisible(true);
				guiFrame.add(backBut, BorderLayout.SOUTH);

				backBut.setVisible(true);
				
				comboPanel.setVisible(false);
				listPanel.setVisible(false);
				execBut.setVisible(false);
			}
		});
		
		backBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				comboPanel.setVisible(true);
				listPanel.setVisible(true);
				execBut.setVisible(true);

				jtp.setVisible(false);
			}
		});
		
		guiFrame.add(comboPanel, BorderLayout.NORTH);
		guiFrame.add(listPanel, BorderLayout.CENTER);
		guiFrame.add(execBut, BorderLayout.SOUTH);

		guiFrame.setVisible(true);
	}
	public static void populateTabbedPane(JTabbedPane jtp, int executions, String[] sizes){
		//DecimalFormat dc = new DecimalFormat("0.0000");
		jtp.removeAll();
		for(int i = 0; i<sizes.length; i++){
			//for each of the population size chosen
			int size = Integer.parseInt(sizes[i]);
			
			//generate probabilities based on the number of executions
			ArrayList<Double> probs = new ArrayList<>();
			for(int j = 0; j< executions; j++){
				double d = Main.getDefaultFinalAverage(size);
				probs.add(d);
			}
			
			JPanel jp = new JPanel();
			JLabel label = new JLabel();
			String text = "<html><b>Size:</b> "+sizes[i]+"<b><br>Executions:</b> "+executions+"<br><br><b>Final probabilities:</b><br>";
			for(double prob : probs){
				System.out.println("size: "+size+ ", probability: "+prob);
				text+="<br>"+String.format( "%.4f", prob);
			}
			
			double average = getAverage(probs);
			text+="<br><br><b>Average:</b> "+String.format( "%.4f", average )+"</html>";
	        label.setText(text);
			jp.add(label);
	        jtp.addTab(sizes[i], jp);
		}

	}
	public static double getAverage(ArrayList<Double> list){
		double total = 0;
		for (Double d : list){
			total += d;
		}
		return total/list.size();
	}
}
