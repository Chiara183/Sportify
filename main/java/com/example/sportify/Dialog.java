package com.example.sportify;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class Dialog extends JPanel{
    private JFrame frame;
    private MainApp mainApp;
    private transient CountDownLatch modalitySignal;

    /** Creates the instance*/
    public Dialog() {}

    /** Creates the GUI shown inside the frame's content pane. */
    public Dialog(JFrame frame) {
        this.frame = frame;
        JPanel frequentPanel = createSimpleDialogBox();
        frequentPanel.setName("dialogPanel");
        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
        frequentPanel.setBorder(padding);
        add(frequentPanel, BorderLayout.CENTER);
    }

    /** Creates the panel shown by the first tab. */
    private JPanel createSimpleDialogBox() {
        JRadioButton[] radioButtons = new JRadioButton[2];
        ButtonGroup group = new ButtonGroup();

        JButton showItButton = new JButton("Next");

        String desktop = "Desktop";
        String mobile = "Mobile";

        radioButtons[0] = new JRadioButton("Desktop");
        radioButtons[0].setName("desktop");
        radioButtons[0].setActionCommand(desktop);

        radioButtons[1] = new JRadioButton("Mobile");
        radioButtons[1].setName("mobile");
        radioButtons[1].setActionCommand(mobile);

        for (int i = 0; i < 2; i++) {
            group.add(radioButtons[i]);
        }
        radioButtons[0].setSelected(true);

        showItButton.setName("nextButton");
        showItButton.addActionListener(e -> {
            String command = group.getSelection().getActionCommand();
            //desktop
            if (Objects.equals(command, desktop)) {
                mainApp.setMobile(false);
                modalitySignal.countDown();
                frame.dispose();

            //mobile
            } else if (Objects.equals(command, mobile)) {
                mainApp.setMobile(true);
                modalitySignal.countDown();
                frame.dispose();
            }
        });

        return createPane(radioButtons, showItButton);
    }

    /** Used to create a pane*/
    private JPanel createPane(JRadioButton[] radioButtons, JButton showButton) {
        JPanel box = new JPanel();
        JLabel label = new JLabel();

        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(label);

        for (JRadioButton radioButton : radioButtons) {
            box.add(radioButton);
        }

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);
        pane.add(showButton, BorderLayout.PAGE_END);
        return pane;
    }

    /** Create the GUI and show it*/
    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame fr = new JFrame("Modality");

        //Create and set up the content pane.
        Dialog newContentPane = new Dialog(fr);
        newContentPane.setMainApp(this.mainApp);
        newContentPane.setWait(this.modalitySignal);
        newContentPane.setPreferredSize(new Dimension(220,100));
        fr.setContentPane(newContentPane);

        //Display the window.
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);
    }

    /** Is called to set mainApp*/
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    /** Is called to set wait variable*/
    public void setWait(CountDownLatch modalitySignal) {
        this.modalitySignal = modalitySignal;
    }
}
