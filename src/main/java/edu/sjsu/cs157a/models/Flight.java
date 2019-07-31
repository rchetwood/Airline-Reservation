package edu.sjsu.cs157a.models;

import java.sql.Date;

public class Flight {

	public Flight() {} 
	
	public Flight(Plane plane, Airport departure, Airport destination, Integer duration, Date departureDate,
			Integer price, Integer seatsAvailable) {
		super();
		this.plane = plane;
		this.departure = departure;
		this.destination = destination;
		this.duration = duration;
		this.departureDate = departureDate;
		this.price = price;
		this.seatsAvailable = seatsAvailable;
	}
	

	public Integer getFlID() {
		return flID;
	}

	public void setFlID(Integer flID) {
		this.flID = flID;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public Airport getDeparture() {
		return departure;
	}

	public void setDeparture(Airport departure) {
		this.departure = departure;
	}

	public Airport getDestination() {
		return destination;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(Integer seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flID == null) ? 0 : flID.hashCode());
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
		Flight other = (Flight) obj;
		if (flID == null) {
			if (other.flID != null)
				return false;
		} else if (!flID.equals(other.flID))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Flight [flID=" + flID + ", plane=" + plane + ", departure=" + departure + ", destination=" + destination
				+ ", duration=" + duration + ", departureDate=" + departureDate + ", price=" + price
				+ ", seatsAvailable=" + seatsAvailable + "]";
	}

	private Integer flID;
	private Plane plane;
	private Airport departure;
	private Airport destination;
	private Integer duration;
	private Date departureDate;
	private Integer price;
	private Integer seatsAvailable;
}
