package view;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
 
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
 
public class PriceEstimator extends ApplicationFrame {
 
	private static final long serialVersionUID = 1L;
 
	XYDataset inputData;
	JFreeChart chart;
  

	public PriceEstimator(int preis [], int menge[]) throws IOException {
		super("Technobium - Linear Regression");
 
		int[] p = preis;
		int[] m = menge;
		// Read sample data from prices.txt file and 
		inputData = createDatasetFromFile(p,m);
 
		// Create the chart using the sample data
		chart = createChart(inputData);
 
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
	}
 
	public XYDataset createDatasetFromFile(int price [], int menge []) throws IOException {
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series = new XYSeries("Real estate item");
 
		// Read the price and the living area
		for (int i=0; i< price.length; i++){
			
			
			series.add(price[i],menge[i]);

			
		}
				
		
		dataset.addSeries(series);
 
		return dataset;
	}
 
	private JFreeChart createChart(XYDataset inputData) throws IOException {
		// Create the chart using the data read from the prices.txt file
		JFreeChart chart = ChartFactory.createScatterPlot(
				"Price for living area", "Price", "Luckson Gott", inputData,
				PlotOrientation.VERTICAL, true, true, false);
 
		XYPlot plot = chart.getXYPlot();
		plot.getRenderer().setSeriesPaint(0, Color.blue);
		return chart;
	}


	public void drawRegressionLine() {
		// Get the parameters 'a' and 'b' for an equation y = a + b * x,
		// fitted to the inputData using ordinary least squares regression.
		// a - regressionParameters[0], b - regressionParameters[1]
		double regressionParameters[] = Regression.getOLSRegression(inputData,
				0); // sf
 
		// Prepare a line function using the found parameters
		LineFunction2D linefunction2d = new LineFunction2D(
				regressionParameters[0], regressionParameters[1]);
 
		// Creates a dataset by taking sample values from the line function
		XYDataset dataset = DatasetUtilities.sampleFunction2D(linefunction2d,
				0D, 100, 300, "Fitted Regression Line");
 
		// Draw the line dataset
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setDataset(1, dataset);
		XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(
				true, false);
		xylineandshaperenderer.setSeriesPaint(0, Color.BLACK);
		xyplot.setRenderer(1, xylineandshaperenderer);
	}
}
