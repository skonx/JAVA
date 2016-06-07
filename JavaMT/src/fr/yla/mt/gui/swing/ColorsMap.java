package fr.yla.mt.gui.swing;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class ColorsMap {
	private final Map<String, Color> map;
	
	private ColorsMap() {
		map = new TreeMap<>();
	}
	
	public static ColorsMap build(){
		ColorsMap c = new ColorsMap();
		
		for(ColorsItems item : ColorsItems.values())
			c.map.put(item.name(), item.getColor());
		
		return c;
	}
	
	public Color getColor(String name){
		return map.get(name);
	}
	
	public void addColor(String name, Color color){
		map.put(name, color);
	}
	
	public Set<String> getColors(){
		return map.keySet();
	}
	
	public String lookupNameFromColor(Color color){
		
		if(Objects.isNull(color))
			return ColorsItems.UNCOLORED.name();
		
		if(map.containsValue(color))
			for(Entry<String,Color> e :map.entrySet()){
				if(Objects.nonNull(e.getValue()) && e.getValue().equals(color))
					return e.getKey();
			}
		
		return ColorsItems.UNCOLORED.name();
	}
	
	public void printAll(){
		map.entrySet().forEach(System.out::println);
	}

}
