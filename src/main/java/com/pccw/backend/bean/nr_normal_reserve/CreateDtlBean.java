package com.pccw.backend.bean.nr_normal_reserve;

import com.pccw.backend.bean.BaseBean;
import com.pccw.backend.entity.DbResLogMgtDtl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value="NR_Normal_Reserve - CreateDtlBean",description="")
public class CreateDtlBean extends BaseBean {

    @ApiModelProperty(value="Normal_Reserve",name="detail_id",example="")
    private String detail_id;

    @ApiModelProperty(value="Normal_Reserve",name="sku_id",example="")
//    private long dtlSkuId;
    private String sku_id;

    @ApiModelProperty(value="Normal_Reserve",name="item_id",example="")
//    private long dtlItemId;
    private String item_id;

    @ApiModelProperty(value="Normal_Reserve",name="repo_id",example="")
//    private long dtlRepoId;
    private String repo_id;

    @ApiModelProperty(value="Normal_Reserve",name="quantity",example="")
//    private long dtlQty;
    private long quantity;

    // Transation Number made from SMP self
    @ApiModelProperty(value="Normal_Reserve",name="logTxtBum",example="")
    private String logTxtBum;

    @ApiModelProperty(value="Normal_Reserve",name="dtlLogId",example="")
    private long dtlLogId;

    // A - Add / D - Deduct
    @ApiModelProperty(value="Normal_Reserve",name="item_action",example="")
//    private String dtlAction;
    private String item_action;

    // Good / Faulty / Intran
    @ApiModelProperty(value="Normal_Reserve",name="dtlSubin",example="")
    private String dtlSubin;

    // W - waiting LIS to handle / D - Done
    @ApiModelProperty(value="Normal_Reserve",name="lisStatus",example="")
    private String lisStatus;

    // result from LIS
    @ApiModelProperty(value="Normal_Reserve",name="lisResult",example="")
    private String lisResult;

    // AVL(available)/DEM(demo)/RES(reserve)/ARE(ao_reserve)
    // FAU(faulty)
    // INT(ondelivery/intransit)
    @ApiModelProperty(value="Normal_Reserve",name="action_status",example="")
//    private String Status;
    private String action_status;

    // Shop code mapping 根据传入参数补加参数
    @ApiModelProperty(value="Normal_Reserve",name="ccc",example="")
    private String ccc;

    //  Work order 根据传入参数补加参数
    @ApiModelProperty(value="Normal_Reserve",name="wo",example="")
    private String wo;

}
