package com.auction.app.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
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
import org.springframework.web.client.RestClientException;

import com.auction.app.AuctionApplication;
import com.auction.app.model.Auction;
import com.auction.app.model.Bid;
import com.auction.app.model.Item;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuctionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles(profiles = "test")
@TestPropertySource(locations = "/application-test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuctionAppTest {

	@LocalServerPort
	private int port;

	private static TestRestTemplate restTemplate;

	private static HttpHeaders headers;

	private static String bidderName;
	
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
	public void aSaveAuctionTest() throws Exception {
		Auction auction = new Auction();
		auction.setReservePrice(11111.0);
		auction.setCurrentBid(1000.0);
		auction.setItem(new Item("ABC", "This is test record"));
		HttpEntity<Auction> entity = new HttpEntity<Auction>(auction, headers);
		String finalUrl = createUrlWithPort(this.port, "/auctionItems");
		ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.POST, entity, String.class);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());	

	}

	
	@Test
	public void bFindAllAuctionTest() throws Exception {

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

		Bid bid = new Bid();
		bid.setAuctionItemId(response.getBody().get(0).getAuctionItemId());
		bid.setBidderName("Test Bidder");
		bid.setMaxAutoBidAmount(12000.0);
		HttpEntity<Bid> bidEntity = new HttpEntity<Bid>(bid, headers);
		String bidFinalUrl = createUrlWithPort(this.port, "/bids");
		ResponseEntity<HttpStatus> bidResp = restTemplate.exchange(bidFinalUrl, HttpMethod.POST, bidEntity, HttpStatus.class);
		assertNotNull(bidResp);
		assertEquals(HttpStatus.OK, bidResp.getStatusCode());
		
		bidderName = bid.getBidderName(); 

	}

	
	@Test(expected = RestClientException.class)
	public void testSaveBidWithException() throws Exception {

		Bid bid = new Bid();
		bid.setAuctionItemId("123");
		bid.setBidderName("Test Bidder");
		bid.setMaxAutoBidAmount(12000.0);
		HttpEntity<Bid> bidEntity = new HttpEntity<Bid>(bid, headers);
		String bidFinalUrl = createUrlWithPort(this.port, "/bids");
		ResponseEntity<HttpStatus> bidResp = restTemplate.exchange(bidFinalUrl, HttpMethod.POST, bidEntity, HttpStatus.class);
		assertNotNull(bidResp);
		assertEquals(HttpStatus.NOT_FOUND, bidResp.getStatusCode());
	}

	
	@Test(expected = RestClientException.class)
	public void testSaveBidWithException1() throws Exception {

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String finalUrl = createUrlWithPort(this.port, "auctionItems");
		ResponseEntity<List<Auction>> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Auction>>() {});

		Bid bid = new Bid();
		bid.setAuctionItemId(response.getBody().get(0).getAuctionItemId());
		bid.setBidderName("Test Bidder for Exception");
		bid.setMaxAutoBidAmount(10.0);
		HttpEntity<Bid> bidEntity = new HttpEntity<Bid>(bid, headers);
		String bidFinalUrl = createUrlWithPort(this.port, "/bids");
		ResponseEntity<HttpStatus> bidResp = restTemplate.exchange(bidFinalUrl, HttpMethod.POST, bidEntity, HttpStatus.class);
		assertNotNull(bidResp);
		assertEquals(HttpStatus.BAD_REQUEST, bidResp.getStatusCode());

	}

	@Test(expected = RestClientException.class)
	public void testOutBidWithException1() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String finalUrl = createUrlWithPort(this.port, "/bids/"+bidderName);
		ResponseEntity<Long> deleteResp = restTemplate.exchange(finalUrl, HttpMethod.DELETE, entity, Long.class);
		assertNotNull(deleteResp);
		assertEquals(1,	deleteResp);
	}

	public String createUrlWithPort(int port, String resourceUrl) {
		String finalUrl = "http://localhost:" + port + resourceUrl;
		return finalUrl;
	}
}
