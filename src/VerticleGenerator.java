
public class VerticleGenerator extends PointGenerator{
	
	
	public VerticleGenerator(Point2D begin, Point2D end, boolean smooth, boolean mapping){
		super(begin, end, smooth, mapping);
		
		incre_y = end.y - begin.y > 0 ? 1 : -1;
		
		du = (end.u - begin.u)/dy;
		dv = (end.v - begin.v)/dy;
		
		r_length = dr / dy;
		g_length = dg / dy;
		b_length = db / dy;
		
	}

	@Override
	public boolean hasNext(){
		return currentPoint.y != end.y;
	}
	
	@Override
	public Point2D getNext(){
		
		if(mapping){
			
			currentPoint.u = begin.u + dist(currentPoint.x, currentPoint.y, begin.x, begin.y) * du;
			currentPoint.v = begin.v + dist(currentPoint.x, currentPoint.y, begin.x, begin.y) * dv;
			
		}
		else if(smooth){
			
			int[] colors = readColor(currentPoint);
			
			rr += (dr - r_length * dy);
			gr += (dg - g_length * dy);
			br += (db - b_length * dy);
			
			int R = colors[0] + incre_r * r_length;
			int G = colors[1] + incre_g * g_length;
			int B = colors[2] + incre_b * b_length;
			
			if(rr*2 >= dy){
				R += incre_r;
				rr -= dy;
			}
			
			if(gr*2 >= dy){
				G += incre_g;
				gr -= dy;
			}
			
			if(br*2 >= dy){
				B += incre_b;
				br -= dy;
			}
			
			currentPoint.c.r = (R+0.0f)/255;
			currentPoint.c.g = (G+0.0f)/255;
			currentPoint.c.b = (B+0.0f)/255;
		}
		
		currentPoint.y += incre_y;
		
		return currentPoint;
	}
	
}
