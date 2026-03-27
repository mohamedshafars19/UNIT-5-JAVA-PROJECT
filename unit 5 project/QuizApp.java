import javax.swing.*;

public class QuizApp {

    JFrame f;
    JLabel qLabel, tLabel;
    JRadioButton a, b, c, d;
    JButton next;

    int q = 0;
    int score = 0;
    int time = 5;

    String questions[][] = {
            {"Java is?", "Language", "OS", "Browser", "DB"},
            {"Thread is?", "Heavy", "Lightweight", "None", "All"}
    };

    int answers[] = {0, 1};

    public QuizApp() {

        f = new JFrame("Quiz");
        f.setSize(300, 250);
        f.setLayout(null);

        qLabel = new JLabel();
        qLabel.setBounds(20, 20, 250, 20);
        f.add(qLabel);

        tLabel = new JLabel("Time: 5");
        tLabel.setBounds(200, 20, 100, 20);
        f.add(tLabel);

        a = new JRadioButton();
        b = new JRadioButton();
        c = new JRadioButton();
        d = new JRadioButton();

        a.setBounds(20, 50, 200, 20);
        b.setBounds(20, 70, 200, 20);
        c.setBounds(20, 90, 200, 20);
        d.setBounds(20, 110, 200, 20);

        ButtonGroup g = new ButtonGroup();
        g.add(a); g.add(b); g.add(c); g.add(d);

        f.add(a); f.add(b); f.add(c); f.add(d);

        next = new JButton("Next");
        next.setBounds(100, 150, 80, 25);
        f.add(next);

        next.addActionListener(e -> nextQ());

        loadQ();

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void loadQ() {
        if (q >= questions.length) {
            JOptionPane.showMessageDialog(f, "Score: " + score);
            System.exit(0);
        }

        qLabel.setText(questions[q][0]);
        a.setText(questions[q][1]);
        b.setText(questions[q][2]);
        c.setText(questions[q][3]);
        d.setText(questions[q][4]);

        startTimer();
    }

    void nextQ() {
        int ans = -1;

        if (a.isSelected()) ans = 0;
        if (b.isSelected()) ans = 1;
        if (c.isSelected()) ans = 2;
        if (d.isSelected()) ans = 3;

        if (ans == answers[q]) score++;

        q++;
        loadQ();
    }

    void startTimer() {
        time = 5;
        tLabel.setText("Time: " + time);

        new Thread(() -> {
            try {
                while (time > 0) {
                    Thread.sleep(1000);
                    time--;
                    tLabel.setText("Time: " + time);
                }
                nextQ();
            } catch (Exception e) {}
        }).start();
    }

    public static void main(String[] args) {
        new QuizApp();
    }
}