import java.util.*;
import java.io.*;

class FractionSearcher
{
  public static void main (String[]args)throws IOException
  {
    PrintWriter out=new PrintWriter (new FileWriter ("fractions4.txt"));
    for (int x=2;x<100;x++)
    {
      for (int y=x;y<100;y++)
      {
        for (int z=y;z<100;z++)
        {
          for (int n=z;n<100;n++)
          {
                      Fraction ans=new Fraction (x);
          ans=ans.add (new Fraction (y));
          ans=ans.add (new Fraction (z));
          ans=ans.add (new Fraction (n));
          if (ans.checkSize(ans))continue;
          if (ans.check(ans))
          {
          out.println ("Solution: 1/"+x+" 1/"+y+" 1/"+z+" "+"1/"+n+" "+ans.toString());
          }
         // else 
          //  System.out.println(x+" "+y+" "+z+" "+ans.toString());
          }
        }
      }
    }
          System.out.println ("done");
          out.close();
  }
}