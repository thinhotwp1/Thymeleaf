/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.common;

/**
 *
 * @author Viet Do-Van
 */
public class ClientConst {

	public static class EcmInterestRate {

		public enum Status {
			ACTIVE("A"), INACTIVE("I");

			private final String code;

			Status(String code) {
				this.code = code;
			}

			public String getCode() {
				return code;
			}
		}

		public static final String STATUS_SUCCESS = "0";
		public static final String GLOBAL_ERROR_CODE_SUCCESS = "00";
		public static final String GLOBAL_ERROR_DESCRIPTION_SUCCESS = "SUCCESS";
		public static final String ERROR_CODE_SUCCESS = "00";
		public static final String ERROR_DESC_SUCCESS = "SUCCESS";

	}

	public static class CustomerService {

		public enum Status {
			ACTIVE("A"), INACTIVE("I");

			private final String code;

			Status(String code) {
				this.code = code;
			}

			public String getCode() {
				return code;
			}
		}

		public static final String STATUS_SUCCESS = "0";
		public static final String GLOBAL_ERROR_CODE_SUCCESS = "00";
		public static final String GLOBAL_ERROR_DESCRIPTION_SUCCESS = "SUCCESS";
		public static final String ERROR_CODE_SUCCESS = "00";
		public static final String ERROR_DESC_SUCCESS = "SUCCESS";

	}

	public static class BillService {

		public enum Status {
			ACTIVE("A"), INACTIVE("I");

			private final String code;

			Status(String code) {
				this.code = code;
			}

			public String getCode() {
				return code;
			}
		}

		public static final String STATUS_SUCCESS = "0";
		public static final String GLOBAL_ERROR_CODE_SUCCESS = "00";
		public static final String GLOBAL_ERROR_DESCRIPTION_SUCCESS = "SUCCESS";
		public static final String ERROR_CODE_SUCCESS = "00";
		public static final String ERROR_DESC_SUCCESS = "SUCCESS";

	}

	public static class TransService {

		public enum Status {
			ACTIVE("A"), INACTIVE("I");

			private final String code;

			Status(String code) {
				this.code = code;
			}

			public String getCode() {
				return code;
			}
		}

		public static final String STATUS_SUCCESS = "0";
		public static final String GLOBAL_ERROR_CODE_SUCCESS = "00";
		public static final String GLOBAL_ERROR_DESCRIPTION_SUCCESS = "SUCCESS";
		public static final String ERROR_CODE_SUCCESS = "00";
		public static final String ERROR_DESC_SUCCESS = "SUCCESS";

	}

	public static class AccountService {

		public static final String STATUS_SUCCESS = "0";
		public static final String GLOBAL_ERROR_CODE_SUCCESS = "00";
		public static final String GLOBAL_ERROR_DESCRIPTION_SUCCESS = "SUCCESS";

	}

	public static class HydroService {

		public enum Status {
			ACTIVE("A"), INACTIVE("I");

			private final String code;

			Status(String code) {
				this.code = code;
			}

			public String getCode() {
				return code;
			}
		}

		public static final String STATUS_SUCCESS = "HYD-16-000";
		public static final String GLOBAL_ERROR_CODE_SUCCESS = "00";

		public static final String GLOBAL_ERROR_DESCRIPTION_SUCCESS = "SUCCESS";
		public static final String ERROR_CODE_SUCCESS = "HYD-16-000";
		public static final String ERROR_DESC_SUCCESS = "HYD-16-000";
		public static final String ERROR_CODE_EXCEPTION = "HYD-16-001";
		public static final String ERROR_CODE_SERVICE_NOT_ALLOW = "HYD-16-002";
		public static final String ERROR_CODE_ACCESS_DENIED = "HYD-16-003";
		public static final String ERROR_CODE_TRANSACTION_INVALID = "HYD-16-005";
		public static final String ERROR_CODE_CHECKSUM_INVALID = "HYD-16-006";

	}
}
