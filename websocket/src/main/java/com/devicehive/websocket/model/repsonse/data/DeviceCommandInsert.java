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


package com.devicehive.websocket.model.repsonse.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * DeviceCommand
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-07-20T15:03:42.016+03:00")
@Data
public class DeviceCommandInsert implements Comparable<DeviceCommandInsert> {

    @SerializedName("command")
    DeviceCommand command;
    @SerializedName("subscriptionId")
    private Long subscriptionId;


    @Override
    public int compareTo(DeviceCommandInsert o) {
        return this.command.getTimestamp().compareTo(o.command.getTimestamp());
    }
}

