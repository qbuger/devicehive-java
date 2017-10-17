/*
 *
 *
 *   DeviceWS.java
 *
 *   Copyright (C) 2017 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.devicehive.websocket.api;

import com.devicehive.websocket.listener.DeviceListener;
import com.devicehive.websocket.model.repsonse.DeviceGetResponse;
import com.devicehive.websocket.model.repsonse.DeviceListResponse;
import com.devicehive.websocket.model.repsonse.ResponseAction;
import com.devicehive.websocket.model.repsonse.data.DeviceVO;
import com.devicehive.websocket.model.request.DeviceDeleteAction;
import com.devicehive.websocket.model.request.DeviceGetAction;
import com.devicehive.websocket.model.request.DeviceListAction;
import com.devicehive.websocket.model.request.DeviceSaveAction;
import lombok.NonNull;
import okhttp3.WebSocket;

import static com.devicehive.websocket.model.ActionConstant.*;

public class DeviceWS extends BaseWebSocketApi implements DeviceApi {

    static final String TAG = "device";
    private final DeviceListener listener;

    DeviceWS(WebSocket ws, DeviceListener listener) {
        super(ws, listener);
        this.listener = listener;
    }

    @Override
    public String getKey() {
        return TAG;
    }


    @Override
    public void onSuccess(String message) {
        ResponseAction action = getResponseAction(message);
        String actionName = action.getAction();
        if (actionName.equalsIgnoreCase(DEVICE_LIST)) {
            DeviceListResponse response = gson.fromJson(message, DeviceListResponse.class);
            listener.onList(response.getDevices());
        } else if (actionName.equalsIgnoreCase(DEVICE_GET)) {
            DeviceGetResponse response = gson.fromJson(message, DeviceGetResponse.class);
            listener.onGet(response.getDevice());
        } else if (actionName.equalsIgnoreCase(DEVICE_DELETE)) {
            listener.onDelete(action);
        } else if (actionName.equalsIgnoreCase(DEVICE_SAVE)) {
            listener.onSave(action);
        }
    }

    @Override
    public void get( Long requestId, String deviceId) {
        DeviceGetAction action = new DeviceGetAction();
        action.setDeviceId(deviceId);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void list( Long requestId, String name, String namePattern, Long networkId, String networkName, String sortField, String sortOrder, int take, int skip) {
        DeviceListAction action = new DeviceListAction();
        action.setName(name);
        action.setNamePattern(namePattern);
        action.setNetworkId(networkId);
        action.setNetworkName(networkName);
        action.setSortField(sortField);
        action.setSortOrder(sortOrder);
        action.setTake(take);
        action.setSkip(skip);
        action.setRequestId(requestId);

        send(action);
    }

    @Override
    public void save( Long requestId,  DeviceVO device) {
        DeviceSaveAction action = new DeviceSaveAction();
        action.setDevice(device);
        action.setRequestId(requestId);
        send(action);
    }


    @Override
    public void delete( Long requestId,  String deviceId) {
        DeviceDeleteAction action = new DeviceDeleteAction();
        action.setDeviceId(deviceId);
        action.setRequestId(requestId);
        send(action);
    }
}
