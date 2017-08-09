package examples;

import com.devicehive.websocket.WSClient;
import com.devicehive.websocket.api.CommandWS;
import com.devicehive.websocket.api.DeviceWS;
import com.devicehive.websocket.listener.CommandListener;
import com.devicehive.websocket.listener.DeviceListener;
import com.devicehive.websocket.model.repsonse.*;
import com.devicehive.websocket.model.repsonse.data.DeviceVO;

import java.util.List;

public class WebSocketExample {
    private static final String URL = "ws://playground.dev.devicehive.com/api/websocket";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTAyMjgyOTIzOTU3LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.LWnobrxJxRzKzXX8emIulampR0qQUwUXqaFxPW53qu4";

    public static void main(String[] args) {


        WSClient client = new WSClient
                .Builder()
                .url(URL)
                .token(TOKEN)
                .build();


        DeviceWS deviceWS = client.createDeviceWS(new DeviceListener() {
            @Override
            public void onList(List<DeviceVO> response) {
                System.out.println("LIST:" + response.size());
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
        });

        CommandWS commandWS = client.createCommandWS(new CommandListener() {
            @Override
            public void onInsert(CommandInsertResponse response) {
                System.out.println(response);
            }

            @Override
            public void onUpdate(ResponseAction response) {

            }

            @Override
            public void onList(CommandListResponse response) {
                System.out.println(response);

            }

            @Override
            public void onGet(CommandGetResponse response) {

            }

            @Override
            public void onSubscribe(CommandSubscribeResponse response) {

            }

            @Override
            public void onUnsubscribe(ResponseAction response) {

            }

            @Override
            public void onError(ErrorResponse error) {
                System.out.println(error);
            }
        });

        deviceWS.list(null, null, null, null,
                null, null,
                null, 0, 0);
        deviceWS.get(null, "441z79GRgY0QnV9HKrLra8Jt2FXRQ6MzqmuP");
//        deviceWS.delete(null, "1234");
        commandWS.list(null, "3d77f31c-bddd-443b-b11c-640946b0581z4123tzxc3", null, null, "ALARM", null, null, null, null);

    }
}
