package by.epamtc.sinitsyna.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Candy implements Serializable, Comparable<Candy> {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private int energy;
	private String producer;
	private LocalDateTime productionDateTime;
	private Map<String, Integer> ingredients;
	private NutritionalValue nutritionalValue;
	private Glaze glaze;
	private String filling;

	public Candy() {
		this.glaze = Glaze.UNGLAZED;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public LocalDateTime getProductionDateTime() {
		return productionDateTime;
	}

	public void setProductionDateTime(LocalDateTime productionDateTime) {

		this.productionDateTime = productionDateTime;
	}

	public Map<String, Integer> getIngredients() {
		return new HashMap<String, Integer>(ingredients);
	}

	public void setIngredients(Map<String, Integer> ingredients) {
		this.ingredients = new HashMap<String, Integer>(ingredients);
	}

	public Iterator<Entry<String, Integer>> getIngredientsIterator() {
		return ingredients.entrySet().iterator();
	}

	public NutritionalValue getNutritionalValue() {
		return (NutritionalValue) nutritionalValue.clone();
	}

	public void setNutritionalValue(NutritionalValue nutritionValue) {
		this.nutritionalValue = (NutritionalValue) nutritionValue.clone();
	}

	public Glaze getGlaze() {
		return glaze;
	}

	public void setGlaze(Glaze glazeType) {
		if (glazeType == null) {
			this.glaze = Glaze.UNGLAZED;
		} else {
			this.glaze = glazeType;
		}
	}

	public String getFilling() {
		return filling;
	}

	public void setFilling(String fillingType) {
		this.filling = fillingType;
	}

	@Override
	public int compareTo(Candy o) {
		return id.compareTo(o.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + energy;
		result = prime * result + ((filling == null) ? 0 : filling.hashCode());
		result = prime * result + ((glaze == null) ? 0 : glaze.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nutritionalValue == null) ? 0 : nutritionalValue.hashCode());
		result = prime * result + ((producer == null) ? 0 : producer.hashCode());
		result = prime * result + ((productionDateTime == null) ? 0 : productionDateTime.hashCode());
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
		Candy other = (Candy) obj;
		if (energy != other.energy)
			return false;
		if (filling == null) {
			if (other.filling != null)
				return false;
		} else if (!filling.equals(other.filling))
			return false;
		if (glaze != other.glaze)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nutritionalValue == null) {
			if (other.nutritionalValue != null)
				return false;
		} else if (!nutritionalValue.equals(other.nutritionalValue))
			return false;
		if (producer == null) {
			if (other.producer != null)
				return false;
		} else if (!producer.equals(other.producer))
			return false;
		if (productionDateTime == null) {
			if (other.productionDateTime != null)
				return false;
		} else if (!productionDateTime.equals(other.productionDateTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", name=" + name + ", energy=" + energy + ", producer="
				+ producer + ", productionDateTime=" + productionDateTime + ", ingredients=" + ingredients
				+ ", nutritionalValue=" + nutritionalValue + ", glaze=" + glaze + ", filling=" + filling + "]";
	}

	public enum Glaze {
		UNGLAZED("без глазури"), CHOCOLATE("шоколадная"), FAT("жировая"), CARAMEL("карамельная"), SUGAR("сахарная"),
		FONDANT("помадная"), PECTIN("пектиновая");

		private String type;

		private Glaze(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;

		}
	}
}
