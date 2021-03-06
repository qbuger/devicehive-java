/*
 *
 *
 *   Device.java
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

import com.github.devicehive.rest.adapters.NullJsonAdapter;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Device
 */

public class Device {
    @SerializedName("id")
    private String id = null;

    @SerializedName("name")
    private String name = null;
    @JsonAdapter(value = NullJsonAdapter.class)
    @SerializedName("data")
    private JsonObject data = new JsonObject();

    @SerializedName("networkId")
    private Long networkId = null;

    @SerializedName("isBlocked")
    private Boolean isBlocked = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        if (data != null) this.data = data;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "{\n\"Device\":{\n"
                + "\"id\":\"" + id + "\""
                + ",\n \"name\":\"" + name + "\""
                + ",\n \"data\":" + data
                + ",\n \"networkId\":\"" + networkId + "\""
                + ",\n \"isBlocked\":\"" + isBlocked + "\""
                + "}\n}";
    }
}

