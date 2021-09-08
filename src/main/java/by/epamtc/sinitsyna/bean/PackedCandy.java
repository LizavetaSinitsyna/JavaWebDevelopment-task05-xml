package by.epamtc.sinitsyna.bean;

import java.io.Serializable;

public class PackedCandy extends Candy implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pack;

	public PackedCandy() {
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String packType) {
		this.pack = packType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((pack == null) ? 0 : pack.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		PackedCandy other = (PackedCandy) obj;
		if (pack == null) {
			if (other.pack != null)
				return false;
		} else if (!pack.equals(other.pack))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + getId() + ", name=" + getName() + ", energy=" + getEnergy()
				+ ", producer=" + getProducer() + ", productionDateTime=" + getProductionDateTime() + ", ingredients="
				+ getIngredients() + ", nutritionalValue=" + getNutritionalValue() + ", glaze=" + getGlaze()
				+ ", filling=" + getFilling() + ", pack=" + getPack() + "]";
	}

}
