package com.generali.burritoorderingservice.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name="order_data")
public class Order {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private Tortilla tortilla;
	
	@NotNull
	private Protein protein;
	
	@ElementCollection
	private List<Vegetable> vegetable;
	
	@NotNull
	private Salsa salsa;
	
	@NotNull
	private Extras extras;
	
}
