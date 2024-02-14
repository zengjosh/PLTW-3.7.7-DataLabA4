import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.knowm.xchart.style.Styler;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import core.data.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    DataSource ds = DataSource.connect("games.csv").load();

    ArrayList<String> ratingWS = ds.fetchStringList("white_rating");
    ArrayList<String> ratingBS = ds.fetchStringList("black_rating");
    ArrayList<String> turnsS = ds.fetchStringList("turns");
    ArrayList<Integer> ratingI = new ArrayList<Integer>();
    ArrayList<Integer> turnsI = new ArrayList<Integer>();

    for (int i = 0; i < turnsS.size(); i++) {
      turnsI.add(Integer.parseInt(turnsS.get(i)));
    }
    for (int i = 0; i < ratingWS.size(); i++) {
      //average of black and white rating
      ratingI.add((Integer.parseInt(ratingWS.get(i)) + Integer.parseInt(ratingBS.get(i)))/2);
    }

    XYChart chart = new XYChartBuilder()
      .width(1000)  // Increased width
      .height(600)  
      .title("Average Rating vs Number of Turns")
      .xAxisTitle("Average Rating")
      .yAxisTitle("Number of Turns")
      .build();

    // Customize Chart
    chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
    chart.getStyler().setChartTitleVisible(true);
    chart.getStyler().setMarkerSize(5);  // Smaller marker size
    
    chart.getStyler().setPlotMargin(0);
    chart.getStyler().setPlotContentSize(.95);

    // Add series
    XYSeries series = chart.addSeries("Chess Games", ratingI, turnsI);
    series.setMarker(SeriesMarkers.CIRCLE);
    series.setMarkerColor(java.awt.Color.BLUE);

    // Adjust the transparency
    series.setMarkerColor(new java.awt.Color(0, 0, 255, 100));  // Semi-transparent blue

    // Show the chart
    new SwingWrapper(chart).displayChart();

    SimpleRegression regression = new SimpleRegression();

    // Assuming ratingI and turnsI are populated and have the same size
    for (int i = 0; i < ratingI.size(); i++) {
        regression.addData(ratingI.get(i), turnsI.get(i));
    }

    // Output the regression results
    System.out.println("Intercept: " + regression.getIntercept());
    System.out.println("Slope: " + regression.getSlope());

    // Retrieve the intercept (a) and slope (b) of the regression line
    double intercept = regression.getIntercept();
    double slope = regression.getSlope();

    // Output the regression equation
    System.out.println("The best fit line is: y = " + intercept + " + " + slope + "x");

    // If you also want to provide the R-squared value to indicate the fit quality:
    System.out.println("R-squared: " + regression.getRSquare());

  }
}
