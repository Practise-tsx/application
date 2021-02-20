package com.urlapp.app.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.urlapp.app.api.dto.URLShortenerDTO;
import com.urlapp.app.logic.URLShortener;
import com.urlapp.app.repo.URLRepository;
import com.urlapp.app.utils.Base62;
//Implementing Business logic here
//Here we save original URL to database and return the shorterned URL

@Service
public class URLServiceImpl implements URLService {
	
	@Autowired
	private URLRepository urlRepository;
	
	private final String domain;
	
	@Autowired
	public URLServiceImpl(@Value("${domain.shortener}") String domain) {
		this.domain = domain;
	}
	/**
	 * Reverse the original URL from the shortened URL
	 */
	public URLShortenerDTO getURL(String shortenURL) {
		URLShortenerDTO dto = new URLShortenerDTO();
		if (validateURL(shortenURL)) {
			//Remove domain to short URL if possible.
			String str = shortenURL.replace(this.domain +"/", "");
			
			// Resolve a short URL to the initial ID.
			long id = Base62.toBase10(str);
			// locate the record with ID in the DB
			Optional<URLShortener> urlShortener = urlRepository.findById(id);
			
			if(urlShortener.isPresent()) {
				// Map domain to DTO
				URLShortener url = urlShortener.get();
				dto.setId(url.getId().toString());
				dto.setShortenedURL(shortenURL);
				dto.setOriginalURL(url.getOriginalURL());
				dto.setCreatedOn(url.getCreatedOn().toString());
			} 
		}
		return dto;
	}
	
	/**
	 * Save an original URL to database and then
	 * generate a shortened URL.
	 */
	public URLShortenerDTO saveUrl(String originalURL) {
		URLShortener url = new URLShortener();
		if (validateURL(originalURL)) {
			originalURL = sanitizeURL(originalURL);
			// check if Original URL exsist in the DB
			Optional<URLShortener> exitURL = urlRepository.findByOriginalURL(originalURL);
		
			if(exitURL.isPresent()) {
				// Retrieved from the system.
				url = exitURL.get();
			} else {
				// orelse, save a new original URL
				url.setId(urlRepository.getIdWithNextUniqueId());
				url.setOriginalURL(originalURL);
				url = urlRepository.save(url);
			}
		}
		return generateURLShorterner(url);
	}
	/**
	 * Generate a shortened URL.
	 */
	private URLShortenerDTO generateURLShorterner(URLShortener url) {
		// Mapped domain to DTO
		URLShortenerDTO dto = new URLShortenerDTO();
		dto.setId(url.getId().toString());
		dto.setOriginalURL(url.getOriginalURL());
		dto.setCreatedOn(url.getCreatedOn().toString());
		
		// Generate shortenedURL via base62 encode.
		String shortenedURL = this.domain +"/" + Base62.toBase62(url.getId().intValue());
		dto.setShortenedURL(shortenedURL);
		return dto;
	}
	
	/**
	 * Validate URL not implemented always return true for this stage of implementation
	 */
	private boolean validateURL(String url) {
		return true;
	}
	
	/** 
	 * This method should take care various issues with a valid url
	 */
	private String sanitizeURL(String url) {
		if (url.substring(0, 7).equals("http://"))
			url = url.substring(7);

		if (url.substring(0, 8).equals("https://"))
			url = url.substring(8);

		if (url.charAt(url.length() - 1) == '/')
			url = url.substring(0, url.length() - 1);
		return url;
	}

}
