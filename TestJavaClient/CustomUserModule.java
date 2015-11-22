package TestJavaClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *  
 * A class to specify the trading algorithm.
 * simplifies user interaction instead of messing w/ controller
 *
 */
public class CustomUserModule {
	private Controller controller;
	private TimeKeeper timeKeeper;
		
	private PriorityQueue<Integer> queue;
        
        
        //private ArrayList<Holding> holdings; 
	// our unique list variables		
	private ArrayList<Holding> holdings = new ArrayList<Holding>();		
	private HashMap<String, Long> volumes = new HashMap<String, Long>();// based on a company's symbol, return the opening volume (able to constantly check current vs. opening for 'BXO')		
			
	// unique class variables		
	private double yesterdaysClose;// controls variable for determining yesterdaysClose		
	private double todaysOpen;// controls variable for determining todaysOpen		
	private boolean buying;// determines an action for buying		
	private boolean shortSelling;// determines an action for selling short
	
        
        
        
	/**
	 * The main algorithm that makes trades goes here.
	 * called from gateKeeper
	 */
	public void mainAlgorithm(Transaction transaction) {
		
		startAlgorithm();
		System.out.println("I am in mainAlgorithms");
                    		 		
		/**		
		 * based on a certain transaction type, cast our transaction to that specific type		
		 * and make a specified action based on type and options presented		
		 */		
				
		if (transaction.getTransactionType() == TransactionType.PLACE_ORDER) {// if a transaction places an order		
			PlaceOrderTransaction t = (PlaceOrderTransaction) transaction;// type cast our transaction to a place order		
					
			// Make holding object and get the volume		
			if (buying == true) {// if we are buying		
				if (t.m_action == "BUY") {// set the action to buy		
					Holding h = new Holding (t, volumes.get(t.m_symbol));// create a new holding object based on transaction and the hashMap's return of our symbol		
					holdings.add(h);// add it to our holding object		
				}		
			} else if (shortSelling == true) {// if we are selling		
				if (t.m_action == "SELL") {// set the action to sell		
					Holding h = new Holding (t, volumes.get(t.m_symbol));// create a new holding object based on transaction and the hashMap's return of our symbol		
					holdings.add(h);// add it to our holding object		
				}		
			}		
					
		} else if(transaction.getTransactionType() == TransactionType.HISTORICAL_DATA) {// if a transaction requests historical information		
			RequestHistoricalDataTransaction t = (RequestHistoricalDataTransaction) transaction;// type cast our transaction to a req hist data		
			
                        /*
                        if (t.m_symbol == "BXO") {// if the symbol is BXO		
				if (yesterdaysClose == 0.0 || timeKeeper.getCurrentTime() == "08:35" ||		
						timeKeeper.getCurrentTime() == "08:36" || timeKeeper.getCurrentTime() == "08:37") {// if yesterday's close is not initialized yet		
					yesterdaysClose = t.getClose();// set yesterday's close to the transaction's close		
				}		
				if (todaysOpen == 0.0) {		
					todaysOpen = t.open;		
				}		
			}*/
                        
                        /*
                                con.m_symbol = "EUR";
                                con.m_currency = "USD";
                                con.m_exchange = "IDEALPRO";
                                con.m_secType = "CASH";
                        */
                        
                        System.out.println("Main Alg. Yesterday's Close: " + yesterdaysClose);
                        System.out.println("Main Alg. Today's Open: " + todaysOpen);
                        
                        if (t.m_symbol == "EUR") {// if the symbol is IBM		
                            
				if (yesterdaysClose == 0.0 ) {// if yesterday's close is not initialized yet		
					yesterdaysClose = t.getClose();// set yesterday's close to the transaction's close
                                        System.out.println("Yesterday's Close: " + yesterdaysClose);
				}		
				if (todaysOpen == 0.0) {		
					todaysOpen = t.open;		
                                        System.out.println("Today's Open: " + todaysOpen);
				}		
			}
                        
		} else if(transaction.getTransactionType() == TransactionType.MARKET_DATA) {// if a transaction requests current market data		
			RequestMarketDataTransaction t = (RequestMarketDataTransaction) transaction;// type cast our transaction to a req market data	
                        System.out.println("A REQUEST MARKET DATA TRANSCATION");
                        System.out.println("TickerID: " + t.getTickerId() + "\tField: " + t.getField() + "\tPrice: " + t.getPrice() );
					
		} else if(transaction.getTransactionType() == TransactionType.MARKET_SCANNER) {// if a transaction scans the market for pertinent information		
			MarketScannerTransaction t = (MarketScannerTransaction) transaction;// type cast our transaction to a market scanner transaction		
			if (buying) {// if needing to buy on scanned results		
				String name = t.contractDetails.get(0).m_marketName;// string name is set to the contract's market name	  //the top one	
				/*
                                controller.reqRealTimeBars(name, SecurityType.STK, "CBOE", "CBOE", "USD", false, 0, BarRequestType.TRADES, true);// req real time bars for a specified stock		
				// place the order and buy 500 shares		
				controller.placeOrder(name, SecurityType.STK, "", 0.0, "", "", "CBOE", "CBOE", "USD", "", false, "", "", "BUY", 1, "MKT", 0.0, 0.0, "", "", false);		
                                */
                                
                                controller.reqRealTimeBars(name, SecurityType.CASH, "IDEALPRO", "IDEALPRO", "USD", false, 0, BarRequestType.TRADES, true);// req real time bars for a specified stock		
				// place the order and buy 500 shares		
				controller.placeOrder(name, SecurityType.CASH, "", 0.0, "", "", "IDEALPRO", "IDEALPRO", "USD", "", false, "", "", "BUY", 1, "MKT", 0.0, 0.0, "", "", false);	
                                
                        }		
			else if (shortSelling) {// if needing to sell on scanned results		
				String name = t.contractDetails.get(0).m_marketName;// string name is set to the contract's market name		
				
                                /*
                                controller.reqRealTimeBars(name, SecurityType.STK, "CBOE", "CBOE", "USD", false, 0, BarRequestType.TRADES, true);// req real time bars for a specified stock		
				// place the order and sell 500 shares		
				controller.placeOrder(name, SecurityType.STK, "", 0.0, "", "", "CBOE", "CBOE", "USD", "", false, "", "", "SELL", 2, "MKT", 0.0, 0.0, "", "", false);		
                                */
                                controller.reqRealTimeBars(name, SecurityType.CASH, "IDEALPRO", "IDEALPRO", "USD", false, 0, BarRequestType.TRADES, true);// req real time bars for a specified stock		
				// place the order and sell 500 shares		
				controller.placeOrder(name, SecurityType.CASH, "", 0.0, "", "", "IDEALPRO", "IDEALPRO", "USD", "", false, "", "", "SELL", 2, "MKT", 0.0, 0.0, "", "", false);		
 
                                
                        }		
		} else if (transaction.getTransactionType() == TransactionType.REAL_TIME_BARS){// if a transaction is requesting real time bars		
			RequestRealTimeBarsTransaction t = (RequestRealTimeBarsTransaction) transaction;// type cast our transaction to a req real time bars		
			String ticker = t.m_symbol;// create a String variable based on the transaction's symbol name		
			long volume = t.volume;// create a long variable based on the transaction's volume		
			volumes.put(ticker, volume);// add it into our hash map		
		}		
		endAlgorithm();
	}
	
	/**
	 * Run whenever a timer is called. Should be used to query data every so often.
	 * Has example timer functions set up here.
         * 
         * was called in TimerListener, which is set in setUpTimers()
         * 
         * 
	 */
	public void timerCalled(String source) {
                System.out.println("In timerCalled");
		if (source.equals("Check Volumes")) {// if we are checking volumes
			for (int i = 0; i <= holdings.size() - 1; i++) {// for our holding's list
				Holding h = holdings.get(i);// a holding object is the one specified at a certain index
				String symbol = h.transaction.m_symbol;// create a String variable to retrieve the symbol from that holding object
				String action = h.transaction.m_action;// create a String variable to retrieve the current action on our holding object
				//controller.reqRealTimeBars(symbol, SecurityType.STK, "CBOE", "CBOE", "USD", false, 0, null, true);// controller req real time bars for our company's symbol
				controller.reqRealTimeBars(symbol, SecurityType.CASH, "", "IDEALPRO", "USD", false, 0, null, true);// controller req real time bars for our company's symbol

                                
                                if (h.openingVolume == (volumes.get(symbol) * 2)) {// checking current volume to opening volume.  If 2x opening volume
                                    if (action.equals("BUY")) {// if our current action is a buy for our asset
						holdings.remove(h);// remove it from our list
						// place an order to sell that object
						//controller.placeOrder(symbol, SecurityType.STK, "", 0.0, "", "", "CBOE", "CBOE", "USD", "", false, "", "", "SELL", 3, "MKT", 0.0, 0.0, "", "", false);
                                                controller.placeOrder(symbol, SecurityType.CASH, "", 0.0, "", "", "IDEALPRO", "IDEALPRO", "USD", "", false, "", "", "SELL", 3, "MKT", 0.0, 0.0, "", "", false);
                                        }
					else if (action.equals("SELL")) {// if our current action is a sell for our asset
						holdings.remove(h);// remove it from our list
						// place an order to buy that object
						controller.placeOrder(symbol, SecurityType.CASH, "", 0.0, "", "", "IDEALPRO", "IDEALPRO", "USD", "", false, "", "", "BUY", 4, "MKT", 0.0, 0.0, "", "", false);
					}
				}
			}
		}
	}
	
	/**
	 * A space to set up timers, started whenever the user hits the start button.
	 * An example timer is set up already.
	 */
	public void setUpTimers() {
		timeKeeper.scheduleAction(60, "Check Volumes");// check volumes every 60 seconds || every minute || every hour??
	}
	
        /**
	 * @param time
	 * an action occurs based on a specific time.  allows us to run certain methods at specified time
         * was called in minute listener. 
	 */
	public void scheduledEvent(String time) {
		System.out.println(time);
		
                System.out.println("I am in schedule Event");
                //controller.reqHistData("BXO", SecurityType.IND, timeKeeper.getYesterdaysClose(), "1 D", "1 day", BarRequestType.TRADES, "CBOE", "CBOE", "USD", true, 1, false);
		
		if (time.equals("08:35") || time.equals("8:35") || time.equals("12:33")){//for the time @ 8:35 a.m.			
			//request historical data for S&P 500 retrieving yesterdaysClose
			//controller.reqHistData("BXO", SecurityType.IND, timeKeeper.getYesterdaysClose(), "1 D", "1 day", BarRequestType.TRADES, "CBOE", "CBOE", "USD", true, 1, false);
                        //controller.reqMktData("BAC", SecurityType.STK,"SMART","","USD", false, true, ""); // added by Yun
                
                }
		
          
		if (time.equals("9:00") || time.equals("09:00") || time.equals("12:34")) {//for the time @ 9:00 a.m.
			//request Market Data for the S&P 500 (BXO)			
			//controller.reqMktData("BXO", SecurityType.IND,"CBOE","CBOE","USD", false, true, "");
                        
                        //controller.reqHistData("BAC", SecurityType.STK, timeKeeper.getYesterdaysClose(), "1 D", "1 day", BarRequestType.TRADES, "SMART", "", "USD", true, 1, false);
                        //controller.reqMktData("BAC", SecurityType.STK,"SMART","","USD", false, true, ""); // added by Yun
			//System.out.println("After calling BAC"); //added by Yun
                        //controller.placeOrder("APPL", SecurityType.STK, "", 0.0, "", "", "SMART", "CBOE", "USD", "", false, "", "", "SELL", 50, "MKT", 0.0, 0.0, "", "", false); //added by Yun
                        //controller.placeOrder("BXO", SecurityType.IND, "", 0.0, "", "", "SMART", "CBOE", "USD", "", false, "", "", "SELL", 50, "MKT", 0.0, 0.0, "", "", false); //added by Yun
                        //controller.placeOrder("BAC", SecurityType.STK, "", 0.0, "", "", "SMART", "", "USD", "", false, "", "", "SELL", 5, "MKT", 0.0, 0.0, "", "", false); //added by Yun
                        controller.reqHistData("EUR", SecurityType.CASH, timeKeeper.getYesterdaysClose(), "1 D", "1 day", BarRequestType.TRADES, "SMART", "", "USD", true, 1, false);
                        controller.reqMktData("EUR", SecurityType.CASH,"IDEALPRO","","USD", false, false, ""); // added by Yun
                        controller.placeOrder("EUR", SecurityType.CASH, "", 0.0, "", "", "IDEALPRO", "", "USD", "", false, "", "", "SELL", 5, "MKT", 0.0, 0.0, "", "", false); //added by Yun

                    
                    
                        // Buy
                        
                        System.out.println("Yesterday's Close: " + yesterdaysClose);
                        System.out.println("today's Open: " + todaysOpen);
                        if (yesterdaysClose < todaysOpen){// if the close is lower than open
				buying = true;// set buying to true
				// market scan the TOP % Gainers (1)
				//controller.reqScannerSubscription(10, SecurityType.CASH, "STK.US.MAJOR", "TOP_PERC_GAIN", 0.0, 5000.0, 0, 0, 0.0, 1000000000.0, "", "", "", "", "", "", 0.0, 0.0, "", "Annual,true", "ALL");
			}
			else{//current price is higher than the close
				shortSelling = true;// set selling to true
				// market scan the TOP % Losers (1)
				//controller.reqScannerSubscription(10, SecurityType.CASH, "STK.US.MAJOR", "TOP_PERC_LOSE", 0.0, 5000.0, 0, 0, 0.0, 1000000000.0, "", "", "", "", "", "", 0.0, 0.0, "", "Annual,true", "ALL");
			}
		}
		
		if (time.equals("14:30")) {// if the time equals 2:30 (close to market closing time)
			if (buying = true) {// for all those bought
				sellAll();// sell all
				System.out.println("Selling all holdings.");// inform us of our action to sell all holdings
			}
			else if (shortSelling = true) {// for all those sold
				buyAll();// buy all
				System.out.println("Buying all shorted stocks.");// inform us of our action to buy all holdings
			}
		}
		if (time.equals("15:01")) {// at a minute after close (anytime after the market closes)
			// Clear all temporary data in our lists so we can reset them in the morning
			holdings.clear();
			volumes.clear();
			
			// Reset variables
			yesterdaysClose = 0.0;
			todaysOpen = 0.0;
			buying = false;
			shortSelling = false;
		}           
	}

	/**
	 * Run when the user hits the start button.
	 */
	public void start() {
		
	}
	
	/**
	 * Run when the user hits the stop button.
	 */
	public void stop() {

	}
	
	/**
	 *told to sell in regards to previous actions from above 
	 */
	public void sellAll() {
		for (int i = 0; i <= holdings.size() - 1; i++) {// for all elements in our holdings list
			String symbol = holdings.get(i).transaction.m_symbol;// create a string variable based on our list's index retrieval specifying the company symbol
			holdings.remove(i);// remove it from our list
			// sell 500 shares of the stock
			controller.placeOrder(symbol, SecurityType.STK, "", 0.0, "", "", "CBOE", "CBOE", "USD", "", false, "", "", "SELL", 5, "MKT", 0.0, 0.0, "", "", false);
		}
	}
	
	/**
	 * told to buy in regards to previous actions from above
	 */
	public void buyAll() {
		for (int i = 0; i <= holdings.size() - 1; i++) {// for all elements in our holdings list
			String symbol = holdings.get(i).transaction.m_symbol;// create a string variable based on our list's index retrieval specifying the company symbol
			holdings.remove(i);// remove it from our list
			// buy 500 shares of the stock
			controller.placeOrder(symbol, SecurityType.STK, "", 0.0, "", "", "CBOE", "CBOE", "USD", "", false, "", "", "BUY", 5, "MKT", 0.0, 0.0, "", "", false);
		}
	}
	
	/**
	 * a holding class is a inner class variable which will allow us to update and retrieve certain values which need to be constantly check
	 * @author lkjaero, jhartless
	 *
	 */
	private class Holding{
		//composed of a transaction object and a long openingVolume
		public PlaceOrderTransaction transaction;
		public long openingVolume;
		
		//upon initializing creating a holding object, set the transaction, the openVolume, and the closeVolume
		private Holding (PlaceOrderTransaction t, long volume){
			transaction = t;
			openingVolume = volume;
		}
	}
	
	/**
	 * ==========================================================================================
	 * Implementation details. Don't edit anything below here unless you know what you're doing.
	 * ==========================================================================================
	 */
	public CustomUserModule(Controller c) {
		controller = c;
		timeKeeper = new TimeKeeper(this);
		queue = new PriorityQueue<Integer>();
                		
	}
	
	public void stopTimers() {
		timeKeeper.stopTimers();
	}
	
	public void startButtonPressed() {
		this.start();
		this.setUpTimers();
	}
	
	public void marketDataEnd(int id) {
		queue.add(new Integer(id));
	}
	
	public void scannerDataEnd(int id) {
		queue.add(new Integer(id));
	}
	
	public void historicalDataEnd(int id) {
		queue.add(new Integer(id));
	}
	
	public void realTimeBarsEnd(int id) {
		queue.add(new Integer(id));
	}
	
	public void placeOrderEnd(int id) {
		queue.add(new Integer(id));
	}
	
	boolean inUse;
	
	public void gateKeeper() {
                System.out.println("\nGateKeeper Beginning, inUse:" + inUse);
		if(!inUse) {
			Transaction t = controller.getMarketData().getTransactionMap().get(queue.poll());
                        System.out.println("Transaction: " + t);
			if (t != null) {
				if (t.getTransactionType() == TransactionType.MARKET_SCANNER) {
					MarketScannerTransaction mst = (MarketScannerTransaction) t;
					if (mst.isFinished() != true) {
                                                System.out.println("RETURNNING FROM GATEKEEPER");
						return;
					}
				}
                                
				mainAlgorithm(t);
                         
			}
                        
		}
	}
	
	private void startAlgorithm() {
		inUse = true;
	}
	
	private void endAlgorithm() {
		inUse = false;
	}
}
