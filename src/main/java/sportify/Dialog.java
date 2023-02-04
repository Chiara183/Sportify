package sportify;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * Allows you to create a Dialog window
 * external to the application that puts
 * the application on hold
 */
public class Dialog extends JPanel{
    private JFrame frame;
    private transient MainApp mainApp;
    private transient CountDownLatch modalitySignal;

    /**
     *  Creates the instance
     */
    public Dialog() {}

    /**
     * Creates the GUI shown inside the frame's content pane.
     *
     * @param frame the frame where to show the GUI
     */
    public Dialog(JFrame frame) {
        this.frame = frame;
        JPanel frequentPanel = createSimpleDialogBox();
        frequentPanel.setName("dialogPanel");
        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
        frequentPanel.setBorder(padding);
        add(frequentPanel, BorderLayout.CENTER);
    }

    /**
     * Creates the panel shown by the first tab.
     *
     * @return the dialog box created
     */
    private JPanel createSimpleDialogBox() {
        JRadioButton[] radioButtons = new JRadioButton[2];
        ButtonGroup group = new ButtonGroup();
        JPanel panel;

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
            ButtonModel model = group.getSelection();
            String command = model.getActionCommand();
            //desktop
            if (Objects.equals(command, desktop)) {
                mainApp.setMobile(false);
                modalitySignal.countDown();
                frame.dispose();

            //mobile
            }
            else if (Objects.equals(command, mobile)) {
                mainApp.setMobile(true);
                modalitySignal.countDown();
                frame.dispose();
            }
        });

        panel = createPane(radioButtons, showItButton);
        return panel;
    }

    /**
     * Used to create a pane
     *
     * @param radioButtons the list of radioButtons
     *                    to be placed in the dialog box
     * @param showButton the Button to be inserted at the
     *                  end of the dialog box
     *
     * @return the dialog box created
     */
    private JPanel createPane(JRadioButton[] radioButtons, JButton showButton) {
        JPanel box = new JPanel();
        JLabel label = new JLabel();
        BoxLayout boxLayout = new BoxLayout(box, BoxLayout.PAGE_AXIS);
        box.setLayout(boxLayout);
        box.add(label);

        for (JRadioButton radioButton : radioButtons) {
            box.add(radioButton);
        }
        BorderLayout borderLayout = new BorderLayout();
        JPanel pane = new JPanel(borderLayout);
        pane.add(box, BorderLayout.PAGE_START);
        pane.add(showButton, BorderLayout.PAGE_END);
        return pane;
    }

    /**
     * Create the GUI and show it
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame fr = new JFrame("Modality");

        //Create and set up the content pane.
        Dialog newContentPane = new Dialog(fr);
        newContentPane.setMainApp(getMainApp());
        newContentPane.setWait(getModalitySignal());
        Dimension dimension = new Dimension(220,100);
        newContentPane.setPreferredSize(dimension);
        fr.setContentPane(newContentPane);

        //Display the window.
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);
    }

    /**
     * Is called to set mainApp
     *
     * @param mainApp the value to be set
     */
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    /**
     * Is called to set wait variable
     *
     * @param modalitySignal the value to be set
     */
    public void setWait(CountDownLatch modalitySignal) {
        this.modalitySignal = modalitySignal;
    }

    /**
     * getMainApp: returns the main
     * controller of the application.
     *
     * @return the main controller
     */
    public MainApp getMainApp() {
        return mainApp;
    }

    /**
     * getModalitySignal: returns the value
     * of time to wait.
     *
     * @return the waiting value
     */
    public CountDownLatch getModalitySignal() {
        return modalitySignal;
    }
}
