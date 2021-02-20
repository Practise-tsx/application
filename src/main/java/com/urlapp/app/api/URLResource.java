package com.urlapp.app.api;

import com.urlapp.app.api.dto.URLShortenerDTO;

public interface URLResource {
	URLShortenerDTO saveURL(String originalURL);
	URLShortenerDTO getURL(String shortenedURL);
}

