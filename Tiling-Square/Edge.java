
public class Edge implements Cloneable
{
  private double length;
  private char direction;
  
  Edge(char dir, double len)
  {
    length=len;
    direction=dir;
  }
  
  public void setDir(char newDir)
  {
    direction=newDir;
  }
  
  public void setLen(double newLen)
  {
    length=newLen;
  }
  
  public char getDir()
  {
    return direction;
  }
  
  public double getLen()
  {
    return length;
  }
  
  public void printEdge()
  {
    System.out.print ("("+length+","+direction+")->");
  }
  
  protected Edge clone() 
  {
    Edge clone = null; 
    try
    {
      clone = (Edge) super.clone(); 
    }
    catch(CloneNotSupportedException e)
    { 
      throw new RuntimeException(e); 
    } 
    return clone; 
  }
}