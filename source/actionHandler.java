/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.header;
import model.line;
import view.myGUI;
import view.newHeader;
import view.newLine;

/**
 *
 * @author Lenovo
 */
public class actionHandler implements ActionListener, ListSelectionListener {

    private myGUI gui;
    private newHeader headerDialog;
    private newLine lineDialog;
    private int saveFlag = 0;

    public actionHandler(myGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Save File": {
                try {
                    saveFlag = 0;
                    saveFile();

                } catch (IOException ex) {
                    Logger.getLogger(actionHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "Load File": {
                try {
                    try {
                        loadFile();
                    } catch (ParseException ex) {
                        Logger.getLogger(actionHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(actionHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "New Invoice":
                getNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "New Line":
                getNewLine();
                break;
            case "Delete Line":
                deleteLine();
                break;
            case "OK Header": {
                postNewInvoice();
            }
            break;

            case "Cancel Header":
                cancelNewHeader();
                break;
            case "OK Line":

                postNewLine();
                break;
            case "Cancel Line":
                cancelNewLine();
                break;
            case "Exit":
            {
                try {
                    saveFlag = 1;
                    saveFile();
                } catch (IOException ex) {
                    Logger.getLogger(actionHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                System.exit(0);

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) throws ArrayIndexOutOfBoundsException {
        try {
            int choice = this.gui.getHeadersTable().getSelectedRow();
            if (choice != -1) {
                header selectedHeader = this.gui.getHeadersList().get(choice);
                ArrayList<line> myLines = selectedHeader.getLines();
                this.gui.setLinesList(myLines);

                this.gui.getHeaderID().setText(String.valueOf(selectedHeader.getId()));
                this.gui.getHeaderDate().setText(String.valueOf(selectedHeader.getDate()));
                this.gui.getHeaderCust().setText(selectedHeader.getCustomer());
                this.gui.getHeaderTotal().setText(String.valueOf(selectedHeader.getTotal()));
            }
        } catch (ArrayIndexOutOfBoundsException exp) {
            JOptionPane.showMessageDialog(this.gui, "Select a header within table bounds", "Incorrect Selection", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void saveFile() throws IOException {
        if (saveFlag == 0)
        {
        if (this.gui.getHeaderTableModel() == null ) {
            
                
            JOptionPane.showMessageDialog(this.gui, "Nothing to save. Please load a file and create entries first", "No data", JOptionPane.ERROR_MESSAGE);

        } else {
            try {

                JFileChooser f = new JFileChooser();
                int choice = f.showSaveDialog(this.gui);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File headerFile = f.getSelectedFile();
                    if (!".csv".equals((headerFile.toString()).substring((headerFile.toString()).lastIndexOf(".")))) {
                        throw new Exception();
                    }
                    FileWriter fileWriter = new FileWriter(headerFile);
                    ArrayList<header> headersList = this.gui.getHeadersList();
                    String saveHeaders = "";
                    String saveLines = "";

                    for (header header : headersList) {
                        saveHeaders += header.toString();
                        saveHeaders += "\n";
                        for (line line : header.getLines()) {
                            saveLines += line.toString();
                            saveLines += "\n";
                        }
                    }
                    choice = f.showSaveDialog(this.gui);
                    if (choice == JFileChooser.APPROVE_OPTION) {
                        File linesFile = f.getSelectedFile();
                        if (!".csv".equals((linesFile.toString()).substring((linesFile.toString()).lastIndexOf(".")))) {
                            throw new Exception();
                        }
                        FileWriter lineWriter = new FileWriter(linesFile);
                        fileWriter.write(saveHeaders);
                        fileWriter.close();
                        lineWriter.write(saveLines);
                        lineWriter.close();
                    }

                }
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this.gui, "File name with correct format not found", "File not found", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(this.gui, "File format should be .csv", "Incorrect format", JOptionPane.ERROR_MESSAGE);

            }
        }
        }
        else{
        JOptionPane.showMessageDialog(this.gui, "Exiting! Goodbye", "Exit", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    private void loadFile() throws IOException, ParseException {
        try {
            JFileChooser f = new JFileChooser();
            int choice = f.showOpenDialog(this.gui);
            if (choice == JFileChooser.APPROVE_OPTION) {
                File headerFile = f.getSelectedFile();
                if (!".csv".equals((headerFile.toString()).substring((headerFile.toString()).lastIndexOf(".")))) {
                    throw new Exception();
                }
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                ArrayList<header> headersList = new ArrayList<>();
                List<String> headers = Files.readAllLines(headerPath);

                for (String headerStr : headers) {
                    String[] components = headerStr.split(",");
                    int id = Integer.parseInt(components[0]);
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(components[1]);
                    String name = components[2];
                    header temp = new header(id, date, name);
                    headersList.add(temp);

                }
                this.gui.setHeadersList(headersList);
                choice = f.showOpenDialog(this.gui);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File linesFile = f.getSelectedFile();
                    if (!".csv".equals((linesFile.toString()).substring((linesFile.toString()).lastIndexOf(".")))) {
                        throw new Exception();
                    }
                    Path linePath = Paths.get(linesFile.getAbsolutePath());
                    ArrayList<line> linesList = new ArrayList<>();
                    List<String> lines = Files.readAllLines(linePath);
                    for (String lineStr : lines) {
                        String[] components = lineStr.split(",");
                        int id = Integer.parseInt(components[0]);
                        String item = components[1];
                        double price = Double.parseDouble(components[2]);
                        int count = Integer.parseInt(components[3]);
                        header tempHeader = header.getHeaderByID(headersList, id);
                        line myLine = new line(tempHeader, item, price, count);
                        tempHeader.getLines().add(myLine);
                        linesList.add(myLine);
                    }
                    this.gui.setLinesList(linesList);

                }
            }
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(this.gui, "File name not found", "File not found", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e2) {
            JOptionPane.showMessageDialog(this.gui, "Incorrect date format. Date should be 'dd-mm-yyyy'", "Parse Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e3) {
            JOptionPane.showMessageDialog(this.gui, "File format should be .csv", "Incorrect format", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void getNewInvoice() {
        if (this.gui.getHeaderTableModel() != null) {
            this.headerDialog = new newHeader(this.gui);
            this.headerDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this.gui, "Please load a file first", "No data", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void deleteInvoice() {
        if (this.gui.getHeaderTableModel() != null) {
            int choice = this.gui.getHeadersTable().getSelectedRow();
            if (choice != -1) {
                ArrayList<header> tempHeaderList = new ArrayList<header>();
                this.gui.getHeadersList().remove(choice);
                tempHeaderList = this.gui.getHeadersList();
                this.gui.setHeadersList(tempHeaderList);
                this.gui.setLinesList(new ArrayList<>());
                this.gui.getHeaderID().setText("");
                this.gui.getHeaderDate().setText("");
                this.gui.getHeaderCust().setText("");
                this.gui.getHeaderTotal().setText("");

            } else {
                JOptionPane.showMessageDialog(this.gui, "Please Select a header first", "No data", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this.gui, "Please load a file first", "No data", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getNewLine() {

        if (this.gui.getHeaderTableModel() != null) {
            int headerChoice = this.gui.getHeadersTable().getSelectedRow();
            int choice = this.gui.getHeadersTable().getSelectedRow();

            if (headerChoice != -1) {
                if (choice != -1) {
                    this.lineDialog = new newLine(this.gui);
                    this.lineDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this.gui, "Please Select a line first", "No data", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this.gui, "Please Select a header first", "No data", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this.gui, "Load table from file first", "No data", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteLine() {
        if (this.gui.getHeaderTableModel() != null) {
            int choice = this.gui.getLinesTable().getSelectedRow();
            int headerChoice = this.gui.getHeadersTable().getSelectedRow();
            if (headerChoice != -1) {
                if (choice != -1) {
                    line removedLine = this.gui.getLinesList().remove(choice);
                    ArrayList<line> tempLineList = this.gui.getLinesList();
                    this.gui.setLinesList(tempLineList);
                    this.gui.getHeaderTableModel().fireTableDataChanged();
                    //this.gui.getHeadersTable().setRowSelectionInterval(choice, choice);
                    this.gui.getLineTableModel().fireTableDataChanged();
                    header header = removedLine.getHeader();
                    this.gui.getHeaderID().setText(String.valueOf(header.getId()));
                    this.gui.getHeaderDate().setText(String.valueOf(header.getDate()));
                    this.gui.getHeaderCust().setText(header.getCustomer());
                    this.gui.getHeaderTotal().setText(String.valueOf(header.getTotal()));
                } else {
                    JOptionPane.showMessageDialog(this.gui, "Please Select a line first", "No data", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this.gui, "Please Select a header first", "No data", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this.gui, "Load table from file first", "No data", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void postNewInvoice() {

        int id = Integer.parseInt(this.headerDialog.getInvoiceID().getText());
        String name = this.headerDialog.getInvoiceCust().getText();

        if (id < 0) {
            JOptionPane.showMessageDialog(this.gui, "Id should be positive", "Invalid id", JOptionPane.ERROR_MESSAGE);
            cancelNewHeader();
        } else if ((header.getHeaderByID(this.gui.getHeadersList(), id)) != null) {
            JOptionPane.showMessageDialog(this.gui, "A header with this id already exists", "Invalid id", JOptionPane.ERROR_MESSAGE);
            cancelNewHeader();
        } else if ("".equals(name)) {
            JOptionPane.showMessageDialog(this.gui, "Customer name should not be left empty", "Invalid Customer name", JOptionPane.ERROR_MESSAGE);
            cancelNewHeader();
        } else {
            try {
                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(this.headerDialog.getInvoiceDate().getText());
                header header = new header(id, date, name);
                this.gui.getHeadersList().add(header);
                this.gui.setHeadersList(this.gui.getHeadersList());
            } catch (ParseException exp) {
                JOptionPane.showMessageDialog(this.gui, "Wrong date format. Date should be 'dd-mm-yyyy'", "Parse Error", JOptionPane.ERROR_MESSAGE);
                cancelNewHeader();

            }
            cancelNewHeader();
        }

    }

    private void cancelNewHeader() {
        this.headerDialog.setVisible(false);
        this.headerDialog.dispose();
        this.headerDialog = null;
    }

    private void postNewLine() {
        int id = 0;
        int choice = this.gui.getHeadersTable().getSelectedRow();
        if (choice != -1) {
            header selectedHeader = this.gui.getHeadersList().get(choice);
            for (line line : selectedHeader.getLines()) {
                if (line.getId() > id) {
                    id = line.getId();
                }
            }
            id++;
            String name = this.lineDialog.getItemName().getText();
            int count = Integer.parseInt(this.lineDialog.getItemCount().getText());
            double price = Double.parseDouble(this.lineDialog.getItemPrice().getText());

            if ("".equals(name)) {
                JOptionPane.showMessageDialog(this.gui, "Item name should not be left empty", "Invalid item name", JOptionPane.ERROR_MESSAGE);
                cancelNewLine();
            } else if (count <= 0) {
                JOptionPane.showMessageDialog(this.gui, "Count shoud be 1 or more", "Invalid count", JOptionPane.ERROR_MESSAGE);
                cancelNewLine();

            } else if (price <= 0) {
                JOptionPane.showMessageDialog(this.gui, "Price should be greater than 0", "Invalid price", JOptionPane.ERROR_MESSAGE);
                cancelNewLine();

            } else {
                line line = new line(selectedHeader, name, price, count);
                this.gui.getLinesList().add(line);
                this.gui.setLinesList(this.gui.getLinesList());
                this.gui.getHeaderTableModel().fireTableDataChanged();
                this.gui.getHeaderID().setText(String.valueOf(selectedHeader.getId()));
                this.gui.getHeaderDate().setText(String.valueOf(selectedHeader.getDate()));
                this.gui.getHeaderCust().setText(selectedHeader.getCustomer());
                this.gui.getHeaderTotal().setText(String.valueOf(selectedHeader.getTotal()));
                cancelNewLine();
            }
        }

    }

    private void cancelNewLine() {
        this.lineDialog.setVisible(false);
        this.lineDialog.dispose();
        this.lineDialog = null;
    }

}
