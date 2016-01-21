package io.github.zkhan93.mailui.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.zkhan93.mailui.R;
import io.github.zkhan93.mailui.model.Mail;
import io.github.zkhan93.mailui.model.User;

/**
 * Created by 1036870 on
 * 1/20/2016.
 */
public class Util {
    public static final String TAG = "Util";

    public static JSONObject getCredentials(Context context) {
        SharedPreferences spf = context.getSharedPreferences(context.getString(R.string.pref_filename), Context.MODE_PRIVATE);
        JSONObject params = new JSONObject();
        try {
            params.put("username", spf.getString(Constants.PREF_KEYS.USERNAME, ""));
            params.put("password", spf.getString(Constants.PREF_KEYS.PASSWORD, ""));
        } catch (JSONException ex) {
            Util.log(TAG, ex.getLocalizedMessage());
        }
        return params;
    }

    public static String getTimeStr(long milisec) {
        return new SimpleDateFormat("DD, mm HH:MM:SS", Locale.ENGLISH).format(new Date(milisec));
    }

    public static void log(String TAG, String error) {
        Log.d(TAG, error);
    }

    public static List<Mail> getMails(JSONArray jmails, User self) {
        List<Mail> mails = new ArrayList<Mail>();
        List<User> ccs, bccs;
        int len = jmails.length();
        Mail mail;
        User sender, cc, bcc;
        JSONObject jsender, jCcUser, jBccUser;
        JSONObject jmail;
        JSONArray jCc, jBcc;
        for (int i = 0; i < len; i++) {
            mail = new Mail();
            try {
                jmail = jmails.getJSONObject(i);
                mail.setBody(jmail.optString(Constants.JSON_KEYS.MAIL.BODY));
                mail.setSubject(jmail.optString(Constants.JSON_KEYS.MAIL.SUBJECT));
                mail.setId(jmail.optInt(Constants.JSON_KEYS.MAIL.ID));
                jsender = jmail.optJSONObject(Constants.JSON_KEYS.MAIL.SENDER);
                sender = new User();
                sender.setId(jsender.optInt(Constants.JSON_KEYS.User.ID));
                sender.setEmail(jsender.optString(Constants.JSON_KEYS.User.EMAIL));
                sender.setUsername(jsender.optString(Constants.JSON_KEYS.User.USERNAME));
                mail.setSender(sender);
                mail.setReceiver(self);
                mails.add(mail);
            } catch (JSONException ex) {
                Util.log(TAG, ex.getLocalizedMessage());
            }
        }
        return mails;
    }

    public static User getSelf(Context context) {
        SharedPreferences spf = context.getSharedPreferences(context.getString(R.string.pref_filename), Context.MODE_PRIVATE);
        User user = new User();
        user.setEmail(spf.getString(Constants.PREF_KEYS.EMAIL, ""));
        user.setUsername(spf.getString(Constants.PREF_KEYS.USERNAME, ""));
        user.setId(-1);
        return user;
    }

    public static String getPriority(int priority) {
        return "HIGH";
    }

    public static List<Mail> getTestMails() {
        List<Mail> mails = new ArrayList<Mail>();
        User receiver = new User();
        receiver.setUsername("zeeshan");
        receiver.setEmail("zkhan1093@gmail.com");
        Mail mail;
        User sender;
        for (int i = 0; i < 10; i++) {
            sender = new User();
            sender.setEmail("email" + i + "@mail.com");
            sender.setUsername("sender" + i);
            mail = new Mail();
            mail.setReceiver(receiver);
            mail.setSender(sender);
            mail.setSubject("subject of mail " + i + " from sender" + i);
            mail.setBody("body of mail " + i + " from sender" + i);
            mails.add(mail);
        }
        return mails;
    }
}
