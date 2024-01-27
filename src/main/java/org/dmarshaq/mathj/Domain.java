package org.dmarshaq.mathj;

public final class Domain {

	private final float a, b;

	public Domain(float a, float b) {
		if (a > b) {
			this.a = b;
			this.b = a;
		}
		else {
			this.a = a;
			this.b = b;
		}
	}

	public Domain(Domain domain) {
		if (domain.a > domain.b) {
			this.a = domain.b;
			this.b = domain.a;
		}
		else {
			this.a = domain.a;
			this.b = domain.b;
		}
	}
	
	public int isInDomain(float value) {
		if (value >= a && value <= b) {
			return 0;
		}
		else if (value < a) {
			return -1;
		}
		else {
			return 1;
		}
			
	}
	public String toString() {
		return "( " + a + " to " + b + " )";
	}
}
