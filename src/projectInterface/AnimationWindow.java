package projectInterface;

import domain.Block;
import domain.SynchronizedBuffer;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class AnimationWindow extends JFrame implements ActionListener, Runnable, WindowListener {

    /**
     * Componentes
     */
    JLabel lblMessageTable;
    public static JLabel jlbTime;
    JButton btnPause, btnPlay, btnStop, back;
    public static JTextArea positions;
    boolean o = false;

    /**
     * Atributos
     */
    private final SynchronizedBuffer myBuffer;
    private Maze myMaze;
    private boolean pause;
    private boolean run;
    Border n = null;

    //constructor
    public AnimationWindow(int fast, int furious, int smart, int item, ArrayList<Block> blocks,
            int[] entranceExit, String path) throws IOException {
        this.setLayout(null);
        this.setTitle("Maze of threads");
        this.setIconImage(new ImageIcon("./ProjectImages/icon.png").getImage());

        jlbTime = new JLabel("00:00:00");
        jlbTime.setFont(new Font("Amatic SC", Font.BOLD, 30));
        jlbTime.setBounds(745, 40, 150, 42);
        jlbTime.setForeground(Color.yellow);

        btnPlay = new JButton(new ImageIcon("./ProjectImages/play.png"));
        btnPlay.setBounds(745, 110, 76, 28);
        btnPlay.addActionListener(this);
        btnPlay.setBorder(n);
        btnPlay.setOpaque(o);

        back = new JButton(new ImageIcon("./ProjectImages/back.png"));
        back.setBounds(745, 600, 76, 28);
        back.setToolTipText("back");
        back.addActionListener(this);
        back.setBorder(n);
        back.setOpaque(o);
        back.setToolTipText("Back");

        btnPause = new JButton(new ImageIcon("./ProjectImages/pause.png"));
        btnPause.setBounds(745, 155, 76, 28);
        btnPause.addActionListener(this);
        btnPause.setBorder(n);
        btnPause.setOpaque(o);

        btnStop = new JButton(new ImageIcon("./ProjectImages/stop.png"));
        btnStop.setBounds(745, 195, 76, 28);
        btnStop.addActionListener(this);
        btnStop.setBorder(n);
        btnStop.setOpaque(o);

        positions = new JTextArea();
        positions.setBounds(745, 245, 130, 250);
        positions.setEditable(false);
        positions.setOpaque(false);
        positions.setForeground(Color.white);
        positions.setFont(new Font("Cambria", Font.BOLD, 16));

        lblMessageTable = new JLabel(new ImageIcon(path));
        lblMessageTable.setBounds(0, 0, 900, 700);

        this.myBuffer = new SynchronizedBuffer();
        this.myMaze = new Maze(myBuffer, fast, furious, smart, item, blocks, entranceExit);
        this.myMaze.setBounds(30, 40, 681, 601);

        this.add(positions);
        this.add(btnStop);
        this.add(btnPause);
        this.add(btnPlay);
        this.add(back);
        this.add(jlbTime);
        this.add(myMaze);
        this.add(lblMessageTable);

        this.pause = false;
        this.run = true;

        this.setSize(900, 700);
        this.setLocation(240, 10);
        this.setBackground(Color.red);
        this.setVisible(true);
        this.addWindowListener(this);
        initStopWatch();
        this.myMaze.createAndStart();
    }
    
    //cronometro
    public void initStopWatch() {
        Thread mi = new Thread(this);
        mi.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPause) {
            try {
                this.pause = true;
                getMyMaze().pause();
                String soundName = "./Sounds/plup.wav";
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == btnPlay) {
            this.pause = false;
            try {
                getMyMaze().resume();
                String soundName = "./Sounds/plup.wav";
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == btnStop) {
            this.run = false;
            try {
                getMyMaze().stop();
                String soundName = "./Sounds/plup.wav";
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == back )  {
            AudioInputStream audioInputStream = null;
            try {
                MainWindow mainWindow = new MainWindow();
                mainWindow.setVisible(true);
                this.dispose();
                String soundName = "./Sounds/plup.wav";
                audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    audioInputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        }
    }

    public Maze getMyMaze() {
        return myMaze;
    }

    public void setMyMaze(Maze myMaze) {
        this.myMaze = myMaze;
    }

    //metodo para correr el cronometro
    @Override
    public void run() {
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        while (run == true) {
            while (pause == true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
            if (seconds == 59) {
                seconds = 0;
                minutes += 1;
            }
            if (minutes == 59) {
                minutes = 0;
                hours += 1;
            }
            String stopwatchText = (hours <= 9 ? "0" : "") + hours
                    + ":" + (minutes <= 9 ? "0" : "") + minutes
                    + ":" + (seconds <= 9 ? "0" : "") + seconds;
            jlbTime.setText(stopwatchText);
            seconds += 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            myMaze.stop();
        } catch (InterruptedException ex) {
            Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        run = false;
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
