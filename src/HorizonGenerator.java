
public class HorizonGenerator extends PointGenerator{
	public HorizonGenerator(Point2D begin, Point2D end, boolean smooth, boolean mapping){
		super(begin, end, smooth, mapping);
		
		incre_x = end.x - begin.x > 0 ? 1 : -1;
		
		du = (end.u - begin.u)/dx;
		dv = (end.v - begin.v)/dx;
		
		r_length = dr / dx;
		g_length = dg / dx;
		b_length = db / dx;
		
	}

	@Override
	public boolean hasNext(){
		return currentPoint.x != end.x;
	}
	
	@Override
	public Point2D getNext(){
		
		if(mapping){
			
			currentPoint.u = begin.u + dist(currentPoint.x, currentPoint.y, begin.x, begin.y) * du;
			currentPoint.v = begin.v + dist(currentPoint.x, currentPoint.y, begin.x, begin.y) * dv;
			
		}
		else if(smooth){
			
			int[] colors = readColor(currentPoint);
			
			rr += (dr - r_length * dx);
			gr += (dg - g_length * dx);
			br += (db - b_length * dx);
			
			int R = colors[0] + incre_r * r_length;
			int G = colors[1] + incre_g * g_length;
			int B = colors[2] + incre_b * b_length;
			
			if(rr*2 >= dx){
				R += incre_r;
				rr -= dx;
			}
			
			if(gr*2 >= dx){
				G += incre_g;
				gr -= dx;
			}
			
			if(br*2 >= dx){
				B += incre_b;
				br -= dx;
			}
			
			currentPoint.c.r = (R+0.0f)/255;
			currentPoint.c.g = (G+0.0f)/255;
			currentPoint.c.b = (B+0.0f)/255;
		}
		
		currentPoint.x += incre_x;
		
		return currentPoint;
	}
}
