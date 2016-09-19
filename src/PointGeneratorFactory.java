
public class PointGeneratorFactory {

	public static PointGenerator createGenerator(Point2D begin, Point2D end, boolean smooth, boolean mapping){
		
		if(begin.x == end.x && begin.y != end.y){
			return new VerticleGenerator(begin, end, smooth, mapping);
		}
		
		if(begin.x != end.x && begin.y == end.y){
			return new HorizonGenerator(begin, end, smooth, mapping);
		}
		
		int dx = Math.abs(end.x - begin.x);
		int dy = Math.abs(end.y - begin.y);
		
		if(dx > dy){
			return new SmallSlopGenerator(begin, end, smooth, mapping);
		}
		else{
			return new BigSlopGenerator(begin, end, smooth, mapping);
		}
	}
	
}
