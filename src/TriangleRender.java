import java.awt.image.BufferedImage;
import java.util.Arrays;

public class TriangleRender {

	private PointGenerator generatorLeft;
	private PointGenerator generatorRight;
	private BufferedImage buff;
	private BufferedImage texture;
	
	public TriangleRender(BufferedImage buff, BufferedImage texture){
		
		this.buff = buff;
		this.texture = texture;
	}
	
	public void renderTriangle(Point2D p1, Point2D p2, Point2D p3, boolean smooth, boolean mapping){
		
		Point2D [] array = new Point2D[] {p1,p2,p3};
		Arrays.sort(array);
		
		drawTriangle(array[0], array[1], array[2], 1, smooth, mapping);
		drawTriangle(array[0], array[1], array[2], -1, smooth, mapping);
		
	}

	private void drawTriangle(Point2D top, Point2D middle, Point2D bottom, int incre, boolean smooth, boolean mapping) {
		// TODO Auto-generated method stub
		int baseline = incre == 1 ? top.y : bottom.y;
		
		if(incre == 1){
			generatorLeft = PointGeneratorFactory.createGenerator(top, middle, smooth, mapping);
			generatorRight = PointGeneratorFactory.createGenerator(top, bottom, smooth, mapping);
		}
		else{
			generatorLeft = PointGeneratorFactory.createGenerator(bottom, middle, smooth, mapping);
			generatorRight = PointGeneratorFactory.createGenerator(bottom, top, smooth, mapping);
		}
		Point2D left = null, right = null;
		while(baseline != middle.y){
			
			while(generatorLeft.hasNext()){
				left = generatorLeft.getNext();
				
				if(!mapping){
					SketchBase.drawPoint(buff, left);
				}
				else{
					SketchBase.drawPoint(buff, left, SketchBase.readTextureColor(left, texture));
				}
				if(left.y == baseline+incre){
					break;
				}
			}
			
			while(generatorRight.hasNext()){
				right = generatorRight.getNext();
				if(!mapping){
					SketchBase.drawPoint(buff, right);
				}
				else{
					SketchBase.drawPoint(buff, right, SketchBase.readTextureColor(right, texture));
				}
				if(right.y == baseline+incre){
					break;
				}
			}
			
			SketchBase.drawLine(buff, left, right, mapping, texture);
			baseline += incre;
			
		}
	}
	
}
