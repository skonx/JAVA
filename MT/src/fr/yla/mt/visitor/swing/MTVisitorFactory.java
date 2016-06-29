package fr.yla.mt.visitor.swing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JTabbedPane;

public class MTVisitorFactory{

	private final Map<String,AbstractSwingMTVisitor> map;

	private MTVisitorFactory() {
		map = new HashMap<>(MTVisitorTypes.values().length);
	}

	public static MTVisitorFactory build(final JTabbedPane tabbedPane){
		MTVisitorFactory factory = new MTVisitorFactory();

		for (MTVisitorTypes type : MTVisitorTypes.values())
			factory.map.put(type.name(), type.create(tabbedPane));

		return factory;
	}

	/**
	 * Returns the MTVisitor to which the specified key is mapped, or null if the factory contains no mapping for the key.
	 * */
	public AbstractSwingMTVisitor getVisitor(String name){
		return map.get(name);
	}
	
	public Set<String> getVisitors(){
		return map.keySet();
	}
}
