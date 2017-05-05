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

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
//import org.jzy3d.chart.factories.AxeTransformableAWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord2d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Rectangle;
import org.jzy3d.plot2d.rendering.JavaGraphics;
import org.jzy3d.plot3d.primitives.ConcurrentScatterMultiColorList;
import org.jzy3d.plot3d.primitives.Scatter;
//import org.jzy3d.plot3d.primitives.axeTransformablePrimitive.AxeTransformableConcurrentScatterMultiColor;
//import org.jzy3d.plot3d.primitives.axeTransformablePrimitive.AxeTransformableConcurrentScatterMultiColorList;
//import org.jzy3d.plot3d.primitives.axeTransformablePrimitive.AxeTransformableScatter;
//import org.jzy3d.plot3d.primitives.axeTransformablePrimitive.axeTransformers.AxeTransformerSet;
//import org.jzy3d.plot3d.primitives.axeTransformablePrimitive.axeTransformers.LogAxeTransformer;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import static org.jzy3d.plot3d.rendering.view.OverlayUtils.drawText;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;
import org.jzy3d.plot3d.text.DrawableTextWrapper;
import org.jzy3d.plot3d.text.ITextRenderer;
import org.jzy3d.plot3d.text.align.Halign;
import org.jzy3d.plot3d.text.align.Valign;
import org.jzy3d.plot3d.text.drawable.DrawableTextBitmap;
import org.jzy3d.plot3d.text.drawable.DrawableTextTexture;
import org.jzy3d.plot3d.text.drawable.cells.DrawableTextCell;
import org.jzy3d.plot3d.text.drawable.cells.TextCellRenderer;
import org.jzy3d.plot3d.text.overlay.SwingTextOverlay;
import org.jzy3d.plot3d.text.overlay.TextOverlay;
import org.jzy3d.plot3d.transform.space.SpaceTransformer;

public class LogScatterView extends AbstractAnalysis {
    public static Coord3d lgh;
    static JFrame mainFrm;
    public LogScatterView(JFrame mainFrm) {
        this.mainFrm = mainFrm;
    }
    public static void main(String[] args) throws Exception {
        AnalysisLauncher.open(new LogScatterView(mainFrm),
                new Rectangle(mainFrm.getX(), mainFrm.getY(),
                        mainFrm.getWidth(), mainFrm.getHeight()));
    }

    ArrayList<SpaceTransformer> transformers = new ArrayList();

    public void init() {
        if (chart != null) {
            chart.dispose();
        }
        lgh = new Coord3d(Emotion.xxx,Emotion.yyy,Emotion.zzz);
        
        //key 全件メモリにセット
        /*
                try{
                    File file = new File("emotion.csv");
                    FileReader filereader = new FileReader(file);
                    BufferedReader br = new BufferedReader(filereader);
                    String str = br.readLine();
                    while(str != null){
                        //System.out.println(str);
                        String arr[] = str.split("\t");
                        if (arr.length >= 5) {
                            Keyword key = new Keyword();
                            key.word = arr[0];
                            key.x = Double.valueOf(arr[1]);
                            key.y = Double.valueOf(arr[2]);
                            key.z = Double.valueOf(arr[3]);
                            key.bunbo = Double.valueOf(arr[4]);
                            //System.out.println(key.word + ","+ key.x + ","+ key.y + ","+ key.z );
                            keys.add(key);
                        }
                        str = br.readLine();
                    }
                }catch(Exception e){
                  System.out.println(e);
                }
                System.out.println("CSV Read OK " + keys.size());
                // */
        float x;
        float y;
        float z;
        float a;

        ArrayList<Coord3d> points = new ArrayList<Coord3d>();
        Color[] colors = new Color[keys.size()];

        Random r = new Random();
        r.setSeed(0);

        for (int i = 0; i < keys.size(); i++) {
            x = Float.valueOf("" + keys.get(i).x);
            y = Float.valueOf("" + keys.get(i).y);
            z = Float.valueOf("" + keys.get(i).z);
            points.add(new Coord3d(x, y, z));
            a = 0.25f;
            colors[i] = new Color(x, y, z, a);
        }
        /*
                http://doc.jzy3d.org/javadoc/1.0.0/jzy3d-api/index.html
         */

        ConcurrentScatterMultiColorList scatter = new ConcurrentScatterMultiColorList(points, new ColorMapper(new ColorMapRainbow(), 0.1, 1.1, new Color(1, 1, 1, .5f)));
        chart = AWTChartComponentFactory.chart(Quality.Nicest, "awt");
        chart.addLight(lgh, Color.BLACK, Color.BLUE, Color.YELLOW, 0.1f);
        chart.getView();
        chart.getScene().add(scatter);

        chart.getView().setViewPoint(new Coord3d(-0.2f, -0.5f, 0.1f), true);
        //chart.addLight(new Coord3d(1,1,1));

        for (int i = 0; i < anchor.size(); i++) {

            DrawableTextBitmap bit = new DrawableTextBitmap(anchor.get(i).wordEng, new Coord3d(anchor.get(i).x, anchor.get(i).y, anchor.get(i).z), Color.BLUE);
            chart.addDrawable(bit);

            //to.paint(setFont(new Font("VL Gothic",0,16)));
            //TextCellRenderer cellRenderer =new TextCellRenderer(4, anchor.get(i).word, new Font("VL Gothic", Font.PLAIN, 16));
            //cellRenderer.setHorizontalAlignement(Halign.LEFT);
            //cellRenderer.setBorderDisplayed(true);
            //cellRenderer.setBorderColor(java.awt.Color.red);		
            //final DrawableTextCell t8 = new DrawableTextCell(cellRenderer, new Coord2d(anchor.get(i).x,anchor.get(i).y), new Coord2d(anchor.get(i).y,anchor.get(i).z));
            //chart.getScene().getGraph().add( t8 );
            //ITextRenderer.
            //drawText(anchor.get(i).word, new Coord3d(anchor.get(i).x,anchor.get(i).y,anchor.get(i).z), Color.BLUE, cellRenderer);
        }
        
    }

}
