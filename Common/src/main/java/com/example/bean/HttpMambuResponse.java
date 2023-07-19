package com.example.bean;

import lombok.Builder;
import lombok.Data;
import okhttp3.Headers;

@Data
@Builder
public class HttpMambuResponse<B> {
	private B body;
	private Headers header;
}

