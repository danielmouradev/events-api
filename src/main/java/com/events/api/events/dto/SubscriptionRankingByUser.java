package com.events.api.events.dto;

import com.events.api.events.model.Subscription;

public record SubscriptionRankingByUser(SubscriptionRankingItem item, Integer position) {
}
