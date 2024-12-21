package com.mobile.wishtrack.ui;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.mobile.wishtrack.R;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Notification {

    //TODO 현재 알림 종류가 하나라 거의 하드코딩한 수준이다. 변경하는게 좋다.
    private static final String CHANNEL_ID = "price_update_channel";
    private static final String GROUP_KEY = "price_update_group";

    public static boolean checkAndRequestNotificationPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                if (context instanceof Activity) {
                    ActivityCompat.requestPermissions(
                            (android.app.Activity) context,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            101
                    );
                }
                return false;
            }
        }
        return true;
    }

    public static void sendGroupedNotifications(Context context, GroupMessage groupMessage) {
        if (groupMessage.entities.isEmpty()) return;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return;
        createNotificationChannel(context);

        List<GroupMessage.GroupEntity> contents = groupMessage.getEntities();
        for (int i = 0; i < contents.size(); i++) {
            GroupMessage.GroupEntity content = contents.get(i);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(content.getIcon())
                    .setContentTitle(content.getTitle())
                    .setContentText(content.getText())
                    .setGroup(GROUP_KEY)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);

            NotificationManagerCompat.from(context).notify(i, builder.build());
        }

        NotificationCompat.Builder summaryBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_keyboard_double_arrow_down_24)
                .setContentTitle(groupMessage.getGroupTitle())
                .setContentText(groupMessage.getGroupText())
                .setStyle(new NotificationCompat.InboxStyle()
                        .setSummaryText(groupMessage.getGroupSummaryText())
                        .addLine(contents.get(0).getTitle())
                        .addLine("..."))
                .setGroup(GROUP_KEY)
                .setGroupSummary(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat.from(context).notify(0, summaryBuilder.build());
    }

    public static void sendNotification(Context context, int icon, String title, String text) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return;
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Price Update Notifications";
            String description = "Notifies when product prices are updated";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Getter
    @Setter
    public static class GroupMessage {
        private int groupIcon;
        private String groupTitle;
        private String groupText;
        private String groupSummaryText;
        private final List<GroupEntity> entities;

        public GroupMessage(int groupIcon, String groupTitle){
            this.groupIcon = groupIcon;
            this.groupTitle = groupTitle;
            entities = new ArrayList<>();
        }

        public GroupMessage(){
            entities = new ArrayList<>();
        }

        public void addContent(int icon, String title, String text){
            entities.add(new GroupEntity(icon, title, text));
        }

        @Getter
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        public static class GroupEntity {
            private final int icon;
            private final String title;
            private final String text;
        }
    }
}
