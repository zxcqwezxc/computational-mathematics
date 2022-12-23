
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class SecondTask {

    static ArrayList<Double> A = new ArrayList<Double>();
    static ArrayList<Double> B = new ArrayList<Double>();

    public static void main(String[] args) {

        LinkedList<Func> func = new LinkedList<>();
        func.add(x -> Math.pow(x, 3) - 6.5 * Math.pow(x, 2) + 11 * x - 4);
        func.add(x -> 3 * Math.cos(Math.PI * x / 8));
        func.add(x -> Math.exp(-x / 4) * Math.sin(x / 3));
        func.add(x -> 8 * x * Math.exp(-Math.pow(x, 2) / 12));


        /*List<List<PointO>> segment = new ArrayList<>();
        segment.add(Arrays.asList(
                new PointO(2, 4),
                new PointO(0.5, 3),
                new PointO(4, 10),
                new PointO(0, 12)
        ));*/


        A.add(2.0);
        A.add(0.5);
        A.add(4.0);
        A.add(0.0);
        B.add(4.0);
        B.add(3.0);
        B.add(10.0);
        B.add(12.0);


        List<PointO> task;
        int count = 1;
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < func.size(); i++) {
                List<PointO> points = generatePoints(4, func, count);
                double x, lagrange, newton, functionY;
                System.out.println("Уравнение"+ count +"\nВведите значение x в пределах (" + A.get(count - 1) + ";" + B.get(count - 1) + ")");
                x = scanner.nextDouble();

                lagrange = FIrstTask.lagrangePolynome(points, points.size(), x);
                newton = FIrstTask.newtonPolynome(points, x, points.size());
                functionY = func.get(count - 1).calculate(x);

                System.out.println("Значение полинома Лагранжа: " + lagrange);
                System.out.println("Значение полинома Ньютона: " + newton);
                System.out.println("Значение функции: " + functionY + "\n");


                XYSeries lagrangePolynomeSeries = new XYSeries("lagrangePolynome");
                XYSeries newtonPolynomeSeries = new XYSeries("newtonPolynome");
                XYSeries functionSeries = new XYSeries("function");

                int div = func.size();

                for (int j = 0; j < div; j++) {
                    task = generatePoints(func.size(), func, count);


                    for (double xo = task.get(0).getX(); xo < task.get(task.size() - 1).getX(); xo += 0.1) {
                        lagrangePolynomeSeries.add(xo, FIrstTask.lagrangePolynome(task, task.size(), xo));
                        newtonPolynomeSeries.add(xo, FIrstTask.newtonPolynome(task, xo, task.size()));
                        functionSeries.add(xo, func.get(count - 1).calculate(xo));
                    }


                }
                showGraphic(lagrangePolynomeSeries, "полином лагранжа, №" + count);
                showGraphic(newtonPolynomeSeries, "полином ньютона, №" + count);
                showGraphic(functionSeries, "график функции, №" + count);

            count++;
        }
    }


    private static List<PointO> generatePoints(int countOfPoints, LinkedList<Func> func, int count) {
        List<PointO> points = new ArrayList<>();
        double step = Math.abs(B.get(count - 1) - A.get(count - 1)) / countOfPoints;
        double currentX = A.get(count - 1);
        for (int i = 0; i < countOfPoints; i++) {
            points.add(new PointO(currentX, func.get(i).calculate(currentX)));
            currentX += step;
        }
        return points;
    }

    public static void showGraphic(XYSeries series, String title) {
        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart(title, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("MinimalStaticChart");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(400, 300);
        frame.show();
    }

}

interface Func {
    double calculate(double x);
}




