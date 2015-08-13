
import java.io.*;
import java.util.*;

public class Test
{
  static int sum=0;
  public static void main (String[] args )
  {
    boolean []arr=new boolean[8];
    Arrays.fill (arr,false);
    recurse(arr,-1);
    System.out.println (sum);
  }
  public static void recurse(boolean[]arr, int pos)
  {
    if (pos>=1)
    {
      if (arr[pos]&&arr[pos-1])
        return;
    }
    if (pos>=3)return;
    if (pos==2)
    {
      for (int x=0;x<3;x++)
        System.out.print (arr[x]);
      System.out.println();
      evaluate(arr);
    }
    arr[pos+1]=true;
    boolean[]newArr=new boolean[arr.length];
    copy(arr,newArr);
    newArr[pos+1]=false;
    recurse (arr,pos+1);
    recurse(newArr,pos+1);
  }
  public static boolean[]copy(boolean[]arr, boolean[]newArr)
  {
    for (int x=0;x<arr.length;x++)
    {
      newArr[x]=arr[x];
    }
    return newArr;
  }
  public static void evaluate(boolean[]arr)
  {
    int t=0;
    for (int x=0;x<3;x++)
    {
      if (arr[x])
      {
        if (t!=0)t*=(x+1);
        else 
          t=x+1;
      }
    }
    sum+=Math.pow(t,2);
    System.out.println (t);
  }
}