package fr.yla.mt.visitor.javafx;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.scene.control.TabPane;

public class MTVisitorFactory{

	private final Map<String,AbstractJavaFXMTVisitor> map;

	private MTVisitorFactory() {
		map = new HashMap<>(MTVisitorTypes.values().length);
	}

	public static MTVisitorFactory build(final TabPane tabPane){
		MTVisitorFactory factory = new MTVisitorFactory();

		for (MTVisitorTypes type : MTVisitorTypes.values())
			factory.map.put(type.name(), type.create(tabPane));

		return factory;
	}

	public AbstractJavaFXMTVisitor getVisitor(String name){
		return map.get(name);
	}

	public Set<String> getVisitors(){
		return map.keySet();
	}
}
