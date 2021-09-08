package by.epamtc.sinitsyna.bean;

import java.io.Serializable;

public class NutritionalValue implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private double fats;
	private double protains;
	private double carbohydrates;

	public NutritionalValue() {
	}

	public double getFats() {
		return fats;
	}

	public void setFats(double fats) {
		this.fats = fats;
	}

	public double getProtains() {
		return protains;
	}

	public void setProtains(double protains) {
		this.protains = protains;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	
	@Override
	public Object clone() {
		NutritionalValue nutritionalValue = new NutritionalValue();
		nutritionalValue.carbohydrates = this.carbohydrates;
		nutritionalValue.fats = this.fats;
		nutritionalValue.protains = this.protains;
		return nutritionalValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(carbohydrates);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fats);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(protains);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NutritionalValue other = (NutritionalValue) obj;
		if (Double.doubleToLongBits(carbohydrates) != Double.doubleToLongBits(other.carbohydrates))
			return false;
		if (Double.doubleToLongBits(fats) != Double.doubleToLongBits(other.fats))
			return false;
		if (Double.doubleToLongBits(protains) != Double.doubleToLongBits(other.protains))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [fats=" + fats + ", protains=" + protains + ", carbohydrates="
				+ carbohydrates + "]";
	}

}
