package cz.matejcik.openwig;

import java.io.*;

import se.krka.kahlua.vm.*;
import se.krka.kahlua.stdlib.MathLib;

public class ZonePoint implements LuaTable, Serializable {
	public double latitude = 0;
	public double longitude = 0;
	public Distance altitude = null;
	
	public static final double LATITUDE_COEF = 110940.00000395167;
	public static final double METRE_COEF = 9.013881377e-6;
	public static final double PI_180 = Math.PI / 180;
	public static final double DEG_PI = 180 / Math.PI;
	public static final double PI_2 = Math.PI / 2;
	
	public static ZonePoint copy (ZonePoint z) {
		if (z == null) return null;
		else return new ZonePoint (z);
	}

	public ZonePoint () {
		altitude = new Distance();
	}
	
	public ZonePoint (ZonePoint z) {
		latitude = z.latitude;
		longitude = z.longitude;
		altitude = Distance.copy(z.altitude);
	}
	
	private ZonePoint (double lat, double lon, Distance alt) {
		latitude = lat;
		longitude = lon;
		altitude = alt;
	}
	
	public ZonePoint (double lat, double lon, double alt)
	{
		latitude = lat;
		longitude = lon;
		altitude = new Distance(alt,"metres");
	}

	public ZonePoint translate (double angle, double dist) {
		double rad = azimuth2angle(angle);
		double x = m2lat(dist * Math.sin(rad));
		double y = m2lon(latitude, dist * Math.cos(rad));
		return new ZonePoint(latitude + x, longitude + y, altitude);
	}

	public ZonePoint translate (double angle, Distance distance) {
		return translate(angle, distance.value);
	}

	public void sync (ZonePoint z) {
		latitude = z.latitude;
		longitude = z.longitude;
	}
	
	public static double lat2m (double degrees) {
		return degrees * LATITUDE_COEF;
	}
	
	public static double lon2m (double latitude, double degrees) {
		return degrees * PI_180 * Math.cos(latitude * PI_180) * 6367449;
	}
	
	public static double m2lat (double metres) {
		return metres * METRE_COEF;
	}
	
	public static double m2lon (double latitude, double metres) {
		return metres / (PI_180 * Math.cos(latitude * PI_180) * 6367449);
	}
	
	public double distance (double lat, double lon) {
		return distance(lat, lon, latitude, longitude);
	}

	public double distance (ZonePoint z) {
		return distance(z.latitude, z.longitude, latitude, longitude);
	}

	public static double distance (double lat1, double lon1, double lat2, double lon2) {
		double mx = Math.abs(ZonePoint.lat2m(lat1 - lat2));
		double my = Math.abs(ZonePoint.lon2m(lat2, lon1 - lon2));
		return Math.sqrt(mx * mx + my * my);
	}
	
	public String friendlyDistance (double lat, double lon) {
		return makeFriendlyDistance(distance(lat, lon));
	}
	
	public static String makeFriendlyDistance (double dist) {
		double d = 0; long part = 0;
		if (dist > 1500) { // abcd.ef km
			part = (long)(dist / 10);
			d = part / 100.0;
			return Double.toString(d)+" km";
		} else if (dist > 100) { // abcd m
			return Double.toString((long)dist)+" m";
		} else { // abcd.ef m
			part = (long)(dist * 100);
			d = part / 100.0;
			return Double.toString(d)+" m";
		}
	}

	public static String makeFriendlyAngle (double angle) {
		boolean neg = false;
		if (angle < 0) {
			neg = true;
			angle *= -1;
		}
		int degrees = (int)angle;
		angle = (angle - degrees) * 60;
		String an = String.valueOf(angle);
		if (an.indexOf('.') != -1)
			an = an.substring(0, Math.min(an.length(), an.indexOf('.') + 5));
		return (neg ? "- " : "+ ") + String.valueOf(degrees) + "\uc2b0 " + an;
	}

	public static String makeFriendlyLatitude (double angle) {
		return makeFriendlyAngle(angle).replace('+', 'N').replace('-', 'S');
	}

	public static String makeFriendlyLongitude (double angle) {
		return makeFriendlyAngle(angle).replace('+', 'E').replace('-', 'W');
	}
	
	public double bearing (double lat, double lon) {
		// calculates bearing from specified point to here
		return MathLib.atan2(lat2m(latitude - lat), lon2m(lat, longitude - lon));
	}
	
	public static double angle2azimuth (double angle) {
		double degrees = -((angle - PI_2) * DEG_PI);
		while (degrees < 0) degrees += 360;
		while (degrees >= 360) degrees -= 360;
		return degrees;
	}
	
	public static double azimuth2angle (double azim) {
		double ret = -(azim * PI_180) + PI_2;
		while (ret > Math.PI) ret -= Math.PI * 2;
		while (ret <= -Math.PI) ret += Math.PI * 2;
		return ret;
	}

	public void setMetatable (LuaTable metatable) { }
	public LuaTable getMetatable () { return null; }

	public void rawset (Object key, Object value) {
		if (key == null) return;
		String name = key.toString();
		if ("latitude".equals(name))
			latitude = LuaState.fromDouble(value);
		else if ("longitude".equals(name))
			longitude = LuaState.fromDouble(value);
		else if ("altitude".equals(name)) {
			if (value instanceof Distance) altitude = (Distance)value;
		}
	}

	public Object rawget (Object key) {
		if (key == null) return null;
		String name = key.toString();
		if ("latitude".equals(name)) return LuaState.toDouble(latitude);
		if ("longitude".equals(name)) return LuaState.toDouble(longitude);
		if ("altitude".equals(name)) return altitude;
		return null;
	}

	public Object next (Object key) { return null; }
	public int len () { return 3; }

	public void updateWeakSettings (boolean weakKeys, boolean weakValues) { }

	public void serialize (DataOutputStream out) throws IOException {
		out.writeDouble(latitude);
		out.writeDouble(longitude);
		Engine.instance.savegame.storeValue(altitude, out);
	}

	public void deserialize (DataInputStream in) throws IOException {
		latitude = in.readDouble();
		longitude = in.readDouble();
		altitude = (Distance)Engine.instance.savegame.restoreValue(in, null);
	}

	public String toString () {
		return "ZonePoint("+latitude+","+longitude+","+(altitude!=null ? altitude.toString() : "0")+")" /* + "-" + super.toString()*/;
	}
}