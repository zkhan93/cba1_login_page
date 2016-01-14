package io.github.zkhan93.contactpage.util;

/**
 * Created by Zeeshan Khan on 1/8/2016.
 */
public interface Constants {
    interface URL{
        int PORT=8080;
        String HOST="192.168.2.11";
        String BASE="http://"+HOST+":"+PORT+"/CBA2ContactPage/";
        String LOGIN=BASE+"login";
        String GET_CONTACTS=BASE+"getcontacts";
    }
    interface PREF_KEYS{
        String USERNAME="username";
        String PASSWORD="password";
    }
    interface JSON_KEYS{
        String USERNAME="username";
        String PASSWORD="password";
        String AUTHENTICATION="authentication";
        String CONTACTS="contacts";
        interface Contacts{
            String ID="id";
            String NAME="name";
            String NUMBER="number";
        }
    }
}