package Exceptions;

import org.json.JSONException;

public class JSONNotFound extends JSONException {
    public JSONNotFound(String message){
        super(message);
    }
}
