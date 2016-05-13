package com.devicehive.client.api;

import com.devicehive.client.model.DeviceNotification;
import com.devicehive.client.model.DeviceNotificationWrapper;
import com.devicehive.client.model.NotificationPollManyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceNotificationApi {
  /**
   * Get notification
   * Returns notification by device guid and notification id
   * @param deviceGuid Device GUID (required)
   * @param id Notification id (required)
   * @return Call<Void>
   */
  
  @GET("device/{deviceGuid}/notification/{id}")
  Call<DeviceNotification> get(
          @Path("deviceGuid") String deviceGuid, @Path("id") Long id
  );

  /**
   * Create notification
   * Creates notification
   * @param deviceGuid Device GUID (required)
   * @param body Notification body (required)
   * @return Call<Void>
   */
  
  @POST("device/{deviceGuid}/notification")
  Call<DeviceNotification> insert(
          @Path("deviceGuid") String deviceGuid, @Body DeviceNotificationWrapper body
  );

  /**
   * Poll for notifications 
   * Polls for notifications based on provided parameters (long polling)
   * @param deviceGuid Device GUID (required)
   * @param names Notification names (optional)
   * @param timestamp Timestamp to start from (optional)
   * @param waitTimeout Wait timeout (optional, default to 30)
   * @return Call<Void>
   */
  
  @GET("device/{deviceGuid}/notification/poll")
  Call<List<DeviceNotification>> poll(
          @Path("deviceGuid") String deviceGuid, @Query("names") String names, @Query("timestamp") String timestamp, @Query("waitTimeout") Long waitTimeout
  );

  /**
   * Poll for notifications 
   * Polls for notifications based on provided parameters (long polling)
   * @param waitTimeout Wait timeout (optional, default to 30)
   * @param deviceGuids Device guids (optional)
   * @param names Notification names (optional)
   * @param timestamp Timestamp to start from (optional)
   * @return Call<Void>
   */
  
  @GET("device/notification/poll")
  Call<NotificationPollManyResponse> pollMany(
          @Query("waitTimeout") Long waitTimeout, @Query("deviceGuids") String deviceGuids, @Query("names") String names, @Query("timestamp") String timestamp
  );

  /**
   * Get notifications
   * Returns notifications by provided parameters
   * @param deviceGuid Device GUID (required)
   * @param start Start timestamp (optional)
   * @param end End timestamp (optional)
   * @param notification Notification name (optional)
   * @param sortField Sort field (optional)
   * @param sortOrder Sort order (optional)
   * @param take Limit param (optional)
   * @param skip Skip param (optional)
   * @param gridInterval Grid interval (optional)
   * @return Call<Void>
   */
  
  @GET("device/{deviceGuid}/notification")
  Call<DeviceNotification> query(
          @Path("deviceGuid") String deviceGuid, @Query("start") String start, @Query("end") String end, @Query("notification") String notification, @Query("sortField") String sortField, @Query("sortOrder") String sortOrder, @Query("take") Integer take, @Query("skip") Integer skip, @Query("gridInterval") Integer gridInterval
  );

}
