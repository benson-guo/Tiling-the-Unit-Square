import java.util.*;
import java.awt.*;
import javax.swing.*;

public class TilingSquare 
{
  static double size=1200;
  static LineComponent lineComponent = new LineComponent((int)size,(int)size);
  public static ArrayList<Edge> rectangle;
  static JFrame frame = new JFrame();
  static int recAdded=0;
  static double cX=45;
  static double cY=25;
  static double nX=0;
  static double nY=0;
  //0123 NESW
  static double cD=1;
  static boolean flag=false;
  static int [] ranOut=new int[1500];
  static boolean flat[]=new boolean [1200];
  
  public static void main (String []args)
  {
    initialize();
    Arrays.fill(flat,true);
    frame.add(lineComponent, BorderLayout.CENTER);
    frame.setSize(1500,1500);
    frame.setVisible(true);
//    while (true)
//    {
//      System.out.println (recAdded);
//      if (rectangle.size()==4)
//      {
//        if (!addFour (1.0/(double)(recAdded+2),1.0/(double)(recAdded+1),rectangle))
//        {
//          if (!addFour (1.0/(double)(recAdded+1),1.0/(double)(recAdded+2),rectangle))
//            break;
//        }
//      }
//      else
//      {
//        if (!add (1.0/(double)(recAdded+1),1.0/(double)(recAdded+2),rectangle))
//        {
//          if (!add (1.0/(double)(recAdded+2),1.0/(double)(recAdded+1),rectangle))
//            break;
//        }
//      }
//      drawTile();
//    pause(1000);
//    }
    recurse (0,rectangle,true);
    for (int x=0;x<1000;x++)
    {
      System.out.println (x+" Recs: "+ranOut[x]);
    }
    for (int x=0;x<1000;x++)
    {
      System.out.println (x+" Flat: "+flat[x]);
    }
  }
  
  public static void recurse (int rec,ArrayList<Edge> config,boolean tallShort)
  {
//        if (rec>500)
//        {
//          int recAdded=rec;
//              while (true)
//    {
//      System.out.println (recAdded);
//          if (add (1.0/(double)(recAdded+2),1.0/(double)(recAdded+1),config).size()==0)
//            break;
//      drawTile(config);
//    recAdded++;
//    }
//return;
//        }
    
   // pause(500);
    if (flag)return;
    if (rec>=1100)flag=true;
    drawTile(config);
    System.out.println (rec);
    ArrayList<Edge> copy = new ArrayList<Edge>(); 
    Iterator<Edge> iterator = config.iterator();
    while(iterator.hasNext())
    { 
      copy.add(iterator.next().clone()); 
    }
    //width bottom
    if (tallShort)
    {
      if (config.size()==4)
      {
        if(addFour (1.0/(double)(rec+1),1.0/(double)(rec+2),config).size()==0)
        {
          System.out.println ("No more space");
          ranOut[rec]++;
     //     pause(500);
          flat[rec]=false;
          return;
        }
      }
      else
      {
        if(add (1.0/(double)(rec+1),1.0/(double)(rec+2),config).size()==0)
        {
          System.out.println ("No more space");
          ranOut[rec]++;
       //   pause(500);
          flat[rec]=false;
          return;
        }
      }
      flat[rec]=true;
    }
    //length bottom
    else
    {
      if (config.size()==4)
      {
        if(addFour (1.0/(double)(rec+2),1.0/(double)(rec+1),config).size()==0)
        {
          System.out.println ("No more space");
          ranOut[rec]++;
         // pause(500);
          return;
        }
      }
      else
      {
        if(add (1.0/(double)(rec+2),1.0/(double)(rec+1), config).size()==0)
        {
          System.out.println ("No more space");
          ranOut[rec]++;
      //    pause(500);
          return;
        }
        flat[rec]=true;
      }
    }
   // pause(100);
    System.out.println (" "+perim(config));
    rec++;
    recurse (rec,config,true);
    recurse (rec,copy,false);
  }
  
  public static void initialize()
  {
    rectangle=new ArrayList<Edge>();
    rectangle.add(new Edge('R',1));
    rectangle.add(new Edge('R',1));
    rectangle.add(new Edge('R',1));
    rectangle.add(new Edge('R',1));
  }
  
  public void drawTile(ArrayList<Edge>rectangle)
  {
    LineComponent.clearList();
    lineComponent.addLine(45,25,(int)size+45,25);
    lineComponent.addLine((int)size+45,25,(int)size+45,(int)size+25);
    lineComponent.addLine((int)size+45,(int)size+25,45,(int)size+25);
    lineComponent.addLine(45,(int)size+25,45,25);
    
    cX=45+size-(size*rectangle.get(0).getLen());
    cY=25;
    for (int x=0; x<rectangle.size(); x++) 
    {     
      //sets next coord depending on direction
      if (cD==0)
      {
        nX=cX;
        nY=cY-(size*rectangle.get(x).getLen());
      }
      else if (cD==1)
      {      
        nX=cX+(size*rectangle.get(x).getLen());
        nY=cY;
      }
      else if (cD==2)
      {
        nX=cX;
        nY=cY+(size*rectangle.get(x).getLen());
      }
      else
      {
        nX=cX-(size*rectangle.get(x).getLen());
        nY=cY;
      }
      
      lineComponent.addLine(cX,cY,nX,nY);
      
      //sets next direction and co-ordinate
      if (rectangle.get(x).getDir()=='R')
        cD=(cD+1)%4;
      else
        cD=(cD-1)%4;
      cX=nX;
      cY=nY;
    }  
    frame.repaint();
    frame.revalidate();
  }
  
  
  //add rectangle with width on bottom to rectangle 
  public static ArrayList<Edge> addFour (double l, double w, ArrayList<Edge>rectangle)
  {
    if (l>rectangle.get(1).getLen()||w>rectangle.get(0).getLen())return new ArrayList<Edge>();
    else
    {
      rectangle.get(2).setLen(rectangle.get(2).getLen()-w);
      rectangle.get(3).setLen(rectangle.get(3).getLen()-l);
      rectangle.add(3,new Edge ('L',l));
      rectangle.add(4,new Edge ('R',w));
    }
    if (rectangle.get(2).getLen()==0&&rectangle.get(5).getLen()==0)
    {
      return new ArrayList<Edge>();
    }
    else if (rectangle.get(2).getLen()==0)
    {
      rectangle.get(1).setLen(rectangle.get(1).getLen()-l);
      rectangle.remove (2);
      rectangle.remove (2);
    }
    else if (rectangle.get(5).getLen()==0)
    {
      rectangle.get(0).setLen(rectangle.get(0).getLen()-w);
      rectangle.get(3).setDir('R');
      rectangle.remove (4);
      rectangle.remove (4);
    }
    recAdded++;
    return rectangle;
  }
  
  //add rectangle with w on bottom to bottom left corner of smallest space
  public static ArrayList<Edge> add (double l, double w, ArrayList<Edge> rectangle)
  {
    //index of insertion location && length and width of index
    int index=-1;
    //length refers to horizontal, width vertical
    double len=rectangle.get(0).getLen();
    double wid=rectangle.get(1).getLen();
    
    for (int x=2;x<rectangle.size();x+=2)
    {
      if (rectangle.get(x).getLen()>=w&&rectangle.get(x+1).getLen()>=l)
      {
        if (rectangle.get(x).getLen()<=len&&rectangle.get(x+1).getLen()<=wid)
        {
          index=x;
          len=rectangle.get(x).getLen();
          wid=rectangle.get(x+1).getLen();
        }
      }
    }   
    if (index==-1) return new ArrayList<Edge>();
    else
    {
      rectangle.get(index).setLen(rectangle.get(index).getLen()-w);
      rectangle.get(index+1).setLen(rectangle.get(index+1).getLen()-l);
      rectangle.add(index+1,new Edge ('L',l));
      rectangle.add(index+2,new Edge ('R',w));
    }
    //left side
    if (index==rectangle.size()-4)
    {
      if (rectangle.get(index).getLen()==0&&rectangle.get(index+3).getLen()==0)
      {
        rectangle.get(index-1).setLen(rectangle.get(index-1).getLen()+l);
        rectangle.get(0).setLen(rectangle.get(0).getLen()-w);
        rectangle.get(index-1).setDir('R');
        rectangle.remove (index);
        rectangle.remove (index);
        rectangle.remove (index);
        rectangle.remove (index);
      }
      else if (rectangle.get(index).getLen()==0)
      {
        rectangle.get(index-1).setLen(rectangle.get(index-1).getLen()+l);
        rectangle.remove (index);
        rectangle.remove (index);
      }
      else if (rectangle.get(index+3).getLen()==0)
      {
        rectangle.get(0).setLen(rectangle.get(0).getLen()-w);
        rectangle.get(index+1).setDir('R');
        rectangle.remove (index+2);
        rectangle.remove (index+2);
      }
    }
    //right
    else if (index==2)
    {
      if (rectangle.get(index).getLen()==0&&rectangle.get(index+3).getLen()==0)
      {
        rectangle.get(index-1).setLen(rectangle.get(index-1).getLen()-l);
        rectangle.get(index+4).setLen(rectangle.get(index+4).getLen()+w);
        rectangle.remove (index);
        rectangle.remove (index);
        rectangle.remove (index);
        rectangle.remove (index);
      }
      else if (rectangle.get(index).getLen()==0)
      {
        rectangle.get(index-1).setLen(rectangle.get(index-1).getLen()-l);
        rectangle.remove (index);
        rectangle.remove (index);
      }
      else if (rectangle.get(index+3).getLen()==0)
      {
        rectangle.get(index+4).setLen(rectangle.get(index+4).getLen()+w);
        rectangle.remove (index+2);
        rectangle.remove (index+2);
      }
    }
    //middle
    else 
    {
      if (rectangle.get(index).getLen()==0&&rectangle.get(index+3).getLen()==0)
      {
        rectangle.get(index-1).setLen(rectangle.get(index-1).getLen()+l);
        rectangle.get(index+4).setLen(rectangle.get(index+4).getLen()+w);
        rectangle.remove (index);
        rectangle.remove (index);
        rectangle.remove (index);
        rectangle.remove (index);
      }
      else if (rectangle.get(index).getLen()==0)
      {
        rectangle.get(index-1).setLen(rectangle.get(index-1).getLen()+l);
        rectangle.remove (index);
        rectangle.remove (index);
      }
      else if (rectangle.get(index+3).getLen()==0)
      {
        rectangle.get(index+4).setLen(rectangle.get(index+4).getLen()+w);
        rectangle.remove (index+2);
        rectangle.remove (index+2);
      }
    }
    recAdded++;
    return rectangle;
  }
  
  public static double perim (ArrayList<Edge> rectangle)
  {
    double p=0;
    for (int x=0;x<rectangle.size();x++)
    {
      p+=rectangle.get(x).getLen();
    }
    return p;
  }
  
  public static void pause (int time)
  {
          try 
      {
        Thread.sleep(time);
      } 
      catch (InterruptedException e) 
      {
      }
  }
  
  public static void printList()
  {
    for (int x=0;x<rectangle.size();x++)
    {
      rectangle.get(x).printEdge();
    }
  }
}