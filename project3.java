import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class project3 extends JFrame {
    private JTextField inputField;
    private JLabel wpmLabel, cpmLabel, accuracyLabel;
    private JButton startButton, stopButton;
    private long startTime;
    private int totalCharactersTyped, correctCharactersTyped;
    private Timer timer;
    private final String sampleText = "This is a sample text for typing.";

    public project3() {
        // Create and configure the panel
        System.out.println("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG");
        setTitle("Typing Speed Calculator");
        setSize(800,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        String sampleText = "The quick brown fox jumps over the lazy dog";
		        System.out.println(sampleText);

        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Do nothing
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (startTime == 0) {
                    startTime = System.currentTimeMillis();
                    timer.start();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                updateStatistics();
            }
        });

        wpmLabel = new JLabel("Words per Minute: 0");
        cpmLabel = new JLabel("Characters per Minute: 0");
        accuracyLabel = new JLabel("Accuracy: 100%");
       
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new GridLayout(3, 1));
        statisticsPanel.add(wpmLabel);
        statisticsPanel.add(cpmLabel);
        statisticsPanel.add(accuracyLabel);

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
           // Add action listeners to buttons
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                inputField.setText("");
                // Initialize variables for the typing test
                startTime = 0;
                totalCharactersTyped = 0;
                correctCharactersTyped = 0;
                timer.restart();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                timer.stop();
                updateStatistics();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        add(inputField, BorderLayout.CENTER);
        add(statisticsPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatistics();
            }
        });
    }

    private void updateStatistics() {
        String typedText = inputField.getText();

        totalCharactersTyped = typedText.length();
        correctCharactersTyped = 0;

        for (int i = 0; i < totalCharactersTyped && i < sampleText.length(); i++) {
            if (typedText.charAt(i) == sampleText.charAt(i)) {
                correctCharactersTyped++;
            }
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        double minutes = elapsedTime / 60000.0;
          //calculate words per minute , characters per minute and accuracy
        int wpm = (int) ((totalCharactersTyped / 5.0) / minutes);
        int cpm = (int) (totalCharactersTyped / minutes);
        int accuracy = (int) ((correctCharactersTyped / (double) totalCharactersTyped) * 100);
           // Display results in the labels
        wpmLabel.setText("Words per Minute: " + wpm);
        cpmLabel.setText("Characters per Minute: " + cpm);
        accuracyLabel.setText("Accuracy: " + accuracy + "%");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new project3().setVisible(true);
            }
        });
    }
}
