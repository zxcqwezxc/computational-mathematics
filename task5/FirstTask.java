
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FIrstTask {

    public static void main(String[] args) {
        List<List<PointO>> tasks = preparePoints();
        double count = 1;
        JFrame currentFrame;
        for (List<PointO> task :
                tasks) {
            XYSeries lagrangePolynomeSeries = new XYSeries("lagrangePolynome");
            XYSeries newtonPolynomeSeries = new XYSeries("newtonPolynome");
            for (double x = task.get(0).getX(); x < task.get(task.size() - 1).getX(); x += 0.1) {
                lagrangePolynomeSeries.add(x, lagrangePolynome(task, task.size(), x));
                newtonPolynomeSeries.add(x, newtonPolynome(task, x, task.size()));
            }
             showGraphic(lagrangePolynomeSeries, "полином лагранжа, задание " + count);

                    showGraphic(newtonPolynomeSeries, "полином ньютона, задание " + count);


            count++;
        }
    }

    public static JFrame showGraphic(XYSeries series, String title) {
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
        return frame;
    }

    public static double lagrangePolynome(List<PointO> pointOS, int n, double x) {
        double result = 0;
        for (int i = 0; i < n; i++) {
            result += pointOS.get(i).getY() * lagrangeMultiplier(pointOS, i, x);
        }
        return result;
    }

    public static double newtonPolynome(List<PointO> pointOS, double x, int n) {
        double result = 0, buffer;
        result += pointOS.get(0).getY();
        for (int i = 0; i < n; i++) {
            buffer = 1;
            for (int j = 0; j < i + 1; j++) {
                buffer *= (x - pointOS.get(j).getX());
            }
            if (buffer != 0) {
                buffer *= newtonFunction(pointOS.subList(0, i == n - 1 ? n : i + 2));
            }
            result += buffer;
        }
        return result;
    }

    public static double lagrangeMultiplier(List<PointO> pointOS, int i, double x) {
        double result = 1;
        for (int j = 0; j < pointOS.size(); j++) {
            if (j != i) {
                result *= (x - pointOS.get(j).getX()) / (pointOS.get(i).getX() - pointOS.get(j).getX());
            }
        }
        return result;
    }

    public static double newtonFunction(List<PointO> pointOS) {
        double result = 0;
        if (pointOS.size() == 2) {
            result += (pointOS.get(1).getY() - pointOS.get(0).getY()) / (pointOS.get(1).getX() - pointOS.get(0).getX());
        } else {
            double firstF = newtonFunction(new ArrayList<>(pointOS.subList(1, pointOS.size())));
            double secondF = newtonFunction(new ArrayList<>(pointOS.subList(0, pointOS.size() - 1)));
            double deltax = pointOS.get(pointOS.size() - 1).getX() - pointOS.get(0).getX();
            result += (firstF - secondF) / deltax;
        }
        return result;
    }

    private static List<List<PointO>> preparePoints() {
        List<List<PointO>> tasks = new ArrayList<>();
        tasks.add(Arrays.asList(
                new PointO(-1.0, 0.86603),
                new PointO(0.0, 1.0),
                new PointO(1.0, 0.86603),
                new PointO(2.0, 0.50),
                new PointO(3.0, 0.0),
                new PointO(4.0, -0.50)
        ));
        tasks.add(Arrays.asList(
                new PointO(-0.9, -0.36892),
                new PointO(0.0, 0.0),
                new PointO(0.9, 0.36892),
                new PointO(1.8, 0.85408),
                new PointO(2.7, 1.7856),
                new PointO(3.6, 6.3138)
        ));
        tasks.add(Arrays.asList(
                new PointO(1.0, 2.4142),
                new PointO(1.9, 1.0818),
                new PointO(2.8, 0.50953),
                new PointO(3.7, 0.11836),
                new PointO(4.6, -0.24008),
                new PointO(5.5, -0.66818)
        ));
        return tasks;
    }


}


