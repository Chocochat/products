package com.demo.service.impl;

import com.demo.exception.ErrorMessages;
import com.demo.exception.UserServiceExceptions;
import com.demo.io.entity.Deal;
import com.demo.io.entity.Discount;
import com.demo.io.entity.Product;
import com.demo.io.models.requests.PriceRequest;
import com.demo.io.models.responses.PriceResponse;
import com.demo.service.PriceCalculator;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PriceCalculatorImpl implements PriceCalculator {
    private static final DecimalFormat df = new DecimalFormat("#.##");
    @Override
    public PriceResponse calculate(List<Product> products, PriceRequest priceRequest, Discount discount, Deal deal) {

        //  convert ads to hashmap
        Map<String, Double> productMap = new HashMap();
        for (Product product : products) {
            productMap.put(product.getName(), product.getPrice());
        }

        //get count of items
        Map<String, Long> itemsCounts =
                priceRequest.getItems().stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        double price = 0;

        /*if (!priceRequest.getCustomer().equalsIgnoreCase("defaultasd") && discount == null && deal == null)
            throw new UserServiceExceptions(ErrorMessages.DATA_ERROR.getErrorMessage());
        else {*/
            for (Map.Entry<String, Long> entry : itemsCounts.entrySet()) {
                String key = entry.getKey();
                double value = entry.getValue();
                if (value != 0) {
                    switch (key) {
                        case "classic":
                            if (deal != null && !deal.getClassic().equals("1:1") && discount != null && discount.getClassic() != 0)
                                //conflict offer
                                throw new UserServiceExceptions(ErrorMessages.CONFLICT_OFFERS.getErrorMessage());
                            else if (deal != null && !deal.getClassic().equals("1:1")){
                                price = price + customDeal(deal.getClassic(), productMap.get(key), value);
                            } else if (discount != null && discount.getClassic() != 0) {
                                price = price + ((discount.getClassic() !=0 ? discount.getClassic() : productMap.get(key)) * value);
                            } else {
                                //switch to default offer
                                price = price + productMap.get(key) * value;
                            }
                            break;
                        case "std":
                            if (deal != null && !deal.getStd().equals("1:1") && discount != null && discount.getStd() != 0)
                                //conflict offer
                                throw new UserServiceExceptions(ErrorMessages.CONFLICT_OFFERS.getErrorMessage());
                            else if (deal != null && !deal.getStd().equals("1:1"))
                                price = price + customDeal(deal.getStd(), productMap.get(key), value);
                            else if (discount != null && discount.getStd() != 0)
                                price = price + ((discount.getStd() !=0 ? discount.getStd() : productMap.get(key)) * value);
                            else
                                //switch to default offer
                                price = price + productMap.get(key) * value;
                            break;
                        case "premium":
                            if (deal != null && !deal.getPremium().equals("1:1") && discount != null && discount.getPremium() != 0)
                                throw new UserServiceExceptions(ErrorMessages.CONFLICT_OFFERS.getErrorMessage());
                            else if (deal != null && !deal.getPremium().equals("1:1"))
                                price = price + customDeal(deal.getPremium(), productMap.get(key), value);
                            else if (discount != null && discount.getPremium() != 0)
                                price = price + ((discount.getPremium() !=0 ? discount.getPremium(): productMap.get(key)) * value);
                            else
                                //switch to default offer
                                price = price + productMap.get(key) * value;
                            break;
                    }
                }
            }
        /*}*/
        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setCustomer(priceRequest.getCustomer());
        priceResponse.setItems(priceRequest.getItems());
        priceResponse.setTotal(Double.valueOf(df.format(price)));
        return priceResponse;
    }

    private double customDeal(String std, Double price, double count) {
        //3 for 2
        String[] split = std.split(":");
        double first = Double.parseDouble(split[0]);
        double second = Double.parseDouble(split[1]);
        if(count <= first)
            return price * second;
        else {
            double delta = count % first;
            double deltaPrice = delta * price;
            double dealPrice = ((price * second)/first) * (count - delta);
            return deltaPrice + dealPrice;
        }
    }
}

