/*Edwin Mak 1/17/13
 * Victoria Wagner Section BE
 * HW #1 Tiles
 * In this assignment we are implementing an Arraylist
 * to manage tiles created in the TileMain class. Our 
 * methods allow the client to add, lower, raise draw, delete,
 * delete all, and shuffle the tiles.
 */

import java.util.*; 
import java.awt.*;

public class TileManager {

	private ArrayList <Tile> list; 
	
	//makes a new Arraylist to store tiles
	public TileManager() {
		list = new ArrayList <Tile> ();
	}
	
	//adds tiles to the list
	public void addTile(Tile rect) {
		list.add(rect);
	}
	
	//draws the all the tiles in the list
	public void drawAll(Graphics g) {
		for(int i = 0; i < list.size(); i++){
			list.get(i).draw(g);
		}
	}
	
	//moves the selected tile to the top
	public void raise(int x, int y) {
		int topTileIndex = topMostTile(x,y);
		if(topTileIndex != -1){
			list.add(list.get(topTileIndex));
			list.remove(topTileIndex);
		}	
	}
	
	//moves the topmost tile to the bottom
	public void lower(int x, int y) {
		int topTileIndex = topMostTile(x,y);
		if(topTileIndex != -1){
			list.add(0,list.get(topTileIndex));
			list.remove(topTileIndex + 1);
		}
	}
		
	//deletes the tile that is clicked
	public void delete (int x, int y) {
		int topTileIndex = topMostTile(x,y);
		if(topTileIndex != -1){
			list.remove(list.get(topTileIndex));
		}
	}
	
	//deletes all tiles under the coordinates of the mouse click
	public void deleteAll (int x, int y) {
		for(int i = 0; i < list.size(); i++){
			if(inTile(x,y,i)){
				list.remove(list.get(i));
				i--;
			}
		}
	}
	
	//randomizes location of the tiles and also reorders them
	public void shuffle(int width, int height) {
		Collections.shuffle(list);
		for(int i = 0; i < list.size(); i++){
			Random rand = new Random ();
			int newX = rand.nextInt(width - (list.get(i).getX() + list.get(i).getWidth()));
			int newY = rand.nextInt(height - (list.get(i).getY() + list.get(i).getHeight()));
			list.get(i).setX(newX);
			list.get(i).setY(newY);
		}
	}
	
	//determines if each tile is clicked on by the mouse
	private boolean inTile(int x, int y, int i) {
			if (list.get(i).getX() < x && x < (list.get(i).getX() + list.get(i).getWidth())
				&&list.get(i).getY() < y && y < (list.get(i).getY() + list.get(i).getHeight())){
					return true;
			}
		return false;
	}
	
	//gives the index of the top tile that the mouse clicks on
	//returns -1 if mouse clicks on blank space
	private int topMostTile(int x, int y) {
		int max = -1;
		for(int i = 0; i < list.size(); i++){
			if(inTile(x,y,i)){
				max = i;
			}
		}
		return max;
	}	
}