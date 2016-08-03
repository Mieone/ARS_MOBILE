package heardun.in.ars;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by headrun on 15/10/15.
 */

public class Usersession {

    public static final String SESSION = "session";
    public static final String userlogin_name = "loginname";

    public String TAG = Usersession.this.getClass().getSimpleName();
    public SharedPreferences pref;
    public Context _context;
    public SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "Ars";

    public Usersession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public void setLoginname(String loginname) {
        editor.putString(userlogin_name, loginname);
        editor.commit();

    }

    public String getloginname() {
        return pref.getString(userlogin_name, "");
    }

    public void setusersession(String session) {
        editor.putString(SESSION, session);
        editor.commit();
    }

    public String getusersession() {
        return pref.getString(SESSION, "");
    }

    public void clearsession() {
        editor.remove(SESSION);
        editor.commit();
    }
}