/* | Templates
 * and open the template in the editor.
 */
package keyword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DIP
 */
public class KeyWord {

    HashMap<String, Integer> hm = new HashMap<>();

    double[] pos = new double[12];
    int[] count = new int[15];
    BufferedReader br = null;
    FileReader fr = null;
    int word = 0, word1 = 0;

    public KeyWord() {

    }

    public void readpos() throws IOException {

        for (int i = 0; i < 12; ++i) {
            count[i] = 0;
        }
        try {
            fr = new FileReader("Annotated.txt");
            br = new BufferedReader(fr);
            File f1=new File("Keywords.txt");//a file for next_5 matching words of a particular word
           f1.createNewFile();
           FileWriter f3= new FileWriter(f1);
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\s");
                //System.out.println(data[0]+" "+data[1]);

                hm.put(data[0], Integer.parseInt(data[1]));

                count[Integer.parseInt(data[1])]++;
                ++word;
            }

            for (int i = 0; i < 12; ++i) {
                //System.out.println(count[i]);
                pos[i] = (double) count[i] / (double) word;
                //System.out.println(i + " =  " + count[i] + " , " + pos[i]);
            }

            fr.close();
            br.close();

            fr = new FileReader("article.txt");
            br = new BufferedReader(fr);
            String[] data1 = new String[5520];
            String line1 = "";
            
            while ((line1 = br.readLine()) != null) {
               
                if(!line1.equals("। 0")){
                  
                data1[word1++] = line1;
                //System.out.println(data1[word1-1]);
                }
            }
            double[] score = new double[5520];
            int cnt = 1;
            for (int i = 0; i < word1 - 1; ++i) {
                for (int j = i + 1; j < word1; ++j) {
                    if (data1[i].equals(data1[j]) && data1[i] != "") {
                        data1[j] = "";
                        cnt++;
                    }
                }
                String[] s1 = data1[i].split("\\s");
                if (s1.length > 1) {
                    int d = Integer.parseInt(s1[1]);
                    //System.out.println("d : " + cnt);
                    if (data1[i] == "") {
                        score[i] = 0.0;
                    } else {
                        score[i] = pos[d] * cnt;
                    }

                }
                cnt = 1;

            }
            double mini = -10.0;
            int index = 0;
            for(int i =0 ; i<word1; ++i)
                System.out.println("Score : "+score[i]);
            for (int i = 0; i < 150; ++i) {
                for (int j = 0; j < word1; ++j) {
                    if (mini < score[j]) {
                        mini = score[j];
                        index = j;
                    }
                }
                score[index] = -1.0;
                
                //System.out.println(data1[index] + "   " + mini);
                
                f3.write(data1[index]+" : "+mini+System.getProperty( "line.separator" ));
                mini = -10.0;
            }
            f3.close();

            fr.close();
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("Got Exception\n");
        }

// 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        KeyWord keyWord = new KeyWord();
        keyWord.readpos();

    }

}
