package learn.farm.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import learn.farm.domain.PanelResult;
import learn.farm.models.Panel;
import learn.farm.models.PanelMaterial;
import learn.farm.models.PanelSection;

@Component
public class View {

    private final Scanner console = new Scanner(System.in);

    public MenuOption displayMenuAndSelect(List<Panel> all) {
        MenuOption[] values = MenuOption.values();
        printHeader("Main Menu");

        ArrayList<Integer> validChoices = new ArrayList<>();
        validChoices.add(0);
        System.out.println("0: " + values[0].getTitle());
        if (!isEmpty(all)) {
            validChoices.add(1);
            System.out.println("1: " + values[1].getTitle());
        }
        else {
            System.out.println("1: Value unavailable.");
        }

        if (!isFull(all)) {
            validChoices.add(2);
            System.out.println("2: " + values[2].getTitle());
        }
        else {
            System.out.println("2: Value unavailable.");
        }

        if (!isEmpty(all)) {
            validChoices.add(3);
            System.out.println("3: " + values[3].getTitle());
            validChoices.add(4);
            System.out.println("4: " + values[4].getTitle());
        }
        else {
            System.out.println("3: Value unavailable.");
            System.out.println("4: Value unavailable.");
        }

        int index = readInt("Enter the corresponding number: ", validChoices);
        return values[index];
    }

    public void printHeader(String message) {
        System.out.println("\n" + message + "\n" + "=".repeat(message.length()));
    }

    public void displayAll(List<Panel> panels) {
        PanelSection[] values = PanelSection.values();
        for (int i = 0; i < values.length; i++) {
            displaySection(sortBySection(panels, values[i]), values[i]);
        }
    }

    public void displaySection(List<Panel> panels, PanelSection section) {
        printHeader("Panels in " + section.getName() + ":");

        String[][] board = new String[section.getRow()][section.getColumn()];
        for (int row = 0; row < section.getRow(); row++) {
            for (int column = 0; column < section.getColumn(); column++) {
                board[row][column] = "⬜⬜";
            }
        }

        for (int row = 0; row < section.getRow(); row++) {
            for (int column = 0; column < section.getColumn(); column++) {
                for (int current = 0; current < panels.size(); current++) {
                    if (panels.get(current).getRow() == row && panels.get(current).getColumn() == column) {
                        board[row][column] = "⬛⬛";
                    }
                }
            }
        }

        for (int row = 0; row < section.getRow(); row++) {
            for (int column = 0; column < section.getColumn(); column++) {
                System.out.print(board[row][column] + "   ");
            }
            System.out.println();
        }
    }

    public void displayPanel(Panel panel) {
        printHeader("Showing Panel:");
        System.out.printf("Section: %s\nRow: %s\nColumn: %s\nYear Installed: %s\nMaterial Type: %s\nIs Tracking: %s%n",
                panel.getSection().getName(),
                panel.getRow(),
                panel.getColumn(),
                panel.getYearInstalled(),
                panel.getMaterial().getName(),
                panel.isTracking() ? "Yes" : "No");

    }

    public void displayResult(PanelResult result) {
        if (result.isSuccess()) {
            printHeader("Success");
        }
        else {
            printHeader("Error: ");
            for (String err : result.getMessages()) {
                System.out.println(err);
            }
        }
    }

    private List <Panel> sortBySection(List<Panel> all, PanelSection section) {
        List<Panel> result = new ArrayList<>();
        for (Panel p : all) {
            if (p.getSection() == section) {
                result.add(p);
            }
        }
        return result;
    }

    public Panel makePanel() {
        Panel panel = new Panel();
        panel.setSection(readPanelSection());

        ArrayList<Integer> validRows = new ArrayList<>();
        for (int row = 0; row < panel.getSection().getRow(); row++) {
            validRows.add(row);
        }
        panel.setRow(readInt("Row:", validRows));

        ArrayList<Integer> validColumns = new ArrayList<>();
        for (int column = 0; column < panel.getSection().getColumn(); column++) {
            validColumns.add(column);
        }
        panel.setColumn(readInt("Column:", validColumns));

        ArrayList<Integer> validYears = new ArrayList<>();
        for (int year = 1839; year < 2021; year++) {
            validYears.add(year);
        }
        panel.setYearInstalled(readInt("Year:", validYears));

        panel.setMaterial(readPanelMaterial());

        panel.setTracking(readTracking());

        return panel;
    }

    public Panel update(List<Panel> panels) {
        Panel panel = findPanel(panels);
        if (panel != null) {
            return (update(panel));
        }
        return null;
    }

    public Panel update(Panel panel) {
        ArrayList<Integer> validYears = new ArrayList<>();
        for (int year = 1839; year < 2021; year++) {
            validYears.add(year);
        }
        panel.setYearInstalled(readInt("Year:", validYears));

        panel.setMaterial(readPanelMaterial());

        panel.setTracking(readTracking());

        return panel;
    }

    public Panel findPanel(List<Panel> panels) {
        if (panels.size() == 0) {
            return null;
        }
        PanelSection section = readPanelSection();

        ArrayList<Integer> validRows = new ArrayList<>();
        for (int row = 0; row < section.getRow(); row++) {
            validRows.add(row);
        }
        int row = readInt("Row:", validRows);

        ArrayList<Integer> validColumns = new ArrayList<>();
        for (int column = 0; column < section.getColumn(); column++) {
            validColumns.add(column);
        }
        int column = readInt("Column:", validColumns);

        for(Panel p: panels) {
            if (p.getSection() == section && p.getRow() == row && p.getColumn() == column) {
                return p;
            }
        }

        System.out.println("Not found.");
        return null;
    }

    public PanelSection readPanelSection() {
        System.out.println("Section: ");
        ArrayList<Integer> validChoices = new ArrayList<>();
        PanelSection[] values = PanelSection.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println(i + ": " + values[i].getName());
            validChoices.add(i);
        }
        int index = readInt("Enter the corresponding number: ", validChoices);
        return values[index];
    }

    public PanelMaterial readPanelMaterial() {
        System.out.println("Material: ");
        ArrayList<Integer> validChoices = new ArrayList<>();
        PanelMaterial[] values = PanelMaterial.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println(i + ": " + values[i].getName());
            validChoices.add(i);
        }
        int index = readInt("Enter the corresponding number: ", validChoices);
        return values[index];
    }

    public boolean readTracking() {
        System.out.println("Tracking: ");
        ArrayList<Integer> validChoices = new ArrayList<>();

        System.out.println(1 + ": Yes");
        validChoices.add(1);

        System.out.println(2 + ": No");
        validChoices.add(2);

        int index = readInt("Enter the corresponding number: ", validChoices);
        return (1 == index);
    }


    private String readString(String input) {
        System.out.println(input);
        return console.nextLine();
    }

    private String readRequiredString(String input) {
        String result = null;
        do {
            result = readString(input).trim();
            if (result.length() == 0) {
                System.out.println("Input is required.");
            }
        } while (result.length() == 0);
        return result;
    }

    private int readInt(String input) {
        int result = 0;
        boolean isValid = false;
        do {
            String value = readRequiredString(input);
            try {
                result = Integer.parseInt(value);
                isValid = true;
            }
            catch (NumberFormatException ex) {
                System.out.println("Input must be a number.");
            }
        } while(!isValid);
        return result;
    }

    private int readInt(String input, ArrayList<Integer> validChoices) {
        int result = 0;
        Collections.sort(validChoices);
        while (true) {
            result = readInt(input);
            for (Integer i : validChoices) {
                if (Integer.valueOf(i) == result) {
                    return result;
                }
            }

            System.out.print("Value must be ");

            if (validChoices.size() < 15) {
                for (int j = 0; j < validChoices.size(); j++) {
                    if (j == validChoices.size() - 1) {
                        System.out.println("or " + validChoices.get(j) + ".");
                    } else {
                        System.out.print(validChoices.get(j) + ", ");
                    }
                }
            }
            else {
                System.out.println("between " + validChoices.get(0) + " and " + validChoices.get(validChoices.size() - 1) + ".");
            }
        }
    }

    private boolean isEmpty (List<Panel> panels) { return (panels.size() == 0); }

    private boolean isFull (List<Panel> all) {
        PanelSection[] values = PanelSection.values();
        int total = 0;
        for (int i = 0; i < values.length; i++) {
            total += (values[i].getRow() * values[i].getColumn());
        }
        return (all.size() == total);
    }
}
