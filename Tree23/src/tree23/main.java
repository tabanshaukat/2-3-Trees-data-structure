
package tree23;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import static java.lang.Math.pow;
import java.util.Random;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        double startTime,endTime,totalTime;
        System.out.println("DS PROJECT: 2-3 TREE IMPLEMENTATION");
        System.out.println("Please Check the Output_File_23Tree.txt in Directory for Output.\n");
        System.out.println("BY\n HUZAIFAH 16K3924\n TABAN 16K3937\n FAREEHA 16K3934");
        System.out.println("\nSECTION D");
        System.out.println("Dated: 8th Dec'17");
        
    File file = new File("Output_File_23Tree.txt");        
    PrintStream printStreamToFile = new PrintStream(file);
    System.setOut(printStreamToFile);    
        System.out.println("");
        System.out.println("2-3 TREE IMPLEMENTATION:");
        System.out.println("");
        System.out.println("");
        Tree2_3<Integer> tree = new Tree2_3<>(); //2-3 Tree created here
        System.out.println("Is the tree empty?"+tree.isEmpty());
        System.out.print("INSERTING 10 RANDOM ELEMENTS: ");
        Random rand = new Random();
        int[] randNum=new int[10];
        for(int j=0;j<10;j++)
        {   
            randNum[j]=rand.nextInt(50)+1;
            System.out.print(randNum[j]+" ");
        }
        System.out.println("");
        int j=0;
        startTime = System.nanoTime();
        while(j<10)//inserting 10 elements in this loop
        {
                tree.insert(randNum[j]);//the element is inserted into the tree
                j++;//next element 
        }
         endTime= System.nanoTime();
          totalTime = (endTime - startTime);
          System.out.println("The total time of 10 insertion(ns): " +totalTime);
          System.out.println("");
        System.out.println("AFTER INSERTIONS:\n");
        System.out.println("");
        System.out.println("");
        tree.inOrderDisplay();
        System.out.println("");
        System.out.println("");
        tree.preOrderDisplay();
        System.out.println("");
        System.out.println("Is the tree empty? "+tree.isEmpty());
        System.out.println("");
        System.out.println("\nSize of the 2-3 Tree: "+tree.getSize());//Displays the no. of elements in the tree
        System.out.println("");
        //System.out.println("Level of the tree: "+tree.getLevel());
        
        int toSearch;
        System.out.println("");
        System.out.println("SEARCHING 10 RANDOM ELEMENTS:\n");
        System.out.println("");
        System.out.println("");
        startTime = System.nanoTime();
        for(int i=0;i<10;i++)
        {
            toSearch = (int)(Math.random()*50+1);
            System.out.println("\nSearching " + toSearch);
            if(tree.search(toSearch)==null)//return null when element not found
                System.out.println(toSearch +" not found.\n");
            else
                System.out.println(tree.search(toSearch) +" found successfully.\n");
        }
        endTime= System.nanoTime();
          totalTime = (endTime - startTime);
          System.out.println("The total time of 10 searches(ns): " +totalTime);
          System.out.println("");
        int toDelete;
        System.out.println("");
        System.out.println("DELETING 10 RANDOM ELEMENTS:\n");
        System.out.println("");
        System.out.println("");
        startTime = System.nanoTime();
        for(int i=0;i<10;i++)
        {
            toDelete = (int)(Math.random()*50+1);
            System.out.println("\nDeleting " + toDelete);
            if(tree.delete(toDelete)) System.out.println(toDelete +" deleted successfully.\n");
            else System.out.println(toDelete +" Not found\n");
        }
        endTime= System.nanoTime();
          totalTime = (endTime - startTime);
          System.out.println("The total time of 10 deletions(ns): " +totalTime);
          System.out.println("");
        System.out.println("AFTER DELETIONS:\n");
        System.out.println("");
        tree.inOrderDisplay();
        System.out.println("");
        System.out.println("");
        tree.preOrderDisplay();
        System.out.println("");
        System.out.println("Is the tree empty?"+tree.isEmpty());
        System.out.println("");
        System.out.println("Size of the 2-3 Tree: "+tree.getSize());
        System.out.println("");
        System.out.println("Is the tree empty? "+tree.isEmpty());
        //System.out.println("Level of the tree: "+tree.getLevel());
        
    }
}
