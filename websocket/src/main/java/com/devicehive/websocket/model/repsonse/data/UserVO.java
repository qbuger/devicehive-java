/*
 *
 *
 *   UserVO.java
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

import com.devicehive.rest.model.JsonStringWrapper;
import com.devicehive.websocket.model.RoleEnum;
import com.devicehive.websocket.model.StatusEnum;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * UserVO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-07-20T15:03:42.016+03:00")
@Data
public class UserVO {
  @SerializedName("id")
  private Long id = null;

  @SerializedName("login")
  private String login = null;

  @SerializedName("role")
  private RoleEnum role = null;

  @SerializedName("status")
  private StatusEnum status = null;

  @SerializedName("lastLogin")
  private DateTime lastLogin = null;

  @SerializedName("data")
  private JsonStringWrapper data = null;

  @SerializedName("introReviewed")
  private Boolean introReviewed = false;

}

