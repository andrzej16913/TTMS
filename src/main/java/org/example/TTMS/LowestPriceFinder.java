package org.example.TTMS;

import org.example.TTMS.entities.Price;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("LowestPriceFinder")
public class LowestPriceFinder {
    public Price find(List<Price> prices) {
        if (prices.isEmpty()) {
            return new Price();
        }
        Price lowestPrice = prices.get(0);

        for (Price price: prices) {
            if (price.getAmount().compareTo(lowestPrice.getAmount()) < 0) {
                lowestPrice = price;
            }
        }

        return lowestPrice;
    }
}
