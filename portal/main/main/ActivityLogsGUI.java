package main;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ActivityLogsGUI {

	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    private static JFrame parentFrame; // Reference to parent frame 
    private static JFrame jf; // This frame
    private static CleanPortal CP; // Reference to portal
    private static JComboBox cb;
    private static JTextArea textArea;
    
    public static String getLogFile(String logName) {
		BufferedReader reader;
		String line;
		String content = "";
		try {
			reader = new BufferedReader(
				new FileReader(logName));
			line = reader.readLine();
			while (line != null) {
			    content += line + "\n";
			    line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
    
    public static void addComponentsToPane(Container pane) {
    	JLabel label;
    	JButton button;
    	JTextField text; 
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    	
    	if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
        
	    pane.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
	    ArrayList<String> logs = new ArrayList<String>();
	    //aList.forEach(a -> bList.add(a.getB()));

	    //
	    
	    List<File> files = new ArrayList<File>();
		try {
			files = Files.walk(Paths.get("logs"))
				.filter(Files::isRegularFile)
				.map(Path::toFile)
				.collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    cb = new JComboBox(files.toArray());
	    
	    ActionListener cbActionListener = new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String s = String.valueOf(cb.getSelectedItem());
	            textArea.setText("");
	            textArea.append(getLogFile(s));
	        }
	    };
	    
        cb.addActionListener(cbActionListener);
	    cb.setEditable(false);
        cb.addActionListener(cb);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        pane.add(cb, c);
        
        button = new JButton("Send log");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        pane.add(button, c);
        button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        		public void run() {
	        			// TODO: Send log to company's server
	        			parentFrame.setVisible(true);
	        			jf.dispose();
	        		}
	        	});
	        }
	    });
        
        button = new JButton("Back to main menu");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.fill = GridBagConstraints.HORIZONTAL;
//	    c.weightx = 1;
//	    c.gridx = 0;
//	    c.gridy = 1;
	    //c.gridwidth = 6;
	    pane.add(button, c);
	    button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        		public void run() {
	        			parentFrame.setVisible(true);
	        			jf.dispose();
	        		}
	        	});
	        }
	    });
        
        // textarea
        textArea = new JTextArea(5, 20);
        JScrollPane s = new JScrollPane(textArea); 
        textArea.setEditable(false);
        if (files.size() > 0) {
        	textArea.append(getLogFile(String.valueOf(files.get(0))));
        }
//        c.fill = GridBagConstraints.HORIZONTAL;
//	    c.weightx = 1;
//	    c.gridx = 0;
//	    c.gridy = 1;
//        pane.add(t, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        pane.add(s, c);
        
        
        
//        c.fill = GridBagConstraints.HORIZONTAL;
//	    c.weightx = 1;
//	    c.gridx = 1;
//	    c.gridy = 1;
//        pane.add(s, c);
//        
//        button = new JButton("Delete schedule!");
//	    c.fill = GridBagConstraints.HORIZONTAL;
//	    c.weightx = 1;
//	    c.gridx = 0;
//	    c.gridy = 1;
//	    //c.gridwidth = 6;
//	    pane.add(button, c);
//	    
//        button.addActionListener(new ActionListener() {
//	        @Override
//	        public void actionPerformed(ActionEvent e) {
//	        	javax.swing.SwingUtilities.invokeLater(new Runnable() {
//	        		public void run() {
//	        			// delete cb by id
//	        			try {
//	        				CleanPortal.schedule.deleteSchedule(cb.getSelectedIndex());
//	        			} catch (Exception e) {
//	        				// If schedule is empty
//	        			}
//	        			parentFrame.setVisible(true);
//	        			jf.dispose();
//	        		}
//	        	});
//	        }
//	    });

	    
//	    
//	    Integer index = 1;
//	    for (String line : schedules) {
//	    	combo.addItem(new JComboItem(1, "1"));
//	    	
//	    	label = new JLabel("Schedule (" + index.toString() + "): " + line);
//		    c.weightx = 0.5;
//		    c.fill = GridBagConstraints.HORIZONTAL;
//		    c.gridx = 0;
//		    c.gridy = index - 1;
//		    //c.gridwidth = 3;
//		    pane.add(label, c);
//		    
//		    
//		    //
//		    button.addActionListener(new ActionListener() {
//		        @Override
//		        public void actionPerformed(ActionEvent e) {
//		        	javax.swing.SwingUtilities.invokeLater(new Runnable() {
//		        		public void run() {
//		        			parentFrame.setVisible(true);
//		        			jf.dispose();
//		        		}
//		        	});
//		        }
//		    });
//	    }
	 
	    
	    
	    
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    static void createAndShowGUI(JFrame pf, CleanPortal c) {
    	parentFrame = pf;
    	CP = c;
        //Create and set up the window.
        jf = new JFrame("GridBagLayoutDemo");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setBounds(10,10,700,600); 

        
        //Set up the content pane.
        addComponentsToPane(jf.getContentPane());
 
        //Display the window.
        //frame.pack();
        jf.setVisible(true);
    }
 
}