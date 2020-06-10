package com.example.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * [返回值说明类] <br> 
 *  
 * @author [li.qiong]<br>
 * @version 1.0.0<br>
 * @CreateDate 2020年4月6日 <br>
 */
@Data
@ApiModel(value = "返回值和返回说明类")
public class ResponseJson<V> implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	
	// 状态
	private final static int STATUS_SUCCESS = 0;
	private final static int STATUS_FAIL = 1012;
	private final static int STATUS_EXCEPTION = 1014;
	private final static int SESSION_EXCEPTION = 1015;
	private final static int AUTH_EXCEPTION = 1016;
	private final static int STATUS_FAIL2 = 1018;
    
	// 中文描述
	private final static String DESC_SUCCESS = "成功";
	private final static String DESC_SERVER_FAIL = "参数缺失";
	private final static String DESC_SERVER_EXCEPTION = "服务异常,请重试.";
	private final static String DESC_SESSION_EXCEPTION = "用户信息失效,请重新登录.";
	private final static String DESC_AUTH_EXCEPTION = "您无权限操作.";
	private final static String DESC_SERVER_FAIL2 = "操作失败,请重试.";
	
	@ApiModelProperty(value = "状态码")
    private Integer errorCode;

	@ApiModelProperty(value = "中文状态描述")
	private String reason;
	
	@ApiModelProperty(value = "返回前端数据体")
	private V data;

    private ResponseJson(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

	private ResponseJson(int errorCode, String reason, V data) {
        this.errorCode = errorCode;
        this.reason = reason;
        this.data = data;
    }
    
    /**
     * 
     * [返回状态码和具体的数据] <br> 
     *  
     * @author [li.qiong]<br>
     * @param
     * @return <br>
     */
    public static <V> ResponseJson<V> success(V data) {
	    return new ResponseJson<V>(STATUS_SUCCESS, DESC_SUCCESS, data);
    }
    
    /**
	 * 
	 * [返回成功的状态] <br> 
	 *  
	 * @author [li.qiong]<br>
	 * @return <br>
	 */
    public static <V> ResponseJson<V> success() {
    	return new ResponseJson<V>(STATUS_SUCCESS, DESC_SUCCESS);
    }

    /**
     * 
     * [操作失败] <br> 
     *  
     * @author [li.qiong]<br>
     * @param errorCode 状态码
     * @param reason 失败信息
     * @return <br>
     */
	public static <V> ResponseJson<V> fail() {
		return new ResponseJson<V>(STATUS_FAIL2 ,DESC_SERVER_FAIL2 );
    }

    /**
     * 
     * [自定义失败码和失败原因] <br> 
     *  
     * @author [li.qiong]<br>
     * @param errorCode 状态码
     * @param reason 失败信息
     * @return <br>
     */
	public static <V> ResponseJson<V> failed(int errorCode, String reason) {
		return new ResponseJson<V>(errorCode, reason);
    }

	/**
	 * 
	 * [参数信息缺失失败] <br> 
	 *  
	 * @author [li.qiong]<br>
	 * @return <br>
	 */
	public static <V> ResponseJson<V> failed() {
		return new ResponseJson<V>(STATUS_FAIL, DESC_SERVER_FAIL);
    }

	/**
	 *
	 * [用户信息失效，返回错误信息] <br>
	 *
	 * @author [li.qiong]<br>
	 * @return <br>
	 */
	public static <V> ResponseJson<V> sessionFailed(){
		return new ResponseJson<V>(SESSION_EXCEPTION, DESC_SESSION_EXCEPTION);
	}

	/**
	 * 
	 * [异常返回] <br> 
	 *  
	 * @author [li.qiong]<br>
	 * @return <br>
	 */
    public static <V> ResponseJson<V> exceptioned() {
        return new ResponseJson<V>(STATUS_EXCEPTION,DESC_SERVER_EXCEPTION);
    }
    
    /**
	 * 
	 * [无权限操作返回] <br> 
	 *  
	 * @author [li.qiong]<br>
	 * @return <br>
	 */
    public static <V> ResponseJson<V> authFailed() {
        return new ResponseJson<V>(AUTH_EXCEPTION,DESC_AUTH_EXCEPTION);
    }

}
