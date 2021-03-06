/**
 * INSTACLARITY TEACHER APIS
 * INSTACLARITY TEACHER APIS
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.swagger.client.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class Token implements Serializable
{
	
	
	@SerializedName("access_token")
	private String accessToken = null;
	
	@SerializedName("expires_in")
	private String expiresIn = null;
	
	@SerializedName("refresh_token")
	private String refreshToken = null;
	
	@SerializedName("scope")
	private String scope = null;
	
	@SerializedName("token_type")
	private String tokenType = null;

	/**
	 * Authorization.
	 **/
	@ApiModelProperty(value = "Authorization.")
	public String getAccessToken() 
	{
		return accessToken;
	}
	public void setAccessToken(String accessToken) 
	{
		this.accessToken = accessToken;
	}

	/**
	 * Auth token expiry time.
	 **/
	@ApiModelProperty(value = "Auth token expiry time.")
	public String getExpiresIn() 
	{
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) 
	{
		this.expiresIn = expiresIn;
	}

	/**
	 * Refresh Token.
	 **/
	@ApiModelProperty(value = "Refresh Token.")
	public String getRefreshToken() 
	{
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) 
	{
		this.refreshToken = refreshToken;
	}

	/**
	 * Scope of Authorization.
	 **/
	@ApiModelProperty(value = "Scope of Authorization.")
	public String getScope() 
	{
		return scope;
	}
	public void setScope(String scope) 
	{
		this.scope = scope;
	}

	/**
	 * Type of Token.
	 **/
	@ApiModelProperty(value = "Type of Token.")
	public String getTokenType() 
	{
		return tokenType;
	}
	public void setTokenType(String tokenType) 
	{
		this.tokenType = tokenType;
	}

	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("class Token {\n");
		
		sb.append("accessToken: ").append(accessToken).append("\n");
		sb.append("expiresIn: ").append(expiresIn).append("\n");
		sb.append("refreshToken: ").append(refreshToken).append("\n");
		sb.append("scope: ").append(scope).append("\n");
		sb.append("tokenType: ").append(tokenType).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
