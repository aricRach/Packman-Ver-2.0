package GIS;

import java.util.ArrayList;

import Geom.Point3D;
import Map.converts;

/**
 * this class represent Packman object each of packman has : id,position,speed,eating radius,
 * ArrayList of fruits that he ate and more data associated with him 
 * @author Tal and Aric
 */

public class packman {

	private Point3D position;
	private metaDataPack data;
	private ArrayList<fruit> eatenFruits;

	// Constructor //
	public packman(metaDataPack data,Point3D position) {

		this.data=data;
		this.position=position;
		eatenFruits = new ArrayList<fruit>();
	}

	//Getters and Setters
	
	public ArrayList<fruit> getEatenFruits() {
		return eatenFruits;
	}

	public void setEatenFruits(ArrayList<fruit> eatenFruits) {
		this.eatenFruits = eatenFruits;
	}

	public Point3D getPosition() {

		return position;
	}

	public metaDataPack getData() {
		return data;
	}

	public void setData(metaDataPack data) {
		this.data = data;
	}

	public double getSpeed() {

		return data.getSpeed();
	}
	
	public double getRadius() {

		return data.getRadius();
	}

	public void setPosition(Point3D p) {

		position.set_x(p.x());
		position.set_y(p.y());
		position.set_z(p.z());
	}

	public String getId() {
		return data.getId();
	}

	public void setId(String id) {
		data.setId(id);
	}

	public double getX() {
		return position.get_x();
	}

	public void setX(double x) {
		position.set_x(x);
	}

	public double getY() {
		return position.get_y();
	}

	public void setY(double y) {
		position.set_y(y);
	}

	public double getZ() {
		return position.get_z();
	}

	public void setZ(double z) {
		position.set_z(z);
	}

	public void setSpeed(double speed) {
		data.setSpeed(speed);	
	}

	public void setRadius(double radius) {
		data.setRadius(radius);	
	}

	public double getPoints() {
		return data.getScore();
	}

	public void setPoints(double points) {
		data.setScore(points);
	}
	
	@Override
	public String toString() {
		return "packman [position=" + position + ", data=" + data +"]";
	}
}