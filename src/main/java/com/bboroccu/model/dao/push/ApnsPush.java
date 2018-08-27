package com.bboroccu.model.dao.push;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.PushedNotifications;

/**
 * Created by jae-seongheo on 2016. 9. 17..
 */
public class ApnsPush {
    public String keyStore = "dis.p12";
    public String password = "1234";
    public void sendPush(String alertMessage, boolean isProduction, String deviceToken) throws Exception {
        PushNotificationPayload payload = PushNotificationPayload.alert(alertMessage);
        PushedNotifications notifications = Push.payload(payload, keyStore, password, isProduction, deviceToken);
        PushedNotification notification = notifications.firstElement();
        System.out.print(notification);
    }
}
