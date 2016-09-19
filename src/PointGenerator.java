import java.awt.image.BufferedImage;

public class PointGenerator {

	protected Point2D begin;
	protected Point2D end;
	protected Point2D currentPoint;
	
	protected boolean mapping;
	protected boolean smooth;
	
	protected int dx, dy;
	protected float slope;
	
	protected int incre_r, incre_g, incre_b;
	
	protected int incre_x, incre_y;
	protected float du, dv;
	
	protected int dr, dg, db;
	
	protected int r_length, g_length, b_length;
	protected int rr, gr, br;
	protected int p, twoDy, twoDyMinusDx;
	
	protected BufferedImage texture;
	
	
	public PointGenerator(Point2D begin, Point2D end, boolean smooth, boolean mapping){
		this.begin = begin;
		this.end = end;
		this.mapping = mapping;
		this.smooth = smooth;
		//this.texture = texture;
		
		dx = Math.abs(end.x - begin.x);
		dy = Math.abs(end.y - begin.y);
		
		if(dx == 0){
			slope = -1;
		}
		else{
			slope = (float)(dy+0.0f)/dx;
		}
		
		incre_x = end.x - begin.x > 0 ? 1 : -1;
		incre_y = end.y - begin.y > 0 ? 1 : -1;
		
		incre_r = end.c.r - begin.c.r > 0 ? 1 : -1;
		incre_g = end.c.g - begin.c.g > 0 ? 1 : -1;
		incre_b = end.c.b - begin.c.b > 0 ? 1 : -1;
		
		int r0 = (begin.c.getBRGUint8() >> 16) & 0xff, r1 = (end.c.getBRGUint8() >> 16) & 0xff;
		int g0 = (begin.c.getBRGUint8() >> 8) & 0xff, g1 = (end.c.getBRGUint8() >> 8) & 0xff;
		int b0 = (begin.c.getBRGUint8() & 0xff), b1 = (end.c.getBRGUint8() & 0xff);
		
		dr = Math.abs(r0 - r1);
		dg = Math.abs(g0 - g1);
		db = Math.abs(b0 - b1);
		

		
		currentPoint = new Point2D(begin);
		
	}
	
	public boolean hasNext(){
		return false;
	}
	
	public Point2D getNext(){
		return null;
	}

	
	public int[] readColor(Point2D p){
		
		int[] result = {0,0,0};
		
		result[0] = (p.c.getBRGUint8() >> 16) & 0xff;
		result[1] = (p.c.getBRGUint8() >> 8) & 0xff;
		result[2] = (p.c.getBRGUint8()) & 0xff;
		return result;
	}
	
	
	protected float dist(int x0, int y0, int x1, int y1){
		
		int v1 = x0 - x1;
		int v2 = y0 - y1;
		return (float)Math.sqrt(v1*v1 + v2*v2);
		
	}

	
}
