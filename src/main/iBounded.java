package main;

public interface iBounded {
	int size = 0; // use a default const instead
	int getSize();
	boolean isFull();
}
