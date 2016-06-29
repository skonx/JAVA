package fr.yla.mt.visitor.javafx;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javafx.scene.paint.Color;

public final class ColorUtils {

	private static final Map<Color,String> javaFXColorMap = getJavaFXColorMap();

	private ColorUtils(){
		//Garbage Constructor
	}

	/**
	 * <pre>
	 * RGBA format : rgba(<integer>,<integer>,<integer>,<number>)
	 * The red, green, and blue values must be integers between 0 and 255.
	 * The alpha value must be a number in the range 0.0 (representing completely transparent) and 1.0 (completely opaque). 
	 * </pre>
	 * @param color the color to format in String
	 * @return the string with the rgba format
	 * @throws NullPointerException if color is null
	 */
	public final static String formatColorToRGBA(final Color color){
		if(color == null)
			throw new NullPointerException("Color is null");

		StringBuilder sb = new StringBuilder();

		int r = new Double(color.getRed()*255).intValue();
		int g = new Double(color.getGreen()*255).intValue();
		int b = new Double(color.getBlue()*255).intValue();
		double a = color.getOpacity();

		sb.append("rgba(").
		append(r).append(",").
		append(g).append(",").
		append(b).append(",").
		append(a).append(")");

		return sb.toString();
	}

	/**
	 * RGBA Format : 0xhhhhhhhh with h between 00 and ff (hexadecimal base).
	 * @param color to format in Hex String
	 * @return
	 */
	public final static String formatColorToHexWeb(final Color color){
		if(color == null)
			throw new NullPointerException("Color is null");

		StringBuilder sb = new StringBuilder();
		sb.append("0x");

		int[] rgba = new int[4];

		rgba[0] = new Double(color.getRed()*255).intValue();
		rgba[1] = new Double(color.getGreen()*255).intValue();
		rgba[2] = new Double(color.getBlue()*255).intValue();
		rgba[3] = new Double(color.getOpacity()*255).intValue();



		for(int i = 0;i<rgba.length;i++){
			if(rgba[i]==0)
				sb.append("00");
			else
				sb.append(Integer.toHexString(rgba[i]));
		}

		return sb.toString();
	}

	/**
	 * Create a Map<Color,String> from the Color class with its public static final fields.
	 */
	private final static Map<Color, String> getJavaFXColorMap() {
		Field[] fields = Color.class.getDeclaredFields();
		Map<Color, String> colors = new TreeMap<>(new Comparator<Color>() {

			@Override
			public int compare(Color o1, Color o2) {
				return formatColorToHexWeb(o1).compareTo(formatColorToHexWeb(o2));
			}
		});

		for (Field f : fields) {
			if (Modifier.isStatic(f.getModifiers()) 
					&& Modifier.isPublic(f.getModifiers())
					&& Modifier.isFinal(f.getModifiers())
					&& Color.class.equals(f.getType())) {
				try {
					colors.put((Color)f.get(null), f.getName().toLowerCase());
				} catch (Exception ex){
					ex.printStackTrace();
				} 
			}
		}

		return colors;
	}

	/**
	 * Returns the String associated to the color in the javaFXColorMap.
	 * This method should not be used because there is no further check on empty string or null element
	 * @param color the color to find in the map
	 * @return the String associated to the color or null
	 */
	@Deprecated
	public final static String getColorName(Color color){
		return javaFXColorMap.get(color);
	}

	/**
	 * Returns an Optional value (used with a ifPresent call) instead of a raw string in order to avoid NullPointerExceptions.
	 * @param color the color to find
	 * @return an Optional with the Map.Entry<Color,String> found or an empty Optional otherwise.
	 */
	public final static Optional<Map.Entry<Color, String>> getOptionalColorName(Color color){
		return javaFXColorMap.entrySet().stream().filter(e->e.getKey().equals(color)).findFirst();
	}

}
