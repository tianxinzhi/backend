package com.pccw.backend.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "res_productline")
//@SequenceGenerator(name="id_productline",sequenceName = "productline_seq",allocationSize = 1)
public class DbResProductLine extends Base {
	
	
	@Id
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_productline")
	@GeneratedValue(strategy=GenerationType.TABLE,generator="res_productline_gen")
    @TableGenerator(
            name = "res_productline_gen",
            table="fendo_generator",
            pkColumnName="seq_name",     //指定主键的名字
            pkColumnValue="res_productline_pk",      //指定下次插入主键时使用默认的值
            valueColumnName="seq_id",    //该主键当前所生成的值，它的值将会随着每次创建累加
            //initialValue = 1,            //初始化值
            allocationSize=1             //累加值
            )
	private Long id;
	
	
	@Column(name = "pl_name", length = 255)
	private String plName;
	
	@Column(name = "pl_code", length = 255)
	private String plCode;

}
