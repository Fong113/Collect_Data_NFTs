package test;

import marketplace.IMarketplace;
import marketplace.crawl.MarketplaceType;
import marketplace.crawl.binance.BinanceChainType;
import marketplace.crawl.binance.BinancePeriodType;
import marketplace.crawl.opensea.OpenseaChainType;
import marketplace.crawl.opensea.OpenseaPeriodType;
import marketplace.handle.Collection;
import marketplace.handle.Handler;
import marketplace.handle.Trending;

public class Test {
	public static void main(String[] args) {
		IMarketplace m = new Handler();
		Trending trend =  m.getTrending(MarketplaceType.OPENSEA, OpenseaChainType.ETH, OpenseaPeriodType.ONEDAY, 100);
		int num = 0;
		for(Collection c : trend.getData()) {
			num++;
			System.out.println(c.toString());
		}
		System.out.println(num);
	}
}
