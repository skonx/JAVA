package fr.yla.mt.core;

import java.util.function.IntFunction;
/**
 * <pre>
 * An enum of the different multiplication table types.
 * Each element is associated to a create factory method.
 * A factory method will create and initialize a MultiplicationTable object where a simple constructor will only create an object.
 * This enum is used in the factory method generateRandomMultiplicationTables() in order to create and initialize random types of MultiplicationTable.
 * </pre> 
 * @author jsie
 *
 */
public enum MTTypes {
	/**
	 * BasicMT creator
	 */
	BASIC(MTFactory::createBasicMultiplicationTable), 
	/**
	 * MirroredMT creator
	 */
	MIRRORED(MTFactory::createMirroredMultiplicationTable), 
	/**
	 * OptimizedInitMT creator
	 */
	OPTIMIZED(MTFactory::createOptimizedInitMultiplicationTable);

	private final IntFunction<MultiplicationTable> creator;
	
	private MTTypes(IntFunction<MultiplicationTable> creator){
		this.creator = creator;
	}

	public MultiplicationTable create(int cap){
		return creator.apply(cap);
	}

}
