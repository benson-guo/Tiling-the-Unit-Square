import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

//In this algorithm, rectangles are represented with rectangle2D, and the outer eddges of the tilings points are saved, the last ten configurations are saved

public class RotationalAlgorithm 
{
  static int current=0;
  static double squareLength=800.0;
  static double squareFactor=3.0/3.0;
  static double sizex=squareLength*squareFactor;
  //static double sizey=squareLength*0.75;
  static double sizey=squareLength*1.0/squareFactor;
  static ArrayList<LineComponent> configurations=new ArrayList<LineComponent>();
  static JTextField displayTile=new JTextField (10);
  static JFrame frame = new JFrame();
  static Font font = new Font("Verdana", Font.BOLD, 22);
  static JPanel p=new JPanel();
  static long counter=0;
  
  public static void main (String[]args)
  {
    //intialize
    // configurations.add(new LineComponent((int)sizex,(int)sizey));
    configurations.add(new LineComponent((int)sizex,(int)sizey));
    frame.add(configurations.get(current), BorderLayout.WEST);
    frame.add (p,BorderLayout.NORTH);
    frame.setSize((int)sizex+300,(int)sizey+200);
    frame.setVisible(true);
    
    displayTile.setFont(font);
    displayTile.setText("Tile: "+configurations.get(current).tilesAdded);
    
    JButton addFlatTile=new JButton ("Add Flat");
    addFlatTile.setVisible(true);
    addFlatTile.addActionListener(new AddFlat());
    JButton addTallTile=new JButton ("Add Tall");
    addTallTile.setVisible(true);
    addTallTile.addActionListener(new AddTall());
    JButton goBack=new JButton ("Go Back");
    goBack.setVisible(true);
    goBack.addActionListener(new GoBack());
    
    p.add(displayTile,BorderLayout.NORTH);
    p.add(addTallTile,BorderLayout.WEST);
    p.add(addFlatTile,BorderLayout.CENTER);
    p.add(goBack,BorderLayout.EAST);
    
    frame.repaint();
    frame.revalidate();
    recurseAll(configurations.get(0));
    //recurseDouble(configurations.get(0));
    //pause(1000);
    //recurse(configurations.get(0));
    //recurseVar(configurations.get(0));
    //new LineComponent((int)size,(int)size)
    System.out.println (counter);
  }
  
  //recurse all
  public static boolean addAll(LineComponent data,double tileX,double tileY, int point)
  {
    tileX*=squareLength;
    tileY*=squareLength;
    double x1=0;
    double x2=0;
    double y1=0;
    double y2=0;
    double minX=5000;
    double minY=5000;
    int index=0;
    boolean space=false;
    boolean matchX=false;
    boolean matchY=false;
    
      x1=data.points.get(point-1).getX();
      y1=data.points.get(point-1).getY();
      x2=data.points.get(point).getX();
      y2=data.points.get(point).getY();
      if ((x2-x1)>=tileX&&(y2-y1)>=tileY&&minX>=(x2-x1)&&minY>=(y2-y1))
      {
        space=true;
        if ((x2-x1)==tileX)
        {
          matchX=true;
        }
        if (round(y2-y1)==round(tileY))
        {
          matchY=true;
        }
        index=point-1;
        if (matchX||matchY)
        minX=x2-x1;
        minY= y2-y1;
      }

    if (!space) 
    {
     // System.out.println ("no space");
      return false;
    }
    else 
    {
      data.tilesAdded++;
      data.addRect(data.points.get(index).getX(),data.points.get(index+1).getY()-tileY,tileX,tileY);
      if (matchX&&!matchY)
      {
        //System.out.println ("x, not y");
        data.points.get(index+1).setLocation(data.points.get(index+1).getX(),data.points.get(index+1).getY()-tileY);
      }
      else if (!matchX&&matchY)
      {
        //System.out.println ("y, not x");
        data.points.get(index).setLocation(data.points.get(index).getX()+tileX,data.points.get(index).getY());
      }
      else if (matchX&&matchY)
      {
        //System.out.println ("x and y");
        data.removePoint(index);
        data.points.get(index).setLocation(data.points.get(index).getX(),data.points.get(index).getY()-tileY);
      }
      else
      {
        //System.out.println ("not x, not y");
        data.addPoint(data.points.get(index).getX()+tileX,data.points.get(index+1).getY()-tileY,index+1);
      }
      displayTile.setText("Tile: "+data.tilesAdded);
      return true;
    }
  }
  
  //adds a tile and updates points & rectangles
  public static boolean add(LineComponent data,double tileX,double tileY)
  {
    tileX*=squareLength;
    tileY*=squareLength;
    double x1=0;
    double x2=0;
    double y1=0;
    double y2=0;
    double minX=5000;
    double minY=5000;
    int index=0;
    boolean space=false;
    boolean matchX=false;
    boolean matchY=false;
    
    for (int x=1;x<data.points.size();x++)
    {
      x1=data.points.get(x-1).getX();
      y1=data.points.get(x-1).getY();
      x2=data.points.get(x).getX();
      y2=data.points.get(x).getY();
      if ((x2-x1)>=tileX&&(y2-y1)>=tileY&&minX>=(x2-x1)&&minY>=(y2-y1))
      {
        space=true;
        if ((x2-x1)==tileX)
        {
          matchX=true;
        }
        if (round(y2-y1)==round(tileY))
        {
          matchY=true;
        }
        index=x-1;
        if (matchX||matchY)break;
        minX=x2-x1;
        minY= y2-y1;
      }
    }
    if (!space) 
    {
      System.out.println ("no space");
      return false;
    }
    else 
    {
      data.tilesAdded++;
      data.addRect(data.points.get(index).getX(),data.points.get(index+1).getY()-tileY,tileX,tileY);
      if (matchX&&!matchY)
      {
        //System.out.println ("x, not y");
        data.points.get(index+1).setLocation(data.points.get(index+1).getX(),data.points.get(index+1).getY()-tileY);
      }
      else if (!matchX&&matchY)
      {
        //System.out.println ("y, not x");
        data.points.get(index).setLocation(data.points.get(index).getX()+tileX,data.points.get(index).getY());
      }
      else if (matchX&&matchY)
      {
        //System.out.println ("x and y");
        data.removePoint(index);
        data.points.get(index).setLocation(data.points.get(index).getX(),data.points.get(index).getY()-tileY);
      }
      else
      {
        //System.out.println ("not x, not y");
        data.addPoint(data.points.get(index).getX()+tileX,data.points.get(index+1).getY()-tileY,index+1);
      }
      displayTile.setText("Tile: "+data.tilesAdded);
      return true;
    }
  }
  
  //adds a tile and updates points & rectangles ++ double splitting features
  public static boolean addVar(LineComponent data,double tileX,double tileY)
  {
    tileX*=squareLength;
    tileY*=squareLength;
    double x1=0;
    double x2=0;
    double y1=0;
    double y2=0;
    double cDev=0;
    double deviation=3000;
    double midX=0;
    double midY=0;
    int index=-1;
    boolean space=false;
    boolean matchX=false;
    boolean matchY=false;
    boolean skip=false;
    
    //first spot
    x1=data.points.get(0).getX();
    y1=data.points.get(0).getY();
    x2=data.points.get(1).getX();
    y2=data.points.get(1).getY();
    if (round(x2-x1)>=round(tileX)&&round(y2-y1)>=round(tileY))space=true;
    index=0;
    if (round(x2-x1)==round(tileX))
    {
      matchX=true;
    }
    if (round(y2-y1)==round(tileY))
    {
      matchY=true;
    }
    if (matchY||(matchX&&matchY))
    {
      index=0;
      skip=true;
      space=true;
    }
    //last spot
//    x1=data.points.get(data.points.size()-2).getX();
//    y1=data.points.get(data.points.size()-2).getY();
//    x2=data.points.get(data.points.size()-1).getX();
//    y2=data.points.get(data.points.size()-1).getY();
//    if ((x2-x1)>=tileX&&(y2-y1)>=tileY)
//    {
//      if (index==-1)
//        index=data.points.size()-2; 
//      if ((x2-x1)==tileX)
//      {
//        matchX=true;
//      }
//      if ((y2-y1)==tileY)
//      {
//        matchY=true;
//      }
//      if (matchX||(matchX&&matchY))
//      {
//        index=data.points.size()-2; 
//        skip=true;
//      }
//    }
    if(!skip)
    {
      for (int x=1;x<data.points.size();x++)
      {
        x1=data.points.get(x-1).getX();
        y1=data.points.get(x-1).getY();
        x2=data.points.get(x).getX();
        y2=data.points.get(x).getY();
        if ((x2-x1)>=tileX&&(y2-y1)>=tileY)
        {
          matchX=false;
          matchY=false;
          space=true;
          midX=(x2-x1)/2.0;
          midY=(y2-y1)/2.0;
          cDev=Math.abs(midX-tileX)+Math.abs(midY-tileY);
          if (round(x2-x1)==round(tileX))
          {
            matchX=true;
          }
          if (round(y2-y1)==round(tileY))
          {
            matchY=true;
          }
          if (cDev<deviation)
          {
            index=x-1;
            deviation=cDev;
          }
        }
      }
    }
    if (!space) 
    {
      System.out.println ("no space");
      return false;
    }
    else 
    {
      data.tilesAdded++;
      data.addRect(data.points.get(index).getX(),data.points.get(index+1).getY()-tileY,tileX,tileY);
      if (matchX&&!matchY)
      {
        //System.out.println ("x, not y");
        data.points.get(index+1).setLocation(data.points.get(index+1).getX(),data.points.get(index+1).getY()-tileY);
      }
      else if (!matchX&&matchY)
      {
        //System.out.println ("y, not x");
        data.points.get(index).setLocation(data.points.get(index).getX()+tileX,data.points.get(index).getY());
      }
      else if (matchX&&matchY)
      {
        //System.out.println ("x and y");
        data.removePoint(index);
        data.points.get(index).setLocation(data.points.get(index).getX(),data.points.get(index).getY()-tileY);
      }
      else
      {
        //System.out.println ("not x, not y");
        data.addPoint(data.points.get(index).getX()+tileX,data.points.get(index+1).getY()-tileY,index+1);
      }
      displayTile.setText("Tile: "+data.tilesAdded);
      return true;
    }
  }
  
  public static void printData(LineComponent data)
  {
    for (int x=0;x<data.points.size();x++) 
    {
      System.out.println (data.points.get(x).toString());
    }
    for (int x=0;x<data.rectangles.size();x++) 
    {
      System.out.println (data.rectangles.get(x).toString());
    }
  }
  
  public static void refreshScreen()
  {
    frame.repaint();
    frame.validate();
  }
  
  public static void recurseAll(LineComponent data)
  {
    frame.add(data,BorderLayout.WEST);
    refreshScreen();
    pause (250 );
    frame.remove(data);
    //flat
    for (int x=1;x<data.points.size();x++)
    {
      LineComponent dataCopy=new LineComponent((int)sizex,(int)sizey,data); 
      boolean flat=addAll (dataCopy,1.0/(double)(dataCopy.tilesAdded+1),1.0/(double)(dataCopy.tilesAdded+2),x);
      counter++;
      if (flat)
        recurseAll(dataCopy);
    }
    //tall
    for (int x=1;x<data.points.size();x++)
    {
      LineComponent dataCopy=new LineComponent((int)sizex,(int)sizey,data); 
      boolean tall=addAll (dataCopy,1.0/(double)(dataCopy.tilesAdded+2),1.0/(double)(dataCopy.tilesAdded+1),x);
      counter++;
      if (tall)
        recurseAll(dataCopy);
    }
  }
  
  public static void recurse(LineComponent data)
  {
    frame.add(data,BorderLayout.WEST);
    refreshScreen();
    LineComponent dataCopy=new LineComponent((int)sizex,(int)sizey,data); 
    pause (15);
    //flat
    boolean flat=add (data,1.0/(double)(data.tilesAdded+1),1.0/(double)(data.tilesAdded+2));
    //tall
    boolean tall=add (dataCopy,1.0/(double)(dataCopy.tilesAdded+2),1.0/(double)(dataCopy.tilesAdded+1));
    counter+=2;
    frame.remove(data);
    if (flat)
      recurse(data);
    if (tall)
      recurse(dataCopy);
  }
  
    public static void recurseDouble(LineComponent data)
  {
    frame.add(data,BorderLayout.WEST);
    refreshScreen();
    LineComponent dataCopy=new LineComponent((int)sizex,(int)sizey,data); 
    pause (15);
    //flat
    boolean flat=add (data,1.0/(double)(data.tilesAdded/2+1),1.0/(double)(data.tilesAdded/2+2));
    //tall
    boolean tall=add (dataCopy,1.0/(double)(dataCopy.tilesAdded+2),1.0/(double)(dataCopy.tilesAdded+1));
    counter+=2;
    frame.remove(data);
    if (flat)
      recurseDouble(data);
    if (tall)
      recurseDouble(dataCopy);
  }
  
  public static void recurseVar(LineComponent data)
  {
    frame.add(data,BorderLayout.WEST);
    refreshScreen();
    LineComponent dataCopy=new LineComponent((int)sizex,(int)sizey,data); 
    pause (100);
    //flat
    boolean flat=addVar (data,1.0/(double)(data.tilesAdded+1),1.0/(double)(data.tilesAdded+2));
    //tall
    boolean tall=addVar (dataCopy,1.0/(double)(dataCopy.tilesAdded+2),1.0/(double)(dataCopy.tilesAdded+1));
    frame.remove(data);
    if (flat)
      recurseVar(data);
    if (tall)
      recurseVar(dataCopy);
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
  
  //rounds to .00001
  public static double round (double d)
  {
    d=(double)((int)(d*100000)/1)/100000.0;
    return d;
  }
  
  //adds a tile long side down
  static class AddFlat implements ActionListener
  {
    public void actionPerformed (ActionEvent e) 
    {
      configurations.add(new LineComponent((int)sizex,(int)sizey,configurations.get(current)));
      int num=configurations.get(current).tilesAdded+1;
      if(add(configurations.get(current+1),1.0/num,1.0/(num+1)))
      {
        current++;
        frame.remove(configurations.get(current-1));
        frame.add(configurations.get(current),BorderLayout.WEST);
        refreshScreen();
        if (current>10)
        {
          current--;
          configurations.remove(0);
        }
      }
      else
      {
        configurations.remove(configurations.size()-1);
      }
      // System.out.println (current);
    }
  }
  
  //adds a tile long side up
  static class AddTall implements ActionListener
  {
    public void actionPerformed (ActionEvent e) 
    {
      configurations.add(new LineComponent((int)sizex,(int)sizey,configurations.get(current)));
      int num=configurations.get(current).tilesAdded+1;
      if (add(configurations.get(current+1),1.0/(num+1),1.0/num))
      {
        current++;
        frame.remove(configurations.get(current-1));
        frame.add(configurations.get(current),BorderLayout.WEST);
        refreshScreen();
        if (current>10)
        {
          current--;
          configurations.remove(0);
        }
      }
      else
      {
        configurations.remove(configurations.size()-1);
      }
      //  System.out.println (current);
    }
  }
  
  //goes back a saved configuration
  static class GoBack implements ActionListener
  {
    public void actionPerformed (ActionEvent e) 
    {
      printData(configurations.get(current));
      if(current>0)
      {
        frame.remove(configurations.get(current));
        configurations.remove(current);
        current--;
        frame.add(configurations.get(current),BorderLayout.WEST);
        refreshScreen();
      }
    }
  }
}