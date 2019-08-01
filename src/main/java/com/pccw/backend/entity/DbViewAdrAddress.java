package com.pccw.backend.entity;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "view_adr_address")
public class DbViewAdrAddress implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "country_id", length = 22)
	private Long countryId;

	@Column(name = "country_name", length = 512)
	private String countryName;

	@Column(name = "country_cd", length = 32)
	private String countryCd;

	@Column(name = "state_id", length = 22)
	private Long stateId;

	@Column(name = "state_name", length = 512)
	private String stateName;

	@Column(name = "state_cd", length = 32)
	private String stateCd;

	@Column(name = "city_id", length = 22)
	private Long cityId;

	@Column(name = "city_name", length = 512)
	private String cityName;

	@Column(name = "city_cd", length = 32)
	private String cityCd;

	@Column(name = "area_id", length = 22)
	private Long areaId;

	@Column(name = "area_name", length = 512)
	private String areaName;

	@Column(name = "area_cd", length = 32)
	private String areaCd;

	@Column(name = "district_id", length = 22)
	private Long districtId;

	@Column(name = "district_name", length = 512)
	private String districtName;

	@Column(name = "district_cd", length = 32)
	private String districtCd;

	@Column(name = "town_id", length = 22)
	private Long townId;

	@Column(name = "town_name", length = 512)
	private String townName;

	@Column(name = "town_cd", length = 32)
	private String townCd;

	@Column(name = "street_id", length = 22)
	private Long streetId;

	@Column(name = "street_name", length = 512)
	private String streetName;

	@Column(name = "street_type", length = 32)
	private String streetType;

	@Column(name = "segment_id", length = 22)
	private Long segmentId;

	@Column(name = "start_num", length = 22)
	private Long startNum;

	@Column(name = "end_num", length = 22)
	private Long endNum;

	@Column(name = "section_id", length = 22)
	private Long sectionId;

	@Column(name = "section_name", length = 32)
	private String sectionName;

	@Column(name = "building_id", length = 22)
	private Long buildingId;

	@Column(name = "building_name", length = 128)
	private String buildingName;

	@Column(name = "geo_pt_x", length = 32)
	private String geoPtX;

	@Column(name = "geo_pt_y", length = 32)
	private String geoPtY;

	@Column(name = "postal_cd", length = 32)
	private String postalCd;

	public String getName(String type) {
		String name = "";
		try {
			Field field = this.getClass().getDeclaredField(type);
			name = field.get(this).toString();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return name;
	}

}
