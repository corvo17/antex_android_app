package uz.codic.ahmadtea.data.network;

public class ApiEndPoint {

    public static final String BASE_URL = "http://t2.oltinolma.uz/";
    public static final String IMAGE_URL = "http://192.168.0.108:3088/";

    public static final String Attach_File = "Books/post/file";
    public static final String GET_File = "Books/All";

    public static final String ENDPOINT_LOGIN = "/auth/login";

    public static final String ENDPOINT_EMPLOYEE_INFO = "/employee/getme";

    public static final String ENDPOINT_REQUEST = "/v1/request";

    public static final String ENDPOINT_REQUEST_WORKSPACE_EMPLOYEE = "/workspaces/employee";

    public static final String ENDPOINT_REQUEST_WORKSPACE_RELATION = "/workspace/relations";

    public static final String ENDPOINT_SEND = "/v1/order/send";

    public static final String ENDPOINT_GET_SHADER_OBJECT = "/reference/mobile_app";

    public static final String ENDPOINT_LOCATION_INSERT = "/employees/locations/insert";

    public static final String ROUTING_KEY_SYNC= "workspaces.sync";

    public static final String ROUTING_KEY_LIST_BY_LOGIN = "workspaces.list.by.login";

    public static final String ROUTING_KEY_INFO_BY_LOGIN = "workspaces.user.info.by.login";

    public static final String ROUTING_KEY_SEND_ORDER= "order.take";

    public static final String ROUTING_KEY_SEND_AS_DRAFT_ORDER= "order.save.as.draft";

    public static final String ROUTING_KEY_ADD_MERCHANT = "merchant.insertAndSetId";

    public static final String ROUTING_KEY_ADD_MERCHANT_RESULT = "merchant.by.id";

    public static final String ROUTING_KEY_SEND_PENDING_ORDER= "order.take";

    public static final String ROUTING_KEY_PLANNING_FOR_TODAY= "planning.for.today";

    public static final String ENDPOINT_KEY_WORKSPACE_RELATIONS= "workspace.relations";

    public static final String ENDPOINT_KEY_BEFORE_SYNC= "set.sync.time";

    public static final String ENDPOINT_REQUEST_V1_SEND = "/v1/send";

    public static final String ERROR_SEND = "/log";

    public static final String ENDPOINT_REQUEST_CENTRAL_CERVER = "/open/register/info";



}
