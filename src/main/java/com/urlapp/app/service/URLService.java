package com.urlapp.app.service;

import com.urlapp.app.api.dto.URLShortenerDTO;

public interface URLService {
	URLShortenerDTO saveUrl(String originalURL);
	URLShortenerDTO getURL(String shortenedURL);
}
