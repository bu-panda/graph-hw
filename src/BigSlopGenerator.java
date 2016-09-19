
public class BigSlopGenerator extends PointGenerator{

	public BigSlopGenerator(Point2D begin, Point2D end, boolean smooth, boolean mapping) {
		super(begin, end, smooth, mapping);
		
		du = (float) ((end.u - begin.u)/(Math.sqrt(dx*dx + dy*dy)));
		dv = (float) ((end.v - begin.v)/(Math.sqrt(dx*dx + dy*dy)));
		
		twoDy = 2 * dx;
		twoDyMinusDx = 2 * (dx - dy);
		p = 2*dx - dy;
		
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
		
		currentPoint.y += incre_y;
		if(p < 0){
			p += twoDy;
		}
		else{
			currentPoint.x += incre_x;
			p += twoDyMinusDx;
		}
		
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
		
		return currentPoint;
	}
	
}
