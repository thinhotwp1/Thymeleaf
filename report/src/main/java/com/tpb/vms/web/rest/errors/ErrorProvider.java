/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.web.rest.errors;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Viet Do-Van
 */
public enum ErrorProvider {

	// COMMON
	INTERNAL_SERVER_ERROR("000", "errors.common.internal-server-error", "Internal server error"),
	VALIDATION_ERROR("001", "errors.common.validation-error", "Validation error"),
	REQUIRED_ERROR("002", "errors.common.required-error", "Required information"),
	INVALID_CUSTOMER_SEGMENT("003", "errors.common.invalid-customer-segment", "Invalid customer segment"),
	INVALID_COLLATERAL_TYPE("004", "errors.common.invalid-collateral-type", "Invalid collateral type"),
	INVALID_LOAN_TYPE("005", "errors.common.invalid-loan-type", "Invalid loan type"),
	INVALID_CUSTOMER_LOAN("006", "errors.common.invalid-customer-loan", "Invalid customer loan"),
	INVALID_LOAN_METHOD("007", "errors.common.invalid-loan-method", "Invalid loan method"),
	INVALID_LOAN_TERM("008", "errors.common.invalid-loan-term", "Invalid loan term"),
	INVALID_PRODUCT_CODE("009", "errors.common.invalid-product-code", "Invalid product code"),
	INVALID_PRIME_RATE("010", "errors.common.invalid-prime-rate", "Invalid prime rate"),
	INVALID_PERIOD_ADJUSTMENT("011", "errors.common.invalid-period-adjustment", "Invalid period adjustment"),
	INVALID_FIRST_PERIOD_RATE_ADJUSTMENT_APPROVAL("012", "errors.common.invalid_first_period_rate_adjustment_approval",
			"Invalid first period rate adjustment approval"),
	INVALID_AMPLITUDE_RATE_ADJUSTMENT_APPROVAL("013", "errors.common.invalid_amplitude_rate_adjustment_approval",
			"Invalid amplitude rate adjustment approval"),
	INVALID_FILE("014", "errors.common.invalid-file", "Invalid file"),
	INVALID_MAX_LENGTH("015", "errors.common.invalid-max-length", "Invalid max length"),
	UPDATE_INTEREST_RATE_WS_ECM_ERROR("016", "errors.common.update-interest-rate-ws-ecm-error",
			"Update interest rate ws ecm error"),
	NOT_EXIST_CUSTOMER_CIF_NO("017", "errors.common.not-exist-customer-cifno", "Not exist customer CIF NO"),
	INVALID_CUSTOMER_NAME("018", "errors.common.invalid-customer-name", "Invalid customer name"),
	CONTENT_IS_NULL_OR_EMPTY("019", "errors.common.content-null-or-empty", "Content is null or empty"),
	BULK_POST_MAX_SIZE_ERROR("020", "errors.common.bulk-post-max-size-error", "Max size error when bulk create item"),
	EREF_NOT_FOUND("021", "errors.common.xref-not-found", "XREF not found"),
	CALL_CUSTOMER_INFO_SERVICE_ERROR("022", "errors.common.call-customer-info-service-error",
			"Call customer info service error"),
	INVALID_DATE_FORMAT("023", "errors.common.invalid-date-format", "Invalid date format"),
	INVALID_DATE_RANGE("024", "errors.common.invalid-date-range", "Invalid date range"),
	DUPLICATE_RECORD("025", "errors.common.duplicate-record", "Duplicate record"),
	MUST_NON_EMPTY_ERROR("026", "errors.common.must-non-emtpy-error", "Must non empty"),
	CALL_TOKEN_SERVICE_ERROR("027", "errors.common.call-token-service-error", "Call token service error"),
	//
	// TRANS-IB
	TRANS_IB_CANNOT_CALCULATE_INTEREST_RATE("100", "errors.trans-ib.cannot-calculate-interest-rate",
			"Can not calculate interest rate"),
	TRANS_IB_REFER_MATRIX_INT_RECORD_NOT_FOUND("101", "errors.trans-ib.refer-matrix-int-record-not-found",
			"Refer matrix int record not found"),
	TRANS_IB_CANNOT_GET_PRIME_RATE_VALUE("102", "errors.trans-ib.cannot-get-prime-rate", "Can not get prime rate"),
	INCENTIVE_TRANS_IB_CANNOT_CALCULATE_INTEREST_RATE("103", "errors.incentive-trans-ib.cannot-calculate-interest-rate",
			"Can not calculate interest rate"),
	//
	// CATEGORY
	CATEGORY_BULK_POST_ERROR("104", "errors.category.bulk-post-error", "Bulk create category error"),
	CATEGORY_RECORD_DUPLICATED("105", "errors.category.record-duplicated", "Category is duplicated"),
	CATEGORY_RECORD_EXISTED("106", "errors.category.record-existed", "Category is existed"),
	CATEGORY_BULK_PUT_ERROR("107", "errors.category.bulk-put-error", "Bulk update category error"),
	//
	INTEREST_RATE_UPLOAD("108", "errors.interest-rate-management.value-upload-wrong", "Can not upload file"),
	// Original Interest Rate
	ORIGNAL_INTEREST_RATE_ADD_DUPLICATE("109", "errors.orginal-interest-rate.duplicate-value-add",
			"Can not add more original interest"),
	ORIGNAL_INTEREST_RATE_NOT_FOUND("110", "errors.orginal-interest-rate.not-found",
			"Original interest rate not found"),
	TRANS_CB_OVER_WEIGHT("111", "errors.trans-cb.over-weight", "Over weight"),
	TRANS_CB_PRESENT_NOT_FOUND("112", "errors.trans-cb.cannot-get-present-interest", "Present interest not found"),;

	private final String status;
	private final String code;
	private final String message;

	ErrorProvider(String status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static ErrorProvider getByCode(String code) {
		for (ErrorProvider interestRateError : ErrorProvider.values()) {
			if (StringUtils.equals(code, interestRateError.getCode())) {
				return interestRateError;
			}
		}
		return null;
	}

	public VmsException toException() {
		return new VmsException(this.getStatus(), this.getCode(), this.getMessage());
	}

	public VmsException toExceptionWithCustomMessage(String message) {
		return new VmsException(this.getStatus(), null, message);
	}

	public VmsException toExceptionWithParameter(List<String> listString) {
		
		VmsException exception = new VmsException(this.getStatus(), this.getCode(), this.getMessage());
		System.out.println(exception);
		String messWithParam = String.format(exception.getMessage(), listString);
		return new VmsException(this.getStatus(), null, messWithParam); 
	}
}
