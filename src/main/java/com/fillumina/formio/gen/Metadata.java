package com.fillumina.formio.gen;

import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class Metadata {
    
    private final String timezone;
    private final Integer offset;
    private final String origin;
    private final String referrer;
    private final String browserName;
    private final String userAgent;
    private final String pathName;
    private final Boolean online;

    public Metadata(JSONObject json) {
        JSONObject metadata = json.getJSONObject("metadata");
        timezone = (String) metadata.get("timezone");
        offset = (Integer) metadata.get("offset");
        origin = (String) metadata.get("origin");
        referrer = (String) metadata.get("referrer");
        browserName = (String) metadata.get("browserName");
        userAgent = (String) metadata.get("userAgent");
        pathName = (String) metadata.get("pathName");
        online = (Boolean) metadata.get("onLine");
    }

    public String getTimezone() {
        return timezone;
    }

    public int getOffset() {
        return offset;
    }

    public String getOrigin() {
        return origin;
    }

    public String getReferrer() {
        return referrer;
    }

    public String getBrowserName() {
        return browserName;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getPathName() {
        return pathName;
    }

    public boolean isOnline() {
        return online;
    }
}
