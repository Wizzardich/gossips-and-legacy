package org.rug.masdesign;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    	//set look and feel to the interface
    	try {
    	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    	        if ("Nimbus".equals(info.getName())) {
    	            UIManager.setLookAndFeel(info.getClassName());
    	            break;
    	        }
    	    }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	//the frame correspondent to the interface
        final JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Gossip Simulation");
        guiFrame.setSize(1000, 600);
        guiFrame.setLocationRelativeTo(null);

        //options for how many executions per group size
        String[] execOptions = { "1", "2", "3", "4","5", "6", "7", "8", "9" };
        final JComboBox <String>executions = new JComboBox<>(execOptions);
        //default of one execution per group size
        executions.setSelectedIndex(0);
        executions.setAlignmentX(Component.CENTER_ALIGNMENT);
        executions.setMaximumSize(new Dimension (50, 30));
        
        //options for display result option
        String[] resultDisplayOptions = { "graph", "table" };
        final JComboBox <String>resultDisplay = new JComboBox<>(resultDisplayOptions);
        //default of one execution per group size
        resultDisplay.setSelectedIndex(0);
        resultDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultDisplay.setMaximumSize(new Dimension (90, 30));
        
        //labels
        JLabel titleLabel = new JLabel("GOSSIP SIMULATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel sizesLabel = new JLabel("Population Sizes (From/To):");
        sizesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel intervalLabel = new JLabel("Interval of Years Between Generations:");
        intervalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel executionsLabel = new JLabel("Executions Per Generation:");
        executionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel displayLabel = new JLabel("Format of Display:");
        displayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        //sliders
        
        //slider for the starting population size
        final JSlider sliderFrom = new JSlider();
        sliderFrom.setPaintTicks(true);
        sliderFrom.setPaintLabels(true);
        sliderFrom.setSnapToTicks(true);
        sliderFrom.setMinimum(0);
        sliderFrom.setMaximum(200);
        sliderFrom.setMajorTickSpacing(50);
        sliderFrom.setMinorTickSpacing(10);
        sliderFrom.setValue(10);
        
        //slider for the ending population size
        final JSlider sliderTo = new JSlider();
        sliderTo.setPaintTicks(true);
        sliderTo.setPaintLabels(true);
        sliderTo.setSnapToTicks(true);
        sliderTo.setMinimum(0);
        sliderTo.setMaximum(200);
        sliderTo.setMajorTickSpacing(50);
        sliderTo.setMinorTickSpacing(10);
        sliderTo.setValue(50);

        //slider for the interval between population sizes  
        final JSlider sliderInterval = new JSlider();
        sliderInterval.setPaintTicks(true);
        sliderInterval.setPaintLabels(true);
        sliderInterval.setSnapToTicks(true);
        sliderInterval.setMinimum(0);
        sliderInterval.setMaximum(40);
        sliderInterval.setMaximumSize(new Dimension(200, 50));
        sliderInterval.setMajorTickSpacing(10);
        sliderInterval.setMinorTickSpacing(5);
        sliderInterval.setValue(10);

        //panels
        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.PAGE_AXIS));
        titlePanel.add(Box.createRigidArea(new Dimension(5,40)));
        titlePanel.add(titleLabel);
        
        final JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
        
        final JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.PAGE_AXIS));
        
        final JPanel innerPanel0 = new JPanel();
        innerPanel0.setLayout(new FlowLayout());
        
        final JPanel innerPanel1 = new JPanel();
        innerPanel1.setLayout(new FlowLayout());
        
        
        //setting the inner panels and option panel
        
        innerPanel0.add(sliderFrom);
        innerPanel0.add(sliderTo);
        innerPanel0.setMaximumSize(new Dimension (600, 10));
        
        innerPanel1.add(executionsLabel);
        innerPanel1.add(executions);
        innerPanel1.add(Box.createRigidArea(new Dimension(25,5)));
        innerPanel1.add(displayLabel);
        innerPanel1.add(resultDisplay);
        
        optionsPanel.add(Box.createRigidArea(new Dimension(5,40)));
        optionsPanel.add(sizesLabel);
        optionsPanel.add(innerPanel0);
        optionsPanel.add(Box.createRigidArea(new Dimension(5,30)));
        optionsPanel.add(intervalLabel);
        optionsPanel.add(sliderInterval);
        optionsPanel.add(Box.createRigidArea(new Dimension(5,30)));
        optionsPanel.add(innerPanel1);
       
        
        //buttons
        final JButton execBut = new JButton("Execute");
        final JButton backBut = new JButton("Back");
        
        execBut.setPreferredSize(new Dimension(100, 30));
        backBut.setPreferredSize(new Dimension(100, 30));

        final JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel flowPanel = new JPanel(new FlowLayout());
        flowPanel.add(execBut);
        buttonPanel.add(BorderLayout.CENTER, flowPanel);
        
        final JPanel buttonBackPanel = new JPanel(new BorderLayout());
        JPanel flowBackPanel = new JPanel(new FlowLayout());
        flowBackPanel.add(backBut);
        buttonBackPanel.add(BorderLayout.CENTER, flowBackPanel);
        
        execBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String display = (String)resultDisplay.getSelectedItem();
                
                int numberOfExecutions = Integer.parseInt((String)executions.getSelectedItem());
                
                int rangeFrom = sliderFrom.getValue();
                int rangeTo = sliderTo.getValue();
                int populationInterval = sliderInterval.getValue();
                
                HashMap<Integer, ArrayList<Double>> results = 
                		generateResults(numberOfExecutions, rangeFrom, rangeTo, populationInterval);
                
                resultsPanel.removeAll();
                
                if(display.equalsIgnoreCase("graph")){
                	ChartPanel graphPanel = generateGraphPanel(results);	
                	resultsPanel.add(graphPanel, BorderLayout.CENTER);
                }
                else{
                	JTabbedPane tabbedPane = generateTabbedPane(results);
                	resultsPanel.add(tabbedPane, BorderLayout.CENTER);
                }
                guiFrame.add(buttonBackPanel, BorderLayout.SOUTH);
            	guiFrame.add(resultsPanel, BorderLayout.CENTER);
                
                buttonBackPanel.setVisible(true);

                optionsPanel.setVisible(false);
                buttonPanel.setVisible(false);
                resultsPanel.setVisible(true);


            }
        });

        backBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                optionsPanel.setVisible(true);
                resultsPanel.setVisible(false);
                buttonPanel.setVisible(true);

            }
        });

        
        //setting all all components in the frame
        
        guiFrame.add(titlePanel, BorderLayout.NORTH);
        guiFrame.add(optionsPanel, BorderLayout.CENTER);
        guiFrame.add(buttonPanel, BorderLayout.SOUTH);

        guiFrame.setVisible(true);
    }
    
    
    public static ChartPanel generateGraphPanel(HashMap<Integer, ArrayList<Double>> results) {
    	
    	XYDataset dataset = xydataset(results);
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

    public static JTabbedPane generateTabbedPane(HashMap<Integer, ArrayList<Double>> results){
    	JTabbedPane tabbedPane = new JTabbedPane();
        
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
            tabbedPane.addTab(""+size, jp);
        }
        return tabbedPane;
    }
    
    public LinkedHashMap<Integer, ArrayList<Double>>  generateResults(int executions, int lowerValue, int higherValue, int interval){
    	LinkedHashMap<Integer, ArrayList<Double>> hash = new LinkedHashMap<Integer, ArrayList<Double>>();
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
