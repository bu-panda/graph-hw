//****************************************************************************
// SketchBase.  
//****************************************************************************
// Comments : 
//   Subroutines to manage and draw points, lines an triangles
//
// History :
//   Aug 2014 Created by Jianming Zhang (jimmie33@gmail.com) based on code by
//   Stan Sclaroff (from CS480 '06 poly.c)

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SketchBase 
{
	public SketchBase()
	{
		// deliberately left blank
	}
	
	
	public static int readTextureColor(Point2D p, BufferedImage texture){
		
		int u, v;
		int [] result = new int[3];
		int [] nearCell = new int[] {0,0,0,0};
		int [] r = {0,0,0,0}, g = {0,0,0,0}, b = {0,0,0,0};
		float lr, rr, lb, rb, lg, rg;
		
		u = (int)Math.floor(p.u * (texture.getWidth()-1));
		v = (int)Math.floor(p.v * (texture.getHeight()-1));
		
		float pu = p.u * (texture.getWidth() - 1);
		float pv = p.v * (texture.getHeight() - 1); 
		
		if(u < texture.getWidth() - 1 && v < texture.getHeight() - 1 && u >= 0 && v >= 0)
		{
			nearCell[0] = texture.getRGB(u, v);
			nearCell[1] = texture.getRGB(u+1, v);
			nearCell[2] = texture.getRGB(u, v+1);
			nearCell[3] = texture.getRGB(u+1, v+1);
			
			readColor(r, nearCell, 16);
			readColor(g, nearCell, 8);
			readColor(b, nearCell, 0);
			
			//Verticle calculation, get the value for two side.
			
			lr = (float)(r[2] - r[0])*(pv - v) + r[0];
			rr = (float)(r[3] - r[1])*(pv - v) + r[1];
			
			lg = (float)(g[2] - g[0])*(pv - v) + g[0];
			rg = (float)(g[3] - g[1])*(pv - v) + g[1];
			
			lb = (float)(b[2] - b[0])*(pv - v) + b[0];
			rb = (float)(b[3] - b[1])*(pv - v) + b[1];
			
			// horizon calculation
			
			
			result[0] = Math.round(((rr - lr)*(pu - u) + lr));
			result[1] = Math.round(((rg - lg)*(pu - u) + lg));
			result[2] = Math.round(((rb - lb)*(pu - u) + lb));
			
		}
		else{
			
			if(u < 0){
				u = 0;
			}
			if(v < 0){
				v = 0;
			}
			
			int color = texture.getRGB(u, v);
			result[0] = (color >> 16) & 0xff;
			result[1] = (color >> 8) & 0xff;
			result[2] = (color) & 0xff;
			
		}
		int rs = result[0] << 16 | result[1] << 8 | result[2];
		return rs;
	}
	
	
	private static void readColor(int[] r, int[] nearCell, int d) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < 4; i++){
			r[i] = (nearCell[i] >> d) & 0xff;
		}
	}


	// draw a point
	public static void drawPoint(BufferedImage buff, Point2D p)
	{
		buff.setRGB(p.x, buff.getHeight()-p.y-1, p.c.getBRGUint8());
	}
	
	public static void drawPoint(BufferedImage buff, Point2D p, int color)
	{
		buff.setRGB(p.x, buff.getHeight()-p.y-1, color);
	}
	
	//////////////////////////////////////////////////
	//	Implement the following two functions
	//////////////////////////////////////////////////
	
	// draw a line segment
	public static void drawLine(BufferedImage buff, Point2D p1, Point2D p2, boolean doMap, BufferedImage texture)
	{
		// replace the following line with your implementation
		
		if(p1.x == p2.x && p1.y == p2.y){
			return;
		}
		
		PointGenerator generator = PointGeneratorFactory.createGenerator(p1, p2, true, doMap);
		
		while(generator.hasNext()){
			
			if(!doMap){
				drawPoint(buff, generator.getNext());
			}
			else{
				Point2D p = generator.getNext();
				drawPoint(buff, p, readTextureColor(p, texture));
			}
		}
		
		
	}
	
	// draw a triangle
	public static void drawTriangle(BufferedImage buff, Point2D p1, Point2D p2, Point2D p3, boolean do_smooth, boolean fill, boolean doMap, BufferedImage texture)
	{
		// replace the following line with your implementation
		if(fill){
			
			if(!do_smooth){
				p2.c = new ColorType(p1.c);
				p3.c = new ColorType(p1.c);
			}
			
			TriangleRender render = new TriangleRender(buff, texture);
			render.renderTriangle(p1, p2, p3, do_smooth, doMap);
		}
		else{
			drawLine(buff, p2, p3, doMap, texture);
			drawLine(buff, p1, p3, doMap, texture);
			drawLine(buff, p1, p2, doMap, texture);
		}
		

	}
	
	/////////////////////////////////////////////////
	// for texture mapping (Extra Credit for CS680)
	/////////////////////////////////////////////////
	public static void triangleTextureMap(BufferedImage buff, BufferedImage texture, Point2D p1, Point2D p2, Point2D p3)
	{
		// replace the following line with your implementation
		
		p1.u = 0.5f;
		p1.v = 1.0f;
		p2.u = 1.0f; 
		p2.v = 0.0f;
		p3.u = 0.0f;
		p3.v = 0.0f;
		
		TriangleRender render = new TriangleRender(buff, texture);
		render.renderTriangle(p1, p2, p3, false, true);
	}
}
