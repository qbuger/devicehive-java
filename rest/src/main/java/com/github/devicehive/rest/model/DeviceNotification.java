/*
 *
 *
 *   DeviceNotification.java
 *
 *   Copyright (C) 2018 DataArt
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

/*
 * Device Hive REST API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 3.3.0-SNAPSHOT
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.github.devicehive.rest.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

/**
 * DeviceNotification
 */

public class DeviceNotification implements Comparable<DeviceNotification> {
    @SerializedName("id")
    private Long id = null;

    @SerializedName("notification")
    private String notification = null;

    @SerializedName("deviceId")
    private String deviceId = null;

    @SerializedName("networkId")
    private Long networkId = null;

    @SerializedName("timestamp")
    private DateTime timestamp = null;

    @SerializedName("parameters")
    private JsonObject parameters = new JsonObject();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public JsonObject getParameters() {
        return parameters;
    }

    public void setParameters(JsonObject parameters) {
        if (parameters != null) this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "{\n\"DeviceNotification\":{\n"
                + "\"id\":\"" + id + "\""
                + ",\n \"notification\":\"" + notification + "\""
                + ",\n \"deviceId\":\"" + deviceId + "\""
                + ",\n \"networkId\":\"" + networkId + "\""
                + ",\n \"timestamp\":" + timestamp
                + ",\n \"parameters\":" + parameters
                + "}\n}";
    }

    @Override
    public int compareTo(DeviceNotification notification) {
        return getTimestamp().compareTo(notification.getTimestamp());
    }

}

