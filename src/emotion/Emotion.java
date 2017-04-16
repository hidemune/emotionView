/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emotion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.Document;

/**
 *
 * @author user
 */
public class Emotion {
    public static ArrayList<EmotionAnchor> anchor = new ArrayList<>();
    public static ArrayList<Keyword> keys = new ArrayList<>();
    static emotionJFrame frm;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        anchor.add(new EmotionAnchor("無関心",0d,0d,0d));
        anchor.add(new EmotionAnchor("愛",1d,0d,0d));
        anchor.add(new EmotionAnchor("楽観",1d,1d,0d));
        anchor.add(new EmotionAnchor("喜び",1d,1d,1d));
        anchor.add(new EmotionAnchor("関心",1d,1d,-1d));
        anchor.add(new EmotionAnchor("服従",1d,-1d,0d));
        anchor.add(new EmotionAnchor("信頼",1d,-1d,1d));
        anchor.add(new EmotionAnchor("恐怖",1d,-1d,-1d));
        anchor.add(new EmotionAnchor("自責の念",-1d,0d,0d));
        anchor.add(new EmotionAnchor("軽蔑",-1d,1d,0d));
        anchor.add(new EmotionAnchor("憎しみ",-1d,1d,1d));
        anchor.add(new EmotionAnchor("怒り",-1d,1d,-1d));
        anchor.add(new EmotionAnchor("失望",-1d,-1d,0d));
        anchor.add(new EmotionAnchor("悲しみ",-1d,-1d,1d));
        anchor.add(new EmotionAnchor("驚き",-1d,-1d,-1d));
        anchor.add(new EmotionAnchor("攻撃的",0d,1d,0d));
        anchor.add(new EmotionAnchor("畏れ",0d,-1d,0d));
        
        frm = new emotionJFrame();
        frm.setVisible(true);
        
        //key 全件メモリにセット
        
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
                    key.z = Double.valueOf(arr[2]);
                    key.y = Double.valueOf(arr[3]);
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
        /*
        //Postgres
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //-----------------
            // 接続
            //-----------------
            /*
            sudo su postgres
            psql
            postgres=# \password
            Enter new password: 
            Enter it again: 
create table emotionwords (
  keyword    varchar     primary key,
  x          float8,
  y          float8,
  z          float8,
  bunbo      float8
);
            
            
            postgres=# \q
            // * /
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", // "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
                    "postgres", // ログインロール
                    "xxxxxxxx"); // パスワード
            statement = connection.createStatement();

            //-----------------
            // SQLの発行
            //-----------------
            //ユーザー情報のテーブル
            resultSet = statement.executeQuery("SELECT * FROM emotionwords");

            //-----------------
            // 値の取得
            //-----------------
            // フィールド一覧を取得
            List<String> fields = new ArrayList<String>();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                fields.add(rsmd.getColumnName(i));
            }

            //結果の出力
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;

                //System.out.println("---------------------------------------------------");
                //System.out.println("--- Rows:" + rowCount);
                //System.out.println("---------------------------------------------------");

                //値は、「resultSet.getString(<フィールド名>)」で取得する。
                //for (String field : fields) {
                //    System.out.println(field + ":" + resultSet.getString(field));
                //}
                Keyword key = new Keyword();
                key.word = resultSet.getString("keyword");
                key.x = resultSet.getDouble("x");
                key.y = resultSet.getDouble("y");
                key.z = resultSet.getDouble("z");
                key.bunbo = resultSet.getDouble("bunbo");
                //System.out.println(key.word + ","+ key.x + ","+ key.y + ","+ key.z );
                keys.add(key);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            //接続を切断する
            if (resultSet != null) {
                try{
                    resultSet.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (statement != null) {
                try{
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
            if (connection != null) {
                try{
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } 
        */
        
        frm.setOk();
    }
    public static void setEmotion(String anchorP, emotionJFrame frm) {
        EmotionAnchor ancNow = null;
        for (int i = 0; i < anchor.size(); i++) {
            if (anchor.get(i).word.equals(anchorP)) {
                ancNow = anchor.get(i);
            }
        }
        try {
            //mecab
            //BufferedWriter bw = null;
            FileWriter fw = null;
            fw = new FileWriter("emotion_wk.txt");
            //bw = new BufferedWriter(fw);
            Document doc = frm.getHtml();
            HteWriter hte = new HteWriter(fw, doc, 0, 999999999);
            hte.write();
            fw.close();
            
            ProcessBuilder pb = new ProcessBuilder("mecab", "emotion_wk.txt");
            Process process = pb.start();
            InputStream is = process.getInputStream();	//標準出力
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                for (;;) {
                    String line2 = br.readLine();
                    if (line2 == null) break;
                    line2 = line2.replaceAll("<.+?>", "");
                    String arr[] = line2.split("[\t,]");
                    String keyword = arr[0];
                    boolean flgOk = true;
                    if (arr.length <= 1) {
                        flgOk = false;
                    }
                    if (flgOk && (arr[2].equals("数"))) {
                        flgOk = false;
                    }
                    if (flgOk) {
                        if (!("*".equals(arr[7]))) {
                            keyword = arr[7];
                        }
                        //System.out.println(keyword);
                        int i = 0;
                        boolean flg = false;
                        for (i = 0;i < keys.size(); i++) {
                            if (((Keyword)keys.get(i)).word.equals(keyword)) {
                                flg = true;
                                break;
                            }
                        }
                        if (!flg) {
                            Keyword wk = new Keyword();
                            wk.word = keyword;
                            wk.bunbo = 1d;
                            wk.x = ancNow.x;
                            wk.y = ancNow.y;
                            wk.z = ancNow.z;
                            keys.add(wk);
                            //System.out.println("add:" + keyword);
                        } else {
                            double X = keys.get(i).x;
                            double Y = keys.get(i).y;
                            double Z = keys.get(i).z;
                            
                            double bunbo = ((Keyword)keys.get(i)).bunbo;
                            //内分点を探す
                            X = (X * bunbo + ancNow.x)/(bunbo + 1d);
                            Y = (Y * bunbo + ancNow.y)/(bunbo + 1d);
                            Z = (Z * bunbo + ancNow.z)/(bunbo + 1d);
                            bunbo = bunbo + 1d;
                            ((Keyword)keys.get(i)).bunbo = bunbo;
                            ((Keyword)keys.get(i)).x = X;
                            ((Keyword)keys.get(i)).y = Y;
                            ((Keyword)keys.get(i)).z = Z;
                            //System.out.println(keys.get(i).word + ","+ X + ","+ Y + ","+ Z );
                            //System.out.println(keys.get(i).word + ","+ keys.get(i).x + ","+ keys.get(i).y + ","+ keys.get(i).z );
                        }
                    } else {
                        //EOS
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            } finally {
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void saveEmotion(boolean exit) throws IOException {
        frm.setExit();
        
        System.out.println("Size(" + keys.size() + ")");
        
        //tab CSV Out
        System.out.println("CSV Write");
        try{
            File file = new File("emotion.csv");
            FileWriter filewriter = new FileWriter(file);
            String str ;
            for (int i = 0; i < keys.size(); i++) {
                str = keys.get(i).word + "\t" + keys.get(i).x + "\t" + keys.get(i).z + "\t" + keys.get(i).y + "\t" + keys.get(i).bunbo + "\t\n" ;
                filewriter.write(str);
            }
            filewriter.close();
        }catch(Exception e){
          System.out.println(e);
        }
        if (!exit) {
            frm.setOk();
            return;
        }
        /*
        //Postgres
        System.out.println("Truncate & Insert");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //-----------------
            // 接続
            //-----------------
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", // "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
                    "postgres", // ログインロール
                    "xxxxxxxx"); // パスワード
            statement = connection.createStatement();

            //-----------------
            // SQLの発行
            //-----------------
            //ユーザー情報のテーブル
            try {
                PreparedStatement ps = connection.prepareStatement("truncate table emotionwords");
                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //-----------------
            // 値の取得
            //-----------------
            for (int i = 0; i < keys.size(); i++) {
                try {
                    PreparedStatement ps = connection.prepareStatement("Insert into emotionwords VALUES(? ,?,?,?, ?)");
                    //System.out.println(keys.get(i).word + ","+ keys.get(i).x + ","+ keys.get(i).y + ","+ keys.get(i).z );
                    ps.setString(1, keys.get(i).word);
                    ps.setDouble(2, keys.get(i).x);
                    ps.setDouble(3, keys.get(i).y);
                    ps.setDouble(4, keys.get(i).z);
                    ps.setDouble(5, keys.get(i).bunbo);
                    ps.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            //接続を切断する
            if (resultSet != null) {
                try{
                    resultSet.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (statement != null) {
                try{
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
            if (connection != null) {
                try{
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } 
        //End
        System.out.println("Insert End...");
        // */
        System.out.println("exit");
        Runtime.getRuntime().exit(0);
    }
}

class EmotionAnchor {
    public String word;
    public double x;
    public double y;
    public double z;
    EmotionAnchor(String Word,double X,double Z,double Y) {
        word = Word;
        x = X;
        y = Y;
        z = Z;
    }
}
class Keyword {
    public String word;
    public double bunbo;
    public double x;
    public double y;
    public double z;
}

