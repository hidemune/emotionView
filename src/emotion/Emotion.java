/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emotion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    public static Double xxx, yyy, zzz;
    public static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        anchor.add(new EmotionAnchor("", "無関心", 0d, 0d, 0d));
        anchor.add(new EmotionAnchor("love", "愛", 1d, 0d, 0d));
        anchor.add(new EmotionAnchor("optimism", "楽観", 1d, 1d, 0d));
        anchor.add(new EmotionAnchor("joy", "喜び", 1d, 1d, 1d));
        anchor.add(new EmotionAnchor("anticipation", "関心", 1d, 1d, -1d));
        anchor.add(new EmotionAnchor("submission", "服従", 1d, -1d, 0d));
        anchor.add(new EmotionAnchor("trust", "信頼", 1d, -1d, 1d));
        anchor.add(new EmotionAnchor("fear", "恐怖", 1d, -1d, -1d));
        anchor.add(new EmotionAnchor("remorse", "自責の念", -1d, 0d, 0d));
        anchor.add(new EmotionAnchor("contempt", "軽蔑", -1d, 1d, 0d));
        anchor.add(new EmotionAnchor("disgust", "憎しみ", -1d, 1d, 1d));
        anchor.add(new EmotionAnchor("anger", "怒り", -1d, 1d, -1d));
        anchor.add(new EmotionAnchor("disapproval", "失望", -1d, -1d, 0d));
        anchor.add(new EmotionAnchor("sadness", "悲しみ", -1d, -1d, 1d));
        anchor.add(new EmotionAnchor("surprise", "驚き", -1d, -1d, -1d));
        anchor.add(new EmotionAnchor("aggressiveness", "攻撃的", 0d, 1d, 0d));
        anchor.add(new EmotionAnchor("awe", "畏れ", 0d, -1d, 0d));
        xxx = 0d;
        yyy = 0d;
        zzz = 0d;
        frm = new emotionJFrame();
        frm.setVisible(true);

        //key 全件メモリにセット
        try {
            File file = new File("emotion.csv");
            FileReader filereader = new FileReader(file);
            BufferedReader br = new BufferedReader(filereader);
            String str = br.readLine();
            while (str != null) {
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
        } catch (Exception e) {
            System.out.println(e);
            //Anchor そのものをセット
            for (int i = 0; i < anchor.size(); i++) {
                Keyword key = new Keyword();
                key.word = anchor.get(i).word;
                key.x = anchor.get(i).x;
                key.z = anchor.get(i).z;
                key.y = anchor.get(i).y;
                key.bunbo = 1;
                keys.add(key);
            }
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
    public static void bunseki() {
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
            //タグの無効化
            FileReader fr = new FileReader("emotion_wk.txt");
            BufferedReader br2 = new BufferedReader(fr);
            StringBuilder sb2 = new StringBuilder();
            for (;;) {
                String line3 = br2.readLine();
                if (line3 == null) {
                    break;
                }
                line3 = line3.replaceAll("<.+?>", "").trim();
                sb2.append(line3);
                sb2.append("\n");
            }
            br2.close();
            fr.close();
            FileWriter fw2 = new FileWriter("emotion_wk.txt");
            BufferedWriter bw = new BufferedWriter(fw2);
            bw.write(sb2.toString());
            bw.flush();
            bw.close();
            fw2.close();
            
            ProcessBuilder pb = new ProcessBuilder("mecab", "emotion_wk.txt");
            Process process = pb.start();
            InputStream is = process.getInputStream();	//標準出力
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            ArrayList<Keyword> bkup;
            ArrayList<Keyword> bunseki = new ArrayList<>();
            try {
                boolean flgCm = false;
                for (;;) {
                    String line2 = br.readLine();
                    if (line2 == null) {
                        break;
                    }
                    line2 = line2.replaceAll("<.+?>", "");
                    if (line2.indexOf("<!--") >= 0) {
                        flgCm = true;
                    }
                    if (line2.indexOf("-->") >= 0) {
                        flgCm = false;
                        line2 = line2.replaceAll("^.+?-->", "");
                    }
                    if (!flgCm) {
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
                            for (i = 0; i < keys.size(); i++) {
                                if (((Keyword) keys.get(i)).word.equals(keyword)) {
                                    flg = true;
                                    break;
                                }
                            }
                            if (!flg) {
                                Keyword wk = new Keyword();
                                wk.word = keyword;
                                wk.bunbo = 1d;
                                wk.x = 0;
                                wk.y = 0;
                                wk.z = 0;
                                bunseki.add(wk);
                                //System.out.println("add:" + keyword);
                            } else {
                                bunseki.add(keys.get(i));
                            }
                        } else {
                            //EOS
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                br.close();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(sb2.toString());
            sb.append("<br>");
            for (int j = 0; j < bunseki.size(); j++) {
                sb.append(bunseki.get(j).word);
                sb.append("\t");
                sb.append(String.format("%.3f",  bunseki.get(j).x));
                sb.append("\t");
                sb.append(String.format("%.3f",  bunseki.get(j).z));
                sb.append("\t");
                sb.append(String.format("%.3f",  bunseki.get(j).y));
                sb.append("<br>");
            }
            frm.setText(sb.toString());
            bkup = keys;
            keys = bunseki;
            int maxCnt = keys.size();
            
            for (int i = 0; i < maxCnt-1; i++){
                Keyword sta = new Keyword();
                Keyword end = new Keyword();
                sta = keys.get(i);
                end = keys.get(i+1);
                //sta-end Line
                for (int j = 0; j < 20; j++){
                    //内分点
                    Keyword pt = new Keyword();
                    pt.x = (sta.x * (double)j + end.x * (double)(20-j)) / 20d;
                    pt.y = (sta.y * (double)j + end.y * (double)(20-j)) / 20d;
                    pt.z = (sta.z * (double)j + end.z * (double)(20-j)) / 20d;
                    keys.add(pt);
                }
            }
            for (int i = 0; i < anchor.size(); i++) {
                Keyword key = new Keyword();
                key.word = anchor.get(i).word;
                key.x = anchor.get(i).x;
                key.z = anchor.get(i).z;
                key.y = anchor.get(i).y;
                key.bunbo = 1;
                keys.add(key);
            }
            LogScatterView lv = new LogScatterView();
            lv.init();
            try {
                Emotion.saveEmotion(false);
                LogScatterView.main(null);
            }catch  (Exception e) {
                e.printStackTrace();
            }
            keys = bkup;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void bengakuniisosimu(String fname) {
        long total = 0;
        long putCnt = 0;
        try {
            File file = new File("../nihongoDic.txt");
            BufferedReader brW = new BufferedReader(new FileReader(file));

            String str = brW.readLine();
            while (str != null) {
                //mecab 分解
                try {
                    //mecab
                    //BufferedWriter bw = null;
                    FileWriter fw = null;
                    fw = new FileWriter("emotion_wk.txt");
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(str);
                    bw.close();
                    fw.close();

                    ProcessBuilder pb = new ProcessBuilder("mecab", "emotion_wk.txt");
                    Process process = pb.start();
                    InputStream is = process.getInputStream();	//標準出力
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    
                    Keyword bkKey = keys.get(0); //無関心
                    try {
                        boolean flgCm = false;
                        for (;;) {
                            String line2 = br.readLine();
                            if (line2 == null) {
                                break;
                            }
                            System.out.println(line2);
                            line2 = line2.replaceAll("<.+?>", "");
                            if (line2.indexOf("<!--") >= 0) {
                                flgCm = true;
                            }
                            if (line2.indexOf("-->") >= 0) {
                                flgCm = false;
                                line2 = line2.replaceAll("^.+?-->", "");
                            }
                            if (!flgCm) {
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
                                    //emotion Search
                                    double bunbo = 0;
                                    for (int j = 0; j < keys.size(); j++) {
                                        if (keyword.equals(keys.get(j).word)) {
                                            Keyword wkKey = keys.get(j);
                                            //distance
                                            //double distbk = Math.pow(bkKey.x, 2) + Math.pow(bkKey.y, 2) + Math.pow(bkKey.z, 2) ;
                                            //double distwk = Math.pow(wkKey.x, 2) + Math.pow(wkKey.y, 2) + Math.pow(wkKey.z, 2) ;
                                            //if (distwk > distbk) {
                                            //    bkKey = wkKey;
                                            //}
                                            
                                            bkKey.x = (bkKey.x * bunbo + wkKey.x) / (bunbo + 1);
                                            bkKey.y = (bkKey.y * bunbo + wkKey.y) / (bunbo + 1);
                                            bkKey.z = (bkKey.z * bunbo + wkKey.z) / (bunbo + 1);
                                            bunbo ++;
                                        }
                                    }
                                    total ++;
                                    double distbk = Math.pow(bkKey.x, 2) + Math.pow(bkKey.y, 2) + Math.pow(bkKey.z, 2) ;
                                    if (distbk <= 1d) {
                                        //無関心は記憶できない
                                        System.out.println("lost");
                                    }else{
                                        //印象登録
                                        putCnt++;
                                        System.out.println("put");
                                        setFuzzyEmotion(bkKey, line2);
                                    }
                                    
                                } else {
                                    //EOS
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        br.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //固定のアンカーを探す
                str = brW.readLine();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.err.println("Total:" + total);
        System.err.println("putCnt:" + putCnt);
        
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
                boolean flgCm = false;
                for (;;) {
                    String line2 = br.readLine();
                    if (line2 == null) {
                        break;
                    }
                    line2 = line2.replaceAll("<.+?>", "");
                    if (line2.indexOf("<!--") >= 0) {
                        flgCm = true;
                    }
                    if (line2.indexOf("-->") >= 0) {
                        flgCm = false;
                        line2 = line2.replaceAll("^.+?-->", "");
                    }
                    if (!flgCm) {
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
                            for (i = 0; i < keys.size(); i++) {
                                if (((Keyword) keys.get(i)).word.equals(keyword)) {
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

                                double bunbo = ((Keyword) keys.get(i)).bunbo;
                                //内分点を探す
                                X = (X * bunbo + ancNow.x) / (bunbo + 1d);
                                Y = (Y * bunbo + ancNow.y) / (bunbo + 1d);
                                Z = (Z * bunbo + ancNow.z) / (bunbo + 1d);
                                bunbo = bunbo + 1d;
                                ((Keyword) keys.get(i)).bunbo = bunbo;
                                ((Keyword) keys.get(i)).x = X;
                                ((Keyword) keys.get(i)).y = Y;
                                ((Keyword) keys.get(i)).z = Z;
                                //System.out.println(keys.get(i).word + ","+ X + ","+ Y + ","+ Z );
                                //System.out.println(keys.get(i).word + ","+ keys.get(i).x + ","+ keys.get(i).y + ","+ keys.get(i).z );
                            }
                        } else {
                            //EOS
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFuzzyEmotion(Keyword ancNow, String line) {
        try {
            //mecab
            //BufferedWriter bw = null;
            FileWriter fw = null;
            fw = new FileWriter("emotion_wk.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line);
            bw.close();
            fw.close();

            ProcessBuilder pb = new ProcessBuilder("mecab", "emotion_wk.txt");
            Process process = pb.start();
            InputStream is = process.getInputStream();	//標準出力
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                boolean flgCm = false;
                for (;;) {
                    String line2 = br.readLine();
                    if (line2 == null) {
                        break;
                    }
                    line2 = line2.replaceAll("<.+?>", "");
                    if (line2.indexOf("<!--") >= 0) {
                        flgCm = true;
                    }
                    if (line2.indexOf("-->") >= 0) {
                        flgCm = false;
                        line2 = line2.replaceAll("^.+?-->", "");
                    }
                    if (!flgCm) {
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
                            for (i = 0; i < keys.size(); i++) {
                                if (((Keyword) keys.get(i)).word.equals(keyword)) {
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

                                double bunbo = ((Keyword) keys.get(i)).bunbo;
                                //内分点を探す
                                X = (X * bunbo + ancNow.x) / (bunbo + 1d);
                                Y = (Y * bunbo + ancNow.y) / (bunbo + 1d);
                                Z = (Z * bunbo + ancNow.z) / (bunbo + 1d);
                                bunbo = bunbo + 1d;
                                ((Keyword) keys.get(i)).bunbo = bunbo;
                                ((Keyword) keys.get(i)).x = X;
                                ((Keyword) keys.get(i)).y = Y;
                                ((Keyword) keys.get(i)).z = Z;
                                //System.out.println(keys.get(i).word + ","+ X + ","+ Y + ","+ Z );
                                //System.out.println(keys.get(i).word + ","+ keys.get(i).x + ","+ keys.get(i).y + ","+ keys.get(i).z );
                            }
                        } else {
                            //EOS
                        }
                    }
                }
            } catch (Exception e) {
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
        try {
            File file = new File("emotion.csv");
            FileWriter filewriter = new FileWriter(file);
            String str;
            for (int i = 0; i < keys.size(); i++) {
                str = keys.get(i).word + "\t" + keys.get(i).x + "\t" + keys.get(i).z + "\t" + keys.get(i).y + "\t" + keys.get(i).bunbo + "\t\n";
                filewriter.write(str);
            }
            filewriter.close();
        } catch (Exception e) {
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

    public String wordEng;
    public String word;
    public double x;
    public double y;
    public double z;

    EmotionAnchor(String WordEng, String Word, double X, double Z, double Y) {
        wordEng = WordEng;
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
