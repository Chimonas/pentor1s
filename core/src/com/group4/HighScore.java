package com.group4;

/**
 * Created by Tobias on 27/11/2017.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class HighScore {
    private ArrayList<Integer> points = new ArrayList<Integer>();
    private ArrayList<String> names = new ArrayList<String>();


    /**
     * The only constructor that initializes to ArrayLists which are linked to txt files.
     */
    public HighScore() {
        try {
            BufferedReader a = new BufferedReader(new FileReader("points.txt"));
            BufferedReader b = new BufferedReader(new FileReader("names.txt"));
            ArrayList<String> temp = new ArrayList(0);
            temp = createArray(a);
            names = createArray(b);
            a.close();
            b.close();
            for (String x : temp) {
                points.add(Integer.valueOf(x));
            }


        }catch (IOException e)
        {}
    }


    public void gameShow() {
        ArrayList<String> nameList = new ArrayList();
        ArrayList<Integer> pointList = new ArrayList();
        for (int i=0; i<10; i++) {
            if (i>=names.size()) {
                nameList.add("0");
                pointList.add(0);
            } else {
                nameList.add(names.get(i));
                pointList.add(points.get(i));
            }
        }
        //new GUI(nameList,pointList);

    }

    /**
     * Returns the highest score.
     * @return the highest score
     */
    public int TopScore(){
        if(points.size()==0)
            return 0;
        return points.get(0);
    }

    /**
     * Returns a String ArrayList of the names in the highscore list.
     * @return the highscore list of names
     */
    public ArrayList<String> getNames(){
        return names;
    }

    /**
     * Returns an Integer ArrayList of the points in the highscore list.
     * @return the highscore list of points
     */
    public ArrayList<Integer> getPoints(){
        return points;
    }

    /**
     * Sends the 2 ArrayLists to be displayed.
     * @param game
     */
    public void menuShow(TetrisGame game) {
        new HighscoreScreen(game,names,points);
    }


    /**
     * Takes the 2 parametres and adds them on the 2 ArrayLists in the correct order and after that
     * it adds them on the local text files.
     * @param newScore the score that the last player that played has made
     * @param newName   the name of the last player that has played
     */
    public void add(int newScore,String newName) {

        //adding the new score to the list
        points.add(newScore);
        for (int i = 0; i < points.size()-1; i++) {
            for (int j = i+1; j < points.size(); j++) {
                if (points.get(i) < points.get(j)) {
                    int temp = points.get(i);
                    points.set(i, points.get(j));
                    points.set(j, temp);
                }
            }
        }

        //adding the new name on the list
        int k = points.indexOf(newScore);
        if (names.size()!=0) {
            String tempx = names.get(names.size() - 1);
            names.add(tempx);

            for (int i = names.size() - 1; i > k; i--) {
                names.set(i, names.get(i - 1));
            }
            names.set(k, newName);
        } else {
            names.add(newName);
        }

        //updating the files
        addOn(points);
        addOn(names);

        //print when the game finishes
        //new GUI(names,points,true, k);
    }


    /**
     *  Takes a buffered reader and transfers it's contents into an ArrayList<Strings>
     * @param reader the buffered reader that has readen a file
     * @return the ArrayList that has the contents of the reader.
     */
    public ArrayList<String> createArray(BufferedReader reader) {
        ArrayList<String> s = new ArrayList<String>();
        try {

            for (String x = reader.readLine(); x != null; x = reader.readLine()) {
                s.add(x);
            }

        }catch (IOException e)
        {}
        return s;
    }

    /**
     * Prints the ArrayList into the text file.
     * @param array an ArrayList of the contents in the highscore.
     */
    public void addOn(ArrayList array) {
        try {
            PrintWriter writer;
            if (array.get(0) instanceof Integer)
                writer = new PrintWriter("points.txt");
            else writer = new PrintWriter("names.txt");

            for (int i=0; i<array.size(); i++)
                writer.println(array.get(i));
            writer.close();
        } catch (IOException e)
        {}
    }
}
