package com.bussiness.jwt.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class Product {
	
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Integer productId;
	
	private String productName;
	
	@Column(length=2000)
	private String productDescription;
	private Double productDiscountedPrice;
	private Double productActualPrice;
	
	@ManyToMany(fetch= FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="product_images",
	joinColumns= {
			@JoinColumn(name="product_id")
	},
	inverseJoinColumns= {
			@JoinColumn(name="image_id")
	}
	)
	private Set<ImageModel> productImages;
	
	
	
	public Set<ImageModel> getProductImages() {
		return productImages;
	}
	public void setProductImages(Set<ImageModel> productImages) {
		this.productImages = productImages;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public Double getProductDiscountedPrice() {
		return productDiscountedPrice;
	}
	public void setProductDiscountedPrice(Double productDiscountedPrice) {
		this.productDiscountedPrice = productDiscountedPrice;
	}
	public Double getProductActualPrice() {
		return productActualPrice;
	}
	public void setProductActualPrice(Double productActualPrice) {
		this.productActualPrice = productActualPrice;
	}
	

}
