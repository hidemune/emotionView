/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emotion;

import static emotion.Emotion.anchor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import javafx.scene.input.MouseButton;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author user
 */
public class emotionJFrame extends javax.swing.JFrame implements HyperlinkListener {
    private boolean clipmode = false;
    /**
     * Creates new form emotionJFrame
     */
    public emotionJFrame() {
        initComponents();
        url.setText("https://search.yahoo.co.jp/search?ei=UTF-8&p=");
        //url.setText("https://search.yahoo.co.jp/search?ei=SJIS&p=");
        html.addHyperlinkListener(this);
        html.setBackground(Color.white);
        html.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        html.putClientProperty(JEditorPane.W3C_LENGTH_UNITS, Boolean.TRUE);
        setStyle();
        emotion.removeAllItems();
        for (int i = 0; i < Emotion.anchor.size(); i++) {
            emotion.addItem(Emotion.anchor.get(i).word);
        }
        
    }
    public Document getHtml() {
        html.setText(html.getText().replaceAll("<.+?>", ""));
        return html.getDocument();
    }
    void setStyle() {
        //  /*
        //System.out.println(html.getText());
        HTMLDocument doc = (HTMLDocument) html.getDocument();
        StyleSheet ss = doc.getStyleSheet();
        StyleSheet[] sss = ss.getStyleSheets();
        for (int i = sss.length - 1; i >= 0; i--) {
            Style body = sss[i].getStyle("body");	//StyleはAttributeSetの具象クラス
            if (body != null) {
                    StyleConstants.setFontFamily(body, "VL Gothic");
                    StyleConstants.setFontSize(body, 14);
                    StyleConstants.setBackground(body, Color.white);

                    //break;
            }
            Style div = sss[i].getStyle("div");	//StyleはAttributeSetの具象クラス
            if (div != null) {
                    StyleConstants.setFontFamily(div, "VL Gothic");
                    StyleConstants.setFontSize(div, 14);
                    StyleConstants.setBackground(div, Color.white);

                    //break;
            }
            html.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);	//setFont()が有効になる
            html.setFont(new Font("VL Gothic", 0, 14));
            
        }
        // */
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        url = new javax.swing.JTextField();
        emotion = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        html = new javax.swing.JEditorPane();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        jSlider2 = new javax.swing.JSlider();
        jSlider3 = new javax.swing.JSlider();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("感情モデル作成");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        url.setText("jTextField1");
        url.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlActionPerformed(evt);
            }
        });

        emotion.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        emotion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        emotion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emotionActionPerformed(evt);
            }
        });

        jButton1.setText("登録");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        html.setEditable(false);
        html.setContentType("text/html;charset=UTF-8"); // NOI18N
        html.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        html.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        html.setHighlighter(null);
        html.setInheritsPopupMenu(true);
        html.setOpaque(false);
        jScrollPane2.setViewportView(html);

        jButton2.setText("ページ読込");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Save & Exit");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("View");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Help");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("クリップボードから");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton6MouseReleased(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jSlider1.setMaximum(2000);
        jSlider1.setValue(1000);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jSlider2.setMaximum(2000);
        jSlider2.setValue(1000);
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider2StateChanged(evt);
            }
        });

        jSlider3.setMaximum(2000);
        jSlider3.setValue(1000);
        jSlider3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider3StateChanged(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(url)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emotion, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSlider3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emotion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton4)
                            .addComponent(jButton5)
                            .addComponent(jButton6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void urlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlActionPerformed
        jButton2ActionPerformed(null);
    }//GEN-LAST:event_urlActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jButton1.setEnabled(false);
        Emotion.setEmotion(emotion.getSelectedItem().toString(), this);
        jButton1.setEnabled(true);
        clipmode = false;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        html.setText("");
        jButton2.setEnabled(false);
        try {
            html.setPage(url.getText());
            html.setBackground(Color.white);
        }catch (Exception e) {
            e.printStackTrace();
        }
        jButton2.setEnabled(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        try{
            Emotion.saveEmotion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            Emotion.saveEmotion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        LogScatterView view = new LogScatterView();
        view.init();
        try {
            Emotion.saveEmotion(false);
            LogScatterView.main(null);
        }catch  (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Help hlp = new Help();
        hlp.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        html.setText("");
        clipmode = true;
        //クリップボードから貼り付け
        Toolkit kit = Toolkit.getDefaultToolkit();
        Clipboard clip = kit.getSystemClipboard();

        try {
            html.setText((String) clip.getData(DataFlavor.stringFlavor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        Emotion.xxx = (double)jSlider1.getValue() / 1000d - 1d;
        setWord();
    }//GEN-LAST:event_jSlider1StateChanged

    private void jSlider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider2StateChanged
        Emotion.zzz = (double)jSlider2.getValue() / 1000d - 1d;
        setWord();
    }//GEN-LAST:event_jSlider2StateChanged

    private void jSlider3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider3StateChanged
        Emotion.yyy = (double)jSlider3.getValue() / 1000d - 1d;
        setWord();
    }//GEN-LAST:event_jSlider3StateChanged

    private void emotionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emotionActionPerformed
        if (clipmode) {
            return;
        }
        EmotionAnchor ancNow = null;
        for (int i = 0; i < anchor.size(); i++) {
            if (anchor.get(i).word.equals(emotion.getSelectedItem())) {
                ancNow = anchor.get(i);
            }
        }
        if (ancNow == null) {
            return;
        }
        jSlider1.setValue((int) (ancNow.x * 1000 + 1000));
        jSlider2.setValue((int) (ancNow.z * 1000 + 1000));
        jSlider3.setValue((int) (ancNow.y * 1000 + 1000));
    }//GEN-LAST:event_emotionActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        searchKey();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseReleased
        if (evt.getButton() == 3) {
            clipmode = false;
        }
    }//GEN-LAST:event_jButton6MouseReleased
    private void setWord(){
        //一番近い感情に寄せる
        boolean bk = clipmode;
        clipmode = true;
        double[] dist = new double[30];
        int idx = 100;
        double min=99999;
        for (int i = 0; i < Emotion.anchor.size(); i++) {
            //距離
            dist[i] = Math.pow((Emotion.anchor.get(i).x - Emotion.xxx ),2) 
                    + Math.pow((Emotion.anchor.get(i).y - Emotion.yyy ),2) 
                    + Math.pow((Emotion.anchor.get(i).z - Emotion.zzz ),2);
            System.out.println("dist" + dist[i]);
        }
        for (int i = 0; i < Emotion.anchor.size(); i++) {
            if (dist[i] < min) {
                idx = i;
                min = dist[i];
            }
        }
        System.out.println("idx" + idx);
        emotion.setSelectedIndex(idx);
        clipmode = bk;
        
        html.setText("");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Emotion.keys.size(); i++) {
            if (Math.abs((Emotion.keys.get(i).x) - Emotion.xxx ) < 0.1d) {
            if (Math.abs((Emotion.keys.get(i).y) - Emotion.yyy ) < 0.1d) {
            if (Math.abs((Emotion.keys.get(i).z) - Emotion.zzz ) < 0.1d) {
                sb.append(Emotion.keys.get(i).word + " ");
            }}}
        }
        html.setText(sb.toString());
    }
    private void searchKey() {
        //html.setText("Search...");
        //jTextField1.setBackground(Color.red);
        String str = jTextField1.getText();
        for (int i = 0; i < Emotion.keys.size(); i++) {
            if (str.equals(Emotion.keys.get(i).word)) {
                jSlider1.setValue((int) (Emotion.keys.get(i).x * 1000 + 1000));
                jSlider2.setValue((int) (Emotion.keys.get(i).z * 1000 + 1000));
                jSlider3.setValue((int) (Emotion.keys.get(i).y * 1000 + 1000));
                //setWord();
                break;
            }
        }
        //jTextField1.setBackground(Color.white);
        //html.setText("End...");

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(emotionJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(emotionJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(emotionJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(emotionJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new emotionJFrame().setVisible(true);
            }
        });
    }
    public void setText(String str) {
        html.setText(str);
    }
    public void setOk() {
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        html.setVisible(true);
    }
    public void setExit() {
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        html.setVisible(false);
    }
    public void hyperlinkUpdate(HyperlinkEvent e) {
        //System.out.println("emotion.emotionJFrame.hyperlinkUpdate()");
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane) e.getSource();
            if (e instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                HTMLDocument doc = (HTMLDocument)pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
                setStyle();
            } else {
                try {
                    pane.setPage(e.getURL());
                    setStyle();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> emotion;
    private javax.swing.JEditorPane html;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JSlider jSlider3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField url;
    // End of variables declaration//GEN-END:variables
}
