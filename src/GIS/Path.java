package GIS;

import java.util.LinkedList;

public class Path extends LinkedList<Fruit>
{
    public Path getCopyPath()
    {
        Path new_path = new Path();
        for(int i = 0; i < this.size(); i++)
        {
            new_path.add(new Fruit(this.get(i).getPosition() , this.get(i).getId(),this.get(i).getWeight()));
        }
        return new_path;
    }
}
