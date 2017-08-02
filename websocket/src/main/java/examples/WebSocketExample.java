package examples;

import com.devicehive.websocket.WSClient;
import com.devicehive.websocket.api.DeviceWS;
import com.devicehive.websocket.api.TokenWS;
import com.devicehive.websocket.listener.DeviceListener;
import com.devicehive.websocket.listener.LoginListener;
import com.devicehive.websocket.model.repsonse.ErrorResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.devicehive.websocket.model.repsonse.TokenRefreshResponse;
import com.devicehive.websocket.model.repsonse.data.DeviceVO;

import java.util.List;

public class WebSocketExample {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";

    public static void main(String[] args) {


        WSClient client = new WSClient
                .Builder()
                .url(URL)
                .build();


        final TokenWS loginWS = client.createLoginWS(new LoginListener() {
            @Override
            public void onGet(TokenGetResponse response) {
                System.out.println(response);

            }

            @Override
            public void onCreate(TokenGetResponse response) {
                System.out.println(response);
            }

            @Override
            public void onRefresh(TokenRefreshResponse response) {
                System.out.println(response);
            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println("TokenWS:" + error);

            }
        });
        loginWS.get(null, "dhadmin", "dhadmin_#911");


        DeviceListener deviceListener = new DeviceListener() {
            @Override
            public void onList(List<DeviceVO> response) {
                System.out.println("LIST:" + response);
            }

            @Override
            public void onGet(DeviceVO response) {
                System.out.println("Single:" + response);
            }

            @Override
            public void onDelete(ResponseAction response) {

            }

            @Override
            public void onSave(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println("DeviceListener:" + error);
            }
        };
        DeviceWS deviceWS = client.createDeviceWS(deviceListener);

        deviceWS.list(null, null, null, null,
                null, null,
                null, 0, 0);
        deviceWS.get(null, "441z79GRgY0QnV9HKrLra8Jt2FXRQ6MzqmuP");
        deviceWS.delete(null, "1234");


    }
}
