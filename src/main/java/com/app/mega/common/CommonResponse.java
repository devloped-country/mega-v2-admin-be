package com.app.mega.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
//@ApiModel(value = "기본 응답")
public class CommonResponse<T> {
  //    @ApiModelProperty(position = 1, value = "응답 코드", example = "RESPONSE_CODE")
  private int responseCode;
  //    @ApiModelProperty(position = 2, value = "응답 메세지", example = "응답 메세지")
  private String responseMessage;
  //    @ApiModelProperty(position = 3, value = "응답 데이터", example = "응답 데이터")
  private T data;

  public CommonResponse(final int responseCode, final String responseMessage){
    this.responseCode = responseCode;
    this.responseMessage = responseMessage;
    this.data = null;
  }

  public static <T> CommonResponse<T> response(final int responseCode, final String responseMessage){
    return response(responseCode, responseMessage, null);
  }

  public static <T> CommonResponse<T> response(final int responseCode, final String responseMessage, final T data){
    return CommonResponse.<T>builder()
        .responseCode(responseCode)
        .responseMessage(responseMessage)
        .data(data)
        .build();
  }
}