package main.java.pl.edu.agh.iisg.to.visualizer.demo;

/**
 * Created by Cinek on 2016-11-29.
 */
public class DemoStarter {

    public static void main(String[] args) throws Exception {

        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(PieChartSample.class);
            }
        }.start();

        PieChartSample pieChartSample = PieChartSample.waitPieChartSample();
        pieChartSample.printSomething();
        pieChartSample.setAmountOfTCP(2000);

        //PieChartSample.launch(PieChartSample.class);
        //BarChartSample.launch(BarChartSample.class);
        //LiveLineChart.launch(LiveLineChart.class);

    }
}
