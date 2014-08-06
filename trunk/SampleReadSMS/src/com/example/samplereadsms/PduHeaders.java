package com.example.samplereadsms;

public class PduHeaders {
	Object pduHeaders;
	public PduHeaders(Object pduHeaders) {
		this.pduHeaders = pduHeaders;
	}

    /**
     * All pdu header fields.
     */
    public static final int BCC                             = 0x81;
    public static final int CC                              = 0x82;
    public static final int CONTENT_LOCATION                = 0x83;
    public static final int CONTENT_TYPE                    = 0x84;
    public static final int DATE                            = 0x85;
    public static final int DELIVERY_REPORT                 = 0x86;
    public static final int DELIVERY_TIME                   = 0x87;
    public static final int EXPIRY                          = 0x88;
    public static final int FROM                            = 0x89;
    public static final int MESSAGE_CLASS                   = 0x8A;
    public static final int MESSAGE_ID                      = 0x8B;
    public static final int MESSAGE_TYPE                    = 0x8C;
    public static final int MMS_VERSION                     = 0x8D;
    public static final int MESSAGE_SIZE                    = 0x8E;
    public static final int PRIORITY                        = 0x8F;

    public static final int READ_REPLY                      = 0x90;
    public static final int READ_REPORT                     = 0x90;
    public static final int REPORT_ALLOWED                  = 0x91;
    public static final int RESPONSE_STATUS                 = 0x92;
    public static final int RESPONSE_TEXT                   = 0x93;
    public static final int SENDER_VISIBILITY               = 0x94;
    public static final int STATUS                          = 0x95;
    public static final int SUBJECT                         = 0x96;
    public static final int TO                              = 0x97;
    public static final int TRANSACTION_ID                  = 0x98;
    public static final int RETRIEVE_STATUS                 = 0x99;
    public static final int RETRIEVE_TEXT                   = 0x9A;
    public static final int READ_STATUS                     = 0x9B;
    public static final int REPLY_CHARGING                  = 0x9C;
    public static final int REPLY_CHARGING_DEADLINE         = 0x9D;
    public static final int REPLY_CHARGING_ID               = 0x9E;
    public static final int REPLY_CHARGING_SIZE             = 0x9F;

    public static final int PREVIOUSLY_SENT_BY              = 0xA0;
    public static final int PREVIOUSLY_SENT_DATE            = 0xA1;
    public static final int STORE                           = 0xA2;
    public static final int MM_STATE                        = 0xA3;
    public static final int MM_FLAGS                        = 0xA4;
    public static final int STORE_STATUS                    = 0xA5;
    public static final int STORE_STATUS_TEXT               = 0xA6;
    public static final int STORED                          = 0xA7;
    public static final int ATTRIBUTES                      = 0xA8;
    public static final int TOTALS                          = 0xA9;
    public static final int MBOX_TOTALS                     = 0xAA;
    public static final int QUOTAS                          = 0xAB;
    public static final int MBOX_QUOTAS                     = 0xAC;
    public static final int MESSAGE_COUNT                   = 0xAD;
    public static final int CONTENT                         = 0xAE;
    public static final int START                           = 0xAF;

    public static final int ADDITIONAL_HEADERS              = 0xB0;
    public static final int DISTRIBUTION_INDICATOR          = 0xB1;
    public static final int ELEMENT_DESCRIPTOR              = 0xB2;
    public static final int LIMIT                           = 0xB3;
    public static final int RECOMMENDED_RETRIEVAL_MODE      = 0xB4;
    public static final int RECOMMENDED_RETRIEVAL_MODE_TEXT = 0xB5;
    public static final int STATUS_TEXT                     = 0xB6;
    public static final int APPLIC_ID                       = 0xB7;
    public static final int REPLY_APPLIC_ID                 = 0xB8;
    public static final int AUX_APPLIC_ID                   = 0xB9;
    public static final int CONTENT_CLASS                   = 0xBA;
    public static final int DRM_CONTENT                     = 0xBB;
    public static final int ADAPTATION_ALLOWED              = 0xBC;
    public static final int REPLACE_ID                      = 0xBD;
    public static final int CANCEL_ID                       = 0xBE;
    public static final int CANCEL_STATUS                   = 0xBF;

    /**
     * X-Mms-Message-Type field types.
     */
    public static final int MESSAGE_TYPE_SEND_REQ           = 0x80;
    public static final int MESSAGE_TYPE_SEND_CONF          = 0x81;
    public static final int MESSAGE_TYPE_NOTIFICATION_IND   = 0x82;
    public static final int MESSAGE_TYPE_NOTIFYRESP_IND     = 0x83;
    public static final int MESSAGE_TYPE_RETRIEVE_CONF      = 0x84;
    public static final int MESSAGE_TYPE_ACKNOWLEDGE_IND    = 0x85;
    public static final int MESSAGE_TYPE_DELIVERY_IND       = 0x86;
    public static final int MESSAGE_TYPE_READ_REC_IND       = 0x87;
    public static final int MESSAGE_TYPE_READ_ORIG_IND      = 0x88;
    public static final int MESSAGE_TYPE_FORWARD_REQ        = 0x89;
    public static final int MESSAGE_TYPE_FORWARD_CONF       = 0x8A;
    public static final int MESSAGE_TYPE_MBOX_STORE_REQ     = 0x8B;
    public static final int MESSAGE_TYPE_MBOX_STORE_CONF    = 0x8C;
    public static final int MESSAGE_TYPE_MBOX_VIEW_REQ      = 0x8D;
    public static final int MESSAGE_TYPE_MBOX_VIEW_CONF     = 0x8E;
    public static final int MESSAGE_TYPE_MBOX_UPLOAD_REQ    = 0x8F;
    public static final int MESSAGE_TYPE_MBOX_UPLOAD_CONF   = 0x90;
    public static final int MESSAGE_TYPE_MBOX_DELETE_REQ    = 0x91;
    public static final int MESSAGE_TYPE_MBOX_DELETE_CONF   = 0x92;
    public static final int MESSAGE_TYPE_MBOX_DESCR         = 0x93;
    public static final int MESSAGE_TYPE_DELETE_REQ         = 0x94;
    public static final int MESSAGE_TYPE_DELETE_CONF        = 0x95;
    public static final int MESSAGE_TYPE_CANCEL_REQ         = 0x96;
    public static final int MESSAGE_TYPE_CANCEL_CONF        = 0x97;

    /**
     *  X-Mms-Delivery-Report |
     *  X-Mms-Read-Report |
     *  X-Mms-Report-Allowed |
     *  X-Mms-Sender-Visibility |
     *  X-Mms-Store |
     *  X-Mms-Stored |
     *  X-Mms-Totals |
     *  X-Mms-Quotas |
     *  X-Mms-Distribution-Indicator |
     *  X-Mms-DRM-Content |
     *  X-Mms-Adaptation-Allowed |
     *  field types.
     */
    public static final int VALUE_YES                       = 0x80;
    public static final int VALUE_NO                        = 0x81;

    /**
     *  Delivery-Time |
     *  Expiry and Reply-Charging-Deadline |
     *  field type components.
     */
    public static final int VALUE_ABSOLUTE_TOKEN            = 0x80;
    public static final int VALUE_RELATIVE_TOKEN            = 0x81;

    /**
     * X-Mms-MMS-Version field types.
     */
    public static final int MMS_VERSION_1_3                 = ((1 << 4) | 3);
    public static final int MMS_VERSION_1_2                 = ((1 << 4) | 2);
    public static final int MMS_VERSION_1_1                 = ((1 << 4) | 1);
    public static final int MMS_VERSION_1_0                 = ((1 << 4) | 0);

    // Current version is 1.2.
    public static final int CURRENT_MMS_VERSION             = MMS_VERSION_1_2;

    /**
     *  From field type components.
     */
    public static final int FROM_ADDRESS_PRESENT_TOKEN      = 0x80;
    public static final int FROM_INSERT_ADDRESS_TOKEN       = 0x81;

    public static final String FROM_ADDRESS_PRESENT_TOKEN_STR = "address-present-token";
    public static final String FROM_INSERT_ADDRESS_TOKEN_STR = "insert-address-token";

    /**
     *  X-Mms-Status Field.
     */
    public static final int STATUS_EXPIRED                  = 0x80;
    public static final int STATUS_RETRIEVED                = 0x81;
    public static final int STATUS_REJECTED                 = 0x82;
    public static final int STATUS_DEFERRED                 = 0x83;
    public static final int STATUS_UNRECOGNIZED             = 0x84;
    public static final int STATUS_INDETERMINATE            = 0x85;
    public static final int STATUS_FORWARDED                = 0x86;
    public static final int STATUS_UNREACHABLE              = 0x87;

    /**
     *  MM-Flags field type components.
     */
    public static final int MM_FLAGS_ADD_TOKEN              = 0x80;
    public static final int MM_FLAGS_REMOVE_TOKEN           = 0x81;
    public static final int MM_FLAGS_FILTER_TOKEN           = 0x82;

    /**
     *  X-Mms-Message-Class field types.
     */
    public static final int MESSAGE_CLASS_PERSONAL          = 0x80;
    public static final int MESSAGE_CLASS_ADVERTISEMENT     = 0x81;
    public static final int MESSAGE_CLASS_INFORMATIONAL     = 0x82;
    public static final int MESSAGE_CLASS_AUTO              = 0x83;

    public static final String MESSAGE_CLASS_PERSONAL_STR = "personal";
    public static final String MESSAGE_CLASS_ADVERTISEMENT_STR = "advertisement";
    public static final String MESSAGE_CLASS_INFORMATIONAL_STR = "informational";
    public static final String MESSAGE_CLASS_AUTO_STR = "auto";

    /**
     *  X-Mms-Priority field types.
     */
    public static final int PRIORITY_LOW                    = 0x80;
    public static final int PRIORITY_NORMAL                 = 0x81;
    public static final int PRIORITY_HIGH                   = 0x82;

    /**
     *  X-Mms-Response-Status field types.
     */
    public static final int RESPONSE_STATUS_OK                   = 0x80;
    public static final int RESPONSE_STATUS_ERROR_UNSPECIFIED    = 0x81;
    public static final int RESPONSE_STATUS_ERROR_SERVICE_DENIED = 0x82;

    public static final int RESPONSE_STATUS_ERROR_MESSAGE_FORMAT_CORRUPT     = 0x83;
    public static final int RESPONSE_STATUS_ERROR_SENDING_ADDRESS_UNRESOLVED = 0x84;

    public static final int RESPONSE_STATUS_ERROR_MESSAGE_NOT_FOUND    = 0x85;
    public static final int RESPONSE_STATUS_ERROR_NETWORK_PROBLEM      = 0x86;
    public static final int RESPONSE_STATUS_ERROR_CONTENT_NOT_ACCEPTED = 0x87;
    public static final int RESPONSE_STATUS_ERROR_UNSUPPORTED_MESSAGE  = 0x88;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_FAILURE    = 0xC0;

    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_SENDNG_ADDRESS_UNRESOLVED = 0xC1;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_MESSAGE_NOT_FOUND         = 0xC2;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_NETWORK_PROBLEM           = 0xC3;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_PARTIAL_SUCCESS           = 0xC4;

    public static final int RESPONSE_STATUS_ERROR_PERMANENT_FAILURE                             = 0xE0;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_SERVICE_DENIED                      = 0xE1;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_MESSAGE_FORMAT_CORRUPT              = 0xE2;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_SENDING_ADDRESS_UNRESOLVED          = 0xE3;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_MESSAGE_NOT_FOUND                   = 0xE4;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_CONTENT_NOT_ACCEPTED                = 0xE5;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_LIMITATIONS_NOT_MET  = 0xE6;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_REQUEST_NOT_ACCEPTED = 0xE6;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_FORWARDING_DENIED    = 0xE8;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_NOT_SUPPORTED        = 0xE9;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_ADDRESS_HIDING_NOT_SUPPORTED        = 0xEA;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_LACK_OF_PREPAID                     = 0xEB;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_END                                 = 0xFF;

    /**
     *  X-Mms-Retrieve-Status field types.
     */
    public static final int RETRIEVE_STATUS_OK                                  = 0x80;
    public static final int RETRIEVE_STATUS_ERROR_TRANSIENT_FAILURE             = 0xC0;
    public static final int RETRIEVE_STATUS_ERROR_TRANSIENT_MESSAGE_NOT_FOUND   = 0xC1;
    public static final int RETRIEVE_STATUS_ERROR_TRANSIENT_NETWORK_PROBLEM     = 0xC2;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_FAILURE             = 0xE0;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_SERVICE_DENIED      = 0xE1;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_MESSAGE_NOT_FOUND   = 0xE2;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_CONTENT_UNSUPPORTED = 0xE3;
    public static final int RETRIEVE_STATUS_ERROR_END                           = 0xFF;

    /**
     *  X-Mms-Sender-Visibility field types.
     */
    public static final int SENDER_VISIBILITY_HIDE          = 0x80;
    public static final int SENDER_VISIBILITY_SHOW          = 0x81;

    /**
     *  X-Mms-Read-Status field types.
     */
    public static final int READ_STATUS_READ                        = 0x80;
    public static final int READ_STATUS__DELETED_WITHOUT_BEING_READ = 0x81;

    /**
     *  X-Mms-Cancel-Status field types.
     */
    public static final int CANCEL_STATUS_REQUEST_SUCCESSFULLY_RECEIVED = 0x80;
    public static final int CANCEL_STATUS_REQUEST_CORRUPTED             = 0x81;

    /**
     *  X-Mms-Reply-Charging field types.
     */
    public static final int REPLY_CHARGING_REQUESTED           = 0x80;
    public static final int REPLY_CHARGING_REQUESTED_TEXT_ONLY = 0x81;
    public static final int REPLY_CHARGING_ACCEPTED            = 0x82;
    public static final int REPLY_CHARGING_ACCEPTED_TEXT_ONLY  = 0x83;

    /**
     *  X-Mms-MM-State field types.
     */
    public static final int MM_STATE_DRAFT                  = 0x80;
    public static final int MM_STATE_SENT                   = 0x81;
    public static final int MM_STATE_NEW                    = 0x82;
    public static final int MM_STATE_RETRIEVED              = 0x83;
    public static final int MM_STATE_FORWARDED              = 0x84;

    /**
     * X-Mms-Recommended-Retrieval-Mode field types.
     */
    public static final int RECOMMENDED_RETRIEVAL_MODE_MANUAL = 0x80;

    /**
     *  X-Mms-Content-Class field types.
     */
    public static final int CONTENT_CLASS_TEXT              = 0x80;
    public static final int CONTENT_CLASS_IMAGE_BASIC       = 0x81;
    public static final int CONTENT_CLASS_IMAGE_RICH        = 0x82;
    public static final int CONTENT_CLASS_VIDEO_BASIC       = 0x83;
    public static final int CONTENT_CLASS_VIDEO_RICH        = 0x84;
    public static final int CONTENT_CLASS_MEGAPIXEL         = 0x85;
    public static final int CONTENT_CLASS_CONTENT_BASIC     = 0x86;
    public static final int CONTENT_CLASS_CONTENT_RICH      = 0x87;

    /**
     *  X-Mms-Store-Status field types.
     */
    public static final int STORE_STATUS_SUCCESS                                = 0x80;
    public static final int STORE_STATUS_ERROR_TRANSIENT_FAILURE                = 0xC0;
    public static final int STORE_STATUS_ERROR_TRANSIENT_NETWORK_PROBLEM        = 0xC1;
    public static final int STORE_STATUS_ERROR_PERMANENT_FAILURE                = 0xE0;
    public static final int STORE_STATUS_ERROR_PERMANENT_SERVICE_DENIED         = 0xE1;
    public static final int STORE_STATUS_ERROR_PERMANENT_MESSAGE_FORMAT_CORRUPT = 0xE2;
    public static final int STORE_STATUS_ERROR_PERMANENT_MESSAGE_NOT_FOUND      = 0xE3;
    public static final int STORE_STATUS_ERROR_PERMANENT_MMBOX_FULL             = 0xE4;
    public static final int STORE_STATUS_ERROR_END                              = 0xFF;

    
}
