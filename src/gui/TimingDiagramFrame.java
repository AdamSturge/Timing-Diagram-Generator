package gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


import model.Instruction;

public class TimingDiagramFrame extends ApplicationFrame{

	public TimingDiagramFrame(ArrayList<Instruction> instructions) {
		super("TimingDiagram");
		JFreeChart chart = createChart(instructions);
		ChartPanel chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);
	}

	private JFreeChart createChart(ArrayList<Instruction> instructions ){
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		XYSeries series;
		Instruction s;
		int startTime;
		int runTime; //how long you need to run the line horizontally for the graph

		for(int i = 0; i < instructions.size(); ++i){
			series = new XYSeries(i+1);
			s = instructions.get(i);
			startTime = s.getStartTime();
			runTime = 0;
			if(s.getOperation().equals("ADD")){
				while(runTime <= Instruction.iUTime + Instruction.addTime) {
					series.add(startTime + runTime,i+1);
					runTime++;
				}
			}else if(s.getOperation().equals("MULT")){
				while(runTime <= Instruction.iUTime + Instruction.multTime) {
					series.add(startTime + runTime,i+1);
					runTime++;
				}
			}
			dataset.addSeries(series);
			renderer.setSeriesLinesVisible(i, true);
		}

		JFreeChart chart = ChartFactory.createXYLineChart("TimingDiagram","Time","Instruction Numer",dataset, 
				PlotOrientation.VERTICAL,true,true,false);     

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		return chart;
	}
}
