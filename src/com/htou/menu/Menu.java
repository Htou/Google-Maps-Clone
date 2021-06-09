package com.htou.menu;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.InputElement;
import com.teamdev.jxbrowser.dom.Node;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import com.teamdev.jxbrowser.frame.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JPanel mainPanel;
    private JLabel OVProtoypeAppLabel;
    private JTextField searchLocationA;
    private JButton searchButton;
    private JButton resetButton;
    private JPanel maps_panel;
    private JTextField searchLocationB;

    private static final RenderingMode HARDWARE_ACCELERATED = RenderingMode.HARDWARE_ACCELERATED;

    private Engine engine = Engine.newInstance(EngineOptions.newBuilder(HARDWARE_ACCELERATED).licenseKey("").build());
    private Browser browser = engine.newBrowser();
    private BrowserView view = BrowserView.newInstance(browser);

    public Menu() {

        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                browser.mainFrame().flatMap(Frame::document).ifPresent(document -> {
                    String baseUri = document.baseUri();
                    System.out.println(searchLocationA.getText());
                    System.out.println(searchLocationB.getText());
                    document.findElementById("departure")
                            .ifPresent(element -> ((InputElement) element).value(searchLocationA.getText()));
                    document.findElementById("destination")
                            .ifPresent(element -> ((InputElement) element).value(searchLocationB.getText()));
                    document.findElementById("submit").ifPresent(Node::click);
                    System.out.println("buttons work");
                });

            }
        });

        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                browser.navigation().reload();
                System.out.println("button works");
            }
        });

        open_site();
    }

    public void open_site() {
        //
        maps_panel.add(view, BorderLayout.CENTER);

        browser.navigation().loadUrl("src/index.html");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu");
        frame.setContentPane(new Menu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
