package fr.treeptik.examples;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "comment")
public class Comment {
	@Id @GeneratedValue private long id;
	private String name;
	private double rating;

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	/**********************************************************************/
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Comment comment = (Comment) o;

		if (id != comment.id) return false;
		if (Double.compare(comment.rating, rating) != 0) return false;
		return name != null ? name.equals(comment.name) : comment.name == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		temp = Double.doubleToLongBits(rating);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
