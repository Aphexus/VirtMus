package com.ebixio.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.ErrorManager;
import org.openide.awt.NotificationDisplayer;

/**
 * @author qbeukes.blogspot.com, used by metalklesk
 */
public class NotifyUtil {

    private NotifyUtil() {}

    /**
    * Show message with the specified type and action listener
    * @param title Notification title
    * @param message Notification message
    * @param type Notification type
    * @param actionListener An action listener
    */
    public static void show(String title, String message, MessageType type, ActionListener actionListener) {
        NotificationDisplayer.getDefault().notify(title, type.getIcon(), message, actionListener);
    }

    /**
    * Show message with the specified type and a default action which displays the
    * message using {@link MessageUtil} with the same message type
    * @param title Notification title
    * @param message Notification message
    * @param type Notification type
    */
    public static void show(String title, final String message, final MessageType type) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageUtil.show(message, type);
            }
        };

        show(title, message, type, actionListener);
    }

    /**
    * Show an information notification
    * @param title Notification title
    * @param message Notification message
    */
    public static void info(String title, String message) {
        show(title, message, MessageType.INFO);
    }

    /**
    * Show an error notification
    * @param title Notification title
    * @param message Notification message
    */
    public static void error(String title, String message) {
        show(title, message, MessageType.ERROR);
    }

    /**
    * Show an error notification for an exception
    * @param title Notification title
    * @param message Notification message
    * @param exception Exception
    */
    public static void error(String title, final String message, final Throwable exception) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ErrorManager.getDefault ().notify (exception);
                //MessageUtil.showException(message, exception);
            }
        };

        show(title, message, MessageType.ERROR, actionListener);
    }

    /**
    * Show an warning notification
    * @param title Notification title
    * @param message Notification message
    */
    public static void warn(String title, String message) {
        show(title, message, MessageType.WARNING);
    }

    /**
    * Show an plain notification
    * @param title Notification title
    * @param message Notification message
    */
    public static void plain(String title, String message) {
        show(title, message, MessageType.PLAIN);
    }

}