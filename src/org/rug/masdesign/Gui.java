package org.rug.masdesign;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;
import org.rug.masdesign.agents.Population;

public class Gui {

    public static void main(String[] args) {
        new Gui();
    }

    public Gui() {
    	try {
    	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    	        if ("Nimbus".equals(info.getName())) {
    	            UIManager.setLookAndFeel(info.getClassName());
    	            break;
    	        }
    	    }
    	} catch (Exception e) {
    		
    	}


        final JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Gossip Simulation");
        guiFrame.setSize(1000, 600);
        guiFrame.setLocationRelativeTo(null);

        String[] execOptions = { "1", "2", "3", "4","5", "6", "7", "8", "9" };
        String[] sizeOptions = {"10","20","30","40","50","60","70","80","90","100",
                "110","120","130","140","150","160","170","180","190","200",
                "210","220","230","240","250","260","270","280","290","300",
                "310","320"
        };
        

        
        final JComboBox <String>executions = new JComboBox<>(execOptions);
        executions.setSelectedIndex(4);
        
        

        final JPanel comboPanel = new JPanel();
        JLabel executionsLabel = new JLabel("Executions Per Size (From/To):");
        executionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                
        //comboPanel.add(comboLbl, BorderLayout.NORTH);
        
        final JSlider slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMinimum(0);
        slider.setMaximum(320);
        slider.setMaximumSize(new Dimension(400, 50));
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        
        final JSlider slider2 = new JSlider();
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider2.setMinimum(0);
        slider2.setMaximum(320);
        slider2.setMaximumSize(new Dimension(400, 50));
        slider2.setMajorTickSpacing(25);
        slider2.setMinorTickSpacing(5);
        
        JLabel intervalLabel = new JLabel("Interval of Years Between Generations:");
        intervalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        executions.setAlignmentX(Component.CENTER_ALIGNMENT);
        executions.setMaximumSize(new Dimension (50, 30));
        
        
        final JSlider slider3 = new JSlider();
        slider3.setPaintTicks(true);
        slider3.setPaintLabels(true);
        slider3.setMinimum(0);
        slider3.setMaximum(40);
        slider3.setMaximumSize(new Dimension(200, 50));
        slider3.setMajorTickSpacing(10);
        slider3.setMinorTickSpacing(5);
        
        
        /*comboPanel.add(comboLbl);
        comboPanel.add(executions);*/

        comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.PAGE_AXIS));
        comboPanel.add(executionsLabel);
        comboPanel.add(slider);
        comboPanel.add(slider2);
        comboPanel.add(intervalLabel);
        comboPanel.add(slider3);
        comboPanel.add(executions);
        comboPanel.setVisible(true);


        final JPanel listPanel = new JPanel();
        listPanel.setVisible(false);
        JLabel listLbl = new JLabel("Population Sizes:");
        final JList <String>sizes = new JList<>(sizeOptions);
        sizes.setFixedCellHeight(20);
        sizes.setFixedCellWidth(50);
        listPanel.setVisible(true);



        DefaultListCellRenderer renderer =  (DefaultListCellRenderer)sizes.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        sizes.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listPanel.add(listLbl);
        listPanel.add(sizes);

        final JTabbedPane  jtp = new JTabbedPane();;

        final JButton execBut = new JButton("Execute");
        final JButton backBut = new JButton("Back");


        execBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {


                List<String> values = sizes.getSelectedValuesList();
                String[] selected = values.toArray(new String[values.size()]);

                HashMap<Integer, ArrayList<Double>> results = 
                		generateResults(Integer.parseInt((String)executions.getSelectedItem()), slider.getValue(), slider2.getValue(), slider3.getValue());
                		//generateResults(Integer.parseInt((String)executions.getSelectedItem()),selected);
                
                /*populateTabbedPane(jtp, results);
                guiFrame.add(jtp, BorderLayout.CENTER);
                jtp.setVisible(true);*/
                
                
                
                JPanel jpanel = createDemoPanel(xydataset(results));
                guiFrame.add(jpanel, BorderLayout.CENTER);
                jpanel.setVisible(true);
                
                
                
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

        guiFrame.add(comboPanel, BorderLayout.CENTER);
        //guiFrame.add(listPanel, BorderLayout.NORTH);
        guiFrame.add(execBut, BorderLayout.SOUTH);

        guiFrame.setVisible(true);
    }
    public static void populateTabbedPane(JTabbedPane jtp, HashMap<Integer, ArrayList<Double>> results){
        //DecimalFormat dc = new DecimalFormat("0.0000");
        jtp.removeAll();
        
        for (Map.Entry<Integer, ArrayList<Double>> entry : results.entrySet()) {
            int size = entry.getKey();
            ArrayList<Double> probs = entry.getValue();
            JPanel jp = new JPanel();
            JLabel label = new JLabel();
            String text = "<html><b>Size:</b> "+size+"<b><br>Executions:</b> "+probs.size()+"<br><br><b>Final probabilities:</b><br>";
            for(double prob : probs){
                System.out.println("size: "+size+ ", probability: "+prob);
                text+="<br>"+String.format( "%.4f", prob);
            }

            double average = getAverage(probs);
            text+="<br><br><b>Average:</b> "+String.format( "%.4f", average )+"</html>";
            label.setText(text);
            jp.add(label);
            jtp.addTab(""+size, jp);
        }


    }
    
    public HashMap<Integer, ArrayList<Double>>  generateResults(int executions, int lowerValue, int higherValue, int interval){
    	HashMap<Integer, ArrayList<Double>> hash = new HashMap<Integer, ArrayList<Double>>();
        for(int size = lowerValue; size <=higherValue; size+=interval){
            //for each of the population size chosen

            //generate probabilities based on the number of executions
            ArrayList<Double> probs = new ArrayList<>();
            for(int j = 0; j< executions; j++){
                double d = getDefaultFinalAverage(size);
                probs.add(d);
                System.out.println("Size: "+size + ", probability: "+ d);
            }
            hash.put(size, probs);
        }
        return hash;

    }
    
    public HashMap<Integer, ArrayList<Double>>  generateResults(int executions, String[] sizes){
    	HashMap<Integer, ArrayList<Double>> hash = new HashMap<Integer, ArrayList<Double>>();
        for(int i = 0; i<sizes.length; i++){
            //for each of the population size chosen
            int size = Integer.parseInt(sizes[i]);

            //generate probabilities based on the number of executions
            ArrayList<Double> probs = new ArrayList<>();
            for(int j = 0; j< executions; j++){
                double d = getDefaultFinalAverage(size);
                probs.add(d);
            }
            hash.put(size, probs);
        }
        return hash;

    }

    public static double getAverage(ArrayList<Double> list){
        double total = 0;
        for (Double d : list){
            total += d;
        }
        return total/list.size();
    }

    private static double getDefaultFinalAverage(int populationSize){
        Population population = new Population(populationSize, 0.5, 30);
        population.execNGenerations(210);
        return population.getAverage();
    }
    public static JPanel createDemoPanel(XYDataset dataset) {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
            "Simulation Results", "Number of Generations", "Probability", dataset,
            PlotOrientation.VERTICAL, true, true, false);
        Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, cross);
        renderer.setSeriesPaint(0, Color.red);
        return new ChartPanel(jfreechart);
    }

    private static XYDataset xydataset(HashMap<Integer, ArrayList<Double>> results) {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries series = new XYSeries("Results");
        
        for (Map.Entry<Integer, ArrayList<Double>> entry : results.entrySet()) {
            int size = entry.getKey();
            ArrayList<Double> probs = entry.getValue();
            for (Double prob : probs){
            	series.add(size, prob);
            }
        }
              
        xySeriesCollection.addSeries(series);
        return xySeriesCollection;
    }
}
