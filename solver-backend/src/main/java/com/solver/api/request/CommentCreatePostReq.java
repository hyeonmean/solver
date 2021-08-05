package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CommentCreatePostReq")
public class CommentCreatePostReq {
	@ApiModelProperty(name="content", example="test Content")
	private String content;
}
