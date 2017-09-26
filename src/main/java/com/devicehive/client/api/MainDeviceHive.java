package com.devicehive.client.api;

import com.devicehive.client.model.*;
import com.devicehive.client.model.Device;
import com.devicehive.rest.model.*;
import okhttp3.WebSocketListener;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public interface MainDeviceHive {

    DHResponse<ApiInfo> getInfo() throws IOException;

    DHResponse<ClusterConfig> getClusterInfo();

    DHResponse<JwtToken> createToken(List<String> actions, Long userId, List<String> networkIds, List<String> deviceIds, DateTime expiration) throws IOException;

    DHResponse<JwtAccessToken> refreshToken() throws IOException;

    DHResponse<Configuration> getProperty(String name) throws IOException;

    DHResponse<Configuration> setProperty(String name, String value) throws IOException;

    DHResponse<Void> removeProperty(String name) throws IOException;

    void subscribeCommands(List<String> ids, WebSocketListener callback, CommandFilter commandFilter);

    void subscribeNotifications(List<String> ids, WebSocketListener callback, NameFilter nameFilter);

    void unsubscribeCommands(List<String> ids, CommandFilter commandFilter);

    void unsubscribeNotifications(List<String> ids, NameFilter nameFilter);

    DHResponse<List<Network>> listNetworks(NetworkFilter filter) throws IOException;

    DHResponse<NetworkVO> getNetwork(long id) throws IOException;

    DHResponse<Void> removeNetwork(long id) throws IOException;

    DHResponse<NetworkId> createNetwork(String name, String description) throws IOException;

    DHResponse<List<Device>> listDevices(DeviceFilter filter);

    DHResponse<Void> removeDevice(String id);

    DHResponse<Device> getDevice(String id) throws IOException;

    DHResponse<Void> putDevice(String id, String name) throws IOException;
}
