package com.thakur.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageResponse {
		private List<PostDto>  content;
		private int pageNumber;
		private int pageSize;
		private long totalElements;
		private int totalPages;
		private boolean isLast;
}
