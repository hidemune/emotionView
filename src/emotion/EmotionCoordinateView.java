package emotion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static emotion.Emotion.anchor;
import static emotion.Emotion.keys;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

import java.util.Random;
import org.jzy3d.analysis.AWTAbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartFactory;
import org.jzy3d.chart.factories.AWTPainterFactory;
import org.jzy3d.chart.factories.IChartFactory;
import org.jzy3d.chart.factories.IPainterFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

public class EmotionCoordinateView extends AWTAbstractAnalysis {
  public static void main(String[] args) throws Exception {
    AnalysisLauncher.open(new EmotionCoordinateView());
  }

@Override
  public void init() {

        float x;
        float y;
        float z;
        float a;

        Coord3d[] points = new Coord3d[keys.size()];
        Color[] colors = new Color[keys.size()];
        
        
        
        Random r = new Random();
        r.setSeed(0);

        for (int i = 0; i < keys.size(); i++) {
            x = Float.valueOf("" + keys.get(i).x);
            y = Float.valueOf("" + keys.get(i).y);
            z = Float.valueOf("" + keys.get(i).z);
            points[i] = new Coord3d(x, y, z);
            a = 0.25f;
            colors[i] = new Color(x, y, z, a);
        }
        Scatter scatter = new Scatter(points, colors);

        Quality q = Quality.Advanced();
        // q.setPreserveViewportSize(true);

        GLCapabilities c = new GLCapabilities(GLProfile.get(GLProfile.GL2));
        IPainterFactory p = new AWTPainterFactory(c);
        IChartFactory f = new AWTChartFactory(p);

        chart = f.newChart(q);
        chart.getScene().add(scatter);
  }

}
