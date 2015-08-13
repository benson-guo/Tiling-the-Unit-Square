import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;


import java.util.ArrayList;

class LineComponent extends JComponent 
{
  static Font font = new Font("Verdana", Font.BOLD, 13);
  ArrayList<Line2D.Double> lines;
  ArrayList<Point2D.Double> points;
  ArrayList<Rectangle2D.Double>rectangles;
  int tilesAdded;
  
  LineComponent(int width, int height) 
  {
    super();
    setPreferredSize(new Dimension(width+25,height+25));
    lines = new ArrayList<Line2D.Double>();
    points = new ArrayList<Point2D.Double>();
    rectangles=new ArrayList<Rectangle2D.Double>();
    addPoint (25,25);
    addPoint (25+(int)RotationalAlgorithm.sizex,25+(int)RotationalAlgorithm.sizey);
    //addRect(25,25,(int)RotationalAlgorithm.size,(int)RotationalAlgorithm.size);
    tilesAdded=0;
  }
  
  LineComponent(int width, int height, LineComponent config) 
  {
    super();
    this.tilesAdded=config.tilesAdded;
    setPreferredSize(new Dimension(width+25,height+25));
    this.points = new ArrayList<Point2D.Double>();
    this.rectangles=new ArrayList<Rectangle2D.Double>();
    //System.out.println (config.points.size()+" "+config.rectangles.size());
    for (int x=0;x<config.points.size();x++)
    {
      this.points.add(new Point2D.Double(config.points.get(x).getX(),config.points.get(x).getY()));
    }
    for (int x=0;x<config.rectangles.size();x++)
    {
      this.rectangles.add(new Rectangle2D.Double(config.rectangles.get(x).getX(),config.rectangles.get(x).getY(),config.rectangles.get(x).getWidth(),config.rectangles.get(x).getHeight()));
    }
  }
  
  public void clearList() 
  {
    lines=new ArrayList<Line2D.Double>();
  }
  
  public void addLine(double x1,double y1, double x2, double y2) 
  {
    Line2D.Double line = new Line2D.Double(x1,y1,x2,y2);
    lines.add(line);
    repaint();
  }
  
  public void addPoint(double x,double y) 
  {
    Point2D.Double point = new Point2D.Double(x,y);
    points.add(point);
    repaint();
  }
  
  public void addPoint(double x,double y, int position) 
  {
    Point2D.Double point = new Point2D.Double(x,y);
    points.add(position,point);
    repaint();
  }
  
  public void removePoint(int position)
  {
    points.remove(position);
  }
  
  public void addRect(double x,double y, double w, double h) 
  {
    Rectangle2D.Double rect = new Rectangle2D.Double(x,y,w,h);
    rectangles.add(rect);
    repaint();
  }
  
  public void drawLines(Graphics g)
  {
    for (int x=0;x<lines.size();x++) 
    {
      if (x==4)
        g.setColor(Color.black);  
      g.drawLine(
                 (int)lines.get(x).getX1(),
                 (int)lines.get(x).getY1(),
                 (int)lines.get(x).getX2(),
                 (int)lines.get(x).getY2()
                );
    }
  }
  
  public void paintRec(Graphics g)
  {
    for (int x=0;x<lines.size();x++) 
    {
      if (x==4)continue;
      if (x<4)
      {
        g.drawLine(
                   (int)lines.get(x).getX1(),
                   (int)lines.get(x).getY1(),
                   (int)lines.get(x).getX2(),
                   (int)lines.get(x).getY2()
                  );
      }
      else 
      {
        //g.fillRect(45.0,lines.get(x).getY2(), lines.get(x).getX2()-45, TilingSquare.size+25-lines.get(x).getY2());
        //System.out.println ((int)lines.get(x).getY2()+" "+((int)lines.get(x).getX1()-(int)lines.get(x).getX2())+" "+((int)lines.get(x).getY1()-(int)lines.get(x).getY2()));
        x++;
      }
    }
  }
  
  public void paintRecPoints(Graphics g)
  {
    int rgb=1;
    Graphics2D g2=(Graphics2D)g;
    g2.setColor (new Color (rgb,rgb,rgb));
    g2.setFont(font);
    //System.out.println (rectangles.size());
    for (int x=0;x<rectangles.size();x++) 
    {
      // System.out.println (rectangles.get(x).toString());
      g2.fill(rectangles.get(x));
      rgb+=25;
      rgb=rgb%255;
      g2.setColor (new Color (rgb,255-rgb,rgb/2+50));
      g2.drawString(""+(x+1),(int)(rectangles.get(x).getX()+rectangles.get(x).getWidth()/2.0),(int)(rectangles.get(x).getY()+rectangles.get(x).getHeight()/2.0));
    }
  }
  
  public void paintComponent(Graphics g) 
  {
    g.setColor(Color.white);
    g.fillRect(25, 25, getWidth(), getHeight());
     // drawLines(g);
    //paintRec(g);
    paintRecPoints(g);
  }
}