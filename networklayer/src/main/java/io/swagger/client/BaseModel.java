package io.swagger.client;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseModel<T> implements Serializable
{
    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("response")
    private T response;

    public int getStatus()
    {
        return status;
    }

    public String getMessage()
    {
        return message;
    }

    public T getResponse()
    {
        return response;
    }
}