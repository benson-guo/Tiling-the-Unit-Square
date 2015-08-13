import java.util.*;

public class Greedy
{
  static ArrayList<Integer> usedVal=new ArrayList<Integer>();
  public static void main (String[]args)
  {
   // usedVal.add(1);
    recurse (new Fraction(3).add(new Fraction (3)),new Fraction(2));
  }
  public static void recurse (Fraction bigger, Fraction smaller)
  {
    System.out.println (bigger.toString()+" "+smaller.toString());
    if (Fraction.evaluate (smaller)==0)return;
    double n=3;
     double val=1/n;
    while (val>Fraction.evaluate(smaller)||usedVal.contains((int)n))
    {
      n++;
      val=1/n;
    }
    usedVal.add((int)n);
   // else System.out.println (n);
    recurse(Fraction.biggerOne(smaller.sub(new Fraction ((int)n)),new Fraction((int)n+1)),Fraction.smallerOne(smaller.sub(new Fraction ((int)n)),new Fraction((int)n+1)));
    recurse(Fraction.biggerOne(smaller,bigger.sub(new Fraction((int)n+1))),Fraction.smallerOne(smaller,bigger.sub(new Fraction((int)n+1))));
  }
}