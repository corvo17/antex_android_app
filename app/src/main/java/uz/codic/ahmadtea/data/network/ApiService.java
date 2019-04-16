package uz.codic.ahmadtea.data.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.MyWorkspace;
import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.data.network.model.BeforeSyncObject;
import uz.codic.ahmadtea.data.network.model.CentralObject;
import uz.codic.ahmadtea.data.network.model.DailyBody;
import uz.codic.ahmadtea.data.network.model.DailyMerchants;
import uz.codic.ahmadtea.data.network.model.Employee;
import uz.codic.ahmadtea.data.network.model.EmployeeLocation;
import uz.codic.ahmadtea.data.network.model.ErrorObject;
import uz.codic.ahmadtea.data.network.model.LocationResponse;
import uz.codic.ahmadtea.data.network.model.Login;
import uz.codic.ahmadtea.data.network.model.Message;
import uz.codic.ahmadtea.data.network.model.ObjectsForEmployee;
import uz.codic.ahmadtea.data.network.model.Send;
import uz.codic.ahmadtea.data.network.model.SendResponse;
import uz.codic.ahmadtea.data.network.model.SharedObject;
import uz.codic.ahmadtea.data.network.model.Synchronisation;
import uz.codic.ahmadtea.data.network.model.Token;
import uz.codic.ahmadtea.data.network.model.WorkspaceRelations;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.data.network.model.api_objects.Payload;

public interface ApiService {

    //Login request
    @Headers({
            "X-Requested-With: XMLHttpRequest"
    })
    @POST(ApiEndPoint.ENDPOINT_LOGIN)
    Single<Token> requestToken(@Body Login login);

    //Employee info
    @GET(ApiEndPoint.ENDPOINT_EMPLOYEE_INFO)
    Single<Employee> requestEmployeInfo(@Header("X-Authorization")String token);

    //List Emloyee_workspace to employee
    @GET(ApiEndPoint.ENDPOINT_REQUEST_WORKSPACE_EMPLOYEE)
    Single<ApiObeject<MyWorkspace>> requestWorkspace(@Header("X-Authorization")String token);

    @GET(ApiEndPoint.ENDPOINT_GET_SHADER_OBJECT)
    Single<ApiObeject<Payload>> requestAllSharedObjects(@Header("X-Authorization")String token);


    //Send Request
    @Headers({
            "Routing-Key:" + ApiEndPoint.ROUTING_KEY_SEND_ORDER
    })
    @POST(ApiEndPoint.ENDPOINT_SEND)
    Single<SendResponse> requestSend(@Header("X-Authorization")String token, @Body Send send);


    //Send DRAFT Request
    @Headers({
            "Routing-Key:" + ApiEndPoint.ROUTING_KEY_SEND_AS_DRAFT_ORDER
    })
    @POST(ApiEndPoint.ENDPOINT_SEND)
    Single<SendResponse> requestSendDraft(@Header("X-Authorization")String token, @Body Send send);

    //Send pending Request
    @Headers({
            "Routing-Key:" + ApiEndPoint.ROUTING_KEY_SEND_PENDING_ORDER
    })
    @POST(ApiEndPoint.ENDPOINT_SEND)
    Single<SendResponse> requestSendPending(@Header("X-Authorization")String token, @Body Send send);

    //Send Request
    @Headers({
            "Routing-Key:" + ApiEndPoint.ROUTING_KEY_PLANNING_FOR_TODAY
    })
    @POST(ApiEndPoint.ENDPOINT_REQUEST)
    Single<DailyMerchants> requestDailyMerchants(@Header("X-Authorization")String token, @Body DailyBody body);

    //Location
    @POST(ApiEndPoint.ENDPOINT_LOCATION_INSERT)
    Call<LocationResponse> sendLocation(@Header("X-Authorization")String token, @Body List<EmployeeLocation> employeeLocations);

    @GET(ApiEndPoint.ENDPOINT_REQUEST_SYNCHRONISATION)
    Single<ApiObeject<Synchronisation>> requestSynchronization(@Header("X-Authorization")String token);

    // new workspace relation
    @GET(ApiEndPoint.ENDPOINT_REQUEST_WORKSPACE_RELATION)
    Single<ApiObeject<WorkspaceRelations>> getWorkspaceRelations(@Header("X-Authorization")String token);

    // Send Request before Synchronisation
    @Headers({
            "Routing-Key:" + ApiEndPoint.ENDPOINT_KEY_BEFORE_SYNC
    })
    @POST(ApiEndPoint.ENDPOINT_REQUEST_V1_SEND)
    Single<BeforeSyncObject> requesBeforeSync(@Header("X-Authorization")String token);

    @Multipart
    @POST(ApiEndPoint.Attach_File)
    Single<ResponseBody> uploadeBook(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part fileAttached
    );

    //Send add merchant
    @Headers({
            "Routing-Key:" + ApiEndPoint.ROUTING_KEY_ADD_MERCHANT
    })
    @POST(ApiEndPoint.ENDPOINT_REQUEST)
    Single<SendResponse> requestAddMerchant(@Header("X-Authorization")String token, @Body HashMap<String, uz.codic.ahmadtea.data.network.model.Merchant> hashMap);

    @POST(ApiEndPoint.ERROR_SEND)
    Single<ErrorObject> sendError(@Header("X-Authorization")String token, @Body ErrorObject errorObject);

}
