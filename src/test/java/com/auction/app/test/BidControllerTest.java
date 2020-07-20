package com.auction.app.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.auction.app.AuctionApplication;
import com.auction.app.model.Auction;
import com.auction.app.model.Bid;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuctionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles(profiles = "test")
@TestPropertySource(locations = "/application-test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BidControllerTest {

	@LocalServerPort
	private int port;
	
	private static TestRestTemplate restTemplate;

	private static HttpHeaders headers;
	
	@BeforeClass
	public static void setup() throws Exception {
		restTemplate = new TestRestTemplate();
	}

	@Before
	public void setUp() throws Exception {

		headers = new HttpHeaders();
		headers.setContentType(APPLICATION_JSON);
		headers.setAccept(Arrays.asList(APPLICATION_JSON));

	}
	
	@Test
	public void aSaveAuctionTest() throws JsonProcessingException {
		
		HttpEntity<String> entity1 = new HttpEntity<String>(headers);
		String finalUrl = createUrlWithPort(this.port, "auctionItems");
		ResponseEntity<List<Auction>> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity1, new ParameterizedTypeReference<List<Auction>>() {});

		Bid bid = new Bid();
		bid.setAuctionItemId(response.getBody().get(0).getAuctionItemId());
		HttpEntity<Bid> entity = new HttpEntity<Bid>(bid, headers);
		finalUrl = createUrlWithPort(this.port, "/bids");
		ResponseEntity<String> response1 = restTemplate.exchange(finalUrl, HttpMethod.POST, entity, String.class);
		assertNotNull(response1);
		assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
		assertNotNull(response1.getBody());	
		
	}
	
	@Test
	public void bfindAllAuction() throws JsonProcessingException {

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String finalUrl = createUrlWithPort(this.port, "auctionItems");
		ResponseEntity<List<Auction>> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Auction>>() {});
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		
		finalUrl = createUrlWithPort(this.port, "auctionItems/"+response.getBody().get(0).getAuctionItemId());
		ResponseEntity<Auction> response1 = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, Auction.class);
		assertNotNull(response1);
		assertEquals(HttpStatus.OK, response1.getStatusCode());
		assertNotNull(response1.getBody());
	}
	

	public String createUrlWithPort(int port, String resourceUrl) {
		String finalUrl = "http://localhost:" + port + resourceUrl;
		return finalUrl;
	}
}
